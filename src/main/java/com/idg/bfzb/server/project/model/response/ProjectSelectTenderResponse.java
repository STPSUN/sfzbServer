package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：ProjectSelectTenderResponse 
 * 类描述：项目选标response 
 * 创建人：ouzhb 
 * 创建时间：2016/11/11
 */
public class ProjectSelectTenderResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	// 项目id
	private String projectId;
	// 投标人
	private String tenderUserId;
	// 投标金额
	private String offer;
	// 预付金额
	private Double imprestMoney;
	// 兜底服务费
	private Double revealMoney;
	// 预付比例
	private Float imprestScale;
	// 兜底比例
	private Float revealScale;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public Double getImprestMoney() {
		return imprestMoney;
	}

	public void setImprestMoney(Double imprestMoney) {
		this.imprestMoney = imprestMoney;
	}

	public Double getRevealMoney() {
		return revealMoney;
	}

	public void setRevealMoney(Double revealMoney) {
		this.revealMoney = revealMoney;
	}

	public Float getImprestScale() {
		return imprestScale;
	}

	public void setImprestScale(Float imprestScale) {
		this.imprestScale = imprestScale;
	}

	public Float getRevealScale() {
		return revealScale;
	}

	public void setRevealScale(Float revealScale) {
		this.revealScale = revealScale;
	}

}
