package com.idg.bfzb.server.homepage.service;

import org.springframework.data.domain.PageRequest;

/**
 * 认证相关server
 *
 * @author Administrator
 */
public interface HomepageService {
	/**
	 * 获取首页banners
	 * @param adv_client_type
	 * @param adv_location
	 * @return
	 */
	Object getHomePageBanners(String adv_client_type, String adv_location);

	Object getWebNewsList(PageRequest pageable);

	Object getWebNewsDetail(String advId);

	Object updateReadCount(String advId);

	Object getWebNewsRelevantList(PageRequest pageable, String keyWord, String webNewsAdvId);

	Object getWebNewsqueryPreNest(String webNewsAdvId);
}
