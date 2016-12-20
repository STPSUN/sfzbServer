package com.idg.bfzb.server.authentication.model;

public class EnterpriceAuthRequest {

	private String enterpriseName;
	private String enterpriseCity;
	private String phoneNumber;
	private String businessEntity;
	private String businessScope;
	private String enterpriseNumber;
	
	private String businessLicenseImage;
	//企业营业执照地址
	private String businessLicenseImageUrl;
	
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getEnterpriseCity() {
		return enterpriseCity;
	}
	public void setEnterpriseCity(String enterpriseCity) {
		this.enterpriseCity = enterpriseCity;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getBusinessEntity() {
		return businessEntity;
	}
	public void setBusinessEntity(String businessEntity) {
		this.businessEntity = businessEntity;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getEnterpriseNumber() {
		return enterpriseNumber;
	}
	public void setEnterpriseNumber(String enterpriseNumber) {
		this.enterpriseNumber = enterpriseNumber;
	}
	public String getBusinessLicenseImage() {
		return businessLicenseImage;
	}
	public void setBusinessLicenseImage(String businessLicenseImage) {
		this.businessLicenseImage = businessLicenseImage;
	}
	public String getBusinessLicenseImageUrl() {
		return businessLicenseImageUrl;
	}
	public void setBusinessLicenseImageUrl(String businessLicenseImageUrl) {
		this.businessLicenseImageUrl = businessLicenseImageUrl;
	}
}
