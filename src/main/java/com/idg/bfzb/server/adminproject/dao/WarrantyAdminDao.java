package com.idg.bfzb.server.adminproject.dao;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminproject.model.WarrantyAdminRequest;
import com.idg.bfzb.server.adminproject.model.WarrantyAdminResponse;
import com.idg.bfzb.server.common.model.PageInfo;

public interface WarrantyAdminDao {

	PageInfo<WarrantyAdminResponse> findProjectWarrantyListByCond(
			WarrantyAdminRequest projectRequest, PageRequest pageable);

	boolean auditPassWarranty(String projectId, String reason, String userId);

	boolean auditNotPassWarranty(String projectId, String reason, String userId);

}
