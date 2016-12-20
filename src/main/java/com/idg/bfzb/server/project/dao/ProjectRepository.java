package com.idg.bfzb.server.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.idg.bfzb.server.project.model.dto.ProjectEntity;

/**
 * 类名称：ProjectRepository
 * 类描述：项目信息
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectRepository extends JpaRepository<ProjectEntity, String>, ProjectRepositoryCust{
	
	/*@Query("select f.projectId,f.projectName,f.budget,f.categoryIds,f.applyDeadline,f.signUpCount from ProjectEntity f where f.state = ?1")
    public Page<ProjectEntity> findPageProjectByState(short state, Pageable pageable);*/
	
	@Query("select f.projectId,f.projectName from ProjectEntity f where f.state!=-1 and f.projectName = ?1")
	public List<ProjectEntity> findByProjectName(String projectName);
}
