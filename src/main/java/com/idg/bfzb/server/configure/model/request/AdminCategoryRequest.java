package com.idg.bfzb.server.configure.model.request;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class AdminCategoryRequest implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String categoryId;
    private String categoryName;
    private String parentId;
    private Timestamp createTime;
    private Timestamp lastModified;
    private List<String> abilityIds;
    
    public String getCategoryId() {
        return this.categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return this.categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getParentId() {
        return this.parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
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
    public List<String> getAbilityIds(){
        return this.abilityIds;
    }
    public void setAbilityIds(List<String> abilityIds) {
        this.abilityIds = abilityIds;
    }
}
