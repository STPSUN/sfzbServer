package com.idg.bfzb.server.message.model.request;

import java.io.Serializable;

/**
 * 类名称：MessageListRequest
 * 类描述：消息列表request
 * 创建人：ouzhb
 * 创建日期：2016/11/15
 */
public class MessageListRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer offset = 1;
	
	private Integer size = 10;
	
	private String userId;
	
	private Short isRead;
	
	private String channel;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Short getIsRead() {
		return isRead;
	}

	public void setIsRead(Short isRead) {
		this.isRead = isRead;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
