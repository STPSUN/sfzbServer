package com.idg.bfzb.server.content.service;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.content.model.WebNewsAdminRequest;
import com.idg.bfzb.server.content.model.WebNewsAdminResponse;
import com.idg.bfzb.server.content.model.dto.TContAdvertisementEntity;

public interface WebNewsMaganerService {

	PageInfo<WebNewsAdminResponse> getWebNewsList(
			WebNewsAdminRequest projectRequest, PageRequest pageable);

	APIResponse addWebNews(TContAdvertisementEntity adver);

	APIResponse updateWebNews(TContAdvertisementEntity adver);

	APIResponse deleteWebNewsByAdvId(String advId);

	APIResponse getWebNewsContentByAdvId(String advId);

}
