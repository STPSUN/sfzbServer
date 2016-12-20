package com.idg.bfzb.server.adminfinance.model.request;

public class FinanceWithdrawRequest {
	private Long recordId;
	private String applyUserId;
	private Double applyMoney;
	private String applyBank;//提现银行
    private String applyType;//提现类型 支付宝 alipay | 银联 unionpay'
    private String applyCode;//提现账户号（银行卡号/支付宝账号)
    private String applyCodeName;//提现账户户名
    private String reviewReason;//处理原因
	private String applyTime;
	private String reviewState;
	private String reviewAdminId;
	private String reviewTime;
	private String mobile;//平台账号
	
	private String qryStartTime;//开始时间
    private String qryEndTime;//结束时间
    
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public Double getApplyMoney() {
		return applyMoney;
	}
	public void setApplyMoney(Double applyMoney) {
		this.applyMoney = applyMoney;
	}
	public String getApplyBank() {
		return applyBank;
	}
	public void setApplyBank(String applyBank) {
		this.applyBank = applyBank;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public String getApplyCodeName() {
		return applyCodeName;
	}
	public void setApplyCodeName(String applyCodeName) {
		this.applyCodeName = applyCodeName;
	}
	public String getReviewReason() {
		return reviewReason;
	}
	public void setReviewReason(String reviewReason) {
		this.reviewReason = reviewReason;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getReviewState() {
		return reviewState;
	}
	public void setReviewState(String reviewState) {
		this.reviewState = reviewState;
	}
	public String getReviewAdminId() {
		return reviewAdminId;
	}
	public void setReviewAdminId(String reviewAdminId) {
		this.reviewAdminId = reviewAdminId;
	}
	public String getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getQryStartTime() {
		return qryStartTime;
	}
	public void setQryStartTime(String qryStartTime) {
		this.qryStartTime = qryStartTime;
	}
	public String getQryEndTime() {
		return qryEndTime;
	}
	public void setQryEndTime(String qryEndTime) {
		this.qryEndTime = qryEndTime;
	}
}
