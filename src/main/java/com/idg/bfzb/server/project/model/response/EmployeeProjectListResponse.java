package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：ProjectListResponse
 * 类描述：项目列表
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class EmployeeProjectListResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String projectName;
	
	private Double budget;
	
	private String categorys;
	
	private String applyDeadline;
	
	private String submitDeadline;
	
	private int signUpCount;
	
	private short state;
	
	private String projectType;
	
	private String tenderType;
	
	private String createTime;
	
	private String employerId;
	
	private String tenderUserId;
	
	private String regionProvince;
	
	private String regionCity;
	
	private String regionArea;
	
	private String employerNickName;
	
	private String employerMobile;
	
	private Double tradeMoney;
	
	private String regionDetail;
	
	private String houseNumber;

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

	public int getSignUpCount() {
		return signUpCount;
	}

	public void setSignUpCount(int signUpCount) {
		this.signUpCount = signUpCount;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
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

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
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
