package com.idg.bfzb.server.configure.model.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TContMsgConfigure entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cont_msg_configure", catalog = "bfzb")
public class TContMsgConfigureEntity implements java.io.Serializable {

	// Fields

	private Long configureId;
	private String alertStyle;
	private String mobile;
	private String realName;
	private Timestamp createTime;
	private Short state;

	// Constructors

	/** default constructor */
	public TContMsgConfigureEntity() {
	}

	/** minimal constructor */
	public TContMsgConfigureEntity(Long configureId) {
		this.configureId = configureId;
	}

	/** full constructor */
	public TContMsgConfigureEntity(Long configureId, String alertStyle,
			String mobile, String realName, Timestamp createTime, Short state) {
		this.configureId = configureId;
		this.alertStyle = alertStyle;
		this.mobile = mobile;
		this.realName = realName;
		this.createTime = createTime;
		this.state = state;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "configure_id", unique = true, nullable = false)
	public Long getConfigureId() {
		return this.configureId;
	}

	public void setConfigureId(Long configureId) {
		this.configureId = configureId;
	}

	@Column(name = "alert_style", length = 32)
	public String getAlertStyle() {
		return this.alertStyle;
	}

	public void setAlertStyle(String alertStyle) {
		this.alertStyle = alertStyle;
	}

	@Column(name = "mobile", length = 50)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "real_name", length = 128)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

}