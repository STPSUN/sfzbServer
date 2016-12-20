package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：ProjectActiveRevealRequest
 * 类描述：项目激活兜底request
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class ProjectActiveRevealRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String mobile;
	
	private String applyContent;
	
	private String employerId;

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

	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	public String getEmployerId() {
		return employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

}
