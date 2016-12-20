package com.idg.bfzb.server.team.service;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.team.model.request.TeamModifyRequest;
import com.idg.bfzb.server.team.model.request.TeamSetRequest;

/**
 * 类名称：TeamService
 * 类描述：团队管理业务类
 * 创建人：ouzhb
 * 创建日期：2016/10/31
 */
public interface TeamService {
	/**
	 * 创建团队
	 * @param userId		   创建人用户ID
	 * @param teamSetRequest 创建团队请求参数体
	 * @return	APIResponse
	 */
	APIResponse setTeam(String userId, TeamSetRequest teamSetRequest);
	/**
	 * 获取团队详情
	 * @param teamId	团队ID
	 * @return	APIResponse
	 */
	APIResponse detail(String teamId);
	/**
	 * 修改团队信息
	 * @param userId			 创建人用户ID
	 * @param teamModifyRequest	修改团队请求参数体
	 * @return	APIResponse
	 */
	APIResponse modifyTeam(String userId, TeamModifyRequest teamModifyRequest);
}
