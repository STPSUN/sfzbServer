package com.idg.bfzb.server.pay.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.pay.model.response.FinanceTradeDetailResponse;


public interface FinanceTradeDetailRepositoryCust {
	/**
	 * 获取用户资金明细
	 * @param entity
	 * @param pageable
	 * @return
	 */
	PageInfo<FinanceTradeDetailResponse> findAllFinanceDetailByCond(
			FinanceTradeDetailEntity entity,Pageable pageable);
	/**
	 * 获取用户资金明细
	 * @param entity
	 * @return
	 */
	List<FinanceTradeDetailEntity> findFinanceDetailByCond(FinanceTradeDetailEntity entity);
	
	int countByCond(FinanceTradeDetailEntity entity);
}
