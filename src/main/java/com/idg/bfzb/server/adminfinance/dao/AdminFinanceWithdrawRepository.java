package com.idg.bfzb.server.adminfinance.dao;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminfinance.model.request.FinanceWithdrawRequest;
import com.idg.bfzb.server.adminfinance.model.response.FinanceWithdrawResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.pay.model.dto.FinanceWithdrawApplyEntity;

public interface AdminFinanceWithdrawRepository {

	PageInfo<FinanceWithdrawResponse> findWithdrawListByCond(
			FinanceWithdrawRequest financeRechargeRequest, PageRequest pageable);

	boolean withrawActionSuccess(String recordId, String reason);

	boolean withrawActionFailure(String recordId, String reason, String audituserId);

	FinanceWithdrawApplyEntity findWithdrawById(String recordId);

}
