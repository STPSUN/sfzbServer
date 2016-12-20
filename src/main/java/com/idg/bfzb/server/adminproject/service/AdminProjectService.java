package com.idg.bfzb.server.adminproject.service;

import org.springframework.data.domain.PageRequest;

import com.idg.bfzb.server.adminproject.model.ProjectAdminRequest;
import com.idg.bfzb.server.adminproject.model.ProjectAdminResponse;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.model.dto.ProjectPlanEntity;

public interface AdminProjectService {
	/**
	 * 获取项目列表
	 * @param projectRequest
	 * @param pageable
	 * @return
	 */
	PageInfo<ProjectAdminResponse> getProjectList(ProjectAdminRequest projectRequest,
			PageRequest pageable);
	/**
	 * 获取项目详情
	 * @param prjectUuid
	 * @return
	 */
	APIResponse getProjectById(String prjectUuid);
	APIResponse getProjectAblitisById(String prjectUuid);
	/**
	 * 新增或者更新
	 * @param plan
	 * @param type  add  edit
	 * @return
	 */
	APIResponse saveProjectPlan(ProjectPlanEntity plan, String type);
	APIResponse getProjectPlansById(String prjectUuid);
	APIResponse getProjectProgressById(String prjectUuid);
	APIResponse deleteProjectPlan(String planId);
	APIResponse auditPassProject(String prjectUuid);
	APIResponse auditNotPassProject(String prjectUuid,String reviewReason);
	APIResponse auditCustomerConfirmProject(ProjectAdminResponse project, String userId);
	APIResponse getProjectCategoryById(String prjectUuid);
	APIResponse getProjectUpdateLogById(String prjectUuid);
	APIResponse getUserByTelephone(String telephone);
	APIResponse inviteUserForProject(String[] inviteUsers, String projectId);

}
