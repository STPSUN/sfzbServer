package com.idg.bfzb.server.authentication.dao;

import com.idg.bfzb.server.authentication.model.EnterpriceAuthRequest;
import com.idg.bfzb.server.authentication.model.UserAuthRequest;
import com.idg.bfzb.server.authentication.model.dto.UserEnterpriseEntity;
import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：chen
 * 创建日期：2016/10/29
 */
public interface AuthenticationDao {

	UserPersonalEntity queryUserAuthenticationInfo(String userId);

	UserEnterpriseEntity queryUserEnterpriceAuthenticationInfo(String userId);

	int addPersonAuthentication(String userId, UserAuthRequest user);

	int addEnterpriseAuthentication(String userId,
			EnterpriceAuthRequest enterprice);

	int auditPersonAuthentication(String userId, String audituserId,
			String action);

	int auditEnterpriceAuthentication(String userId,String enterpriseId, String audituserId,
			String action,String reason);

	int auditTeamAuthentication(String userId, String teamId, String audituserId, String action,String reason);
	/**
	 * 类名称：更新余额（累加减）
	 * 类描述：金额：正值加，负值减
	 * 创建人：weibeifeng
	 * 创建日期：2016/11/1
	 * @param userId 用户ID
	 * @param balance 变动金额
	 * @return 返回 0 更新失败，1更新成功
	 */
	int updatePersonBalance(String userId,Double balance);

	int updatePersonAuthentication(String userId, UserAuthRequest user);
	
	boolean userIsAudith(String userId);
	
	int deleteEnterpriceAuthentication(String enterpriseId);
}
