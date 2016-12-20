package com.idg.bfzb.server.content.model.dto;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TContAdvertisement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cont_advertisement")
public class TContAdvertisementEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String advId;
	private String title;
	private String advContent;
	private String advImg;
	private Integer advSort;
	private String redirectType;
	private String advLink;
	private String advType;
	private String advLocation;
	private Short status;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String updateAdminId;
	private String advClientType;
	private Timestamp startTime;
	private Timestamp endTime;
	private String playArea;
	private String advUser;
	private String advUserMobile;
	private String advLocationDetail;
	private Integer advReadCount;
	private String advKeyword;
	// Constructors

	/** default constructor */
	public TContAdvertisementEntity() {
	}

	/** minimal constructor */
	public TContAdvertisementEntity(String advId, Timestamp createTime,
			Timestamp updateTime) {
		this.advId = advId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public TContAdvertisementEntity(String advId, String title, String advContent,
			String advImg, Integer advSort, String redirectType,
			String advLink, String advType, String advLocation, Short status,
			Timestamp createTime, Timestamp updateTime, String updateAdminId,
			String advClientType, Timestamp startTime, Timestamp endTime,
			String playArea, String advUser, String advUserMobile,
			String advLocationDetail) {
		this.advId = advId;
		this.title = title;
		this.advContent = advContent;
		this.advImg = advImg;
		this.advSort = advSort;
		this.redirectType = redirectType;
		this.advLink = advLink;
		this.advType = advType;
		this.advLocation = advLocation;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.updateAdminId = updateAdminId;
		this.advClientType = advClientType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.playArea = playArea;
		this.advUser = advUser;
		this.advUserMobile = advUserMobile;
		this.advLocationDetail = advLocationDetail;
	}

	// Property accessors
	@Id
	@Column(name = "adv_id", unique = true, nullable = false, length = 64)
	public String getAdvId() {
		return this.advId;
	}

	public void setAdvId(String advId) {
		this.advId = advId;
	}

	@Column(name = "title", length = 128)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "adv_content", length = 1024)
	public String getAdvContent() {
		return this.advContent;
	}

	public void setAdvContent(String advContent) {
		this.advContent = advContent;
	}

	@Column(name = "adv_img")
	public String getAdvImg() {
		return this.advImg;
	}

	public void setAdvImg(String advImg) {
		this.advImg = advImg;
	}

	@Column(name = "adv_sort")
	public Integer getAdvSort() {
		return this.advSort;
	}

	public void setAdvSort(Integer advSort) {
		this.advSort = advSort;
	}

	@Column(name = "redirect_type", length = 16)
	public String getRedirectType() {
		return this.redirectType;
	}

	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}

	@Column(name = "adv_link")
	public String getAdvLink() {
		return this.advLink;
	}

	public void setAdvLink(String advLink) {
		this.advLink = advLink;
	}

	@Column(name = "adv_type", length = 64)
	public String getAdvType() {
		return this.advType;
	}

	public void setAdvType(String advType) {
		this.advType = advType;
	}

	@Column(name = "adv_location", length = 10)
	public String getAdvLocation() {
		return this.advLocation;
	}

	public void setAdvLocation(String advLocation) {
		this.advLocation = advLocation;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
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

	@Column(name = "update_admin_id", length = 64)
	public String getUpdateAdminId() {
		return this.updateAdminId;
	}

	public void setUpdateAdminId(String updateAdminId) {
		this.updateAdminId = updateAdminId;
	}

	@Column(name = "adv_client_type", length = 64)
	public String getAdvClientType() {
		return this.advClientType;
	}

	public void setAdvClientType(String advClientType) {
		this.advClientType = advClientType;
	}

	@Column(name = "start_time", length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "play_area")
	public String getPlayArea() {
		return this.playArea;
	}

	public void setPlayArea(String playArea) {
		this.playArea = playArea;
	}

	@Column(name = "adv_user")
	public String getAdvUser() {
		return this.advUser;
	}

	public void setAdvUser(String advUser) {
		this.advUser = advUser;
	}

	@Column(name = "adv_user_mobile", length = 64)
	public String getAdvUserMobile() {
		return this.advUserMobile;
	}

	public void setAdvUserMobile(String advUserMobile) {
		this.advUserMobile = advUserMobile;
	}

	@Column(name = "adv_location_detail")
	public String getAdvLocationDetail() {
		return this.advLocationDetail;
	}

	public void setAdvLocationDetail(String advLocationDetail) {
		this.advLocationDetail = advLocationDetail;
	}
	@Column(name = "adv_keyword")
	public String getAdvKeyword() {
		return advKeyword;
	}
	public void setAdvKeyword(String advKeyword) {
		this.advKeyword = advKeyword;
	}

	public Integer getAdvReadCount() {
		return advReadCount;
	}

	public void setAdvReadCount(Integer advReadCount) {
		this.advReadCount = advReadCount;
	}

}