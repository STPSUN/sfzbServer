package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：ReceiverReportRequest
 * 类描述：提交工作内容request
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class ReceiverReportRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String projectId;
    
    private String userId;
    
    private String location;
    
    private String workDesc;
    
    private String workPic;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getWorkDesc() {
		return workDesc;
	}

	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}

	public String getWorkPic() {
		return workPic;
	}

	public void setWorkPic(String workPic) {
		this.workPic = workPic;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
