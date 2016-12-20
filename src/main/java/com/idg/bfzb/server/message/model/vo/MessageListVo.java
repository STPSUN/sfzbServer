package com.idg.bfzb.server.message.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 类名称：MessageListVo
 * 类描述：消息列表vo
 * 创建人：ouzhb
 * 创建日期：2016/11/15
 */
public class MessageListVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long MessageId;
	
	private String content;
	
	private Timestamp createTime;
	
	private Short isRead;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getMessageId() {
		return MessageId;
	}

	public void setMessageId(Long messageId) {
		MessageId = messageId;
	}

	public Short getIsRead() {
		return isRead;
	}

	public void setIsRead(Short isRead) {
		this.isRead = isRead;
	}

}
