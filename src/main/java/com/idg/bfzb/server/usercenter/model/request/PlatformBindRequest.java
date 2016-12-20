package com.idg.bfzb.server.usercenter.model.request;

import java.io.Serializable;

/**
 * 类名称：PlatformBindRequest
 * 类描述：第三方应用绑定请求
 * 创建人：jiangdong
 * 创建日期：2016/11/19
 */
public class PlatformBindRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String mobile;
	
	private String authCode;
	
	private String platformType;
	
	private String platformCode;
	
	private Short isForceBind;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

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

	public Short getIsForceBind() {
		return isForceBind;
	}

	public void setIsForceBind(Short isForceBind) {
		this.isForceBind = isForceBind;
	}

}
