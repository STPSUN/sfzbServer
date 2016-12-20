package com.idg.bfzb.server.project.model.dto;

// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProjectAbilityEntityId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class ProjectAbilityEntityId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String abilityId;
	private String projectId;

	// Constructors

	/** default constructor */
	public ProjectAbilityEntityId() {
	}

	/** full constructor */
	public ProjectAbilityEntityId(String abilityId, String projectId) {
		this.abilityId = abilityId;
		this.projectId = projectId;
	}

	// Property accessors

	@Column(name = "ability_id", nullable = false, length = 64)
	public String getAbilityId() {
		return this.abilityId;
	}

	public void setAbilityId(String abilityId) {
		this.abilityId = abilityId;
	}

	@Column(name = "project_id", nullable = false, length = 64)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProjectAbilityEntityId))
			return false;
		ProjectAbilityEntityId castOther = (ProjectAbilityEntityId) other;

		return ((this.getAbilityId() == castOther.getAbilityId()) || (this
				.getAbilityId() != null && castOther.getAbilityId() != null && this
				.getAbilityId().equals(castOther.getAbilityId())))
				&& ((this.getProjectId() == castOther.getProjectId()) || (this
						.getProjectId() != null
						&& castOther.getProjectId() != null && this
						.getProjectId().equals(castOther.getProjectId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getAbilityId() == null ? 0 : this.getAbilityId().hashCode());
		result = 37 * result
				+ (getProjectId() == null ? 0 : this.getProjectId().hashCode());
		return result;
	}

}