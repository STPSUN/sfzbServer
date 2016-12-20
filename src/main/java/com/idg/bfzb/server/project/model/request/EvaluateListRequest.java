package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：EvaluateListRequest
 * 类描述：评价列表request
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class EvaluateListRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String targetType;
	
	private String targetOwnerId;
	
	private Integer offset = 1;
	
	private Integer size = 10;

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getTargetOwnerId() {
		return targetOwnerId;
	}

	public void setTargetOwnerId(String targetOwnerId) {
		this.targetOwnerId = targetOwnerId;
	}

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

}
