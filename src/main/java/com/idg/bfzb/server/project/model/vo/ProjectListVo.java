package com.idg.bfzb.server.project.model.vo;

import java.io.Serializable;

/**
 * /**
 * 类名称：ProjectListVo
 * 类描述：项目列表查询vo
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class ProjectListVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String projectName;
	
	private Double budget;
	
	private String categoryIds;
	
	private String applyDeadline;
	
	private String submitDeadline;
	
	private Integer signUpCount;
	
	private Short projectState;
	
	private Short tenderState;
	
	private String projectType;
	
	private String tenderType;
	
	private String createTime;
	
	private String employerId;
	
	private String tenderUserId;
	
	private String regionProvince;
	
	private String regionCity;
	
	private String regionArea;
	
	private Double tradeMoney;
	
	private Short isMargin;
	
	private Integer marginDay;
	
	private Float marginScale;
	
	private String longitude;
	
	private String latitude;
	
	private String regionDetail;
	
	private String houseNumber;
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getApplyDeadline() {
		return applyDeadline;
	}

	public void setApplyDeadline(String applyDeadline) {
		this.applyDeadline = applyDeadline;
	}

	public String getSubmitDeadline() {
		return submitDeadline;
	}

	public void setSubmitDeadline(String submitDeadline) {
		this.submitDeadline = submitDeadline;
	}

	public Integer getSignUpCount() {
		return signUpCount;
	}

	public void setSignUpCount(Integer signUpCount) {
		this.signUpCount = signUpCount;
	}

	public Short getProjectState() {
		return projectState;
	}

	public void setProjectState(Short projectState) {
		this.projectState = projectState;
	}

	public Short getTenderState() {
		return tenderState;
	}

	public void setTenderState(Short tenderState) {
		this.tenderState = tenderState;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEmployerId() {
		return employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

	public String getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public String getRegionProvince() {
		return regionProvince;
	}

	public void setRegionProvince(String regionProvince) {
		this.regionProvince = regionProvince;
	}

	public String getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(String regionCity) {
		this.regionCity = regionCity;
	}

	public String getRegionArea() {
		return regionArea;
	}

	public void setRegionArea(String regionArea) {
		this.regionArea = regionArea;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Short getIsMargin() {
		return isMargin;
	}

	public void setIsMargin(Short isMargin) {
		this.isMargin = isMargin;
	}

	public Integer getMarginDay() {
		return marginDay;
	}

	public void setMarginDay(Integer marginDay) {
		this.marginDay = marginDay;
	}

	public Float getMarginScale() {
		return marginScale;
	}

	public void setMarginScale(Float marginScale) {
		this.marginScale = marginScale;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getRegionDetail() {
		return regionDetail;
	}

	public void setRegionDetail(String regionDetail) {
		this.regionDetail = regionDetail;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
}
