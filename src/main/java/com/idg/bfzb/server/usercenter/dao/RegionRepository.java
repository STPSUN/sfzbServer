package com.idg.bfzb.server.usercenter.dao;

import com.idg.bfzb.server.usercenter.model.dto.UcRegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public interface RegionRepository extends JpaRepository<UcRegionEntity,Long>,RegionRepositoryCust{

}
