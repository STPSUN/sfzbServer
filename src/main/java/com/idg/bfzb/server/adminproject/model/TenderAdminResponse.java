package com.idg.bfzb.server.adminproject.model;

import com.idg.bfzb.server.project.model.dto.ProjectTenderEntity;

public class TenderAdminResponse extends ProjectTenderEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectName;
	private String tenderUserName;
	private String tenderNickName;
	private String emploperUserName;
	private String emploperNickName;
	private String tenderType;
	private Integer projectState;
	private Integer isMargin;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTenderUserName() {
		return tenderUserName;
	}
	public void setTenderUserName(String tenderUserName) {
		this.tenderUserName = tenderUserName;
	}
	public String getTenderNickName() {
		return tenderNickName;
	}
	public void setTenderNickName(String tenderNickName) {
		this.tenderNickName = tenderNickName;
	}
	public String getEmploperUserName() {
		return emploperUserName;
	}
	public void setEmploperUserName(String emploperUserName) {
		this.emploperUserName = emploperUserName;
	}
	public String getEmploperNickName() {
		return emploperNickName;
	}
	public void setEmploperNickName(String emploperNickName) {
		this.emploperNickName = emploperNickName;
	}
	public String getTenderType() {
		return tenderType;
	}
	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}
	public Integer getProjectState() {
		return projectState;
	}
	public void setProjectState(Integer projectState) {
		this.projectState = projectState;
	}
	public Integer getIsMargin() {
		return isMargin;
	}
	public void setIsMargin(Integer isMargin) {
		this.isMargin = isMargin;
	}
	
	
}
