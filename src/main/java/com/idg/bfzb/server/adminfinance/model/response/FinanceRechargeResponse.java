package com.idg.bfzb.server.adminfinance.model.response;

import java.sql.Timestamp;

public class FinanceRechargeResponse{
	//资金流水表信息
	private Long recordId;//流水号
    private String payUserId;//支付方
    private String payUserName;
    private String incomeUserId;//收入方
    private String incomeUserName;
    private String targetType;//交易对象类型（业务类型）
    private String targetId;//交易对象（项目）
    private String targetName;
    private Double money;//交易金额
    private String transType;//交易方式（交易类型）
    private Short tradeState;//（对应充值状态）交易状态，0未打款，1已打款 ， -1打款失败
    private String tradePlatform;//（对应充值方式）第三方平台类型:支付宝 alipay | 微信wechatpay | 银联 unionpay| 线下 offline
    private String tradeNo;//第三方流水号
    private String tradeExtend;//第三方扩展
    private Timestamp tradeTime;//交易时间（充值时间）
    private String outTradeNo;//商户流水号
    
    //用户表信息
    private String mobile;//联系手机
    private String nickName;//用户昵称
    private String buyerLogonName;//买家账号名称(支付账号名称)
    private String buyerLogonId;//买家账号(充值账号)
    private String idcardCode;//身份证号
    private String realName;//真实姓名
    
    //线下充值扩展
    private String bankName;
    private String bankAccountNumber;
    private String bankAccountName;
    private String bankAccountTime;//到账时间
    private String bankSerialId;//银行流水号
    private Short reviewState;
    private String reviewAdminId;
    private Timestamp reviewTime;
    private Timestamp createTime;
    
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
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getBuyerLogonName() {
		return buyerLogonName;
	}
	public void setBuyerLogonName(String buyerLogonName) {
		this.buyerLogonName = buyerLogonName;
	}
	public String getBuyerLogonId() {
		return buyerLogonId;
	}
	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}
	public String getIdcardCode() {
		return idcardCode;
	}
	public void setIdcardCode(String idcardCode) {
		this.idcardCode = idcardCode;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankAccountTime() {
		return bankAccountTime;
	}
	public void setBankAccountTime(String bankAccountTime) {
		this.bankAccountTime = bankAccountTime;
	}
	public String getBankSerialId() {
		return bankSerialId;
	}
	public void setBankSerialId(String bankSerialId) {
		this.bankSerialId = bankSerialId;
	}
	public Short getReviewState() {
		return reviewState;
	}
	public void setReviewState(Short reviewState) {
		this.reviewState = reviewState;
	}
	public String getReviewAdminId() {
		return reviewAdminId;
	}
	public void setReviewAdminId(String reviewAdminId) {
		this.reviewAdminId = reviewAdminId;
	}
	public Timestamp getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
    
}