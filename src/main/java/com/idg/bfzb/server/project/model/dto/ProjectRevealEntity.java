package com.idg.bfzb.server.project.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProjectRevealEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_project_reveal")
public class ProjectRevealEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String projectId;
	private String mobile;
	private String applyContent;
	private Timestamp applyTime;
	private String adminId;
	private Timestamp adminReviewTime;
	private Short adminReviewState;
	private String adminReviewReason;
	private Timestamp createTime;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public ProjectRevealEntity() {
	}

	/** minimal constructor */
	public ProjectRevealEntity(String projectId, Timestamp applyTime,
			Timestamp adminReviewTime, Timestamp createTime,
			Timestamp updateTime) {
		this.projectId = projectId;
		this.applyTime = applyTime;
		this.adminReviewTime = adminReviewTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public ProjectRevealEntity(String projectId, String mobile,
			String applyContent, Timestamp applyTime, String adminId,
			Timestamp adminReviewTime, Short adminReviewState,
			String adminReviewReason, Timestamp createTime, Timestamp updateTime) {
		this.projectId = projectId;
		this.mobile = mobile;
		this.applyContent = applyContent;
		this.applyTime = applyTime;
		this.adminId = adminId;
		this.adminReviewTime = adminReviewTime;
		this.adminReviewState = adminReviewState;
		this.adminReviewReason = adminReviewReason;
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

	@Column(name = "mobile", length = 32)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "apply_content", length = 1024)
	public String getApplyContent() {
		return this.applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	@Column(name = "apply_time", nullable = false, length = 19)
	public Timestamp getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
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