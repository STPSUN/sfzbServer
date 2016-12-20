package com.idg.bfzb.server.project.model.dto;

// default package

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProjectTenderEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_project_tender")
public class ProjectTenderEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Long recordId;
	private String projectId;
	private String tenderUserId;
	private Double offer;
	private String plan;
	private String description;
	private Timestamp completeTime;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short state;
	private String opinion;
	private String mobile;

	// Constructors

	/** default constructor */
	public ProjectTenderEntity() {
	}

	/** minimal constructor */
	public ProjectTenderEntity(String projectId, String tenderUserId,
			Timestamp createTime, Timestamp updateTime) {
		this.projectId = projectId;
		this.tenderUserId = tenderUserId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public ProjectTenderEntity(String projectId, String tenderUserId,
			Double offer, String plan, String description,
			Timestamp completeTime, Timestamp createTime, Timestamp updateTime,
			Short state, String opinion, String mobile) {
		this.projectId = projectId;
		this.tenderUserId = tenderUserId;
		this.offer = offer;
		this.plan = plan;
		this.description = description;
		this.completeTime = completeTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
		this.opinion = opinion;
		this.mobile = mobile;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "record_id", unique = true, nullable = false)
	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	@Column(name = "project_id", nullable = false, length = 64)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "tender_user_id", nullable = false, length = 64)
	public String getTenderUserId() {
		return this.tenderUserId;
	}

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	@Column(name = "offer", precision = 10)
	public Double getOffer() {
		return this.offer;
	}

	public void setOffer(Double offer) {
		this.offer = offer;
	}

	@Column(name = "plan", length = 1024)
	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	@Column(name = "description", length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "complete_time", length = 19)
	public Timestamp getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
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

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Column(name = "opinion", length = 1024)
	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	@Column(name = "mobile", length = 32)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}