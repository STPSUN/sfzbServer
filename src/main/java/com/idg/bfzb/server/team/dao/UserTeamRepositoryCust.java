package com.idg.bfzb.server.team.dao;

import com.idg.bfzb.server.team.model.dto.UserTeamEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：weibeifeng
 * 创建日期：2016/10/30
 */
public interface UserTeamRepositoryCust {
	
	UserTeamEntity findUserTeamByUserId(String userId);
	
	UserTeamEntity findUserTeamByTeamName(String teamName);
}
