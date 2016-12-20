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
 * ProjectAbilityEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_project_ability")
public class ProjectAbilityEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private ProjectAbilityEntityId id;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public ProjectAbilityEntity() {
	}

	/** full constructor */
	public ProjectAbilityEntity(ProjectAbilityEntityId id, Timestamp createTime) {
		this.id = id;
		this.createTime = createTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "abilityId", column = @Column(name = "ability_id", nullable = false, length = 64)),
			@AttributeOverride(name = "projectId", column = @Column(name = "project_id", nullable = false, length = 64)) })
	public ProjectAbilityEntityId getId() {
		return this.id;
	}

	public void setId(ProjectAbilityEntityId id) {
		this.id = id;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}