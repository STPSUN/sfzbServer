package com.idg.bfzb.server.usercenter.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UcJpushCodeEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "uc_jpush_code")
public class UcJpushCodeEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Long recId;
	private String userUuid;
	private String jpushCode;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public UcJpushCodeEntity() {
	}

	/** minimal constructor */
	public UcJpushCodeEntity(String userUuid, String jpushCode) {
		this.userUuid = userUuid;
		this.jpushCode = jpushCode;
	}

	/** full constructor */
	public UcJpushCodeEntity(String userUuid, String jpushCode,
			Timestamp createTime) {
		this.userUuid = userUuid;
		this.jpushCode = jpushCode;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "rec_id", unique = true, nullable = false)
	public Long getRecId() {
		return this.recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	@Column(name = "user_uuid", nullable = false, length = 36)
	public String getUserUuid() {
		return this.userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	@Column(name = "jpush_code", nullable = false)
	public String getJpushCode() {
		return this.jpushCode;
	}

	public void setJpushCode(String jpushCode) {
		this.jpushCode = jpushCode;
	}

	@Column(name="create_time", nullable=false, length=19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}