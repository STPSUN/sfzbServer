package com.idg.bfzb.server.adminproject.model;

public class RejectionAdminRequest{
	private String adminReviewStateSea;
	private String projectNameSea;
	private String qryBeginTime;
	private String qryEndTime;
	
	public String getAdminReviewStateSea() {
		return adminReviewStateSea;
	}
	public void setAdminReviewStateSea(String adminReviewStateSea) {
		this.adminReviewStateSea = adminReviewStateSea;
	}
	public String getProjectNameSea() {
		return projectNameSea;
	}
	public void setProjectNameSea(String projectNameSea) {
		this.projectNameSea = projectNameSea;
	}
	public String getQryBeginTime() {
		return qryBeginTime;
	}
	public void setQryBeginTime(String qryBeginTime) {
		this.qryBeginTime = qryBeginTime;
	}
	public String getQryEndTime() {
		return qryEndTime;
	}
	public void setQryEndTime(String qryEndTime) {
		this.qryEndTime = qryEndTime;
	}
	
}
