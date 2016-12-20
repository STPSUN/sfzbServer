package com.idg.bfzb.server.pay.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.idg.bfzb.server.authentication.dao.AuthenticationDao;
import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.ArrayResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.pay.alipay.constants.AlipayConfig;
import com.idg.bfzb.server.pay.alipay.constants.AlipayServiceConstants;
import com.idg.bfzb.server.pay.alipay.factory.AlipayAPIClientFactory;
import com.idg.bfzb.server.pay.alipay.util.RSA;
import com.idg.bfzb.server.pay.dao.FinanceOfflineTradeRepository;
import com.idg.bfzb.server.pay.dao.FinanceTradeDetailRepository;
import com.idg.bfzb.server.pay.dao.FinanceWithdrawApplyRepository;
import com.idg.bfzb.server.pay.model.dto.FinanceOfflineTradeEntity;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.pay.model.dto.FinanceWithdrawApplyEntity;
import com.idg.bfzb.server.pay.model.request.FinanceWithdrawApplyRequest;
import com.idg.bfzb.server.pay.model.response.FinanceTradeDetailResponse;
import com.idg.bfzb.server.pay.service.PayService;
import com.idg.bfzb.server.pay.unionpay.acp.sdk.AcpService;
import com.idg.bfzb.server.pay.unionpay.acp.sdk.LogUtil;
import com.idg.bfzb.server.pay.unionpay.acp.sdk.SDKConfig;
import com.idg.bfzb.server.pay.wechatpay.dto.Unifiedorder;
import com.idg.bfzb.server.pay.wechatpay.dto.UnifiedorderResult;
import com.idg.bfzb.server.pay.wechatpay.util.HttpXmlUtils;
import com.idg.bfzb.server.pay.wechatpay.util.JdomParseXmlUtils;
import com.idg.bfzb.server.pay.wechatpay.util.RandCharsUtils;
import com.idg.bfzb.server.pay.wechatpay.util.WXSignUtils;
import com.idg.bfzb.server.pay.wechatpay.util.WeixinConfigUtils;
import com.idg.bfzb.server.usercenter.dao.UserInfoRepository;
import com.idg.bfzb.server.usercenter.dao.UserPersonalRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.usercenter.model.vo.UserBaseVo;
import com.idg.bfzb.server.usercenter.service.ShortMessageService;
import com.idg.bfzb.server.utility.encrypt.MD5Util;
import com.idg.bfzb.server.utility.tools.DateUtil;
import com.idg.bfzb.server.utility.tools.StringUtil;

@Service
public class PayServiceImp implements PayService {
	private  static Logger log = Logger.getLogger(PayServiceImp.class);
	@Autowired
    private UserInfoRepository userInfoRepository;
	@Autowired
	private FinanceWithdrawApplyRepository financeWithdrawApplyRepository;
	@Autowired
	private FinanceTradeDetailRepository financeTradeDetailRepository;
	@Autowired
	private AuthenticationDao authenticationRepository;
	@Autowired
    private ShortMessageService shortMessageService;
	@Autowired
	private UserPersonalRepository userPersonalRepository;
	@Autowired
	private FinanceOfflineTradeRepository financeOfficeTradeRepository;
	
    public static final Integer SUCCESS = 1;
    public static final Integer FAIL = 0;

    /**
     * 支付回调
     */
    @Override
    public boolean callBackAliPay(Map<String, String> map) {
        boolean isOk = true;
        short tradeState = 0;
        FinanceTradeDetailEntity ftdEntity = new FinanceTradeDetailEntity();
        String orderSerial  = map.get("out_trade_no");//SDK生成的订单号
        String payOrderSerial = map.get("trade_no");//支付宝生成流水
        BigDecimal totalFee = null;
        if(!StringUtil.isNull(map.get("total_amount"))){
        	totalFee = new BigDecimal(map.get("total_amount"));//交易金额
        }else if(!StringUtil.isNull(map.get("total_fee"))){
        	totalFee = new BigDecimal(map.get("total_fee"));//交易金额
        }else{
        	return false;
        }
        String tradeStatus = map.get("trade_status");//支付状态描述
        //buyer_logon_id 买家支付宝账号
        try{
	        //根据订单号获取用户ID
	        ftdEntity = this.financeTradeDetailRepository.findOneByTradeOrder(orderSerial);
	        log.info("充值用户："+ftdEntity.getIncomeUserId()+":"+ftdEntity.getPayUserName());
	        //如果第三方发送的请求不是交易成功，则返回
	        if("TRADE_SUCCESS".equals(tradeStatus)){
	        	tradeState=Constants.TRADE_STATE_PAID_SUCCESS;//打款成功
	            double doubleTotalFee = 0;
                doubleTotalFee = totalFee.doubleValue();
                //加入余额
                this.authenticationRepository.updatePersonBalance(ftdEntity.getIncomeUserId(), doubleTotalFee);
                //插入充值成功
                log.info("插入充值成功");
	        }else if("TRADE_CLOSED".equals(tradeStatus)){
	        	isOk = false;
	            tradeState=Constants.TRADE_STATE_PAID_FAIL;//打款失败
	        }
	        log.info("充值状态：========="+tradeState);
	        //更新交易状态
	        this.financeTradeDetailRepository.updateTradeStateById(ftdEntity.getRecordId(),tradeState,payOrderSerial);
        }catch (Exception e){
        	log.info("异常：========="+e.getMessage());
        	e.printStackTrace();
        	isOk = false;
        }
        return isOk;
    }
    
	/**
	 * 支付宝支付
	 * @param orderId 订单编号
	 * @param actualPay 实际支付金额
	 * @param termType 终端类型
	 * @return
	 */
    @Override
	public APIResponse getOrderInfoByAliPay(String orderId,Double actualPay, String termType) {
    	APIResponse apiResponse = new APIResponse();
       if(Constants.TERMTYPE_PC.equals(termType)){
    	    AlipayTradePrecreateResponse response = qrPay(orderId,String.valueOf(actualPay),"闪蝠众包-二维码余额充值");
			// 开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				System.out.println(response.getBody());
				System.out.println(response.isSuccess());
				System.out.println(response.getMsg());
				if (response.getCode().equals("10000")) {
					System.out.println("商户订单号："+response.getOutTradeNo());
					System.out.println("二维码值："+response.getQrCode());//商户将此二维码值生成二维码，然后展示给用户，用户用支付宝手机钱包扫码完成支付
					apiResponse.setData(response);
					apiResponse.setSucess(true);
				} else {
				//打印错误码
				System.out.println("错误码："+response.getSubCode());
				System.out.println("错误描述："+response.getSubMsg());
				apiResponse.setMessage(response.getSubMsg());
				apiResponse.setSucess(false);
				}
			}else{
				apiResponse.setErrorCode(ErrorCode.ALI_PAY_HTTP_FAIL);
				apiResponse.setSucess(false);
			}
    	   
       }else{
    	   String[] parameters={
    	             "service=\""+AlipayServiceConstants.ALIPAY_SECURITYPAY_PAY+"\"",//固定值（手机快捷支付）
    	             "partner=\""+AlipayConfig.PARTNER+"\"",//合作身份者ID（16位）
    	             "app_id=\""+AlipayConfig.APP_ID+"\"",//APPID
    	             "_input_charset=\""+AlipayConfig.CHARSET+"\"",
    	             "notify_url=\""+AlipayConfig.ALI_CALL_BACK_URL+"\"",//通知地址
    	             "out_trade_no=\""+orderId+"\"",//商户内部订单号
    	             "subject=\"闪蝠众包-余额充值\"",//测试
    	             "payment_type=\"1\"",//固定值
    	             "seller_id=\""+AlipayConfig.SELLER_ID+"\"",//账户邮箱
    	            "total_fee=\""+actualPay+"\"",//支付金额（元）
    	             "body=\"订单说明\"",//订单说明
    	             "it_b_pay=\"30m\""//（订单过期时间 30分钟过期无效）
    	       };
    	   String signOrderUrl = signAllString(parameters);
    	   apiResponse.setData(signOrderUrl);
    	   apiResponse.setSucess(true);
       }
       return apiResponse;
    }
    
    /**
	 * 支付宝扫码下单支付
	 * @param out_trade_no
	 * @param auth_code
	 * @author jinlong.rhj
	 * @date 2015年4月28日
	 * @version 1.0
	 * @return 
	 */
	public AlipayTradePrecreateResponse qrPay(String out_trade_no,String total_amount,String subject) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time_expire= sdf.format(System.currentTimeMillis()+24*60*60*1000);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"out_trade_no\":\"" + out_trade_no + "\",");
		sb.append("\"total_amount\":\""+total_amount+"\",\"discountable_amount\":\"0.00\",");
		sb.append("\"subject\":\""+subject+"\",\"body\":\"test\",");
		sb.append("\"operator_id\":\"op001\",\"store_id\":\"store001\",\"terminal_id\":\"t_001\",");
		sb.append("\"time_expire\":\""+time_expire+"\"}");
		System.out.println(sb.toString());
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		// 使用SDK，构建群发请求模型
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setBizContent(sb.toString());
		request.setNotifyUrl(AlipayConfig.ALI_CALL_BACK_URL);
		//request.setReturnUrl(AlipayConfig.ALI_CALL_BACK_URL);
		AlipayTradePrecreateResponse response = null;
		try {
			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return response;
	}

    
	/**
	 * 支付宝签名
	 * 
	 * @param array
	 * @return
	 */
	public String signAllString(String[] array) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < array.length; i++) {
			if (i == (array.length - 1)) {
				sb.append(array[i]);
			} else {
				sb.append(array[i] + "&");
			}
		}
		System.out.println(sb.toString());
		String sign = "";
		try {
			sign = URLEncoder.encode(
					RSA.sign(sb.toString(), AlipayConfig.PRIVATE_KEY, "utf-8"),
					"utf-8");// private_key私钥
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append("&sign=\"" + sign + "\"&");
		sb.append("sign_type=\"RSA\"");
		return sb.toString();
	}
	/**
	 * 交易类型+时间戳
	 */
	public String createOrderId(String orderType){
		return orderType+new Date().getTime();
	}

	@Override
	public APIResponse withdraw(FinanceWithdrawApplyRequest fwaRequest) {
		APIResponse apiResponse = new APIResponse();
		//1.账号密码验证
		UcUserInfoEntity userInfoEntity = this.userInfoRepository.findByNameOrMobile(null,fwaRequest.getMobile());
        if (userInfoEntity==null){
            apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
            log.info(ErrorCode.UC_USER_NOT_EXIST.getMsg()+
                    String.format("[user_name:%1$s,mobile%2$s]",fwaRequest.getMobile(),fwaRequest.getMobile()));
            return apiResponse;
        }

        String encryptPwd = MD5Util.MD5(fwaRequest.getPassword());
        if (!StringUtils.equals(encryptPwd,userInfoEntity.getPassword())){
            apiResponse.setErrorCode(ErrorCode.UC_MOBILE_LOGIN_FAIL);
            log.info(ErrorCode.UC_MOBILE_LOGIN_FAIL.getMsg());
            return apiResponse;
        }
		//2.余额验证
		UserPersonalEntity userPerson = authenticationRepository.queryUserAuthenticationInfo(fwaRequest.getApplyUserId());
		if(userPerson!=null){
			if(userPerson.getBalance()==null){
				userPerson.setBalance(new BigDecimal(0));
			}
			if(fwaRequest.getApplyMoney()>userPerson.getBalance().doubleValue()){
				apiResponse.setErrorCode(ErrorCode.BALANCE_NOT_ENOUGH);
				return apiResponse;
			}
		}else{
			apiResponse.setErrorCode(ErrorCode.USER_NOT_REAL);
			return apiResponse;
		}
		//3.验证码校验
        APIResponse checkResponse = this.shortMessageService.checkVerificationCode(fwaRequest.getMobile(),fwaRequest.getAuthCode());
        if ( !checkResponse.getCode().equals(APIResponse.SUCESS_MSG) ){
            apiResponse.setErrorCode(ErrorCode.UC_PIN_NOT_CORRECT);
            return apiResponse;
        }
        FinanceWithdrawApplyEntity fwaEntity = new FinanceWithdrawApplyEntity();
        fwaEntity.setApplyBank(fwaRequest.getApplyBank());
        fwaEntity.setApplyCode(fwaRequest.getApplyCode());
        fwaEntity.setApplyCodeName(fwaRequest.getApplyCodeName());
        fwaEntity.setApplyType(fwaRequest.getApplyType());
        fwaEntity.setReviewReason(fwaRequest.getReviewReason());
        fwaEntity.setApplyMoney(fwaRequest.getApplyMoney());
        fwaEntity.setApplyUserId(fwaRequest.getApplyUserId());
        fwaEntity.setReviewState(Constants.REVIEW_WATTING);
        
        FinanceTradeDetailEntity ftdEntity = new FinanceTradeDetailEntity();
        ftdEntity.setPayUserId(fwaRequest.getApplyUserId());
        ftdEntity.setPayUserName(userInfoEntity.getUserName());
        ftdEntity.setIncomeUserId(fwaRequest.getApplyUserId());
        ftdEntity.setIncomeUserName(userInfoEntity.getUserName());
        ftdEntity.setMoney(fwaRequest.getApplyMoney());
        ftdEntity.setTransType(Constants.TRANS_TYPE_WITHDRAW);//提现
        ftdEntity.setTradePlatform(fwaRequest.getApplyType());//
        ftdEntity.setTradeState((short)0);
        ftdEntity.setOutTradeNo(createOrderId(Constants.ORDER_TYPE_WITHDRAW));//生成商户订单号
        
        //插入提现申请
        try {
        	//插入资金流水
        	FinanceTradeDetailEntity rsEntity = this.financeTradeDetailRepository.save(ftdEntity);
        	fwaEntity.setRecordId(rsEntity.getRecordId());
        	//资金流水表ID同步到提现申请表
        	this.financeWithdrawApplyRepository.save(fwaEntity);
        	//从余额中扣除提现金额
        	this.authenticationRepository.updatePersonBalance(fwaRequest.getApplyUserId(), -fwaRequest.getApplyMoney());
        } catch (Exception e) {
        	e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.WITHDRAW_APPLY_FAIL);
			return apiResponse;
		}
        apiResponse.setMessage(APIResponse.SUCESS_MSG);
		return apiResponse;
	}

	@Override
	public APIResponse financialDetail(FinanceTradeDetailEntity entity,Pageable pageable) {
		APIResponse apiResponse = new APIResponse();
		ArrayResponse<FinanceTradeDetailResponse> dataArr= new ArrayResponse<FinanceTradeDetailResponse>();
		PageInfo<FinanceTradeDetailResponse> pageInfo = this.financeTradeDetailRepository.findAllFinanceDetailByCond(
				entity, pageable);
		dataArr.setTotal((long)pageInfo.getTotalRows());
		dataArr.setItems(pageInfo.getPageData());
		apiResponse.setData(dataArr);
		apiResponse.setMessage(APIResponse.SUCESS_MSG);
		return apiResponse;
	}

	@Override
	public APIResponse qryBalance(String userId) {
		APIResponse apiResponse = new APIResponse();
		UserPersonalEntity userPerson = authenticationRepository.queryUserAuthenticationInfo(userId);
		if(userPerson!=null){
			Map<String,Object> map = new HashMap<String,Object>();
			/*Double balance = 0.00;
			if(userPerson.getBalance()!=null){
				balance = userPerson.getBalance().doubleValue();
			}*/
			map.put("balance", userPerson.getBalance());
			apiResponse.setData(map);
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
			return apiResponse;
		}else{
			apiResponse.setErrorCode(ErrorCode.USER_NOT_REAL);
			return apiResponse;
		}
	}

	@Override
	public APIResponse qryWithdrawInfoByCond(FinanceWithdrawApplyRequest qryRequest) {
		APIResponse apiResponse = new APIResponse();
		List<FinanceWithdrawApplyEntity> list = this.financeWithdrawApplyRepository.qryWithdrawInfoByCond(qryRequest);
		if(list!=null){
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
			apiResponse.setData(list);
		}
		return apiResponse;
	}

	@Override
	public APIResponse recharge(FinanceTradeDetailEntity ftdEntity) {
		APIResponse apiResponse = new APIResponse();
		UserBaseVo userBaseVo = userPersonalRepository.findUserVoByUserId(ftdEntity.getPayUserId());
        ftdEntity.setPayUserName(userBaseVo.getUserName());
        ftdEntity.setIncomeUserName(userBaseVo.getUserName());
		try {
			//插入资金流水
			FinanceTradeDetailEntity rsEntity = this.financeTradeDetailRepository.save(ftdEntity);
			apiResponse.setData(rsEntity.getRecordId());
		} catch (Exception e) {
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			return apiResponse;
		}
		apiResponse.setMessage(APIResponse.SUCESS_MSG);
		return apiResponse;
	}

	@Override
	public APIResponse getTNByUnionPay(String orderId, Double dfee) {
		APIResponse apiResponse = new APIResponse();
		Map<String, String> contentData = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", "5.0.0");            //版本号 全渠道默认值
		contentData.put("encoding", "UTF-8");     //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01");           		 	//签名方法 目前只支持01：RSA方式证书加密
		contentData.put("txnType", "01");              		 	//交易类型 01:消费
		contentData.put("txnSubType", "01");           		 	//交易子类 01：消费
		contentData.put("bizType", "000201");          		 	//填写000201
		contentData.put("channelType", "08");          		 	//渠道类型 08手机

		/***商户接入参数***/
		contentData.put("merId", SDKConfig.merId);   		 				//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		contentData.put("accessType", "0");            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
		contentData.put("orderId", orderId);        	 	    //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("txnTime", DateUtil.getFormatedDate(new Date(), "yyyyMMddHHmmss"));//订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("accType", "01");					 	//账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
		contentData.put("txnAmt", String.valueOf((int)(dfee*10*10)));	//交易金额 单位为分，不能带小数点
		contentData.put("currencyCode", "156");                 //境内商户固定 156 人民币
		//contentData.put("reqReserved", "透传字段");              //商户自定义保留域，交易应答时会原样返回
		
		//后台通知地址（需设置为外网能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", SDKConfig.callBackUrl);
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(contentData,"UTF-8");			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestAppUrl,"UTF-8");  //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, "UTF-8")){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//成功,获取tn号
					//TODO
					apiResponse.setSucess(true);
					JSONObject jo = new JSONObject();
					jo.put("tn", rspData.get("tn"));
					apiResponse.setData(jo);
				}else{
					//其他应答码为失败请排查原因或做失败处理
					//TODO
					apiResponse.setSucess(false);
					apiResponse.setErrorCode(ErrorCode.UNION_PAY_TN_FAIL);
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
				apiResponse.setSucess(false);
				apiResponse.setErrorCode(ErrorCode.UNION_PAY_VALID_FAIL);
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			apiResponse.setSucess(false);
			apiResponse.setErrorCode(ErrorCode.UNION_PAY_HTTP_FAIL);
		}
		return apiResponse;
	}

	@Override
	public APIResponse getPreInfoByWechartPay(String orderId, Double dfee,String termType) {
		APIResponse apiResponse = new APIResponse();
		//参数组
		String appid = WeixinConfigUtils.appid;
		System.out.println("appid是："+appid);
		String mch_id = WeixinConfigUtils.mch_id;
		System.out.println("mch_id是："+mch_id);
		String nonce_str = RandCharsUtils.getRandomString(16);
		System.out.println("随机字符串是："+nonce_str);
		String body = "闪蝠众包-余额充值";
		String detail = "";
		String attach = "备用参数";
		String out_trade_no = orderId;
		int total_fee = (int)(dfee*10*10);//单位是分，即是0.01元
		String spbill_create_ip = WeixinConfigUtils.spbill_create_ip;//终端IP
		String time_start = RandCharsUtils.timeStart();
		System.out.println(time_start);
		String time_expire = RandCharsUtils.timeExpire();
		System.out.println(time_expire);
		String notify_url = WeixinConfigUtils.wechat_call_back_url;
		System.out.println("notify_url是："+notify_url);
		String trade_type = null;
		if(Constants.TERMTYPE_PC.equals(termType)){
			trade_type = "NATIVE";//扫码支付
		}else{
			trade_type = "APP";
		}
		
		//参数：开始生成签名
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("body", body);
		parameters.put("nonce_str", nonce_str);
		parameters.put("detail", detail);
		parameters.put("attach", attach);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", total_fee);
		parameters.put("time_start", time_start);
		parameters.put("time_expire", time_expire);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", trade_type);
		parameters.put("spbill_create_ip", spbill_create_ip);
		
		String sign = WXSignUtils.createSign("UTF-8", parameters);
		System.out.println("签名是："+sign);
		
		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setSign(sign);
		unifiedorder.setBody(body);
		unifiedorder.setDetail(detail);
		unifiedorder.setAttach(attach);
		unifiedorder.setOut_trade_no(out_trade_no);
		unifiedorder.setTotal_fee(total_fee);
		unifiedorder.setSpbill_create_ip(spbill_create_ip);
		unifiedorder.setTime_start(time_start);
		unifiedorder.setTime_expire(time_expire);
		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type(trade_type);
		
		//构造xml参数
		String xmlInfo = HttpXmlUtils.xmlInfo(unifiedorder);
		String wxUrl = WeixinConfigUtils.wx_url;
		String method = "POST";
		String weixinPost = null;
		UnifiedorderResult result= new UnifiedorderResult();
		try {
			weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();
			System.out.println(weixinPost);
			result = JdomParseXmlUtils.getUnifiedorderResult(weixinPost);
			result.setTimeStamp(String.valueOf(System.currentTimeMillis()/1000));
			
			//二次签名
			SortedMap<Object,Object> params = new TreeMap<Object,Object>();
			params.put("appid", result.getAppid());
			params.put("partnerid", result.getMch_id());
			params.put("prepayid", result.getPrepay_id());
			params.put("package", "Sign=WXPay");//扩展字段（固定值）
			params.put("noncestr", result.getNonce_str());
			params.put("timestamp", result.getTimeStamp());
			String sign2 = WXSignUtils.createSign("UTF-8", params);
			System.out.println("二次签名是："+sign2);
			result.setSign(sign2);
			result.setPackages("Sign=WXPay");
			result.setOut_trade_no(out_trade_no);
			
			if(result.getReturn_code().equals(Constants.WECHAT_RETURN_CODE_FAIL)){
				apiResponse.setSucess(false);
				apiResponse.setMessage(result.getReturn_msg());
			}
			apiResponse.setSucess(true);
			apiResponse.setData(result);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse.setSucess(false);
			apiResponse.setErrorCode(ErrorCode.WECHAT_PAY_HTTP_FAIL);
		}
		return apiResponse;
	}

	@Override
	public boolean callBackWechat(SortedMap<Object, Object> map) {
		boolean isOk = true;
        short tradeState = 0;
        try{
	        String orderSerial  = (String) map.get("out_trade_no");//SDK生成的订单号
	        String payOrderSerial = (String) map.get("transaction_id");//微信支付订单号
	        BigDecimal totalFee = new BigDecimal(String.valueOf((int)map.get("total_fee")*0.01));//交易金额（单位为分，转元）
	        String tradeStatus = (String) map.get("result_code");//支付状态描述
	        //buyer_logon_id 买家支付宝账号
	        //根据订单号获取用户ID
	        FinanceTradeDetailEntity ftdEntity = this.financeTradeDetailRepository.findOneByTradeOrder(orderSerial);
	        //如果第三方发送的请求不是交易成功，则返回
	        if(!"SUCCESS".equals(tradeStatus))
	        {
	            isOk = false;
	            tradeState=Constants.TRADE_STATE_PAID_FAIL;//打款失败
	        }else{
	        	tradeState=Constants.TRADE_STATE_PAID_SUCCESS;//打款成功
	            double doubleTotalFee = 0;
	            doubleTotalFee = totalFee.doubleValue();
	            //加入余额
	            this.authenticationRepository.updatePersonBalance(ftdEntity.getIncomeUserId(), doubleTotalFee);
	        }
	        //更新交易状态
	        this.financeTradeDetailRepository.updateTradeStateById(ftdEntity.getRecordId(),tradeState,payOrderSerial);
        }catch (Exception e){
        	e.printStackTrace();
        	isOk = false;
        }
        return isOk;
	}

	@Override
	public APIResponse addOfflineInfo(FinanceOfflineTradeEntity fotEntity) {
		APIResponse apiResponse = new APIResponse();
		/*if(StringUtil.isNull(fotEntity.getBankName())){
			apiResponse.setErrorCode(ErrorCode.BANK_NAME_BLANK);
			return apiResponse;
		}*/
		if(StringUtil.isNull(fotEntity.getBankAccountName())){
			apiResponse.setErrorCode(ErrorCode.BANK_ACCOUNT_NAME_BLANK);
			return apiResponse;
		}
		if(StringUtil.isNull(fotEntity.getBankAccountNumber())){
			apiResponse.setErrorCode(ErrorCode.BANK_ACCOUNT_NUNBER_BLANK);
			return apiResponse;
		}
		if(StringUtil.isNull(fotEntity.getBankSerialId())){
			apiResponse.setErrorCode(ErrorCode.BANK_SERIAL_ID_BLANK);
			return apiResponse;
		}
		try {
			this.financeOfficeTradeRepository.save(fotEntity);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			return apiResponse;
		}
		return apiResponse;
	}

	@Override
	public APIResponse qryTradeInfo(String outTradeNo) {
		APIResponse apiResponse = new APIResponse();
		FinanceTradeDetailEntity ftdEntity = new FinanceTradeDetailEntity();
		if(StringUtil.isNull(outTradeNo)){
			apiResponse.setSucess(false);
			apiResponse.setErrorCode(ErrorCode.OUT_TRADE_NO_BLANK);
			return apiResponse;
		}
		try {
			ftdEntity = this.financeTradeDetailRepository.findOneByTradeOrder(outTradeNo);
			if(ftdEntity!=null){
				apiResponse.setData(ftdEntity);
				apiResponse.setMessage(APIResponse.SUCESS_MSG);
			}else{
				apiResponse.setSucess(false);
				apiResponse.setErrorCode(ErrorCode.TRADE_NOT_EXIST);
			}
		} catch (Exception e) {
			apiResponse.setSucess(false);
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		return apiResponse;
	}

}
