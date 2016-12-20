package com.idg.bfzb.server.pay.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.pay.model.dto.FinanceWithdrawApplyEntity;

public interface FinanceWithdrawApplyRepository extends 
		JpaRepository<FinanceWithdrawApplyEntity, String>,FinanceWithdrawApplyRepositoryCust {
}
