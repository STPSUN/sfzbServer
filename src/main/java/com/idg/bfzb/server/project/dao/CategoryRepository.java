package com.idg.bfzb.server.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String>,CategoryRepositoryCust {

}
