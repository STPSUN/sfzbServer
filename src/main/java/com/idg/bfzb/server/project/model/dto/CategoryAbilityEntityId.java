package com.idg.bfzb.server.project.model.dto;

// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CategoryAbilityEntityId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class CategoryAbilityEntityId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String categoryId;
	private String abilityId;
	private Short state;

	// Constructors

	/** default constructor */
	public CategoryAbilityEntityId() {
	}

	/** full constructor */
	public CategoryAbilityEntityId(String categoryId, String abilityId, Short state) {
		this.categoryId = categoryId;
		this.abilityId = abilityId;
		this.state = state;
	}

	// Property accessors

	@Column(name = "category_id", nullable = false, length = 64)
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "ability_id", nullable = false, length = 64)
	public String getAbilityId() {
		return this.abilityId;
	}

	public void setAbilityId(String abilityId) {
		this.abilityId = abilityId;
	}
	
	@Column(name="state")

    public Short getState() {
        return this.state;
    }
    
    public void setState(Short state) {
        this.state = state;
    }

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CategoryAbilityEntityId))
			return false;
		CategoryAbilityEntityId castOther = (CategoryAbilityEntityId) other;

		return ((this.getCategoryId() == castOther.getCategoryId()) || (this
				.getCategoryId() != null && castOther.getCategoryId() != null && this
				.getCategoryId().equals(castOther.getCategoryId())))
				&& ((this.getAbilityId() == castOther.getAbilityId()) || (this
						.getAbilityId() != null
						&& castOther.getAbilityId() != null && this
						.getAbilityId().equals(castOther.getAbilityId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCategoryId() == null ? 0 : this.getCategoryId()
						.hashCode());
		result = 37 * result
				+ (getAbilityId() == null ? 0 : this.getAbilityId().hashCode());
		return result;
	}

}