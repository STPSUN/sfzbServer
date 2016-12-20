package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：EmployeeResultResponse
 * 类描述：雇员项目确认完成response
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class EmployeeResultResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private Double remainMoney;
	
	private short state;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Double getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

}
