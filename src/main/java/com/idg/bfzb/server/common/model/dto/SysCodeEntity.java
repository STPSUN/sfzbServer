package com.idg.bfzb.server.common.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysCodeEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_sys_code", catalog = "bfzb")
public class SysCodeEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String confKey;
	private String confName;
	private String confData;
	private String description;
	private Timestamp updateTime;
	private String classify;

	// Constructors

	/** default constructor */
	public SysCodeEntity() {
	}

	/** minimal constructor */
	public SysCodeEntity(String confKey, String confName, String confData,
			Timestamp updateTime) {
		this.confKey = confKey;
		this.confName = confName;
		this.confData = confData;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public SysCodeEntity(String confKey, String confName, String confData,
			String description, Timestamp updateTime, String classify) {
		this.confKey = confKey;
		this.confName = confName;
		this.confData = confData;
		this.description = description;
		this.updateTime = updateTime;
		this.classify = classify;
	}

	// Property accessors
	@Id
	@Column(name = "conf_key", unique = true, nullable = false, length = 64)
	public String getConfKey() {
		return this.confKey;
	}

	public void setConfKey(String confKey) {
		this.confKey = confKey;
	}

	@Column(name = "conf_name", nullable = false, length = 128)
	public String getConfName() {
		return this.confName;
	}

	public void setConfName(String confName) {
		this.confName = confName;
	}

	@Column(name = "conf_data", nullable = false, length = 2048)
	public String getConfData() {
		return this.confData;
	}

	public void setConfData(String confData) {
		this.confData = confData;
	}

	@Column(name = "description", length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "update_time", nullable = false, length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "classify", length = 64)
	public String getClassify() {
		return this.classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

}