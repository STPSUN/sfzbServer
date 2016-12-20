package com.idg.bfzb.server.ability.model.vo;

import java.io.Serializable;

/**
 * 
 * .我的技能列表返回数据
 * 
 * @author Administrator
 * @version Revision 1.0.0
 *
 */
public class AbilityVo implements Serializable{
    private String abilityId;//技能id
    private String abilityName;//技能名称
    private String description;//技能说明
    private String state;//技能状态
    
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
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
