package com.idg.bfzb.server.team.model.request;

import java.io.Serializable;

/**
 * 类名称：TeamModifyRequest
 * 类描述：团队修改请求体
 * 创建人：ouzhb
 * 创建日期：2016/10/31
 */
public class TeamModifyRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
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
	 * 联系人手机号码
	 */
	private String telephone;
	/**
	 * 联系人身份证
	 */
	private String identifyNum;
	/**
	 * 联系人姓名 
	 */
	private String userName;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
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

	public String getServiceExp() {
		return serviceExp;
	}

	public void setServiceExp(String serviceExp) {
		this.serviceExp = serviceExp;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIdentifyNum() {
		return identifyNum;
	}

	public void setIdentifyNum(String identifyNum) {
		this.identifyNum = identifyNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
}
