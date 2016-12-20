package com.idg.bfzb.server.adminproject.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.ability.model.vo.ProjectAbilityVo;
import com.idg.bfzb.server.adminproject.model.ProjectAdminRequest;
import com.idg.bfzb.server.adminproject.model.ProjectAdminResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.model.dto.CategoryEntity;
import com.idg.bfzb.server.project.model.dto.ProjectPlanEntity;
import com.idg.bfzb.server.project.model.dto.ProjectProgressEntity;
import com.idg.bfzb.server.project.model.dto.SysModifiedRecordEntity;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;

public interface AdminProjectDao {

	PageInfo<ProjectAdminResponse> findProjectListByCond(ProjectAdminRequest projectRequest,
			PageRequest pageable);

	ProjectAdminResponse findProjectById(String prjectUuid);

	boolean addProjectPlan(ProjectPlanEntity plan);

	boolean updateProjectPlan(ProjectPlanEntity plan);

	List<ProjectPlanEntity> queryPlansByProjectId(String prjectUuid);

	boolean deleteProjectPlan(String planId);

	boolean auditPassProject(String prjectUuid);

	boolean auditNotPassProject(String prjectUuid, String reviewReason);
	
	boolean updateProjectState(String prjectUuid,String state);
	
	boolean auditCustomerConfirmProject(ProjectAdminResponse project);

	List<CategoryEntity> findProjectCategoryById(String prjectUuid);

	List<ProjectAbilityVo> queryProjectAbility(String prjectUuid);

	List<SysModifiedRecordEntity> queryUpdateLogByProjectId(String prjectUuid);

	List<ProjectProgressEntity> findProjectProgressByProjectIdAndEventType(
			String prjectUuid, String string);

	UcUserInfoEntity queryUserByTelephone(String telephone);

	UcUserInfoEntity queryUserByUserId(String userId);

}
