package com.idg.bfzb.server.message.model.dto;

// default package

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ConfMsgDetailEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cont_msg_detail")
public class ContMsgDetailEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private ContMsgDetailEntityId id;
	private Short isRead;
	private Timestamp readTime;
	private Timestamp createTime;
	private Short state;

	// Constructors

	/** default constructor */
	public ContMsgDetailEntity() {
	}

	/** minimal constructor */
	public ContMsgDetailEntity(ContMsgDetailEntityId id, Timestamp readTime,
			Timestamp createTime, Short state) {
		this.id = id;
		this.readTime = readTime;
		this.createTime = createTime;
		this.state = state;
	}

	/** full constructor */
	public ContMsgDetailEntity(ContMsgDetailEntityId id, Short isRead,
			Timestamp readTime, Timestamp createTime, Short state) {
		this.id = id;
		this.isRead = isRead;
		this.readTime = readTime;
		this.createTime = createTime;
		this.state = state;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "messageId", column = @Column(name = "message_id", nullable = false)),
			@AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false, length = 64)) })
	public ContMsgDetailEntityId getId() {
		return this.id;
	}

	public void setId(ContMsgDetailEntityId id) {
		this.id = id;
	}

	@Column(name = "is_read")
	public Short getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Short isRead) {
		this.isRead = isRead;
	}

	@Column(name = "read_time", nullable = false, length = 19)
	public Timestamp getReadTime() {
		return this.readTime;
	}

	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "state")
    public Short getState() {
        return this.state;
    }

    public void setState(Short state) {
        this.state = state;
    }
}