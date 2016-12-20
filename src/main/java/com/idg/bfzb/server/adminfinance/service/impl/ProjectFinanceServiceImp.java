package com.idg.bfzb.server.adminfinance.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.adminfinance.dao.FinanceRechargeRepository;
import com.idg.bfzb.server.adminfinance.dao.ProjectFinanceRepository;
import com.idg.bfzb.server.adminfinance.model.request.FinanceRechargeRequest;
import com.idg.bfzb.server.adminfinance.model.request.FinanceTradeDetailRequest;
import com.idg.bfzb.server.adminfinance.service.FinanceRechargeService;
import com.idg.bfzb.server.adminfinance.service.ProjectFinanceService;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;

@Service
public class ProjectFinanceServiceImp implements ProjectFinanceService {
    private final Logger logger = LoggerFactory.getLogger(ProjectFinanceServiceImp.class);

    @Autowired
    private ProjectFinanceRepository projectFinanceRepository;

	@Override
	public PageInfo getUserFinanceList(UserManagerRequest userManagerRequest,
			PageRequest pageable) {
		PageInfo pageInfo = this.projectFinanceRepository.findUserFinanceListByCond(
				userManagerRequest, pageable);
		return pageInfo;
	}

	@Override
	public PageInfo userProjectDetailList(FinanceTradeDetailEntity entity,
			PageRequest pageable) {
		PageInfo pageInfo = this.projectFinanceRepository.findUserProjectDetailList(
				entity, pageable);
		return pageInfo;
	}

	@Override
	public PageInfo userWRDetailList(FinanceTradeDetailEntity entity,
			PageRequest pageable) {
		PageInfo pageInfo = this.projectFinanceRepository.findUserWRDetailList(
				entity, pageable);
		return pageInfo;
	}
	
	@Override
	public PageInfo getPlatFinanceList(FinanceTradeDetailRequest tfdRequest,
			PageRequest pageable) {
		PageInfo pageInfo = this.projectFinanceRepository.findPlatFinanceListByCond(
				tfdRequest, pageable);
		return pageInfo;
	}


}
