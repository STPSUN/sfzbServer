package com.idg.bfzb.server.homepage.dao;

import java.util.List;

import com.idg.bfzb.server.homepage.model.dto.ContAdvertisementEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：chen
 * 创建日期：2016/10/29
 */
public interface HomepageDao {
	/**
	 * 获取广告
	 * @param adv_client_type 
	 * @param adv_location 
	 * @return
	 */
	List<ContAdvertisementEntity> getAdvertisementByAdvClientType(String adv_client_type,String adv_location);
}
