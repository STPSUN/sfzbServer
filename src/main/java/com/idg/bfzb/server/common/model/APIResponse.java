package com.idg.bfzb.server.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.idg.bfzb.server.common.ErrorCode;

import java.io.Serializable;

public class APIResponse implements Serializable{
    public final static String SUCESS_MSG = "SUCCESS";

    @JsonIgnore
    private boolean isSucess = true;

    private  String code= "SUCCESS";
    @JsonProperty(value="msg")
    private String message;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setErrorCode(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
        this.isSucess = false;
    }

    public boolean isSucess() {
        return isSucess;
    }

    public void setSucess(boolean sucess) {
        isSucess = sucess;
    }
}
