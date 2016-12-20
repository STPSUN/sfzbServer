package com.idg.bfzb.server.usercenter.model.response;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class EvaluateManagerResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long evaluateId;
    private String targetType;
    private String targetId;
    private String targetOwnerId;
    private String userId;
    private String content;
    private Integer star;
    private String extendData;
    private Short state;
    private Timestamp createTime;
    private Timestamp updateTime;
    
    private String userName;
    private String nickName;
    private String targetUserName;
    private String targetNickName;
    private String projectName;

    public Long getEvaluateId() {
        return this.evaluateId;
    }

    public void setEvaluateId(Long evaluateId) {
        this.evaluateId = evaluateId;
    }

    public String getTargetType() {
        return this.targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return this.targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetOwnerId() {
        return this.targetOwnerId;
    }

    public void setTargetOwnerId(String targetOwnerId) {
        this.targetOwnerId = targetOwnerId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStar() {
        return this.star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getExtendData() {
        return this.extendData;
    }

    public void setExtendData(String extendData) {
        this.extendData = extendData;
    }

    public Short getState() {
        return this.state;
    }

    public void setState(Short state) {
        this.state = state;
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
    
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getTargetUserName() {
        return this.targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }
    
    public String getTargetNickName() {
        return this.targetNickName;
    }

    public void setTargetNickName(String targetNickName) {
        this.targetNickName = targetNickName;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
}
