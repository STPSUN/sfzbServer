package com.idg.bfzb.server.homepage.model.dto;
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ContAdvertisementEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_cont_advertisement"
    ,catalog="bfzb"
)

public class ContAdvertisementEntity  implements java.io.Serializable {


    // Fields    

     private String advId;
     private String title;
     private String advContent;
     private String advImg;
     private Integer advSort;
     private String redirectType;
     private String advLink;
     private String advType;
     private String advLocation;
     private Short status;
     private Timestamp createTime;
     private Timestamp updateTime;
     private String updateAdminId;


    // Constructors

    /** default constructor */
    public ContAdvertisementEntity() {
    }

	/** minimal constructor */
    public ContAdvertisementEntity(String advId, Timestamp createTime, Timestamp updateTime) {
        this.advId = advId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    
    /** full constructor */
    public ContAdvertisementEntity(String advId, String title, String advContent, String advImg, Integer advSort, String redirectType, String advLink, String advType, String advLocation, Short status, Timestamp createTime, Timestamp updateTime, String updateAdminId) {
        this.advId = advId;
        this.title = title;
        this.advContent = advContent;
        this.advImg = advImg;
        this.advSort = advSort;
        this.redirectType = redirectType;
        this.advLink = advLink;
        this.advType = advType;
        this.advLocation = advLocation;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.updateAdminId = updateAdminId;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="adv_id", unique=true, nullable=false, length=64)

    public String getAdvId() {
        return this.advId;
    }
    
    public void setAdvId(String advId) {
        this.advId = advId;
    }
    
    @Column(name="title", length=128)

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="adv_content", length=1024)

    public String getAdvContent() {
        return this.advContent;
    }
    
    public void setAdvContent(String advContent) {
        this.advContent = advContent;
    }
    
    @Column(name="adv_img")

    public String getAdvImg() {
        return this.advImg;
    }
    
    public void setAdvImg(String advImg) {
        this.advImg = advImg;
    }
    
    @Column(name="adv_sort")

    public Integer getAdvSort() {
        return this.advSort;
    }
    
    public void setAdvSort(Integer advSort) {
        this.advSort = advSort;
    }
    
    @Column(name="redirect_type", length=16)

    public String getRedirectType() {
        return this.redirectType;
    }
    
    public void setRedirectType(String redirectType) {
        this.redirectType = redirectType;
    }
    
    @Column(name="adv_link")

    public String getAdvLink() {
        return this.advLink;
    }
    
    public void setAdvLink(String advLink) {
        this.advLink = advLink;
    }
    
    @Column(name="adv_type", length=64)

    public String getAdvType() {
        return this.advType;
    }
    
    public void setAdvType(String advType) {
        this.advType = advType;
    }
    
    @Column(name="adv_location", length=10)

    public String getAdvLocation() {
        return this.advLocation;
    }
    
    public void setAdvLocation(String advLocation) {
        this.advLocation = advLocation;
    }
    
    @Column(name="status")

    public Short getStatus() {
        return this.status;
    }
    
    public void setStatus(Short status) {
        this.status = status;
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
    
    @Column(name="update_admin_id", length=64)

    public String getUpdateAdminId() {
        return this.updateAdminId;
    }
    
    public void setUpdateAdminId(String updateAdminId) {
        this.updateAdminId = updateAdminId;
    }
   








}