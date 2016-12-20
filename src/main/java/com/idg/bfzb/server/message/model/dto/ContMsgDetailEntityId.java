package com.idg.bfzb.server.message.model.dto;

// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ConfMsgDetailEntityId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class ContMsgDetailEntityId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Long messageId;
	private String userId;

	// Constructors

	/** default constructor */
	public ContMsgDetailEntityId() {
	}

	/** full constructor */
	public ContMsgDetailEntityId(Long messageId, String userId) {
		this.messageId = messageId;
		this.userId = userId;
	}

	// Property accessors

	@Column(name = "message_id", nullable = false)
	public Long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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
		if (!(other instanceof ContMsgDetailEntityId))
			return false;
		ContMsgDetailEntityId castOther = (ContMsgDetailEntityId) other;

		return ((this.getMessageId() == castOther.getMessageId()) || (this
				.getMessageId() != null && castOther.getMessageId() != null && this
				.getMessageId().equals(castOther.getMessageId())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMessageId() == null ? 0 : this.getMessageId().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		return result;
	}

}