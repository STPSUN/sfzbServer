package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：TenderUserDetailResponse
 * 类描述：项目招标用户
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class TenderUserDetailResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tenderUserId;
	
	private String tenderUserName;
	
	private String tenderNickName;
	
	private Double star;
	
	private Double offer;
	
	private String description;
	
	private Integer listNum;
	
	private String iconSmallAttchId;
	
	private String iconSmallUrl;

	public Double getStar() {
		return star;
	}

	public void setStar(Double star) {
		this.star = star;
	}

	public Double getOffer() {
		return offer;
	}

	public void setOffer(Double offer) {
		this.offer = offer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getListNum() {
		return listNum;
	}

	public void setListNum(Integer listNum) {
		this.listNum = listNum;
	}

	public String getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public String getTenderUserName() {
		return tenderUserName;
	}

	public void setTenderUserName(String tenderUserName) {
		this.tenderUserName = tenderUserName;
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

	public String getTenderNickName() {
		return tenderNickName;
	}

	public void setTenderNickName(String tenderNickName) {
		this.tenderNickName = tenderNickName;
	}

}
