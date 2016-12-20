package com.idg.bfzb.server.project.model.vo;

import java.io.Serializable;

/**
 * 类名称：ProjectCategoryAbilityVo
 * 类描述：项目分类技能vo
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class ProjectCategoryAbilityVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String projectId;
	
	private String categoryId;
	
	private String abilityId;
	
	private String abilityName;
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getAbilityId() {
		return abilityId;
	}
	public void setAbilityId(String abilityId) {
		this.abilityId = abilityId;
	}
	public String getAbilityName() {
		return abilityName;
	}
	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}

}
