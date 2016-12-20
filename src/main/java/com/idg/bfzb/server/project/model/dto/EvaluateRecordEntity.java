package com.idg.bfzb.server.project.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EvaluateRecordEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_evaluate_record")
public class EvaluateRecordEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Long evaluateId;
	private String targetType;
	private String targetId;
	private String targetOwnerId;
	private String userId;
	private String content;
	private Integer star;
	private String extendData;
	private Short state;
	private Timestamp createTime;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public EvaluateRecordEntity() {
	}

	/** minimal constructor */
	public EvaluateRecordEntity(String targetType, String targetId,
			String targetOwnerId, String userId, Timestamp createTime,
			Timestamp updateTime) {
		this.targetType = targetType;
		this.targetId = targetId;
		this.targetOwnerId = targetOwnerId;
		this.userId = userId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public EvaluateRecordEntity(String targetType, String targetId,
			String targetOwnerId, String userId, String content, Integer star,
			String extendData, Short state, Timestamp createTime,
			Timestamp updateTime) {
		this.targetType = targetType;
		this.targetId = targetId;
		this.targetOwnerId = targetOwnerId;
		this.userId = userId;
		this.content = content;
		this.star = star;
		this.extendData = extendData;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "evaluate_id", unique = true, nullable = false)
	public Long getEvaluateId() {
		return this.evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	@Column(name = "target_type", nullable = false, length = 128)
	public String getTargetType() {
		return this.targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	@Column(name = "target_id", nullable = false, length = 64)
	public String getTargetId() {
		return this.targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	@Column(name = "target_owner_id", nullable = false, length = 64)
	public String getTargetOwnerId() {
		return this.targetOwnerId;
	}

	public void setTargetOwnerId(String targetOwnerId) {
		this.targetOwnerId = targetOwnerId;
	}

	@Column(name = "user_id", nullable = false, length = 64)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "star")
	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	@Column(name = "extend_data", length = 65535)
	public String getExtendData() {
		return this.extendData;
	}

	public void setExtendData(String extendData) {
		this.extendData = extendData;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", nullable = false, length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}