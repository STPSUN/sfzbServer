package com.idg.bfzb.server.adminfinance.service;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.adminfinance.model.request.FinanceRechargeRequest;
import com.idg.bfzb.server.adminfinance.model.request.FinanceTradeDetailRequest;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;



/**
 * 项目资金管理
 * @author weibeifeng
 * @version 1.0, 2016年12月4日 下午9:16:47
 */
public interface ProjectFinanceService {

	/**
	 * 获取用户资金列表
	 * @param userManagerRequest
	 * @param pageable
	 * @return
	 */
	public PageInfo getUserFinanceList(UserManagerRequest userManagerRequest,
			PageRequest pageable);
	
	/**
	 * 平台资金列表
	 * @param userManagerRequest
	 * @param pageable
	 * @return
	 */
	public PageInfo getPlatFinanceList(FinanceTradeDetailRequest tfdRequest,
			PageRequest pageable);

	/**
	 * 获取用户资金中项目明细
	 * @param entity
	 * @param pageable
	 * @return
	 */
	public PageInfo userProjectDetailList(FinanceTradeDetailEntity entity,
			PageRequest pageable);

	/**
	 * 用户充值提现明细
	 * @param entity
	 * @param pageable
	 * @return
	 */
	public PageInfo userWRDetailList(FinanceTradeDetailEntity entity,
			PageRequest pageable);

}
