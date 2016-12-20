package com.idg.bfzb.server.usercenter.dao;

import com.idg.bfzb.server.usercenter.model.dto.UcRegionEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：weibf
 * 创建日期：2016/11/2
 */
public interface RegionRepositoryCust{
     UcRegionEntity findByRegionCode(String regionCode);
}
