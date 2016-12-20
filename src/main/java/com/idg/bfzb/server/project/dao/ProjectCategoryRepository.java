package com.idg.bfzb.server.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.ProjectCategoryEntity;
import com.idg.bfzb.server.project.model.dto.ProjectCategoryEntityId;

public interface ProjectCategoryRepository extends JpaRepository<ProjectCategoryEntity, ProjectCategoryEntityId>, ProjectCategoryRepositoryCust {

}
