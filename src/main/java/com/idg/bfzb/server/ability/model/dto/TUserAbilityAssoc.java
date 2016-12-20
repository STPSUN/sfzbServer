package com.idg.bfzb.server.ability.model.dto;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TUserAbilityAssoc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_ability_assoc")
public class TUserAbilityAssoc implements java.io.Serializable {
    
    // Fields
    
    private TUserAbilityAssocId id;
    private Timestamp           updateTime;
    private Short               state;
    
    // Constructors
    
    /** default constructor */
    public TUserAbilityAssoc() {
    }
    
    /** minimal constructor */
    public TUserAbilityAssoc(TUserAbilityAssocId id, Timestamp updateTime) {
        this.id = id;
        this.updateTime = updateTime;
    }
    
    /** full constructor */
    public TUserAbilityAssoc(TUserAbilityAssocId id, Timestamp updateTime,
        Short state) {
        this.id = id;
        this.updateTime = updateTime;
        this.state = state;
    }
    
    // Property accessors
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false, length = 64)),
            @AttributeOverride(name = "ability", column = @Column(name = "ability", nullable = false, length = 64)) })
    public TUserAbilityAssocId getId() {
        return this.id;
    }
    
    public void setId(TUserAbilityAssocId id) {
        this.id = id;
    }
    
    @Column(name = "update_time", nullable = false, length = 19)
    public Timestamp getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    
    @Column(name = "state")
    public Short getState() {
        return this.state;
    }
    
    public void setState(Short state) {
        this.state = state;
    }
    
}
