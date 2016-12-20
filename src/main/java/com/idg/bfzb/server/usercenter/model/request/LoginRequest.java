package com.idg.bfzb.server.usercenter.model.request;

import java.io.Serializable;

/**
 * 类名称：LoginRequest
 * 类描述：用户登陆请求
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
public class LoginRequest implements Serializable{
    private String loginName;

    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
