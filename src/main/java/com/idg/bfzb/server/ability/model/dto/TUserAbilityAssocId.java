package com.idg.bfzb.server.ability.model.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TUserAbilityAssocId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TUserAbilityAssocId implements java.io.Serializable {
    
    // Fields
    
    private String userId;
    private String ability;
    
    // Constructors
    
    /** default constructor */
    public TUserAbilityAssocId() {
    }
    
    /** full constructor */
    public TUserAbilityAssocId(String userId, String ability) {
        this.userId = userId;
        this.ability = ability;
    }
    
    // Property accessors
    
    @Column(name = "user_id", nullable = false, length = 64)
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Column(name = "ability", nullable = false, length = 64)
    public String getAbility() {
        return this.ability;
    }
    
    public void setAbility(String ability) {
        this.ability = ability;
    }
    
    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof TUserAbilityAssocId))
            return false;
        TUserAbilityAssocId castOther = (TUserAbilityAssocId) other;
        
        return ((this.getUserId() == castOther.getUserId()) || (this
            .getUserId() != null && castOther.getUserId() != null && this
            .getUserId().equals(castOther.getUserId())))
            && ((this.getAbility() == castOther.getAbility()) || (this
                .getAbility() != null && castOther.getAbility() != null && this
                .getAbility().equals(castOther.getAbility())));
    }
    
    public int hashCode() {
        int result = 17;
        
        result = 37 * result + (getUserId() == null ? 0
            : this.getUserId().hashCode());
        result = 37 * result + (getAbility() == null ? 0
            : this.getAbility().hashCode());
        return result;
    }
    
}
