package com.idg.bfzb.server.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.ProjectRevealEntity;

/**
 * 类名称：ProjectRevealRepository
 * 类描述：项目兜底
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectRevealRepository extends JpaRepository<ProjectRevealEntity, String>{
	
}
