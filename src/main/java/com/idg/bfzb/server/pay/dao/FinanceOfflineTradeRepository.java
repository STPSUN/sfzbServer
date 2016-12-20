package com.idg.bfzb.server.pay.dao;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.idg.bfzb.server.pay.model.dto.FinanceOfflineTradeEntity;

public interface FinanceOfflineTradeRepository extends 
		JpaRepository<FinanceOfflineTradeEntity, String>,FinanceOfflineTradeRepositoryCust {
	
	/**
	 * 更新线下充值扩展表
	 * @param recordId
	 * @param bankAccountNumber
	 * @param bankAccountTime
	 * @param bankSerialId
	 * @param money
	 * @param reviewState
	 * @param reviewAdminId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update FinanceOfflineTradeEntity f set f.bankAccountNumber=?2, f.bankAccountTime=?3, f.bankSerialId=?4, "
			+ "f.money=?5, f.reviewState=?6, f.reviewAdminId=?7 where f.recordId = ?1")
	public int updateOfflineTradeById(Long recordId, String bankAccountNumber, Timestamp bankAccountTime,
			String bankSerialId, Double money, Short reviewState, String reviewAdminId);
}
