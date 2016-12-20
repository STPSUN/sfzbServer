package com.idg.bfzb.server.configure.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UcRegionConfigure entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "uc_region_configure", catalog = "bfzb")
public class UcRegionConfigureEntity implements java.io.Serializable {

	// Fields

	private Long configureId;
	private Long regionId;
	private String regionCode;
	private String regionName;
	private Long cityId;
	private String cityCode;
	private String cityName;
	private Short state;

	// Constructors

	/** default constructor */
	public UcRegionConfigureEntity() {
	}

	/** full constructor */
	public UcRegionConfigureEntity(Long regionId, String regionCode,
			String regionName, Long cityId, String cityCode, String cityName,
			Short state) {
		this.regionId = regionId;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.cityId = cityId;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.state = state;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "configure_id", unique = true, nullable = false)
	public Long getConfigureId() {
		return this.configureId;
	}

	public void setConfigureId(Long configureId) {
		this.configureId = configureId;
	}

	@Column(name = "region_id")
	public Long getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	@Column(name = "region_code", length = 16)
	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Column(name = "region_name", length = 32)
	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "city_id")
	public Long getCityId() {
		return this.cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	@Column(name = "city_code", length = 16)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "city_name", length = 32)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

}