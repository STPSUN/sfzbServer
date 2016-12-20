package com.idg.bfzb.server.configure.model.response;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;

public class MsgConfigureResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long configureId;
	private String alertStyle;
	private String mobile;
	private String realName;
	private Timestamp createTime;
	private Short state;
	
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

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}
}
