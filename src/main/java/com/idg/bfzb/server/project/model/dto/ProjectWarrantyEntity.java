package com.idg.bfzb.server.project.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProjectWarrantyEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_project_warranty")
public class ProjectWarrantyEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String projectId;
	private String employerReason;
	private String mobile;
	private Short adminReviewState;
	private String adminReviewReason;
	private String adminId;
	private Timestamp adminReviewTime;
	private Timestamp createTime;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public ProjectWarrantyEntity() {
	}

	/** minimal constructor */
	public ProjectWarrantyEntity(String projectId, Timestamp adminReviewTime,
			Timestamp createTime, Timestamp updateTime) {
		this.projectId = projectId;
		this.adminReviewTime = adminReviewTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public ProjectWarrantyEntity(String projectId, String employerReason,
			String mobile, Short adminReviewState, String adminReviewReason,
			String adminId, Timestamp adminReviewTime, Timestamp createTime,
			Timestamp updateTime) {
		this.projectId = projectId;
		this.employerReason = employerReason;
		this.mobile = mobile;
		this.adminReviewState = adminReviewState;
		this.adminReviewReason = adminReviewReason;
		this.adminId = adminId;
		this.adminReviewTime = adminReviewTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@Column(name = "project_id", unique = true, nullable = false, length = 64)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "employer_reason", length = 1024)
	public String getEmployerReason() {
		return this.employerReason;
	}

	public void setEmployerReason(String employerReason) {
		this.employerReason = employerReason;
	}

	@Column(name = "mobile", length = 16)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "admin_review_state")
	public Short getAdminReviewState() {
		return this.adminReviewState;
	}

	public void setAdminReviewState(Short adminReviewState) {
		this.adminReviewState = adminReviewState;
	}

	@Column(name = "admin_review_reason", length = 1024)
	public String getAdminReviewReason() {
		return this.adminReviewReason;
	}

	public void setAdminReviewReason(String adminReviewReason) {
		this.adminReviewReason = adminReviewReason;
	}

	@Column(name = "admin_id", length = 64)
	public String getAdminId() {
		return this.adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	@Column(name = "admin_review_time", nullable = false, length = 19)
	public Timestamp getAdminReviewTime() {
		return this.adminReviewTime;
	}

	public void setAdminReviewTime(Timestamp adminReviewTime) {
		this.adminReviewTime = adminReviewTime;
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

}