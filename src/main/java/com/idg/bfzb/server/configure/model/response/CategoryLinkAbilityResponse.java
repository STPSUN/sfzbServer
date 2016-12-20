package com.idg.bfzb.server.configure.model.response;

import java.io.Serializable;

public class CategoryLinkAbilityResponse implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String abilityId;
    private String abilityName;
    private String isLink;
    
    public String getAbilityId() {
        return abilityId;
    }
    public void setAbilityId(String abilityId) {
        this.abilityId = abilityId;
    }
    public String getAbilityName() {
        return abilityName;
    }
    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }
    public String getIsLink() {
        return isLink;
    }
    public void setIsLink(String isLink) {
        this.isLink = isLink;
    }
}
