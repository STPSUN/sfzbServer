package com.idg.bfzb.server.usercenter.model.request;

import java.io.Serializable;

/**
 * 类名称：PlatformLoginRequest
 * 类描述：第三方应用登录请求
 * 创建人：jiangdong
 * 创建日期：2016/11/19
 */
public class PlatformLoginRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String platformType;
	
	private String platformCode;
	
	private String weixinId;
	
	private String qqId;

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}
	
}
