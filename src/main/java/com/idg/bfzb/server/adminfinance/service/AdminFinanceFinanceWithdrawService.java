package com.idg.bfzb.server.adminfinance.service;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminfinance.model.request.FinanceWithdrawRequest;
import com.idg.bfzb.server.adminfinance.model.response.FinanceWithdrawResponse;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;

public interface AdminFinanceFinanceWithdrawService {

	PageInfo<FinanceWithdrawResponse> getWithdrawList(
			FinanceWithdrawRequest financeRechargeRequest, PageRequest pageable);

	APIResponse withrawActionSuccess(String recordId, String reason);

	APIResponse withrawActionFailure(String recordId, String reason, String audituserId);

}
