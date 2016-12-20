package com.idg.bfzb.server.project.model.dto;

// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProjectCategoryEntityId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class ProjectCategoryEntityId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String requireId;
	private String categoryId;

	// Constructors

	/** default constructor */
	public ProjectCategoryEntityId() {
	}

	/** full constructor */
	public ProjectCategoryEntityId(String requireId, String categoryId) {
		this.requireId = requireId;
		this.categoryId = categoryId;
	}

	// Property accessors

	@Column(name = "require_id", nullable = false, length = 64)
	public String getRequireId() {
		return this.requireId;
	}

	public void setRequireId(String requireId) {
		this.requireId = requireId;
	}

	@Column(name = "category_id", nullable = false, length = 64)
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProjectCategoryEntityId))
			return false;
		ProjectCategoryEntityId castOther = (ProjectCategoryEntityId) other;

		return ((this.getRequireId() == castOther.getRequireId()) || (this
				.getRequireId() != null && castOther.getRequireId() != null && this
				.getRequireId().equals(castOther.getRequireId())))
				&& ((this.getCategoryId() == castOther.getCategoryId()) || (this
						.getCategoryId() != null
						&& castOther.getCategoryId() != null && this
						.getCategoryId().equals(castOther.getCategoryId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRequireId() == null ? 0 : this.getRequireId().hashCode());
		result = 37
				* result
				+ (getCategoryId() == null ? 0 : this.getCategoryId()
						.hashCode());
		return result;
	}

}