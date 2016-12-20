package com.idg.bfzb.server.content.model.response;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContentResponse implements Serializable{
    private static final long serialVersionUID = 1L;
    
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
    private String advClientType;
    private Timestamp startTime;
    private Timestamp endTime;
    private String playArea;
    private String advUser;
    private String advUserMobile;
    private String advLocationDetail;
    
    public String getAdvId() {
        return this.advId;
    }
    public void setAdvId(String advId) {
        this.advId = advId;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdvContent() {
        return this.advContent;
    }
    public void setAdvContent(String advContent) {
        this.advContent = advContent;
    }

    public String getAdvImg() {
        return this.advImg;
    }
    public void setAdvImg(String advImg) {
        this.advImg = advImg;
    }

    public Integer getAdvSort() {
        return this.advSort;
    }
    public void setAdvSort(Integer advSort) {
        this.advSort = advSort;
    }

    public String getRedirectType() {
        return this.redirectType;
    }
    public void setRedirectType(String redirectType) {
        this.redirectType = redirectType;
    }

    public String getAdvLink() {
        return this.advLink;
    }
    public void setAdvLink(String advLink) {
        this.advLink = advLink;
    }

    public String getAdvType() {
        return this.advType;
    }
    public void setAdvType(String advType) {
        this.advType = advType;
    }

    public String getAdvLocation() {
        return this.advLocation;
    }
    public void setAdvLocation(String advLocation) {
        this.advLocation = advLocation;
    }

    public Short getStatus() {
        return this.status;
    }
    public void setStatus(Short status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateAdminId() {
        return this.updateAdminId;
    }
    public void setUpdateAdminId(String updateAdminId) {
        this.updateAdminId = updateAdminId;
    }

    public String getAdvClientType() {
        return this.advClientType;
    }
    public void setAdvClientType(String advClientType) {
        this.advClientType = advClientType;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getPlayArea() {
        return this.playArea;
    }
    public void setPlayArea(String playArea) {
        this.playArea = playArea;
    }
    
    public String getAdvUser() {
        return this.advUser;
    }
    public void setAdvUser(String advUser) {
        this.advUser = advUser;
    }

    public String getAdvUserMobile() {
        return this.advUserMobile;
    }
    public void setAdvUserMobile(String advUserMobile) {
        this.advUserMobile = advUserMobile;
    }

    public String getAdvLocationDetail() {
        return this.advLocationDetail;
    }
    public void setAdvLocationDetail(String advLocationDetail) {
        this.advLocationDetail = advLocationDetail;
    }
}
