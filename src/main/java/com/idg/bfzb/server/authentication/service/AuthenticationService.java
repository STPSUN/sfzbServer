package com.idg.bfzb.server.authentication.service;

import com.idg.bfzb.server.authentication.model.EnterpriceAuthRequest;
import com.idg.bfzb.server.authentication.model.UserAuthRequest;
import com.idg.bfzb.server.common.model.APIResponse;

/**
 * 认证相关server
 *
 * @author Administrator
 */
public interface AuthenticationService {
	/**
	 * 获取我的个人认证进度
	 * @param userId
	 * @return
	 */
	Object getMyAuthenticationInfo(String userId);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	Object getUserEnterpriceAuthenticationInfo(String userId);
	/**
	 * 个人认证申请接口
	 * @param userId
	 * @param user
	 * @return
	 */
	Object personAuthenticationapply(String userId, UserAuthRequest user);
	/**
	 * 企业认证申请
	 * @param userId
	 * @param enterprice
	 * @return
	 */
	Object enterpriseAuthenticationapply(String userId,
			EnterpriceAuthRequest enterprice);
	/**
	 * 个人用户审核
	 * @param userId
	 * @param audituserId
	 * @param action
	 * @return
	 */
	Object personAuthenticationAudit(String userId, String audituserId,
			String action);
	/**
	 * 企业认证审核
	 * @param userId
	 * @param audituserId
	 * @param action
	 * @return
	 */
	APIResponse enterpriseAuthenticationAudit(String userId, String enterpriseId, String audituserId,
			String action,String reason);
	/**
	 * 团队认证
	 * @param teamId
	 * @param audituserId
	 * @param action
	 * @return
	 */
	APIResponse teamAuthenticationAudit(String userId, String teamId, String audituserId,
			String action,String reason);

}
