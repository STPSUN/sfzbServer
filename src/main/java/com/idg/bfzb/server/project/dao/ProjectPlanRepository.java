package com.idg.bfzb.server.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.ProjectPlanEntity;

public interface ProjectPlanRepository extends JpaRepository<ProjectPlanEntity, String>, ProjectPlanRepositoryCust {

}
