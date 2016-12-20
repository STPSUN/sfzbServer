package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：ReceiverAppealRequest
 * 类描述：接包者向客服提起申诉request
 * 创建人：ouzhb
 * 创建时间：2016/11/12
 */
public class ReceiverAppealRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String content;
	
	private String appealAttachs;
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAppealAttachs() {
		return appealAttachs;
	}

	public void setAppealAttachs(String appealAttachs) {
		this.appealAttachs = appealAttachs;
	}

}
