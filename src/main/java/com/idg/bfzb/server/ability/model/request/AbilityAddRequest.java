package com.idg.bfzb.server.ability.model.request;

import java.io.Serializable;

/**
 * 
 * .新增技能请求体
 * 
 * @author Administrator
 * @version Revision 1.0.0
 *
 */
public class AbilityAddRequest implements Serializable{
    private String abilityName;
    private String description;

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
