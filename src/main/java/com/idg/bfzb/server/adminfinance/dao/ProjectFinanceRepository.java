package com.idg.bfzb.server.adminfinance.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.idg.bfzb.server.adminfinance.model.request.FinanceTradeDetailRequest;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;

/**
 * 
 * @author weibeifeng
 * @version 1.0, 2016年12月4日 下午9:26:37
 */
public interface ProjectFinanceRepository {

	/**
	 * 
	 * @param userManagerRequest
	 * @param pageable
	 * @return
	 */
	PageInfo<UserManagerResponse> findUserFinanceListByCond(UserManagerRequest userManagerRequest,
			PageRequest pageable);

	/**
	 * 
	 * @param entity
	 * @param pageable
	 * @return
	 */
	PageInfo findUserProjectDetailList(FinanceTradeDetailEntity entity,
			PageRequest pageable);

	/**
	 * 
	 * @param entity
	 * @param pageable
	 * @return
	 */
	PageInfo findUserWRDetailList(FinanceTradeDetailEntity entity,
			PageRequest pageable);

	/**
	 * 
	 * @param tfdRequest
	 * @param pageable
	 * @return
	 */
	PageInfo findPlatFinanceListByCond(FinanceTradeDetailRequest tfdRequest,
			PageRequest pageable);

}
