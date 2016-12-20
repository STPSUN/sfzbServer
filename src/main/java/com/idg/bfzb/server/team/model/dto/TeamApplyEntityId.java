package com.idg.bfzb.server.team.model.dto;

// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TeamApplyEntityId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TeamApplyEntityId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String teamId;
	private String userId;

	// Constructors

	/** default constructor */
	public TeamApplyEntityId() {
	}

	/** full constructor */
	public TeamApplyEntityId(String teamId, String userId) {
		this.teamId = teamId;
		this.userId = userId;
	}

	// Property accessors

	@Column(name = "team_id", nullable = false, length = 64)
	public String getTeamId() {
		return this.teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	@Column(name = "user_id", nullable = false, length = 64)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TeamApplyEntityId))
			return false;
		TeamApplyEntityId castOther = (TeamApplyEntityId) other;

		return ((this.getTeamId() == castOther.getTeamId()) || (this
				.getTeamId() != null && castOther.getTeamId() != null && this
				.getTeamId().equals(castOther.getTeamId())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTeamId() == null ? 0 : this.getTeamId().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		return result;
	}

}