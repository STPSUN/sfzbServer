package com.idg.bfzb.server.ability.model.dto;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TAbility entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_ability")

public class TAbilityEntity  implements java.io.Serializable {


    // Fields    

     private String abilityId;
     private String abilityName;
     private String description;
     private Short state;
     private Timestamp createTime;
     private Timestamp updateTime;


    // Constructors

    /** default constructor */
    public TAbilityEntity() {
    }

	/** minimal constructor */
    public TAbilityEntity(String abilityId, Timestamp createTime, Timestamp updateTime) {
        this.abilityId = abilityId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    
    /** full constructor */
    public TAbilityEntity(String abilityId, String abilityName, String description, Short state, Timestamp createTime, Timestamp updateTime) {
        this.abilityId = abilityId;
        this.abilityName = abilityName;
        this.description = description;
        this.state = state;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ability_id", unique=true, nullable=false, length=64)

    public String getAbilityId() {
        return this.abilityId;
    }
    
    public void setAbilityId(String abilityId) {
        this.abilityId = abilityId;
    }
    
    @Column(name="ability_name", length=128)

    public String getAbilityName() {
        return this.abilityName;
    }
    
    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }
    
    @Column(name="description", length=1024)

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="state")

    public Short getState() {
        return this.state;
    }
    
    public void setState(Short state) {
        this.state = state;
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