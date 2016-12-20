package com.idg.bfzb.server.authentication.model;

import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public class UserPersonalVo extends UserPersonalEntity{
	//身份证正面地址
	private String frontImageUrl;
	//身份证照背面地址
	private String backImageUrl;
	//持身份证照片地址
	private String holdImageUrl;
	
	public String getFrontImageUrl() {
		return frontImageUrl;
	}
	public void setFrontImageUrl(String frontImageUrl) {
		this.frontImageUrl = frontImageUrl;
	}
	public String getBackImageUrl() {
		return backImageUrl;
	}
	public void setBackImageUrl(String backImageUrl) {
		this.backImageUrl = backImageUrl;
	}
	public String getHoldImageUrl() {
		return holdImageUrl;
	}
	public void setHoldImageUrl(String holdImageUrl) {
		this.holdImageUrl = holdImageUrl;
	}
}
