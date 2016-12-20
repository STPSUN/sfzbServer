package com.idg.bfzb.server.adminproject.service;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminproject.model.RejectionAdminRequest;
import com.idg.bfzb.server.adminproject.model.RejectionAdminResponse;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;

public interface RejectionAdminService {

	PageInfo<RejectionAdminResponse> getRejectionList(
			RejectionAdminRequest projectRequest, PageRequest pageable);

	APIResponse auditPassRejection(String projectId, String reason);

	APIResponse auditNotPassRejection(String projectId, String reason);

	APIResponse getAttachsPath(String[] attachIds);

}
