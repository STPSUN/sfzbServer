package com.idg.bfzb.server.usercenter.thirdlogin;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.utility.tools.HttpRequest;

/**
 * 类名称：WeixinUtils
 * 类描述：微信接口工具类
 * 创建人：jiangdong
 * 创建日期：2016/11/19
 */
public class WeixinUtils {
	/**
	 * 获取openId
	 * @param code	用户换取access_token的code
	 * @return	openId
	 */
	public static String getOpenId(String code){
		String url = WeixinConstants.ACCESSTOKEN_URL + "?appid="+ WeixinConstants.APPID 
		          + "&secret="+WeixinConstants.APPSECRET + "&code="+code+"&grant_type=authorization_code";
		
		String responseStr = HttpRequest.sendGet(url);
		if(StringUtils.isNotBlank(responseStr)){
			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			if(jsonObject!=null){
				String openId = jsonObject.getString("openid");
				return openId;
			}
		}
		
		return null;
	}

}
