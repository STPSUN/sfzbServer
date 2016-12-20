package com.idg.bfzb.server.project.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProjectRejectionEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_project_rejection")
public class ProjectRejectionEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String projectId;
	private String employerReason;
	private Timestamp employerApplyTime;
	private String employerAttachs;
	private Short tenderReviewState;
	private String tenderReivewReason;
	private Timestamp tenderReivewTime;
	private String tenderAttachs;
	private Short adminReviewState;
	private String adminId;
	private String adminReviewReason;
	private Timestamp adminReviewTime;
	private Timestamp createTime;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public ProjectRejectionEntity() {
	}

	/** minimal constructor */
	public ProjectRejectionEntity(String projectId,
			Timestamp employerApplyTime, Timestamp tenderReivewTime,
			Timestamp adminReviewTime, Timestamp createTime,
			Timestamp updateTime) {
		this.projectId = projectId;
		this.employerApplyTime = employerApplyTime;
		this.tenderReivewTime = tenderReivewTime;
		this.adminReviewTime = adminReviewTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public ProjectRejectionEntity(String projectId, String employerReason,
			Timestamp employerApplyTime, String employerAttachs,
			Short tenderReviewState, String tenderReivewReason,
			Timestamp tenderReivewTime, String tenderAttachs,
			Short adminReviewState, String adminId, String adminReviewReason,
			Timestamp adminReviewTime, Timestamp createTime,
			Timestamp updateTime) {
		this.projectId = projectId;
		this.employerReason = employerReason;
		this.employerApplyTime = employerApplyTime;
		this.employerAttachs = employerAttachs;
		this.tenderReviewState = tenderReviewState;
		this.tenderReivewReason = tenderReivewReason;
		this.tenderReivewTime = tenderReivewTime;
		this.tenderAttachs = tenderAttachs;
		this.adminReviewState = adminReviewState;
		this.adminId = adminId;
		this.adminReviewReason = adminReviewReason;
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

	@Column(name = "employer_apply_time", nullable = false, length = 19)
	public Timestamp getEmployerApplyTime() {
		return this.employerApplyTime;
	}

	public void setEmployerApplyTime(Timestamp employerApplyTime) {
		this.employerApplyTime = employerApplyTime;
	}

	@Column(name = "employer_attachs", length = 2048)
	public String getEmployerAttachs() {
		return this.employerAttachs;
	}

	public void setEmployerAttachs(String employerAttachs) {
		this.employerAttachs = employerAttachs;
	}

	@Column(name = "tender_review_state")
	public Short getTenderReviewState() {
		return this.tenderReviewState;
	}

	public void setTenderReviewState(Short tenderReviewState) {
		this.tenderReviewState = tenderReviewState;
	}

	@Column(name = "tender_reivew_reason", length = 1024)
	public String getTenderReivewReason() {
		return this.tenderReivewReason;
	}

	public void setTenderReivewReason(String tenderReivewReason) {
		this.tenderReivewReason = tenderReivewReason;
	}

	@Column(name = "tender_reivew_time", nullable = false, length = 19)
	public Timestamp getTenderReivewTime() {
		return this.tenderReivewTime;
	}

	public void setTenderReivewTime(Timestamp tenderReivewTime) {
		this.tenderReivewTime = tenderReivewTime;
	}

	@Column(name = "tender_attachs", length = 2048)
	public String getTenderAttachs() {
		return this.tenderAttachs;
	}

	public void setTenderAttachs(String tenderAttachs) {
		this.tenderAttachs = tenderAttachs;
	}

	@Column(name = "admin_review_state")
	public Short getAdminReviewState() {
		return this.adminReviewState;
	}

	public void setAdminReviewState(Short adminReviewState) {
		this.adminReviewState = adminReviewState;
	}

	@Column(name = "admin_id", length = 64)
	public String getAdminId() {
		return this.adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	@Column(name = "admin_review_reason", length = 1024)
	public String getAdminReviewReason() {
		return this.adminReviewReason;
	}

	public void setAdminReviewReason(String adminReviewReason) {
		this.adminReviewReason = adminReviewReason;
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