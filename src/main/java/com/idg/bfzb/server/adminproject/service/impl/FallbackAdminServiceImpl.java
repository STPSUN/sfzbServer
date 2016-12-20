package com.idg.bfzb.server.adminproject.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.adminproject.dao.AdminProjectDao;
import com.idg.bfzb.server.adminproject.dao.FallbackAdminDao;
import com.idg.bfzb.server.adminproject.model.FallbackAdminRequest;
import com.idg.bfzb.server.adminproject.model.FallbackAdminResponse;
import com.idg.bfzb.server.adminproject.service.FallbackAdminService;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.dao.ProjectCategoryRepository;
import com.idg.bfzb.server.project.dao.ProjectRepository;
import com.idg.bfzb.server.project.model.dto.ProjectEntity;
import com.idg.bfzb.server.project.model.dto.ProjectPlanEntity;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;

@Service
public class FallbackAdminServiceImpl implements FallbackAdminService{

	@Autowired
	private FallbackAdminDao fallbackAdminDao;
	@Autowired
	private AdminProjectDao projectDao;
	@Autowired
	private ProjectRepository projectRespository;
	@Autowired
	private ProjectCategoryRepository projectCategoryReponsitory;
	@Override
	public PageInfo<FallbackAdminResponse> getProjectList(
			FallbackAdminRequest projectRequest, PageRequest pageable) {
		PageInfo<FallbackAdminResponse> pageInfo = this.fallbackAdminDao.findProjectRevelListByCond(projectRequest, pageable);
		return pageInfo;
	}
	@Override
	public APIResponse auditPassFallBack(String prjectUuid) {
		APIResponse ret = new APIResponse();
		
		boolean result = fallbackAdminDao.auditPassFallBack(prjectUuid);
		if(result){
			ret.setSucess(true);
			ret.setMessage("兜底记录启用操作成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("兜底记录启用操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse auditNotPassFallBack(String prjectUuid) {
		APIResponse ret = new APIResponse();
		
		boolean result = fallbackAdminDao.auditNotPassFallBack(prjectUuid);
		if(result){
			ret.setSucess(true);
			ret.setMessage("兜底记录审核操作成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("兜底记录审核操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse startFallback(String projectId, String contact) {
		APIResponse ret = new APIResponse();
		
		UcUserInfoEntity user = projectDao.queryUserByTelephone(contact);
		if(user == null){
			ret.setSucess(false);
			ret.setMessage("工程师不存在，请重新输入！");
			ret.setCode("-1");
			
			return ret;
		}
		//修改兜底状态
		boolean result = true;
		if(result){
			//新的项目ID
			String newProjectId = UUID.randomUUID().toString();
			//复制项目相关信息
			ProjectEntity project = projectRespository.findOne(projectId);
			if(project == null){
				ret.setSucess(false);
				ret.setMessage("项目不存在！");
				ret.setCode("-1");
				
				return ret;
			}
			//存储原来的项目ID
			project.setRevealProjectId(projectId);
			//新的项目ID
			project.setProjectId(newProjectId);
			//选中标人
			project.setTenderUserId(user.getUserId());
			//项目状态改为进行中
			project.setState(Constants.PROJECT_STATE_SELECTED);
			//复制项目
			projectRespository.save(project);
			//复制项目计划
			List<ProjectPlanEntity> plans = projectDao.queryPlansByProjectId(projectId);
			for (ProjectPlanEntity projectPlanEntity : plans) {
				
				projectPlanEntity.setProjectId(newProjectId);
				projectPlanEntity.setPlanId(UUID.randomUUID().toString());
				
				projectDao.addProjectPlan(projectPlanEntity);
			}
			//复制项目进度
			fallbackAdminDao.copyProjectProgressByProjectUuid(projectId,newProjectId);
			//复制类别
			fallbackAdminDao.copyProjectCalatasByProjectUuid(projectId,newProjectId);
			//复制技能
			fallbackAdminDao.copyProjectAbilitysByProjectUuid(projectId, newProjectId);
			//接包者
			fallbackAdminDao.copyProjectTendUsersByProjectUuid(projectId, newProjectId);
			//复制质保
			fallbackAdminDao.copyProjectWarrantyByProjectUuid(projectId, newProjectId);
			//修改有的兜底状态
			fallbackAdminDao.auditPassFallBack(projectId);
			ret.setSucess(true);
			ret.setMessage("启用兜底服务成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(false);
			ret.setMessage("启用兜底服务失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}


}
