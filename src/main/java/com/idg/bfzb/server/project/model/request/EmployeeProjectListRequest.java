package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：EmployeeProjectListRequest
 * 类描述：发包者项目列表request
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class EmployeeProjectListRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer offset = 1;
	
	private Integer size = 10;
	
	private String userId;
	
	private Short state;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}
