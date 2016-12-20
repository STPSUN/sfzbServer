package com.idg.bfzb.server.usercenter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.EvaluateRecordEntity;

public interface EvaluateManagerRepository extends JpaRepository<EvaluateRecordEntity,Long>,EvaluateManagerRepositoryCust {
    
}
