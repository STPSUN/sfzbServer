package com.idg.bfzb.server.adminfinance.model.request;

import java.sql.Timestamp;

public class FinanceTradeDetailRequest{
	private Long recordId;
    private String payUserId;
    private String payUserName;
    private String incomeUserId;
    private String incomeUserName;
    private String targetType;
    private String targetId;
    private Double money;
    private String transType;
    private Short tradeState;
    private String tradePlatform;
    private String tradeNo;
    private String tradeExtend;
    private Timestamp tradeTime;
    private String outTradeNo;
    private String userId;
    private String userName;
    private String qryStartTime;//开始时间
    private String qryEndTime;//结束时间
    private String nickName;//用户昵称
    private String mobile;//手机号
    
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public String getPayUserId() {
		return payUserId;
	}
	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}
	public String getPayUserName() {
		return payUserName;
	}
	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}
	public String getIncomeUserId() {
		return incomeUserId;
	}
	public void setIncomeUserId(String incomeUserId) {
		this.incomeUserId = incomeUserId;
	}
	public String getIncomeUserName() {
		return incomeUserName;
	}
	public void setIncomeUserName(String incomeUserName) {
		this.incomeUserName = incomeUserName;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Short getTradeState() {
		return tradeState;
	}
	public void setTradeState(Short tradeState) {
		this.tradeState = tradeState;
	}
	public String getTradePlatform() {
		return tradePlatform;
	}
	public void setTradePlatform(String tradePlatform) {
		this.tradePlatform = tradePlatform;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTradeExtend() {
		return tradeExtend;
	}
	public void setTradeExtend(String tradeExtend) {
		this.tradeExtend = tradeExtend;
	}
	public Timestamp getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Timestamp tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
    
}