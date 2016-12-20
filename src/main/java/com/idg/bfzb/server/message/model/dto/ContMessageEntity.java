package com.idg.bfzb.server.message.model.dto;

// default package

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ConfMessageEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cont_message")
public class ContMessageEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Long messgeId;
	private String content;
	private String channel;
	private Timestamp createTime;
	private Short state;

	// Constructors

	/** default constructor */
	public ContMessageEntity() {
	}

	/** minimal constructor */
	public ContMessageEntity(Timestamp createTime) {
		this.createTime = createTime;
	}

	/** full constructor */
	public ContMessageEntity(String content, String channel,
			Timestamp createTime, Short state) {
		this.content = content;
		this.channel = channel;
		this.createTime = createTime;
		this.state = state;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "messge_id", unique = true, nullable = false)
	public Long getMessgeId() {
		return this.messgeId;
	}

	public void setMessgeId(Long messgeId) {
		this.messgeId = messgeId;
	}

	@Column(name = "content", length = 256)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "channel", length = 32)
	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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