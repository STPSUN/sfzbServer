package com.idg.bfzb.server.controller.api;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.pay.alipay.util.AlipayNotify;
import com.idg.bfzb.server.pay.model.dto.FinanceOfflineTradeEntity;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.pay.model.request.FinanceWithdrawApplyRequest;
import com.idg.bfzb.server.pay.model.request.RechargeRequest;
import com.idg.bfzb.server.pay.service.PayService;
import com.idg.bfzb.server.pay.unionpay.acp.sdk.AcpService;
import com.idg.bfzb.server.pay.unionpay.acp.sdk.LogUtil;
import com.idg.bfzb.server.pay.unionpay.acp.sdk.SDKConstants;
import com.idg.bfzb.server.pay.wechatpay.dto.WXPayResult;
import com.idg.bfzb.server.pay.wechatpay.util.HttpXmlUtils;
import com.idg.bfzb.server.pay.wechatpay.util.JdomParseXmlUtils;
import com.idg.bfzb.server.pay.wechatpay.util.ParseXMLUtils;
import com.idg.bfzb.server.pay.wechatpay.util.WXSignUtils;
import com.idg.bfzb.server.utility.tools.StringUtil;

/**
 * 
 * @author weibeifeng
 *
 */
@Controller
@RequestMapping("/api/pay")
public class PayController extends BaseController {

    private  static Logger log = Logger.getLogger(PayController.class);

    @Resource
    PayService payService;
    
    
    
    /**
     * 发起充值
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "/type/{type}/actions/recharge",method = RequestMethod.POST)
    @ResponseBody
    public 	Object recharge(@PathVariable("type")String type,@RequestBody RechargeRequest rechargeRequest, HttpServletRequest request, HttpServletResponse response){
    	APIResponse apiResponse = new APIResponse();
    	JSONObject jo = new JSONObject();
    	FinanceTradeDetailEntity ftdEntity = new FinanceTradeDetailEntity();
        //获取用户token
    	//String userId = "ab4bb523-fe2c-47a6-abbb-2de494627b55";//用户ID
    	String userId = super.getCurrentUser();
    	Double Dfee = rechargeRequest.getFee();
    	if(Dfee==null){
    		apiResponse.setErrorCode(ErrorCode.INVALID_ARGUMENT);
            return apiResponse;
    	}
    	String orderId = payService.createOrderId(Constants.ORDER_TYPE_RECHARGE);//生成商户订单号
    	ftdEntity.setPayUserId(userId);
		ftdEntity.setIncomeUserId(userId);
        ftdEntity.setMoney(Dfee);
        ftdEntity.setOutTradeNo(orderId);
        ftdEntity.setTradeState(Constants.TRADE_STATE_NOT_PAY);//状态为未打款（待收到回调充值成功后再更新金额同时更新状态为已打款）
    	
        if(type.equals(Constants.TRADE_PLAT_ALIPAY)){
        	//生成凭证(金额)
        	APIResponse poResponse = payService.getOrderInfoByAliPay(orderId,Dfee,rechargeRequest.getTermType());
        	if(poResponse.isSucess()){
	        	//发起充值，插入资金流水表，状态为待付款
	            ftdEntity.setTransType(Constants.TRANS_TYPE_RECHARGE);//充值
	            ftdEntity.setTradePlatform(Constants.TRADE_PLAT_ALIPAY);//支付宝
	        	this.payService.recharge(ftdEntity);
	            if(!Constants.TERMTYPE_PC.equals(rechargeRequest.getTermType())){//手机端调用
	            	jo.put("pay_order", poResponse.getData());
		        	apiResponse.setData(jo);
		        	apiResponse.setMessage(APIResponse.SUCESS_MSG);
		        	return apiResponse;
	            }
        	}
        	return poResponse;
        	
    	}else if(type.equals(Constants.TRADE_PLAT_WECHATPAY)){
    		//获取预支付信息
    		APIResponse preResponse = payService.getPreInfoByWechartPay(orderId,Dfee,rechargeRequest.getTermType());
    		if(preResponse.isSucess()){
    			//发起充值，插入资金流水表，状态为待付款
                ftdEntity.setTransType(Constants.TRANS_TYPE_RECHARGE);//充值
                ftdEntity.setTradePlatform(Constants.TRADE_PLAT_WECHATPAY);//微信
            	this.payService.recharge(ftdEntity);
    		}
    		return preResponse;
    	}else if(type.equals(Constants.TRADE_PLAT_UNIONPAY)){
    		//获取TN号
    		APIResponse tnResponse = payService.getTNByUnionPay(orderId,Dfee);
    		if(tnResponse.isSucess()){
    			//发起充值，插入资金流水表，状态为待付款
                ftdEntity.setTransType(Constants.TRANS_TYPE_RECHARGE);//充值
                ftdEntity.setTradePlatform(Constants.TRADE_PLAT_UNIONPAY);//银联
            	this.payService.recharge(ftdEntity);
    		}
        	return tnResponse;
    		
    		
    	}else if(type.equals(Constants.TRADE_PLAT_OFFLINE)){
    		//发起充值，插入资金流水表，状态为待付款
            ftdEntity.setTransType(Constants.TRANS_TYPE_RECHARGE);//充值
            ftdEntity.setTradePlatform(Constants.TRADE_PLAT_OFFLINE);//线下
            APIResponse  rsResponse= this.payService.recharge(ftdEntity);
        	//插入线下汇款扩展表
        	FinanceOfflineTradeEntity fotEntity = new FinanceOfflineTradeEntity();
        	fotEntity.setRecordId((long)rsResponse.getData());
        	fotEntity.setBankName(rechargeRequest.getBankName());
        	fotEntity.setBankAccountNumber(rechargeRequest.getBankAccountNumber());
        	fotEntity.setBankAccountName(rechargeRequest.getBankAccountName());
        	fotEntity.setBankSerialId(rechargeRequest.getBankSerialId());
        	fotEntity.setMoney(Dfee);
        	fotEntity.setScripAttchId(rechargeRequest.getScripAttchId());
        	fotEntity.setRemark(rechargeRequest.getRemark());
        	apiResponse = this.payService.addOfflineInfo(fotEntity);
        	return apiResponse;
    	}
    	
    	//响应凭证
    	/*apiResponse.setData(jo);
    	apiResponse.setMessage(APIResponse.SUCESS_MSG);
    	return apiResponse;*/
        return null;
    }
    /**
     * 阿里支付回调
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "/aliCallback")
    @ResponseBody
    public void aliCallback(HttpServletRequest request, HttpServletResponse response){
        log.info("[PayController][aliCallBack][Begin]");
        Map<String,String> params = new HashMap<String,String>();
        try{
            Map requestParams = request.getParameterMap();
            for (Object o : requestParams.keySet()) {
                String name = (String) o;
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
                log.info(name+":"+valueStr);
            }
            /*params.put("body", "test");
            params.put("open_id", "20880073992237793022443272318787");
            params.put("subject", "闪蝠众包-二维码余额充值");
            params.put("sign_type", "RSA");
            params.put("buyer_logon_id", "18850443950");
            params.put("auth_app_id", "2016111102715844");
            params.put("notify_type", "trade_status_sync");
            params.put("out_trade_no", "14817227723982de494627b55");
            params.put("point_amount", "0.00");
            params.put("version", "1.0");
            params.put("fund_bill_list", "[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]");
            params.put("total_amount", "0.01");
            params.put("buyer_id", "2088702695200870");
            params.put("trade_no", "2016121421001004870218283133");
            params.put("notify_time", "2016-12-14 21:42:57");
            params.put("charset", "UTF-8");
            params.put("invoice_amount", "0.01");
            params.put("trade_status", "TRADE_SUCCESS");
            params.put("gmt_payment", "2016-12-14 21:42:57");
            params.put("sign", "gR2vzHLF13bBa5U9mvM4GwQK9arnKUPqHn/3PKzcFmNkYg25aXoP6CS/vRsy3ldqqlbuyiuL7yFj9mQ4Ex1GjfPdcErzsBlhGgtySIN+wboh7jAi1JtiF8mxr6kkYLtvKyTh+LMDVNwJ3nnQH+zJf9p2LFt5c4dCWx9ybBMfyJo=");
            params.put("gmt_create", "2016-12-14 21:42:35");
            params.put("buyer_pay_amount", "0.01");
            params.put("receipt_amount", "0.01");
            params.put("seller_id", "2088421450332879");
            params.put("app_id", "2016111102715844");
            params.put("seller_email", "dever@hooju.cn");
            params.put("notify_id", "3d65f90f22bd779765ea604410e4577mpq");*/
            
            log.info("params.size()===="+params.size());
            if(params.size()> 0 ){//判断是否有带返回参数
                //if(AlipayNotify.verify(params)){
                    log.info("验签成功.[PayController][aliCallBack][verify][Success]");
                    boolean isOk =  payService.callBackAliPay(params);
                    if(!isOk){
                    	log.info("数据库错误");
                        response.getWriter().write("failure");
                    }
                    response.getWriter().write("success");//校验成功，返回支付宝success
                /*}else{
                	log.info("验签失败");
                    response.getWriter().write("failure");
                }*/
            }else{
                response.getWriter().write("failure");
            }
        }catch (Exception e){
        	e.printStackTrace();
        	log.info(e.getMessage());
        }
    }
    
    /**
     * 银联支付回调
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "/unionCallback")
    @ResponseBody
    public void unionCallback(HttpServletRequest request, HttpServletResponse response){
    	LogUtil.writeLog("BackRcvResponse接收后台通知开始");
    	try {
		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(request);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes(encoding), encoding);
				valideData.put(key, value);
			}
		}

		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
			String respCode =valideData.get("respCode"); //获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
			
			
			
		}
		//返回给银联服务器http 200  状态码
		response.getWriter().print("ok");
    	} catch (Exception e) {
			// TODO: handle exception
		}
    	LogUtil.writeLog("BackRcvResponse接收后台通知结束");
    }
    
    /** 
     * 微信支付回调 
     * @param request 
     * @param resposne 
     */  
    @RequestMapping(value="/wechatCallback")  
    public void wechatCallback(HttpServletRequest request,HttpServletResponse response){  
        try{  
            BufferedReader reader = request.getReader();  
  
            String line = "";  
            StringBuffer inputString = new StringBuffer();  
  
            try{  
                PrintWriter writer = response.getWriter();  
  
                while ((line = reader.readLine()) != null) {  
                    inputString.append(line);  
                }  
  
                if(reader!=null){  
                    reader.close();
                }  
  
                System.out.println("----[微信回调]接收到的报文---"+inputString.toString());  
  
                if(!StringUtil.isNull(inputString.toString())){  
                    WXPayResult wxPayResult = JdomParseXmlUtils.getWXPayResult(inputString.toString());  
  
                    if("SUCCESS".equalsIgnoreCase(wxPayResult.getReturn_code())){  
                        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
                        parameters.put("appid", wxPayResult.getAppid());  
                        parameters.put("attach", wxPayResult.getAttach());  
                        parameters.put("bank_type", wxPayResult.getBank_type());  
                        parameters.put("cash_fee", wxPayResult.getCash_fee());  
                        parameters.put("fee_type", wxPayResult.getFee_type());  
                        parameters.put("is_subscribe", wxPayResult.getIs_subscribe());  
                        parameters.put("mch_id", wxPayResult.getMch_id());  
                        parameters.put("nonce_str", wxPayResult.getNonce_str());  
                        parameters.put("openid", wxPayResult.getOpenid());  
                        parameters.put("out_trade_no", wxPayResult.getOut_trade_no());  
                        parameters.put("result_code", wxPayResult.getResult_code());  
                        parameters.put("return_code", wxPayResult.getReturn_code());  
                        parameters.put("time_end", wxPayResult.getTime_end());  
                        parameters.put("total_fee", wxPayResult.getTotal_fee());  
                        parameters.put("trade_type", wxPayResult.getTrade_type());  
                        parameters.put("transaction_id", wxPayResult.getTransaction_id());  
  
                        //反校验签名  
                        String sign = WXSignUtils.createSign("UTF-8", parameters);  
  
                        if(sign.equals(wxPayResult.getSign())){  
                            //修改订单的状态  
                        	boolean isOk =  payService.callBackWechat(parameters);
                            if(!isOk){
                            	writer.write(HttpXmlUtils.backWeixin("FAIL","商户业务操作失败"));
                            } 
                            writer.write(HttpXmlUtils.backWeixin("SUCCESS","OK"));  
                        }else{  
                            writer.write(HttpXmlUtils.backWeixin("FAIL","签名失败"));  
                        }  
                    }else{  
                        writer.write(HttpXmlUtils.backWeixin("FAIL",wxPayResult.getReturn_msg()));  
                          
                        System.out.println("---------微信支付返回Fail----------"+wxPayResult.getReturn_msg());  
                    }  
  
                    if(writer!=null){  
                        writer.close();  
                    }  
                }else{  
                    //writer.write(HttpXmlUtils.backWeixin("FAIL","未获取到微信返回的结果"));  
                }  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
        }catch(Exception ex){
            ex.printStackTrace();
        }  
    }  
    
    
    /**
     * 提现申请
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "/actions/withdraw",method = RequestMethod.POST)
    @ResponseBody
    public Object withdraw(@RequestBody FinanceWithdrawApplyRequest financeWithdrawApplyRequest,
    		HttpServletRequest request, HttpServletResponse response){
    	APIResponse apiResponse = new APIResponse();
        //获取用户token
    	//String userId = "ab4bb523-fe2c-47a6-abbb-2de494627b55";//用户ID
    	String userId = super.getCurrentUser();
    	financeWithdrawApplyRequest.setApplyUserId(userId);
    	apiResponse = this.payService.withdraw(financeWithdrawApplyRequest);
    	return apiResponse;
    }
    /**
     * 资金明显流水
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "/financialDetail/type/{type}/query",method = RequestMethod.GET)
    @ResponseBody
    public Object financialDetail(@PathVariable("type") String type,HttpServletRequest request, HttpServletResponse response){
    	APIResponse apiResponse = new APIResponse();
        //获取用户token
    	//String userId = "ab4bb523-fe2c-47a6-abbb-2de494627b55";//用户ID
    	String userId = super.getCurrentUser();
    	Integer pageNum = 0;
    	Integer pageSize = 0;
    	try {
    		pageNum = Integer.parseInt(request.getParameter("offset"));
		} catch (Exception e) {
			pageNum = 1;
		}
    	try {
    		pageSize = Integer.parseInt(request.getParameter("size"));
		} catch (Exception e) {
			pageSize = 10;
		}
    	String termType = request.getParameter("term_type");
    	PageRequest pageable = new PageRequest(pageNum-1, pageSize);
    	FinanceTradeDetailEntity entity = new FinanceTradeDetailEntity();
    	entity.setPayUserId(userId);
    	entity.setIncomeUserId(userId);
    	entity.setTransType(type);
    	if(!Constants.TERMTYPE_PC.equals(termType)){
    		entity.setTradeState((short)1);
    	}
    	apiResponse = this.payService.financialDetail(entity, pageable);
    	return apiResponse;
    }
    /**
     * 余额查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/balance/query",method = RequestMethod.GET)
    @ResponseBody
    public Object qryBalance(HttpServletRequest request, HttpServletResponse response){
    	APIResponse apiResponse = new APIResponse();
        //获取用户token
    	//String userId = "c7392776-9745-42d2-a827-fc7bc566ad20";//用户ID
    	String userId = super.getCurrentUser();
    	apiResponse = this.payService.qryBalance(userId);
    	return apiResponse;
    }
    
    /**
     * 提现申请列表查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/withdrawLists/query",method = RequestMethod.GET)
    @ResponseBody
    public Object qryWithdrawLists(HttpServletRequest request, HttpServletResponse response){
    	APIResponse apiResponse = new APIResponse();
    	FinanceWithdrawApplyRequest qryRequest = new FinanceWithdrawApplyRequest();
        //获取用户token
    	//String userId = "fc5b57d2-f189-4e65-9f02-2248d66ba3b5";//用户ID
    	String userId = super.getCurrentUser();
    	qryRequest.setApplyUserId(userId);
    	apiResponse = this.payService.qryWithdrawInfoByCond(qryRequest);
    	return apiResponse;
    }
    
    /**
     * 根据订单号查询订单信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/tradeInfo/query",method = RequestMethod.GET)
    @ResponseBody
    public Object qryTradeInfo(HttpServletRequest request, HttpServletResponse response){
    	APIResponse apiResponse = new APIResponse();
    	String outTradeNo = request.getParameter("out_trade_no");
        //获取用户token
    	//String userId = "fc5b57d2-f189-4e65-9f02-2248d66ba3b5";//用户ID
    	apiResponse = this.payService.qryTradeInfo(outTradeNo);
    	return apiResponse;
    }
   
    /**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(
			final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======为空的字段名===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}
    
    public static void main(String[] args){
    	String fee = "0.01";
    	Double Dfee = 0.;
    	try {
    		Dfee = Double.parseDouble(fee);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println(Dfee);
    	
    }
    
}
