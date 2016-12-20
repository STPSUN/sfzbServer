package com.idg.bfzb.server.adminfinance.dao;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.adminfinance.model.request.FinanceRechargeRequest;
import com.idg.bfzb.server.adminfinance.model.response.FinanceRechargeResponse;
import com.idg.bfzb.server.common.model.PageInfo;

/**
 * 类名称：
 * 类描述：
 * 创建人：weibf
 * 创建日期：2016/11/30
 */
public interface FinanceRechargeRepositoryCust {
    /**
     * 获取充值记录列表
     * @param financeRechargeRequest
     * @param pageable
     * @return
     */
    PageInfo<FinanceRechargeResponse> findRechargeListByCond(
    		FinanceRechargeRequest financeRechargeRequest, Pageable pageable);
    
}
