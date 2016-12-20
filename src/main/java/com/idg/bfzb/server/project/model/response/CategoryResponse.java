package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：CategoryResponse
 * 类描述：项目分类response
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class CategoryResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String categoryId;
	
	private String categoryName;

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

}
