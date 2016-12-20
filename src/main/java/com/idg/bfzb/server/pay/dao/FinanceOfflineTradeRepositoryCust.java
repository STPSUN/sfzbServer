package com.idg.bfzb.server.pay.dao;

import com.idg.bfzb.server.adminfinance.model.request.FinanceRechargeRequest;



public interface FinanceOfflineTradeRepositoryCust {
	/**
	 * 更新现在充值扩展表
	 * @param financeRechargeRequest
	 * @return
	 */
	int updateOfflineById(FinanceRechargeRequest financeRechargeRequest);
}
