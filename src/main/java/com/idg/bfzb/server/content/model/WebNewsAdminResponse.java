package com.idg.bfzb.server.content.model;

import com.idg.bfzb.server.content.model.dto.TContAdvertisementEntity;

public class WebNewsAdminResponse extends TContAdvertisementEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String createDate;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
