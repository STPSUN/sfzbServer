package com.idg.bfzb.server.authentication.model;

import com.idg.bfzb.server.authentication.model.dto.UserEnterpriseEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public class UserEnterpriceVo extends UserEnterpriseEntity{
	//企业执照地址
	private String businessLicenseImageUrl;

	public String getBusinessLicenseImageUrl() {
		return businessLicenseImageUrl;
	}

	public void setBusinessLicenseImageUrl(String businessLicenseImageUrl) {
		this.businessLicenseImageUrl = businessLicenseImageUrl;
	}
	
}
