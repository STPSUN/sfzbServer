package com.idg.bfzb.server.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.EvaluateRecordEntity;

public interface EvaluateRecordRepository extends JpaRepository<EvaluateRecordEntity, String>, EvaluateRecordRepositoryCust {

}
