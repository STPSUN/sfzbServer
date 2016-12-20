package com.idg.bfzb.server.team.model.response;

import java.io.Serializable;

public class TeamSetResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 团队id
	 */
	private String teamId;
	
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

}
