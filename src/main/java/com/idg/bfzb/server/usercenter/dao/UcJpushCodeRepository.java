package com.idg.bfzb.server.usercenter.dao;

import com.idg.bfzb.server.usercenter.model.dto.UcJpushCodeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 类名称：UcJpushCodeRepository
 * 类描述：用户极光ID关联表
 * 创建人：ouzhb
 * 创建日期：2016/12/8
 */
@Repository
public interface UcJpushCodeRepository extends JpaRepository<UcJpushCodeEntity, String>, UcJpushCodeRepositoryCust{

}
