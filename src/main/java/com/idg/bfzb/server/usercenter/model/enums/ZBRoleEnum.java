package com.idg.bfzb.server.usercenter.model.enums;

/**
 * 类名称：ZBRoleEnum
 * 类描述：众包角色枚举,不能放到UC
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public enum ZBRoleEnum {
    UNKNOWN("unknown"),
    EMPLOYER("employer"),
    WORKER("worker");

    ZBRoleEnum(String value){
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
