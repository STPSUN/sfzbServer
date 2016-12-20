package com.idg.bfzb.server.ability.model.request;

public class AbilityModifyRequest {
    private String abilityId;
    private String abilityName;
    private String description;

    public String getAbilityId(){
        return abilityId;
    }
    
    public void setAbilityId(String abilityId){
        this.abilityId = abilityId;
    }
    
    public String getAbilityName(){
        return abilityName;
    }
    
    public void setAbilityName(String abilityName){
        this.abilityName = abilityName;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
}
