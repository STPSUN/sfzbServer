package com.idg.bfzb.server.usercenter.dao;

import com.idg.bfzb.server.usercenter.model.dto.UcAuthCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public interface UcAuthCodeRepository extends JpaRepository<UcAuthCodeEntity,Long> {

    /**
     *
     * @param userId
     * @return
     */
    @Query(value = "SELECT code FROM uc_auth_code WHERE user_id=:userId ORDER BY record_id DESC LIMIT 1",nativeQuery = true)
    String findAuthCodeByUserId(@Param("userId")String userId);
}
