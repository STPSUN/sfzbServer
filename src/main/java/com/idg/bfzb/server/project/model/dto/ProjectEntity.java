package com.idg.bfzb.server.project.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProjectEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_project")
public class ProjectEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String projectId;
	private String projectName;
	private String employerId;
	private Double budget;
	private String categoryIds;
	private Timestamp applyDeadline;
	private Integer signUpCount;
	private Timestamp submitDeadline;
	private String description;
	private String projectType;
	private String tenderType;
	private String regionDetail;
	private String regionCode;
	private String regionName;
	private String regionProvince;
	private String regionCity;
	private String regionArea;
	private String houseNumber;
	private String longitude;
	private String latitude;
	private Short state;
	private String reviewAdminId;
	private Timestamp reviewTime;
	private String reviewReason;
	private Timestamp createTime;
	private String tenderUserId;
	private Double tradeMoney;
	private Float imprestScale;
	private Float marginScale;
	private Float revealScale;
	private Short isReveal;
	private Timestamp updateTime;
	private String telephone;
	private Short isMargin;
	private Integer marginDay;
	private Timestamp receiverCompleteTime;
	private Timestamp employerCompleteTime;
	private String revealProjectId;
	// Constructors

	/** default constructor */
	public ProjectEntity() {
	}

	/** minimal constructor */
	public ProjectEntity(String projectId, String projectName,
			String employerId, Timestamp applyDeadline,
			Timestamp submitDeadline, Timestamp reviewTime,
			Timestamp createTime, Timestamp updateTime) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.employerId = employerId;
		this.applyDeadline = applyDeadline;
		this.submitDeadline = submitDeadline;
		this.reviewTime = reviewTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public ProjectEntity(String projectId, String projectName,
			String employerId, Double budget, String categoryIds,
			Timestamp applyDeadline, Integer signUpCount,
			Timestamp submitDeadline, String description, String projectType,
			String tenderType, String regionDetail, String regionCode,
			String regionName, String regionProvince, String regionCity,
			String regionArea, String houseNumber, String longitude,
			String latitude, Short state, String reviewAdminId,
			Timestamp reviewTime, Timestamp createTime, String tenderUserId,
			Double tradeMoney, Float imprestScale, Float marginScale,
			Float revealScale, Short isReveal, Timestamp updateTime,
			String telephone, Short isMargin, Integer marginDay,
			Timestamp receiverCompleteTime, Timestamp employerCompleteTime) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.employerId = employerId;
		this.budget = budget;
		this.categoryIds = categoryIds;
		this.applyDeadline = applyDeadline;
		this.signUpCount = signUpCount;
		this.submitDeadline = submitDeadline;
		this.description = description;
		this.projectType = projectType;
		this.tenderType = tenderType;
		this.regionDetail = regionDetail;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.regionProvince = regionProvince;
		this.regionCity = regionCity;
		this.regionArea = regionArea;
		this.houseNumber = houseNumber;
		this.longitude = longitude;
		this.latitude = latitude;
		this.state = state;
		this.reviewAdminId = reviewAdminId;
		this.reviewTime = reviewTime;
		this.createTime = createTime;
		this.tenderUserId = tenderUserId;
		this.tradeMoney = tradeMoney;
		this.imprestScale = imprestScale;
		this.marginScale = marginScale;
		this.revealScale = revealScale;
		this.isReveal = isReveal;
		this.updateTime = updateTime;
		this.telephone = telephone;
		this.isMargin = isMargin;
		this.marginDay = marginDay;
		this.receiverCompleteTime = receiverCompleteTime;
		this.employerCompleteTime = employerCompleteTime;
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

	@Column(name = "project_name", nullable = false, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "employer_id", nullable = false, length = 64)
	public String getEmployerId() {
		return this.employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

	@Column(name = "budget", precision = 10)
	public Double getBudget() {
		return this.budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	@Column(name = "category_ids", length = 1024)
	public String getCategoryIds() {
		return this.categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	@Column(name = "apply_deadline", nullable = false, length = 19)
	public Timestamp getApplyDeadline() {
		return this.applyDeadline;
	}

	public void setApplyDeadline(Timestamp applyDeadline) {
		this.applyDeadline = applyDeadline;
	}

	@Column(name = "sign_up_count")
	public Integer getSignUpCount() {
		return this.signUpCount;
	}

	public void setSignUpCount(Integer signUpCount) {
		this.signUpCount = signUpCount;
	}

	@Column(name = "submit_deadline", nullable = false, length = 19)
	public Timestamp getSubmitDeadline() {
		return this.submitDeadline;
	}

	public void setSubmitDeadline(Timestamp submitDeadline) {
		this.submitDeadline = submitDeadline;
	}

	@Column(name = "description", length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "project_type", length = 32)
	public String getProjectType() {
		return this.projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	@Column(name = "tender_type", length = 32)
	public String getTenderType() {
		return this.tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	@Column(name = "region_detail", length = 128)
	public String getRegionDetail() {
		return this.regionDetail;
	}

	public void setRegionDetail(String regionDetail) {
		this.regionDetail = regionDetail;
	}

	@Column(name = "region_code", length = 16)
	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Column(name = "region_name", length = 16)
	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "region_province", length = 16)
	public String getRegionProvince() {
		return this.regionProvince;
	}

	public void setRegionProvince(String regionProvince) {
		this.regionProvince = regionProvince;
	}

	@Column(name = "region_city", length = 16)
	public String getRegionCity() {
		return this.regionCity;
	}

	public void setRegionCity(String regionCity) {
		this.regionCity = regionCity;
	}

	@Column(name = "region_area", length = 16)
	public String getRegionArea() {
		return this.regionArea;
	}

	public void setRegionArea(String regionArea) {
		this.regionArea = regionArea;
	}

	@Column(name = "house_number", length = 20)
	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	@Column(name = "longitude", length = 20)
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", length = 20)
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Column(name = "review_admin_id", length = 64)
	public String getReviewAdminId() {
		return this.reviewAdminId;
	}

	public void setReviewAdminId(String reviewAdminId) {
		this.reviewAdminId = reviewAdminId;
	}

	@Column(name = "review_time", nullable = false, length = 19)
	public Timestamp getReviewTime() {
		return this.reviewTime;
	}

	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "tender_user_id", length = 64)
	public String getTenderUserId() {
		return this.tenderUserId;
	}

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	@Column(name = "trade_money", precision = 10)
	public Double getTradeMoney() {
		return this.tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	@Column(name = "imprest_scale", precision = 12, scale = 0)
	public Float getImprestScale() {
		return this.imprestScale;
	}

	public void setImprestScale(Float imprestScale) {
		this.imprestScale = imprestScale;
	}

	@Column(name = "margin_scale", precision = 12, scale = 0)
	public Float getMarginScale() {
		return this.marginScale;
	}

	public void setMarginScale(Float marginScale) {
		this.marginScale = marginScale;
	}

	@Column(name = "reveal_scale", precision = 12, scale = 0)
	public Float getRevealScale() {
		return this.revealScale;
	}

	public void setRevealScale(Float revealScale) {
		this.revealScale = revealScale;
	}

	@Column(name = "is_reveal")
	public Short getIsReveal() {
		return this.isReveal;
	}

	public void setIsReveal(Short isReveal) {
		this.isReveal = isReveal;
	}

	@Column(name = "update_time", nullable = false, length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "telephone", length = 32)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "is_margin")
	public Short getIsMargin() {
		return this.isMargin;
	}

	public void setIsMargin(Short isMargin) {
		this.isMargin = isMargin;
	}

	@Column(name = "margin_day")
	public Integer getMarginDay() {
		return this.marginDay;
	}

	public void setMarginDay(Integer marginDay) {
		this.marginDay = marginDay;
	}

	@Column(name = "receiver_complete_time", length = 19)
	public Timestamp getReceiverCompleteTime() {
		return this.receiverCompleteTime;
	}

	public void setReceiverCompleteTime(Timestamp receiverCompleteTime) {
		this.receiverCompleteTime = receiverCompleteTime;
	}

	@Column(name = "employer_complete_time", length = 19)
	public Timestamp getEmployerCompleteTime() {
		return this.employerCompleteTime;
	}

	public void setEmployerCompleteTime(Timestamp employerCompleteTime) {
		this.employerCompleteTime = employerCompleteTime;
	}
	@Column(name = "review_reason")
	public String getReviewReason() {
		return reviewReason;
	}

	public void setReviewReason(String reviewReason) {
		this.reviewReason = reviewReason;
	}
	@Column(name = "reveal_project_id")
	public String getRevealProjectId() {
		return revealProjectId;
	}

	public void setRevealProjectId(String revealProjectId) {
		this.revealProjectId = revealProjectId;
	}

}