package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：EmployeeSelectTenderRequest
 * 类描述：发包者项目选标request
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class EmployeeSelectTenderRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String employerId;
	
	private String tenderUserId;
	
	private String projectId;
	
	private Short isReveal;

	public String getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Short getIsReveal() {
		return isReveal;
	}

	public void setIsReveal(Short isReveal) {
		this.isReveal = isReveal;
	}

	public String getEmployerId() {
		return employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

}
