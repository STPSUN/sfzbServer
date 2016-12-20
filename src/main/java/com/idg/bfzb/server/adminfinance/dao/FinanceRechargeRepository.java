package com.idg.bfzb.server.adminfinance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：weibf
 * 创建日期：2016/11/30
 */
@Repository
public interface FinanceRechargeRepository extends JpaRepository<FinanceTradeDetailEntity, String>,FinanceRechargeRepositoryCust{

}
