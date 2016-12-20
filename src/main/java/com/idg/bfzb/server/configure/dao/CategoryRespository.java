package com.idg.bfzb.server.configure.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.CategoryEntity;

public interface CategoryRespository extends JpaRepository<CategoryEntity, String>,CategoryRespositoryCust {
    
}
