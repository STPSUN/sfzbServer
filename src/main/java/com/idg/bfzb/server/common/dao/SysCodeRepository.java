package com.idg.bfzb.server.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.common.model.dto.SysCodeEntity;

/**
 * 类名称：SysCodeRepository
 * 类描述：字典表持久化
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface SysCodeRepository extends JpaRepository<SysCodeEntity, String>, SysCodeRepositoryCust{
	
	/*@Query("select f from SysCodeEntity f where f.classify = ?1")
    public List<SysCodeEntity> findByClassify(String classify);*/

}
