package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;

/**
 * 类名称：ProjectStateTotalResponse
 * 类描述：项目状态统计response
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
public class ProjectStateTotalResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer total;
	
	private Short state;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}
}
