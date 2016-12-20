package com.idg.bfzb.server.ability.model.response;

import java.sql.Timestamp;

public class AdminAbilityResponse {
    private String abilityId;
    private String abilityName;
    private String description;
    private Short state;
    private Timestamp createTime;
    private Timestamp updateTime;
    
    public String getAbilityId() {
        return this.abilityId;
    }
    public void setAbilityId(String abilityId) {
        this.abilityId = abilityId;
    }
    public String getAbilityName() {
        return this.abilityName;
    }
    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
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
}
