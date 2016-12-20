package com.idg.bfzb.server.usercenter.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;

import java.io.Serializable;


public class LoginResponse implements Serializable {

    private String userId;

    private String userName;
    private String nickName;
    @JsonProperty(value="to")
    private String token;

    private String userRole;

    private String mobile;
    
    private String iconSmallUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

	public String getIconSmallUrl() {
		return iconSmallUrl;
	}

	public void setIconSmallUrl(String iconSmallUrl) {
		this.iconSmallUrl = ConfigFileUtils.HEAD_ICON_URL + iconSmallUrl;
	}
}
