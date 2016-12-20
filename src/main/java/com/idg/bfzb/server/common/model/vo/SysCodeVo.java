package com.idg.bfzb.server.common.model.vo;

import java.io.Serializable;

public class SysCodeVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String confName;
	
	private String confData;
	
	private String classify;
	
	public String getConfName() {
		return confName;
	}
	public void setConfName(String confName) {
		this.confName = confName;
	}
	public String getConfData() {
		return confData;
	}
	public void setConfData(String confData) {
		this.confData = confData;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}

}
