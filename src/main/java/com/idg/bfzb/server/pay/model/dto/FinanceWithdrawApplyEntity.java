package com.idg.bfzb.server.pay.model.dto;

// default package

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FinanceWithdrawApplyEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_finance_withdraw_apply")
public class FinanceWithdrawApplyEntity implements java.io.Serializable {

	// Fields

	private Long recordId;
	private String applyUserId;
	private Double applyMoney;
	private String applyType;
	private String applyBank;
	private String applyCode;
	private String applyCodeName;
	private Timestamp applyTime;
	private String reviewState;
	private String reviewAdminId;
	private Timestamp reviewTime;
	private String reviewReason;

	// Constructors

	/** default constructor */
	public FinanceWithdrawApplyEntity() {
	}

	/** minimal constructor */
	public FinanceWithdrawApplyEntity(Long recordId,String applyType, Timestamp applyTime,
			Timestamp reviewTime) {
		this.recordId = recordId;
		this.applyType = applyType;
		this.applyTime = applyTime;
		this.reviewTime = reviewTime;
	}

	/** full constructor */
	public FinanceWithdrawApplyEntity(Long recordId,String applyUserId, Double applyMoney,
			String applyType, String applyBank, String applyCode,
			String applyCodeName, Timestamp applyTime, String reviewState,
			String reviewAdminId, Timestamp reviewTime, String reviewReason) {
		this.recordId = recordId;
		this.applyUserId = applyUserId;
		this.applyMoney = applyMoney;
		this.applyType = applyType;
		this.applyBank = applyBank;
		this.applyCode = applyCode;
		this.applyCodeName = applyCodeName;
		this.applyTime = applyTime;
		this.reviewState = reviewState;
		this.reviewAdminId = reviewAdminId;
		this.reviewTime = reviewTime;
		this.reviewReason = reviewReason;
	}

	// Property accessors
	@Id
	@Column(name = "record_id", unique = true, nullable = false)
	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	@Column(name = "apply_user_id", length = 64)
	public String getApplyUserId() {
		return this.applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	@Column(name = "apply_money", precision = 10)
	public Double getApplyMoney() {
		return this.applyMoney;
	}

	public void setApplyMoney(Double applyMoney) {
		this.applyMoney = applyMoney;
	}

	@Column(name = "apply_type", nullable = false, length = 32)
	public String getApplyType() {
		return this.applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	@Column(name = "apply_bank", length = 200)
	public String getApplyBank() {
		return this.applyBank;
	}

	public void setApplyBank(String applyBank) {
		this.applyBank = applyBank;
	}

	@Column(name = "apply_code", length = 50)
	public String getApplyCode() {
		return this.applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	@Column(name = "apply_code_name", length = 200)
	public String getApplyCodeName() {
		return this.applyCodeName;
	}

	public void setApplyCodeName(String applyCodeName) {
		this.applyCodeName = applyCodeName;
	}

	@Column(name = "apply_time", nullable = false, length = 19)
	public Timestamp getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "review_state", length = 32)
	public String getReviewState() {
		return this.reviewState;
	}

	public void setReviewState(String reviewState) {
		this.reviewState = reviewState;
	}

	@Column(name = "review_admin_id", length = 64)
	public String getReviewAdminId() {
		return this.reviewAdminId;
	}

	public void setReviewAdminId(String reviewAdminId) {
		this.reviewAdminId = reviewAdminId;
	}

	@Column(name = "review_time", nullable = false, length = 19)
	public Timestamp getReviewTime() {
		return this.reviewTime;
	}

	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}

	@Column(name = "review_reason", length = 500)
	public String getReviewReason() {
		return this.reviewReason;
	}

	public void setReviewReason(String reviewReason) {
		this.reviewReason = reviewReason;
	}

}