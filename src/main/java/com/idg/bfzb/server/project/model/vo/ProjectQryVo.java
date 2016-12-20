package com.idg.bfzb.server.project.model.vo;

import java.io.Serializable;

/**
 * 类名称：ProjectQryVo
 * 类描述：项目查询条件vo
 * 创建人：ouzhb
 * 创建时间：2016/11/6
 */
public class ProjectQryVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String projectName;
	
	private String categoryId;
	
	private Integer deadline;
	
	private Double minBudget;
	
	private Double maxBudget;
	
	private Short state;
	
	private String employerId;
	
	private String tenderUserId;
	
	private String notTendUserId;
	
	private String tenderType;
	
	private String regionProvince;
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public Double getMinBudget() {
		return minBudget;
	}

	public void setMinBudget(Double minBudget) {
		this.minBudget = minBudget;
	}

	public Double getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(Double maxBudget) {
		this.maxBudget = maxBudget;
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

	public String getNotTendUserId() {
		return notTendUserId;
	}

	public void setNotTendUserId(String notTendUserId) {
		this.notTendUserId = notTendUserId;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	public String getRegionProvince() {
		return regionProvince;
	}

	public void setRegionProvince(String regionProvince) {
		this.regionProvince = regionProvince;
	}

}
