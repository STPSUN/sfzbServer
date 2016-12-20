package com.idg.bfzb.server.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.team.model.dto.UserTeamEntity;

/**
 * 类名称：UserTeamRepository
 * 类描述：团队用户持久化
 * 创建人：weibeifeng
 * 创建日期：2016/10/30
 */
public interface UserTeamRepository extends JpaRepository<UserTeamEntity,String>,UserTeamRepositoryCust{
	
}