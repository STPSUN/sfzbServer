package com.idg.bfzb.server.configure.model.request;

import java.io.Serializable;

import javax.persistence.Column;

public class MsgConfigureRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long configureId;
	private String alertStyle;
	private String mobile;
	private String realName;
	
	public Long getConfigureId() {
		return this.configureId;
	}

	public void setConfigureId(Long configureId) {
		this.configureId = configureId;
	}

	public String getAlertStyle() {
		return this.alertStyle;
	}

	public void setAlertStyle(String alertStyle) {
		this.alertStyle = alertStyle;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
