package com.idg.bfzb.server.usercenter.dao;

import com.idg.bfzb.server.authentication.model.dto.UserEnterpriseEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：weibeifeng
 * 创建日期：2016/10/30
 */
public interface UserEnterpriseRepositoryCust {
	
	UserEnterpriseEntity findUserEnterpriseByUserId(String userId);
}
