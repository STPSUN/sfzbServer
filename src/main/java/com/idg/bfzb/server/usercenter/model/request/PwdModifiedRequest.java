package com.idg.bfzb.server.usercenter.model.request;

import java.io.Serializable;

/**
 * 类名称：PwdModifiedRequest
 * 类描述：用户修改密码请求体类
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public class PwdModifiedRequest implements Serializable {

    private String oldPassword;

    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
