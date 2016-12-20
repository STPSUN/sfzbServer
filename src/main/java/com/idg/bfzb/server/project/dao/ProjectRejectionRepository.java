package com.idg.bfzb.server.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.project.model.dto.ProjectRejectionEntity;

/**
 * 类名称：ProjectRejectionRepository
 * 类描述：项目拒收申请
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectRejectionRepository extends JpaRepository<ProjectRejectionEntity, String>{
	
}
