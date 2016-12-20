package com.idg.bfzb.server.adminfinance.service;


import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.adminfinance.model.request.FinanceRechargeRequest;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;



/**
 * 充值管理
 * @author weibeifeng
 * @version 1.0, 2016年11月30日 下午10:20:49
 */
public interface FinanceRechargeService {
	/**
	 * 获取充值记录列表
	 * @param financeRechargeRequest
	 * @param pageable
	 * @return
	 */
	public PageInfo getRechargeList(FinanceRechargeRequest financeRechargeRequest,Pageable pageable);

	/**
	 * 现在充值核对
	 * @param financeRechargeRequest
	 * @return
	 */
	public APIResponse auditOffline(
			FinanceRechargeRequest financeRechargeRequest);
}
