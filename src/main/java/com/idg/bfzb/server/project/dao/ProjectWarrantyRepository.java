package com.idg.bfzb.server.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.ProjectWarrantyEntity;

/**
 * 类名称：ProjectWarrantyRepository
 * 类描述：项目质保申诉
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectWarrantyRepository extends JpaRepository<ProjectWarrantyEntity, String>{
	
}
