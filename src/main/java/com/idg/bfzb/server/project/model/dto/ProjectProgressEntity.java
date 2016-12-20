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
 * ProjectProgressEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_project_progress")
public class ProjectProgressEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Long recordId;
	private String userId;
	private String projectId;
	private Float progress;
	private String eventUserId;
	private String eventContent;
	private String eventType;
	private String eventAddress;
	private String photos;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String eventSource;
	private String externalEventId;
	private String externalEventContent;

	// Constructors

	/** default constructor */
	public ProjectProgressEntity() {
	}

	/** minimal constructor */
	public ProjectProgressEntity(String userId, String projectId,
			String eventUserId, Timestamp createTime, Timestamp updateTime) {
		this.userId = userId;
		this.projectId = projectId;
		this.eventUserId = eventUserId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public ProjectProgressEntity(String userId, String projectId,
			Float progress, String eventUserId, String eventContent,
			String eventType, String eventAddress, String photos,
			Timestamp createTime, Timestamp updateTime, String eventSource,
			String externalEventId, String externalEventContent) {
		this.userId = userId;
		this.projectId = projectId;
		this.progress = progress;
		this.eventUserId = eventUserId;
		this.eventContent = eventContent;
		this.eventType = eventType;
		this.eventAddress = eventAddress;
		this.photos = photos;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.eventSource = eventSource;
		this.externalEventId = externalEventId;
		this.externalEventContent = externalEventContent;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "record_id", unique = true, nullable = false)
	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	@Column(name = "user_id", nullable = false, length = 64)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "project_id", nullable = false, length = 64)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "progress", precision = 12, scale = 0)
	public Float getProgress() {
		return this.progress;
	}

	public void setProgress(Float progress) {
		this.progress = progress;
	}

	@Column(name = "event_user_id", nullable = false, length = 64)
	public String getEventUserId() {
		return this.eventUserId;
	}

	public void setEventUserId(String eventUserId) {
		this.eventUserId = eventUserId;
	}

	@Column(name = "event_content", length = 1024)
	public String getEventContent() {
		return this.eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	@Column(name = "event_type", length = 64)
	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Column(name = "event_address", length = 256)
	public String getEventAddress() {
		return this.eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	@Column(name = "photos", length = 2048)
	public String getPhotos() {
		return this.photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
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

	@Column(name = "event_source", length = 16)
	public String getEventSource() {
		return this.eventSource;
	}

	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}

	@Column(name = "external_event_id", length = 64)
	public String getExternalEventId() {
		return this.externalEventId;
	}

	public void setExternalEventId(String externalEventId) {
		this.externalEventId = externalEventId;
	}

	@Column(name = "external_event_content", length = 1024)
	public String getExternalEventContent() {
		return this.externalEventContent;
	}

	public void setExternalEventContent(String externalEventContent) {
		this.externalEventContent = externalEventContent;
	}

}