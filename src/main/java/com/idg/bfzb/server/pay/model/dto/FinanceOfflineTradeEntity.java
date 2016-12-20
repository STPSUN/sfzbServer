package com.idg.bfzb.server.pay.model.dto;
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * FinanceOfflineTradeEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_finance_offline_trade")

public class FinanceOfflineTradeEntity  implements java.io.Serializable {


    // Fields    

     private Long recordId;
     private String bankName;
     private String bankAccountNumber;
     private String bankAccountName;
     private Timestamp bankAccountTime;
     private String bankSerialId;
     private String scripAttchId;
     private String remark;
     private Double money;
     private Short reviewState;
     private String reviewAdminId;
     private Timestamp reviewTime;
     private Timestamp createTime;


    // Constructors

    /** default constructor */
    public FinanceOfflineTradeEntity() {
    }

	/** minimal constructor */
    public FinanceOfflineTradeEntity(Long recordId, Timestamp reviewTime, Timestamp createTime) {
        this.recordId = recordId;
        this.reviewTime = reviewTime;
        this.createTime = createTime;
    }
    
    /** full constructor */
    public FinanceOfflineTradeEntity(Long recordId, String bankName, String bankAccountNumber, String bankAccountName, Timestamp bankAccountTime, String bankSerialId, String scripAttchId, String remark, Double money, Short reviewState, String reviewAdminId, Timestamp reviewTime, Timestamp createTime) {
        this.recordId = recordId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountName = bankAccountName;
        this.bankAccountTime = bankAccountTime;
        this.bankSerialId = bankSerialId;
        this.scripAttchId = scripAttchId;
        this.remark = remark;
        this.money = money;
        this.reviewState = reviewState;
        this.reviewAdminId = reviewAdminId;
        this.reviewTime = reviewTime;
        this.createTime = createTime;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="record_id", unique=true, nullable=false)

    public Long getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
    
    @Column(name="bank_name", length=128)

    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    @Column(name="bank_account_number", length=64)

    public String getBankAccountNumber() {
        return this.bankAccountNumber;
    }
    
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    
    @Column(name="bank_account_Name", length=128)

    public String getBankAccountName() {
        return this.bankAccountName;
    }
    
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }
    
    @Column(name="bank_account_time", length=19)

    public Timestamp getBankAccountTime() {
        return this.bankAccountTime;
    }
    
    public void setBankAccountTime(Timestamp bankAccountTime) {
        this.bankAccountTime = bankAccountTime;
    }
    
    @Column(name="bank_serial_id", length=64)

    public String getBankSerialId() {
        return this.bankSerialId;
    }
    
    public void setBankSerialId(String bankSerialId) {
        this.bankSerialId = bankSerialId;
    }
    
    @Column(name="scrip_attch_id", length=64)

    public String getScripAttchId() {
        return this.scripAttchId;
    }
    
    public void setScripAttchId(String scripAttchId) {
        this.scripAttchId = scripAttchId;
    }
    
    @Column(name="remark", length=1000)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="money", precision=10)

    public Double getMoney() {
        return this.money;
    }
    
    public void setMoney(Double money) {
        this.money = money;
    }
    
    @Column(name="review_state")

    public Short getReviewState() {
        return this.reviewState;
    }
    
    public void setReviewState(Short reviewState) {
        this.reviewState = reviewState;
    }
    
    @Column(name="review_admin_id", length=64)

    public String getReviewAdminId() {
        return this.reviewAdminId;
    }
    
    public void setReviewAdminId(String reviewAdminId) {
        this.reviewAdminId = reviewAdminId;
    }
    
    @Column(name="review_time", nullable=false, length=19)

    public Timestamp getReviewTime() {
        return this.reviewTime;
    }
    
    public void setReviewTime(Timestamp reviewTime) {
        this.reviewTime = reviewTime;
    }
    
    @Column(name="create_time", nullable=false, length=19)

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
   








}