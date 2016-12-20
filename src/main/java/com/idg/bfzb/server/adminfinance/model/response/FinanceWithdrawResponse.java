package com.idg.bfzb.server.adminfinance.model.response;

import com.idg.bfzb.server.pay.model.dto.FinanceWithdrawApplyEntity;

public class FinanceWithdrawResponse extends FinanceWithdrawApplyEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nickName;
	private String mobile;
	private String idcardCode;
	private String reviewAdminName;
	private String realName;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdcardCode() {
		return idcardCode;
	}
	public void setIdcardCode(String idcardCode) {
		this.idcardCode = idcardCode;
	}
	public String getReviewAdminName() {
		return reviewAdminName;
	}
	public void setReviewAdminName(String reviewAdminName) {
		this.reviewAdminName = reviewAdminName;
	}
	
}
