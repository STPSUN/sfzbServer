package com.idg.bfzb.server.pay.model.dto;
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * FinanceTradeDetailEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_finance_trade_detail")

public class FinanceTradeDetailEntity  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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


    // Constructors

    /** default constructor */
    public FinanceTradeDetailEntity() {
    }

	/** minimal constructor */
    public FinanceTradeDetailEntity(String payUserId, String incomeUserId, Timestamp tradeTime) {
        this.payUserId = payUserId;
        this.incomeUserId = incomeUserId;
        this.tradeTime = tradeTime;
    }
    
    /** full constructor */
    public FinanceTradeDetailEntity(String payUserId, String payUserName, String incomeUserId, String incomeUserName, String targetType, String targetId, Double money, String transType, Short tradeState, String tradePlatform, String tradeNo, String tradeExtend, Timestamp tradeTime, String outTradeNo) {
        this.payUserId = payUserId;
        this.payUserName = payUserName;
        this.incomeUserId = incomeUserId;
        this.incomeUserName = incomeUserName;
        this.targetType = targetType;
        this.targetId = targetId;
        this.money = money;
        this.transType = transType;
        this.tradeState = tradeState;
        this.tradePlatform = tradePlatform;
        this.tradeNo = tradeNo;
        this.tradeExtend = tradeExtend;
        this.tradeTime = tradeTime;
        this.outTradeNo = outTradeNo;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="record_id", unique=true, nullable=false)

    public Long getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
    
    @Column(name="pay_user_id", nullable=false, length=64)

    public String getPayUserId() {
        return this.payUserId;
    }
    
    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId;
    }
    
    @Column(name="pay_user_name", length=64)

    public String getPayUserName() {
        return this.payUserName;
    }
    
    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }
    
    @Column(name="income_user_id", nullable=false, length=64)

    public String getIncomeUserId() {
        return this.incomeUserId;
    }
    
    public void setIncomeUserId(String incomeUserId) {
        this.incomeUserId = incomeUserId;
    }
    
    @Column(name="income_user_name", length=64)

    public String getIncomeUserName() {
        return this.incomeUserName;
    }
    
    public void setIncomeUserName(String incomeUserName) {
        this.incomeUserName = incomeUserName;
    }
    
    @Column(name="target_type", length=64)

    public String getTargetType() {
        return this.targetType;
    }
    
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
    
    @Column(name="target_id", length=64)

    public String getTargetId() {
        return this.targetId;
    }
    
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
    
    @Column(name="money", precision=10)

    public Double getMoney() {
        return this.money;
    }
    
    public void setMoney(Double money) {
        this.money = money;
    }
    
    @Column(name="trans_type", length=32)

    public String getTransType() {
        return this.transType;
    }
    
    public void setTransType(String transType) {
        this.transType = transType;
    }
    
    @Column(name="trade_state")

    public Short getTradeState() {
        return this.tradeState;
    }
    
    public void setTradeState(Short tradeState) {
        this.tradeState = tradeState;
    }
    
    @Column(name="trade_platform", length=32)

    public String getTradePlatform() {
        return this.tradePlatform;
    }
    
    public void setTradePlatform(String tradePlatform) {
        this.tradePlatform = tradePlatform;
    }
    
    @Column(name="trade_no", length=64)

    public String getTradeNo() {
        return this.tradeNo;
    }
    
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
    
    @Column(name="trade_extend", length=1024)

    public String getTradeExtend() {
        return this.tradeExtend;
    }
    
    public void setTradeExtend(String tradeExtend) {
        this.tradeExtend = tradeExtend;
    }
    
    @Column(name="trade_time", nullable=false, length=19)

    public Timestamp getTradeTime() {
        return this.tradeTime;
    }
    
    public void setTradeTime(Timestamp tradeTime) {
        this.tradeTime = tradeTime;
    }
    
    @Column(name="out_trade_no", length=64)

    public String getOutTradeNo() {
        return this.outTradeNo;
    }
    
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
   








}