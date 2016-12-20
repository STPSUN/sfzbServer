package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：ProjectPublishResponse
 * 类描述：项目发布
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class ProjectPublishResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
