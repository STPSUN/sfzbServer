package com.idg.bfzb.server.adminproject.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminproject.model.RejectionAdminRequest;
import com.idg.bfzb.server.adminproject.model.RejectionAdminResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.file.model.dto.AttachmentEntity;

public interface RejectionAdminDao {

	PageInfo<RejectionAdminResponse> findProjectRejectionListByCond(
			RejectionAdminRequest projectRequest, PageRequest pageable);

	boolean auditPassRejection(String projectId, String reason);

	boolean auditNotPassRejection(String projectId, String reason);

	List<AttachmentEntity> getAttachsPath(String[] attachIds);

}
