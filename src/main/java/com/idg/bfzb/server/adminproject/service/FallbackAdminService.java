package com.idg.bfzb.server.adminproject.service;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminproject.model.FallbackAdminRequest;
import com.idg.bfzb.server.adminproject.model.FallbackAdminResponse;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;

public interface FallbackAdminService {
	/**
	 * 获取项目列表
	 * @param projectRequest
	 * @param pageable
	 * @return
	 */
	PageInfo<FallbackAdminResponse> getProjectList(FallbackAdminRequest projectRequest,
			PageRequest pageable);

	APIResponse auditPassFallBack(String prjectUuid);

	APIResponse auditNotPassFallBack(String prjectUuid);

	APIResponse startFallback(String projectId, String contact);

}
