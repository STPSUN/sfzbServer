package com.idg.bfzb.server.usercenter.dao;

import com.idg.bfzb.server.usercenter.model.dto.UcLoginTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 类名称：
 * 类描述：
 * 创建人：Administrator
 * 创建日期：2016/10/25
 */
public interface UcTokenRepository extends JpaRepository<UcLoginTokenEntity,Long> {
}
