package com.idg.bfzb.server.project.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 类名称：ProjectPlanVo
 * 类描述：项目计划vo
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class ProjectPlanVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String planId;
	
	private String planContent;
	
	private Timestamp planStartTime;
	
	private Timestamp planEndTime;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanContent() {
		return planContent;
	}

	public void setPlanContent(String planContent) {
		this.planContent = planContent;
	}

	public Timestamp getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Timestamp planStartTime) {
		this.planStartTime = planStartTime;
	}

	public Timestamp getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Timestamp planEndTime) {
		this.planEndTime = planEndTime;
	}

}
