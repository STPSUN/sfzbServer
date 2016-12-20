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
 * ProjectCategoryEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_project_category")
public class ProjectCategoryEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private ProjectCategoryEntityId id;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public ProjectCategoryEntity() {
	}

	/** full constructor */
	public ProjectCategoryEntity(ProjectCategoryEntityId id,
			Timestamp createTime) {
		this.id = id;
		this.createTime = createTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "requireId", column = @Column(name = "require_id", nullable = false, length = 64)),
			@AttributeOverride(name = "categoryId", column = @Column(name = "category_id", nullable = false, length = 64)) })
	public ProjectCategoryEntityId getId() {
		return this.id;
	}

	public void setId(ProjectCategoryEntityId id) {
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