package com.idg.bfzb.server.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.ProjectProgressEntity;

/**
 * 类名称：ProjectProgressRepository
 * 类描述：项目进度明细
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectProgressRepository extends JpaRepository<ProjectProgressEntity, Long>, ProjectProgressRepositoryCust{
	
}
