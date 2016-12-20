package com.idg.bfzb.server.project.model.request;

import java.io.Serializable;

/**
 * 类名称：ReceiverSearchRequest
 * 类描述：接包者搜索项目任务列表request
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class ReceiverSearchRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer offset = 1;
	
	private Integer size = 10;
	
	private String project_name;
	
	private String category_name;
	
	private Integer deadline;
	
	private String budget;

	private String notUserId;
	
	private String region_province;
	
	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
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

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
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
}
