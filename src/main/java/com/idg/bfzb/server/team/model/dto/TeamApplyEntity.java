package com.idg.bfzb.server.team.model.dto;

// default package

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TeamApplyEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_team_apply", catalog = "bfzb")
public class TeamApplyEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private TeamApplyEntityId id;
	private String description;
	private Timestamp applyTime;
	private Short reviewState;
	private Timestamp reviewTime;

	// Constructors

	/** default constructor */
	public TeamApplyEntity() {
	}

	/** minimal constructor */
	public TeamApplyEntity(TeamApplyEntityId id, Timestamp applyTime,
			Timestamp reviewTime) {
		this.id = id;
		this.applyTime = applyTime;
		this.reviewTime = reviewTime;
	}

	/** full constructor */
	public TeamApplyEntity(TeamApplyEntityId id, String description,
			Timestamp applyTime, Short reviewState, Timestamp reviewTime) {
		this.id = id;
		this.description = description;
		this.applyTime = applyTime;
		this.reviewState = reviewState;
		this.reviewTime = reviewTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "teamId", column = @Column(name = "team_id", nullable = false, length = 64)),
			@AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false, length = 64)) })
	public TeamApplyEntityId getId() {
		return this.id;
	}

	public void setId(TeamApplyEntityId id) {
		this.id = id;
	}

	@Column(name = "description", length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "apply_time", nullable = false, length = 19)
	public Timestamp getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "review_state")
	public Short getReviewState() {
		return this.reviewState;
	}

	public void setReviewState(Short reviewState) {
		this.reviewState = reviewState;
	}

	@Column(name = "review_time", nullable = false, length = 19)
	public Timestamp getReviewTime() {
		return this.reviewTime;
	}

	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}

}