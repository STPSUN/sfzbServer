package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：ReceiverProjectListResponse
 * 类描述：发包者项目列表
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class ReceiverProjectListResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String projectName;
	
	private Double budget;
	
	private String categorys;
	
	private String applyDeadline;
	
	private String submitDeadline;
	
	private Integer signUpCount;
	
	private Short tenderState;
	
	private Short projectState;
	
	private String projectType;
	
	private String tenderType;
	
	private String createTime;
	
	private Double tradeMoney;
	
	private String regionProvince;
	
	private String regionCity;
	
	private String regionArea;
	
	private Short isMargin;
	
	private Integer marginDay;
	
	private Float marginScale;
	
	private String longitude;
	
	private String latitude;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public String getApplyDeadline() {
		return applyDeadline;
	}

	public void setApplyDeadline(String applyDeadline) {
		this.applyDeadline = applyDeadline;
	}

	public Integer getSignUpCount() {
		return signUpCount;
	}

	public void setSignUpCount(Integer signUpCount) {
		this.signUpCount = signUpCount;
	}

	public String getSubmitDeadline() {
		return submitDeadline;
	}

	public void setSubmitDeadline(String submitDeadline) {
		this.submitDeadline = submitDeadline;
	}

	public Short getTenderState() {
		return tenderState;
	}

	public void setTenderState(Short tenderState) {
		this.tenderState = tenderState;
	}

	public Short getProjectState() {
		return projectState;
	}

	public void setProjectState(Short projectState) {
		this.projectState = projectState;
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

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
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

}
