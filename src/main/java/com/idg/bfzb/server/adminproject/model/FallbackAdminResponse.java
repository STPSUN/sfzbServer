package com.idg.bfzb.server.adminproject.model;

import com.idg.bfzb.server.project.model.dto.ProjectRevealEntity;

public class FallbackAdminResponse extends ProjectRevealEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String projectName;
	private Float budget;
	private Integer isReveal;
	private Float revealScale;
	private String tenderUserName;
	private String userName;
	private String nickName;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Float getBudget() {
		return budget;
	}
	public void setBudget(Float budget) {
		this.budget = budget;
	}
	public Integer getIsReveal() {
		return isReveal;
	}
	public void setIsReveal(Integer isReveal) {
		this.isReveal = isReveal;
	}
	public Float getRevealScale() {
		return revealScale;
	}
	public void setRevealScale(Float revealScale) {
		this.revealScale = revealScale;
	}
	public String getTenderUserName() {
		return tenderUserName;
	}
	public void setTenderUserName(String tenderUserName) {
		this.tenderUserName = tenderUserName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
