package com.idg.bfzb.server.authentication.model.dto;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * UserEnterpriseEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_user_enterprise")
public class UserEnterpriseEntity {


    // Fields    

     private String enterpriseId;
     private String userId;
     private String enterpriseName;
     private String regionCode;
     private String businessLicense;
     private String businessScope;
     private String corporate;
     private String mobile;
     private String businessLicenseImage;
     private Short reviewState;
     private String reviewAdminId;
     private Timestamp reviewTime;
     private Timestamp createTime;
     private Timestamp updateTime;


    // Constructors

    /** default constructor */
    public UserEnterpriseEntity() {
    }

	/** minimal constructor */
    public UserEnterpriseEntity(String enterpriseId, String userId, String enterpriseName, Timestamp reviewTime, Timestamp createTime, Timestamp updateTime) {
        this.enterpriseId = enterpriseId;
        this.userId = userId;
        this.enterpriseName = enterpriseName;
        this.reviewTime = reviewTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    
    /** full constructor */
    public UserEnterpriseEntity(String enterpriseId, String userId, String enterpriseName, String regionCode, String businessLicense, String businessScope, String corporate, String mobile, String businessLicenseImage, Short reviewState, String reviewAdminId, Timestamp reviewTime, Timestamp createTime, Timestamp updateTime) {
        this.enterpriseId = enterpriseId;
        this.userId = userId;
        this.enterpriseName = enterpriseName;
        this.regionCode = regionCode;
        this.businessLicense = businessLicense;
        this.businessScope = businessScope;
        this.corporate = corporate;
        this.mobile = mobile;
        this.businessLicenseImage = businessLicenseImage;
        this.reviewState = reviewState;
        this.reviewAdminId = reviewAdminId;
        this.reviewTime = reviewTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="enterprise_id", unique=true, nullable=false, length=64)

    public String getEnterpriseId() {
        return this.enterpriseId;
    }
    
    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
    
    @Column(name="user_id", nullable=false, length=64)

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Column(name="enterprise_name", nullable=false, length=128)

    public String getEnterpriseName() {
        return this.enterpriseName;
    }
    
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    @Column(name="region_code", length=16)

    public String getRegionCode() {
        return this.regionCode;
    }
    
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    
    @Column(name="business_license", length=64)

    public String getBusinessLicense() {
        return this.businessLicense;
    }
    
    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }
    
    @Column(name="business_scope", length=256)

    public String getBusinessScope() {
        return this.businessScope;
    }
    
    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }
    
    @Column(name="corporate", length=16)

    public String getCorporate() {
        return this.corporate;
    }
    
    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }
    
    @Column(name="mobile", length=16)

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    @Column(name="business_license_image", length=64)

    public String getBusinessLicenseImage() {
        return this.businessLicenseImage;
    }
    
    public void setBusinessLicenseImage(String businessLicenseImage) {
        this.businessLicenseImage = businessLicenseImage;
    }
    
    @Column(name="review_state")

    public Short getReviewState() {
        return this.reviewState;
    }
    
    public void setReviewState(Short reviewState) {
        this.reviewState = reviewState;
    }
    
    @Column(name="review_admin_id", length=64)

    public String getReviewAdminId() {
        return this.reviewAdminId;
    }
    
    public void setReviewAdminId(String reviewAdminId) {
        this.reviewAdminId = reviewAdminId;
    }
    
    @Column(name="review_time", nullable=false, length=19)

    public Timestamp getReviewTime() {
        return this.reviewTime;
    }
    
    public void setReviewTime(Timestamp reviewTime) {
        this.reviewTime = reviewTime;
    }
    
    @Column(name="create_time", nullable=false, length=19)

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="update_time", nullable=false, length=19)

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
   








}