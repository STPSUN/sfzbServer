package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：EvaluateUserRequest
 * 类描述：评价request
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class EvaluateUserRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private Integer star;
	
	private String content;
	
	private String targetType;
	
	private String userId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

}
