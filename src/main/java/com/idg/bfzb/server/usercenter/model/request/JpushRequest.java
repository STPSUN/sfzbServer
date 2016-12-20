package com.idg.bfzb.server.usercenter.model.request;

import java.io.Serializable;

/**
 * 类名称：JpushRequest
 * 类描述：极光推送绑定请求
 * 创建人：jiangdong
 * 创建日期：2016/11/19
 */
public class JpushRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String jpushCode;

	public String getJpushCode() {
		return jpushCode;
	}

	public void setJpushCode(String jpushCode) {
		this.jpushCode = jpushCode;
	}

}
