package com.idg.bfzb.server.configure.model.request;

import java.io.Serializable;

public class RegionRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long configureId;
	private Long regionId;
	private String regionCode;
	private String regionName;
	private Long cityId;
	private String cityCode;
	private String cityName;
	private Short state;
	
	public Long getConfigureId() {
		return this.configureId;
	}

	public void setConfigureId(Long configureId) {
		this.configureId = configureId;
	}


	public Long getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}


	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}


	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}


	public Long getCityId() {
		return this.cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}


	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}


	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}
}

