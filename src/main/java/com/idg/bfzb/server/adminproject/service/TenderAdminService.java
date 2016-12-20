package com.idg.bfzb.server.adminproject.service;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminproject.model.TenderAdminRequest;
import com.idg.bfzb.server.adminproject.model.TenderAdminResponse;
import com.idg.bfzb.server.common.model.PageInfo;

public interface TenderAdminService {

	PageInfo<TenderAdminResponse> getTenderList(
			TenderAdminRequest projectRequest, PageRequest pageable);

}
