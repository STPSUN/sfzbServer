package com.idg.bfzb.server.usercenter.dao;

import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类名称：UserPersonalRepository
 * 类描述：普通个人用户持久化
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public interface UserPersonalRepository extends JpaRepository<UserPersonalEntity,String>,UserPersonalRepositoryCust{

    /**
     * 修改指定用户的最后登录角色
     * @param userId 用户id
     * @param lastRole 最后一次登录角色
     * @return 操作影响的行数
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE t_user_personal SET last_role=:lastRole WHERE user_id=:userId",nativeQuery = true)
    int updateLastRoleByUserId(@Param("userId")String userId, @Param("lastRole")String lastRole);
    
   /**
    * 个人认证审核
    * @param userId
    * @param reviewState
    * @return
    */
   @Transactional
   @Modifying
   @Query(value = "UPDATE t_user_personal SET review_state=:reviewState,reason=:reason WHERE user_id=:userId",nativeQuery = true)
   int auditPersonUserById(@Param("userId") String userId, @Param("reviewState")Short reviewState, @Param("reason")String reason);
}
