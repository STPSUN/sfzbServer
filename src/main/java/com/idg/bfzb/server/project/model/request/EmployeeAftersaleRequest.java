package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：EmployeeAftersaleRequest
 * 类描述：质保问题雇主申诉request
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class EmployeeAftersaleRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String mobile;
	
	private String content;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
