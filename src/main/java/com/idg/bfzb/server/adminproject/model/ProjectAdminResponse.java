package com.idg.bfzb.server.adminproject.model;

import com.idg.bfzb.server.project.model.dto.ProjectEntity;

public class ProjectAdminResponse extends ProjectEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//用户名
	private String userName;
	//昵称
	private String nickName;
	//真实姓名
	private String realName;
	//接包者相关信息
	//用户名
	private String tenderUserName;
	//昵称
	private String tenderNickName;
	//真实姓名
	private String tenderRealName;
	
	private String userMobel;
	private String idcardCode;
	private String hisOrderCount;
	private String countStars;
	
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getHisOrderCount() {
		return hisOrderCount;
	}
	public void setHisOrderCount(String hisOrderCount) {
		this.hisOrderCount = hisOrderCount;
	}
	public String getUserMobel() {
		return userMobel;
	}
	public void setUserMobel(String userMobel) {
		this.userMobel = userMobel;
	}
	public String getIdcardCode() {
		return idcardCode;
	}
	public void setIdcardCode(String idcardCode) {
		this.idcardCode = idcardCode;
	}
	public String getCountStars() {
		return countStars;
	}
	public void setCountStars(String countStars) {
		this.countStars = countStars;
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
	public String getTenderRealName() {
		return tenderRealName;
	}
	public void setTenderRealName(String tenderRealName) {
		this.tenderRealName = tenderRealName;
	}
	
}
