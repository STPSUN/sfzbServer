package com.idg.bfzb.server.configure.model.response;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 类名称：CategoryResponse
 * 类描述：项目分类response
 * 创建人：chentao
 * 创建时间：2016/11/24
 */
public class AdminCategoryResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String categoryId;
	private String categoryName;
	private String parentId;
	private String parentName;
	private String abilityIds;
	private String abilityNames;
	private Timestamp createTime;
    private Timestamp lastModified;

	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getAbilityIds() {
        return abilityIds;
    }
    public void setAbilityIds(String abilityIds) {
        this.abilityIds = abilityIds;
    }
    public String getAbilityNames() {
        return abilityNames;
    }
    public void setAbilityNames(String abilityNames) {
        this.abilityNames = abilityNames;
    }
    public Timestamp getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    public Timestamp getLastModified() {
        return this.lastModified;
    }
    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}
