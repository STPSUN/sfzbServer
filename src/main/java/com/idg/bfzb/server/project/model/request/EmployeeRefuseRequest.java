package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：EmployeeRefuseRequest
 * 类描述：发包者项目选标request
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class EmployeeRefuseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String employerId;
	
	private String refuseReason;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getEmployerId() {
		return employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

}
