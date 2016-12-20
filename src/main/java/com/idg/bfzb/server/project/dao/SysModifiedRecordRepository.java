package com.idg.bfzb.server.project.dao;

import com.idg.bfzb.server.project.model.dto.SysModifiedRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 类名称：SysModifiedRecordRepository
 * 类描述：修改记录DAO
 * 创建人：jiangdong
 * 创建日期：2016/12/4
 */
public interface SysModifiedRecordRepository extends JpaRepository<SysModifiedRecordEntity,Long>,SysModifiedRecordRepositoryCust{
}
