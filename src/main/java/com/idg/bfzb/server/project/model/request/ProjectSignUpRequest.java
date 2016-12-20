package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：ProjectSignUpRequest
 * 类描述：项目报名request
 * 创建人：ouzhb
 * 创建时间：2016/11/8
 */
public class ProjectSignUpRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private Double offer;
	
	private String description;
	
	private String userId;
	
	private String mobile;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Double getOffer() {
		return offer;
	}

	public void setOffer(Double offer) {
		this.offer = offer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
