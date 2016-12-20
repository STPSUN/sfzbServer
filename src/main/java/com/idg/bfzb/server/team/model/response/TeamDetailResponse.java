package com.idg.bfzb.server.team.model.response;

import java.io.Serializable;

public class TeamDetailResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 团队id
	 */
	private String teamId;
	/**
	 * 团队名称
	 */
	private String teamName;
	/**
	 * 团队昵称
	 */
	private String teamDesc;
	/**
	 * 团队联系人姓名
	 */
	private String userName;
	/**
	 * 团队联系人电话
	 */
	private String telephone;
	/**
	 * 所在城市
	 */
	private String city;
	/**
	 * 团队服务内容
	 */
	private String serviceContent;
	/**
	 * 团队服务经验
	 */
	private String serviceExp;
	/**
	 * 团队状态
	 */
	private Short state;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamDesc() {
		return teamDesc;
	}

	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public String getServiceExp() {
		return serviceExp;
	}

	public void setServiceExp(String serviceExp) {
		this.serviceExp = serviceExp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

}
