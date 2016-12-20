package com.idg.bfzb.server.usercenter.model.request;

import java.io.Serializable;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangfeng
 * 创建日期：2016/10/26
 */
public class MobileRegisterRequest implements Serializable {
    private String mobile;

    private String authCode;

    private String password;
    
    private String platformType;
    
    private String platformCode;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
