package com.idg.bfzb.server.utility.tools;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SMSUtils {

	/**
	 * 
	 * 发送验证码接口
	 * 
	 * @param code 验证码
	 * @param mobile 手机号码
	 * @return 返回错误描述，如果正常发送，返回空字符串
	 */
	public String sendCheckCodeSMS(String code, String mobile) {
		TaobaoClient client = new DefaultTaobaoClient(ConfigFileUtils.SMS_URL, ConfigFileUtils.SMS_APP_KEY, ConfigFileUtils.SMS_APP_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(mobile);
		req.setSmsType("normal");
		req.setSmsFreeSignName("闪蝠众包");
		req.setSmsParamString("{\"code\":\"" + code + "\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_15520547");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			return "";
		} catch (ApiException e) {
			e.printStackTrace();
			return e.getErrMsg();
		}
	}
	
}
