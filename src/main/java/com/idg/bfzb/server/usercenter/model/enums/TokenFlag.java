package com.idg.bfzb.server.usercenter.model.enums;

/**
 * 登陆类型
 * @author Administrator
 *
 */
public enum TokenFlag {

    CLIENT_LOGIN(0),   //客户端登陆
    SERVER_LOGIN(1);   //服务端登陆

    private int type;

    private TokenFlag(int type) {
        this.setType(type);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
