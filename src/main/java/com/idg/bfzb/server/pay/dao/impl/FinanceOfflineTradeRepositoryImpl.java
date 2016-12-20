package com.idg.bfzb.server.pay.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.adminfinance.model.request.FinanceRechargeRequest;
import com.idg.bfzb.server.pay.dao.FinanceOfflineTradeRepositoryCust;

/**
 * 
 * @author weibeifeng
 * @version 1.0, 2016年12月6日 下午11:30:47
 */
public class FinanceOfflineTradeRepositoryImpl implements FinanceOfflineTradeRepositoryCust {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public int updateOfflineById(FinanceRechargeRequest financeRechargeRequest) {
		
		
		
		
		return 0;
	}
    
}
