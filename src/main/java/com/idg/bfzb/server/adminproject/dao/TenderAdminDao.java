package com.idg.bfzb.server.adminproject.dao;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminproject.model.TenderAdminRequest;
import com.idg.bfzb.server.adminproject.model.TenderAdminResponse;
import com.idg.bfzb.server.common.model.PageInfo;

public interface TenderAdminDao {

	PageInfo<TenderAdminResponse> findProjectTenderListByCond(
			TenderAdminRequest projectRequest, PageRequest pageable);

}
