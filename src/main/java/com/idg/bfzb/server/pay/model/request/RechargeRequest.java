package com.idg.bfzb.server.pay.model.request;

public class RechargeRequest{
	private Double fee;
	private String bankName;//汇款银行
	private String bankAccountNumber;//卡号
	private String bankAccountName;//户名
	private String bankSerialId;//银行流水号
	private String termType;//pc mobile
	private String scripAttchId;//交易凭证附件ID
	private String remark;//备注

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
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

	public String getBankSerialId() {
		return bankSerialId;
	}

	public void setBankSerialId(String bankSerialId) {
		this.bankSerialId = bankSerialId;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getScripAttchId() {
		return scripAttchId;
	}

	public void setScripAttchId(String scripAttchId) {
		this.scripAttchId = scripAttchId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}