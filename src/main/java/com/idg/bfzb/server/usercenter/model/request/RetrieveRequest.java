package com.idg.bfzb.server.usercenter.model.request;


import java.io.Serializable;

/**
 * 类名称：RetrieveRequest
 * 类描述：用户找回密码请求实体
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public class RetrieveRequest implements Serializable {

    private String authCode;

    private String newPassword;

    private String mobile;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
