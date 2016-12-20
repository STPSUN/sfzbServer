package com.idg.bfzb.server.usercenter.model.request;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 类名：用户资金请求类
 * 描述：
 * @author chentao
 * 时间：2016/11/19
 */
public class UserFinancialRequest {
	//资金流水信息
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
    
	//项目资金信息
    private String projectId;
    private String employerId;
    private BigDecimal budget;
    private BigDecimal tradeMoney;
    private float imprestScale;
    private float marginScale;
    private float revealScale;
    private Byte isReveal;
    private Byte isMargin;
    
    //用户资金信息
    private String userId;
    private BigDecimal balance;
    private BigDecimal incoming;
    private BigDecimal expenditure;    
	
	public Long getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }


    public String getPayUserId() {
        return this.payUserId;
    }
    
    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId;
    }

    public String getPayUserName() {
        return this.payUserName;
    }
    
    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }

    public String getIncomeUserId() {
        return this.incomeUserId;
    }
    
    public void setIncomeUserId(String incomeUserId) {
        this.incomeUserId = incomeUserId;
    }

    public String getIncomeUserName() {
        return this.incomeUserName;
    }
    
    public void setIncomeUserName(String incomeUserName) {
        this.incomeUserName = incomeUserName;
    }

    public String getTargetType() {
        return this.targetType;
    }
    
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return this.targetId;
    }
    
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Double getMoney() {
        return this.money;
    }
    
    public void setMoney(Double money) {
        this.money = money;
    }

    public String getTransType() {
        return this.transType;
    }
    
    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Short getTradeState() {
        return this.tradeState;
    }
    
    public void setTradeState(Short tradeState) {
        this.tradeState = tradeState;
    }

    public String getTradePlatform() {
        return this.tradePlatform;
    }
    
    public void setTradePlatform(String tradePlatform) {
        this.tradePlatform = tradePlatform;
    }

    public String getTradeNo() {
        return this.tradeNo;
    }
    
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeExtend() {
        return this.tradeExtend;
    }
    
    public void setTradeExtend(String tradeExtend) {
        this.tradeExtend = tradeExtend;
    }

    public Timestamp getTradeTime() {
        return this.tradeTime;
    }
    
    public void setTradeTime(Timestamp tradeTime) {
        this.tradeTime = tradeTime;
    }
    
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getEmployerId() {
        return employerId;
    }
    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }
    public BigDecimal getBudget() {
        return budget;
    }
    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }
    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }
    public float getImprestScale() {
        return imprestScale;
    }
    public void setImprestScale(float imprestScale) {
        this.imprestScale = imprestScale;
    }
    public float getMarginScale() {
        return marginScale;
    }
    public void setMarginScale(float marginScale) {
        this.marginScale = marginScale;
    }
    public float getRevealScale() {
        return revealScale;
    }
    public void setRevealScale(float revealScale) {
        this.revealScale = revealScale;
    }
    public Byte getIsReveal() {
        return isReveal;
    }
    public void setIsReveal(Byte isReveal) {
        this.isReveal = isReveal;
    }
    public Byte getIsMargin() {
        return isMargin;
    }
    public void setIsMargin(Byte isMargin) {
        this.isMargin = isMargin;
    }
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public BigDecimal getIncoming() {
        return incoming;
    }
    public void setIncoming(BigDecimal incoming) {
        this.incoming = incoming;
    }
    public BigDecimal getExpenditure() {
        return expenditure;
    }
    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }
}
