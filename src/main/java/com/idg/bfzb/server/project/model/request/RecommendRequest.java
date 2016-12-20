package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：RecommendRequest
 * 类描述：项目推荐request
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class RecommendRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer offset = 1;
	
	private Integer size = 10;
	
	private String region_province;
	
	private String category_name;
	//需要过滤的user
	private String notUserId;
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

	public String getNotUserId() {
		return notUserId;
	}

	public void setNotUserId(String notUserId) {
		this.notUserId = notUserId;
	}

	public String getRegion_province() {
		return region_province;
	}

	public void setRegion_province(String region_province) {
		this.region_province = region_province;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
}
