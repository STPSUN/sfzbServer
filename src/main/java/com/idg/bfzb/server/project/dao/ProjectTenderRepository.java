package com.idg.bfzb.server.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.ProjectTenderEntity;

/**
 * 类名称：ProjectRepository
 * 类描述：项目信息
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectTenderRepository extends JpaRepository<ProjectTenderEntity, Long>, ProjectTenderRepositoryCust{
	
}
