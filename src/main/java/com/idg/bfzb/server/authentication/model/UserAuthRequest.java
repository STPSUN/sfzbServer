package com.idg.bfzb.server.authentication.model;

import java.io.Serializable;

public class UserAuthRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userRealName;
	private String idCardNumber;
	
	private String frontBatchId;
	private String backBatchId;
	private String holdBatchId;
	//身份证正面地址
	private String frontImageUrl;
	//身份证照背面地址
	private String backImageUrl;
	//持身份证照片地址
	private String holdImageUrl;
	
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getFrontBatchId() {
		return frontBatchId;
	}
	public void setFrontBatchId(String frontBatchId) {
		this.frontBatchId = frontBatchId;
	}
	public String getBackBatchId() {
		return backBatchId;
	}
	public void setBackBatchId(String backBatchId) {
		this.backBatchId = backBatchId;
	}
	public String getHoldBatchId() {
		return holdBatchId;
	}
	public void setHoldBatchId(String holdBatchId) {
		this.holdBatchId = holdBatchId;
	}
	public String getFrontImageUrl() {
		return frontImageUrl;
	}
	public void setFrontImageUrl(String frontImageUrl) {
		this.frontImageUrl = frontImageUrl;
	}
	public String getBackImageUrl() {
		return backImageUrl;
	}
	public void setBackImageUrl(String backImageUrl) {
		this.backImageUrl = backImageUrl;
	}
	public String getHoldImageUrl() {
		return holdImageUrl;
	}
	public void setHoldImageUrl(String holdImageUrl) {
		this.holdImageUrl = holdImageUrl;
	}
	
}
