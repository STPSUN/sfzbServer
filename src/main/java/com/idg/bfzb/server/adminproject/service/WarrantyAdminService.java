package com.idg.bfzb.server.adminproject.service;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminproject.model.WarrantyAdminRequest;
import com.idg.bfzb.server.adminproject.model.WarrantyAdminResponse;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;

public interface WarrantyAdminService {

	PageInfo<WarrantyAdminResponse> getWarrantyList(
			WarrantyAdminRequest projectRequest, PageRequest pageable);

	APIResponse auditPassWarranty(String projectId, String reason, String userId);

	APIResponse auditNotPassWarranty(String projectId, String reason, String userId);

}
