package com.idg.bfzb.server.usercenter.model.enums;

/**
 * 类名称：PlatformType
 * 类描述：第三方登录平台类型
 * 创建人：jiangdong
 * 创建日期：2016/11/25
 */
public enum PlatformType {

    WEIXIN_LOGIN("weixin"),	//微信登录
    QQ_LOGIN("qq");			//QQ登录

    private String type;

    private PlatformType(String type) {
        this.setType(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}