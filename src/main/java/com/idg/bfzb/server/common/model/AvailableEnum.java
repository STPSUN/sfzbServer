package com.idg.bfzb.server.common.model;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
public enum  AvailableEnum {
    DELETE(-1),NORMAL(0);

    private int value;
    AvailableEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
