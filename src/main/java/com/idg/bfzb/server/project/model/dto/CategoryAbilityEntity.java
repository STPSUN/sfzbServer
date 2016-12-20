package com.idg.bfzb.server.project.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CategoryAbilityEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_category_ability")
public class CategoryAbilityEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private CategoryAbilityEntityId id;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public CategoryAbilityEntity() {
	}

	/** full constructor */
	public CategoryAbilityEntity(CategoryAbilityEntityId id,
			Timestamp updateTime) {
		this.id = id;
		this.updateTime = updateTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "categoryId", column = @Column(name = "category_id", nullable = false, length = 64)),
			@AttributeOverride(name = "abilityId", column = @Column(name = "ability_id", nullable = false, length = 64)) })
	public CategoryAbilityEntityId getId() {
		return this.id;
	}

	public void setId(CategoryAbilityEntityId id) {
		this.id = id;
	}

	@Column(name = "update_time", nullable = false, length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}