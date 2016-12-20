package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：ProjectPublishRequest
 * 类描述：发包者发布项目request
 * 创建人：ouzhb
 * 创建时间：2016/11/8
 */
public class ProjectPublishRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	//项目名称
	private String projectName;
	//工作地址
	private String address;
	//门牌号
	private String houseNumber;
	//项目详细信息
	private String description;
	//招标截止
	private String applyDeadline;
	//完成时间
	private String submitDeadline;
	//联系电话
	private String telephone;
	//服务类别
	private String categoryIds;
	//项目预算
	private Double budget;
	//项目发起者
	private String ownerId;
	//招标类型
	private String tenderType;
	//省
	private String regionProvince;
	//市
	private String regionCity;
	//区
	private String regionArea;
	//经度
	private String longitude;
	//纬度
	private String latitude;

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
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
