package com.idg.bfzb.server.adminproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.adminproject.dao.TenderAdminDao;
import com.idg.bfzb.server.adminproject.model.TenderAdminRequest;
import com.idg.bfzb.server.adminproject.model.TenderAdminResponse;
import com.idg.bfzb.server.adminproject.service.TenderAdminService;
import com.idg.bfzb.server.common.model.PageInfo;

@Service
public class TenderAdminServiceImpl implements TenderAdminService{

	@Autowired
	private TenderAdminDao tenderAdminDao;
	
	@Override
	public PageInfo<TenderAdminResponse> getTenderList(
			TenderAdminRequest projectRequest, PageRequest pageable) {
		PageInfo<TenderAdminResponse> pageInfo = this.tenderAdminDao.findProjectTenderListByCond(projectRequest, pageable);
		return pageInfo;
	}

}
