package com.idg.bfzb.server.project.model.dto;

// default package

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CategoryEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_category")
public class CategoryEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Fields

	private String categoryId;
	private String categoryName;
	private String parentId;
	private Timestamp createTime;
	private Timestamp lastModified;
	private Short state; 

	// Constructors

	/** default constructor */
	public CategoryEntity() {
	}

	/** minimal constructor */
	public CategoryEntity(String categoryId, String categoryName,
			Timestamp createTime, Timestamp lastModified, Short state) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.createTime = createTime;
		this.lastModified = lastModified;
		this.state = state;
	}

	/** full constructor */
	public CategoryEntity(String categoryId, String categoryName,
			String parentId, Timestamp createTime, Timestamp lastModified, Short state) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.parentId = parentId;
		this.createTime = createTime;
		this.lastModified = lastModified;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "category_id", unique = true, nullable = false, length = 64)
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "category_name", nullable = false, length = 128)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "parent_id", length = 64)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_modified", nullable = false, length = 19)
	public Timestamp getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	@Column(name="state")

    public Short getState() {
        return this.state;
    }
    
    public void setState(Short state) {
        this.state = state;
    }
    
}