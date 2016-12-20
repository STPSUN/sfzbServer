package com.idg.bfzb.server.content.dao;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.content.model.WebNewsAdminRequest;
import com.idg.bfzb.server.content.model.WebNewsAdminResponse;
import com.idg.bfzb.server.content.model.dto.TContAdvertisementEntity;

public interface WebNewsManagerDao {

	PageInfo<WebNewsAdminResponse> findWebNewsListByCond(
			WebNewsAdminRequest projectRequest, PageRequest pageable);

	boolean addWebNews(TContAdvertisementEntity adver);

	boolean updateWebNews(TContAdvertisementEntity adver);

	boolean deleteWebNewsByAdvId(String advId);

	WebNewsAdminResponse getWebNewsByAdvId(String advId);

	boolean updateReadCount(String advId);

	WebNewsAdminResponse getWebNewsqueryPre(String webNewsAdvId);

	WebNewsAdminResponse getWebNewsqueryNext(String webNewsAdvId);

}
