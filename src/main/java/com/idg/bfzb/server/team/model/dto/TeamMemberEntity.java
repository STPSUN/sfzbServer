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
 * TeamMemberEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_team_member", catalog = "bfzb")
public class TeamMemberEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private TeamMemberEntityId id;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public TeamMemberEntity() {
	}

	/** full constructor */
	public TeamMemberEntity(TeamMemberEntityId id, Timestamp updateTime) {
		this.id = id;
		this.updateTime = updateTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "teamId", column = @Column(name = "team_id", nullable = false, length = 64)),
			@AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false, length = 64)) })
	public TeamMemberEntityId getId() {
		return this.id;
	}

	public void setId(TeamMemberEntityId id) {
		this.id = id;
	}

	@Column(name = "update_time", nullable = false, length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}