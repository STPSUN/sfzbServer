package com.idg.bfzb.server.project.model.vo;

import java.io.Serializable;

/**
 * 类名称：TenderUserDetailVo
 * 类描述：投标用户vo
 * 创建人：ouzhb
 * 创建时间：2016/11/6
 */
public class TenderUserDetailVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tenderUserId;
	
	private String userName;
	
	private String nickName;
	
	private Double offer;
	
	private String description;
	
	private String iconSmallAttchId;
	
	private String iconSmallUrl;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getOffer() {
		return offer;
	}

	public void setOffer(Double offer) {
		this.offer = offer;
	}

	public String getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public String getIconSmallAttchId() {
		return iconSmallAttchId;
	}

	public void setIconSmallAttchId(String iconSmallAttchId) {
		this.iconSmallAttchId = iconSmallAttchId;
	}

	public String getIconSmallUrl() {
		return iconSmallUrl;
	}

	public void setIconSmallUrl(String iconSmallUrl) {
		this.iconSmallUrl = iconSmallUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
