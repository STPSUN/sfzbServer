package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：ReceiverBaseProjectDetailResponse
 * 类描述：接包者基本项目详情
 * 创建人：ouzhb
 * 创建时间：2016/11/9
 */
public class ReceiverBaseProjectDetailResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String projectName;
	
	private Double budget;
	
	private String categorys;
	
	private String applyDeadline;
	
	private String submitDeadline;
	
	private String createTime;
	
	private Integer signUpCount;
	
	private Short projectState;
	
	private Short signUpState;
	
	private String description;
	
	private String projectType;
	
	private String tenderType;
	
	private String employerUserId;
	
	private String employerUserName;
	
	private String employerNickName;
	
	private String employerMobile;
	
	private String regionDetail;
	
	private String houseNumber;
	
	private String regionProvince;
	
	private String regionCity;
	
	private String regionArea;
	
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

	public Short getProjectState() {
		return projectState;
	}

	public void setProjectState(Short projectState) {
		this.projectState = projectState;
	}

	public Short getSignUpState() {
		return signUpState;
	}

	public void setSignUpState(Short signUpState) {
		this.signUpState = signUpState;
	}

	public String getEmployerUserId() {
		return employerUserId;
	}

	public void setEmployerUserId(String employerUserId) {
		this.employerUserId = employerUserId;
	}

	public String getEmployerUserName() {
		return employerUserName;
	}

	public void setEmployerUserName(String employerUserName) {
		this.employerUserName = employerUserName;
	}

	public String getEmployerNickName() {
		return employerNickName;
	}

	public void setEmployerNickName(String employerNickName) {
		this.employerNickName = employerNickName;
	}

	public String getEmployerMobile() {
		return employerMobile;
	}

	public void setEmployerMobile(String employerMobile) {
		this.employerMobile = employerMobile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
