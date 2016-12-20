package com.idg.bfzb.server.project.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProjectPlanEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_project_plan")
public class ProjectPlanEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String planId;
	private String projectId;
	private String planContent;
	private Integer serialNumber;
	private Timestamp planStartTime;
	private Timestamp planEndTime;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short state;

	// Constructors

	/** default constructor */
	public ProjectPlanEntity() {
	}

	/** minimal constructor */
	public ProjectPlanEntity(String planId, String projectId,
			Timestamp createTime, Timestamp updateTime, Short state) {
		this.planId = planId;
		this.projectId = projectId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
	}

	/** full constructor */
	public ProjectPlanEntity(String planId, String projectId,
			String planContent, Integer serialNumber, Timestamp planStartTime,
			Timestamp planEndTime, Timestamp createTime, Timestamp updateTime,
			Short state) {
		this.planId = planId;
		this.projectId = projectId;
		this.planContent = planContent;
		this.serialNumber = serialNumber;
		this.planStartTime = planStartTime;
		this.planEndTime = planEndTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "plan_id", unique = true, nullable = false, length = 64)
	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	@Column(name = "project_id", nullable = false, length = 64)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "plan_content", length = 1024)
	public String getPlanContent() {
		return this.planContent;
	}

	public void setPlanContent(String planContent) {
		this.planContent = planContent;
	}

	@Column(name = "serial_number")
	public Integer getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "plan_start_time", length = 19)
	public Timestamp getPlanStartTime() {
		return this.planStartTime;
	}

	public void setPlanStartTime(Timestamp planStartTime) {
		this.planStartTime = planStartTime;
	}

	@Column(name = "plan_end_time", length = 19)
	public Timestamp getPlanEndTime() {
		return this.planEndTime;
	}

	public void setPlanEndTime(Timestamp planEndTime) {
		this.planEndTime = planEndTime;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", nullable = false, length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "state", nullable = false)
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

}