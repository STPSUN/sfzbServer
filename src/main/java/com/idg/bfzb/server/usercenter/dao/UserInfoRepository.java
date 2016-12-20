package com.idg.bfzb.server.usercenter.dao;

import java.util.Random;

import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/23
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UcUserInfoEntity, String>,UserInfoRepositoryCust{
    /**
     *
     * @param userId
     * @param passwrd
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE uc_user_info SET password=:password WHERE user_id=:userId",nativeQuery = true)
    int updatePasswordById(@Param("userId") String userId, @Param("password")String passwrd);
}
