package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：ConfirmProjectResponse
 * 类描述：项目确认
 * 创建人：ouzhb
 * 创建时间：2016/11/9
 */
public class ConfirmProjectResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private Short state;

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
