package com.idg.bfzb.server.pay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;

public interface FinanceTradeDetailRepository extends JpaRepository<FinanceTradeDetailEntity, String>,
	PagingAndSortingRepository<FinanceTradeDetailEntity, String>,FinanceTradeDetailRepositoryCust{
	/**
	 * 根据商户订单号获取交易信息
	 * @param orderSerial
	 * @return
	 */
	@Query("select f from FinanceTradeDetailEntity f where f.outTradeNo = ?1")
	public FinanceTradeDetailEntity findOneByTradeOrder(String orderSerial);
	
	/**
	 * 更新订单状态和交易流水号
	 * @param recordId
	 * @param tradeState
	 * @param tradeNo
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update FinanceTradeDetailEntity f set f.tradeState=?2,f.tradeNo=?3 where f.recordId = ?1")
	public int updateTradeStateById(Long recordId, short tradeState,String tradeNo);

	
}
