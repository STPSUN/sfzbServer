package com.idg.bfzb.server.pay.dao;

import java.util.List;

import com.idg.bfzb.server.pay.model.dto.FinanceWithdrawApplyEntity;
import com.idg.bfzb.server.pay.model.request.FinanceWithdrawApplyRequest;


public interface FinanceWithdrawApplyRepositoryCust {
	
	/**
	 * 提现申请列表查询(多字段联合查询)
	 * @param qryRequest
	 * @return
	 */
	List<FinanceWithdrawApplyEntity> qryWithdrawInfoByCond(FinanceWithdrawApplyRequest qryRequest);
}
