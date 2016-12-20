package com.idg.bfzb.server.adminproject.dao;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminproject.model.FallbackAdminRequest;
import com.idg.bfzb.server.adminproject.model.FallbackAdminResponse;
import com.idg.bfzb.server.common.model.PageInfo;

public interface FallbackAdminDao {

	PageInfo<FallbackAdminResponse> findProjectRevelListByCond(
			FallbackAdminRequest projectRequest, PageRequest pageable);

	boolean auditPassFallBack(String prjectUuid);

	boolean auditNotPassFallBack(String prjectUuid);

	boolean startFallback(String projectId, String contact);

	void copyProjectCalatasByProjectUuid(String projectId, String newProjectId);

	void copyProjectAbilitysByProjectUuid(String projectId, String newProjectId);

	void copyProjectTendUsersByProjectUuid(String projectId, String newProjectId);

	void copyProjectWarrantyByProjectUuid(String projectId, String newProjectId);

	void copyProjectProgressByProjectUuid(String projectId, String newProjectId);

}
