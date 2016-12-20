package com.idg.bfzb.server.usercenter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.authentication.model.dto.UserEnterpriseEntity;

/**
 * 类名称：UserTeamRepository
 * 类描述：团队用户持久化
 * 创建人：weibeifeng
 * 创建日期：2016/10/30
 */
public interface UserEnterpriseRepository extends JpaRepository<UserEnterpriseEntity,String>,UserEnterpriseRepositoryCust{
	
}