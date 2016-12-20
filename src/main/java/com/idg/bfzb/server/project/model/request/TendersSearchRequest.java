package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：TendersSearchRequest
 * 类描述：投标用户详情request
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class TendersSearchRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private Integer offset = 1;
	
	private Integer size = 10;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
