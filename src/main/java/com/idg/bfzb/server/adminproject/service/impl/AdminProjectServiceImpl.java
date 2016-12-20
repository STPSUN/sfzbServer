package com.idg.bfzb.server.adminproject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.ability.dao.AbilityRespositoryCust;
import com.idg.bfzb.server.ability.model.vo.ProjectAbilityVo;
import com.idg.bfzb.server.adminproject.dao.AdminProjectDao;
import com.idg.bfzb.server.adminproject.model.ProjectAdminRequest;
import com.idg.bfzb.server.adminproject.model.ProjectAdminResponse;
import com.idg.bfzb.server.adminproject.service.AdminProjectService;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.MsgConstants;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.common.tools.MessageCommon;
import com.idg.bfzb.server.project.dao.ProjectProgressRepositoryCust;
import com.idg.bfzb.server.project.model.dto.CategoryEntity;
import com.idg.bfzb.server.project.model.dto.ProjectEntity;
import com.idg.bfzb.server.project.model.dto.ProjectPlanEntity;
import com.idg.bfzb.server.project.model.dto.ProjectProgressEntity;
import com.idg.bfzb.server.project.model.dto.SysModifiedRecordEntity;
import com.idg.bfzb.server.project.service.SysModifiedRecordService;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;

@Service
public class AdminProjectServiceImpl implements AdminProjectService{

	@Autowired
	private AdminProjectDao adminProjectDao;
	@Autowired
	private AbilityRespositoryCust abilityRespository;
	@Autowired
	private ProjectProgressRepositoryCust projectProgressRepository;
	@Autowired
	private MessageCommon messageCommon;
	@Autowired
	private SysModifiedRecordService sysModifiRecordService;
	@Override
	public PageInfo<ProjectAdminResponse> getProjectList(ProjectAdminRequest projectRequest,
			PageRequest pageable) {
		PageInfo<ProjectAdminResponse> pageInfo = this.adminProjectDao.findProjectListByCond(projectRequest, pageable);
		return pageInfo;
	}
	@Override
	public APIResponse getProjectById(String prjectUuid) {
		APIResponse ret = new APIResponse();
		
		//查询项目
		ProjectAdminResponse project = adminProjectDao.findProjectById(prjectUuid);
		//项目不存在情况
		if(project == null){
			ret.setSucess(false);
			ret.setMessage("项目不存在！");
		}else {
			ret.setData(project);
			ret.setSucess(true);
			ret.setCode("0");
		}
		
		return ret;
	}
	@Override
	public APIResponse getProjectAblitisById(String prjectUuid) {
		APIResponse ret = new APIResponse();
		
		//查询项目
		List<ProjectAbilityVo> abilitys = adminProjectDao.queryProjectAbility(prjectUuid);
		//项目不存在情况
		if(abilitys == null){
			ret.setSucess(false);
			ret.setMessage("技能不存在！");
		}else {
			ret.setData(abilitys);
			ret.setSucess(true);
			ret.setCode("0");
		}
		
		return ret;
	}
	@Override
	public APIResponse saveProjectPlan(ProjectPlanEntity plan, String type) {
		APIResponse ret = new APIResponse();
		
		if(type.equals("add")){
			//新增操作
			boolean result = adminProjectDao.addProjectPlan(plan);
			
			if(result){
				ret.setSucess(true);
				ret.setMessage("计划新增成功！");
				ret.setCode("0");
			}else {
				ret.setSucess(true);
				ret.setMessage("计划新增失败！");
				ret.setCode("-1");
			}
		}else {
			//新增操作
			boolean result = adminProjectDao.updateProjectPlan(plan);
			if(result){
				ret.setSucess(true);
				ret.setMessage("计划更新成功！");
				ret.setCode("0");
			}else {
				ret.setSucess(true);
				ret.setMessage("计划更新失败！");
				ret.setCode("-1");
			}
		}
		
		return ret;
	}
	@Override
	public APIResponse getProjectPlansById(String prjectUuid) {
		APIResponse ret = new APIResponse();
		
		//查询项目
		List<ProjectPlanEntity> abilitys = adminProjectDao.queryPlansByProjectId(prjectUuid);
		//项目不存在情况
		if(abilitys == null){
			ret.setSucess(false);
			ret.setMessage("计划不存在！");
		}else {
			ret.setData(abilitys);
			ret.setSucess(true);
			ret.setCode("0");
		}
		
		return ret;
	}
	@Override
	public APIResponse getProjectProgressById(String prjectUuid) {
		APIResponse ret = new APIResponse();
		
		//查询项目
		List<ProjectProgressEntity> progress = adminProjectDao.findProjectProgressByProjectIdAndEventType(prjectUuid, "work_progress");
		//进度不存在情况
		if(progress == null){
			ret.setSucess(false);
			ret.setMessage("进度不存在！");
		}else {
			ret.setData(progress);
			ret.setSucess(true);
			ret.setCode("0");
		}
		
		return ret;
	}
	@Override
	public APIResponse deleteProjectPlan(String planId) {
		APIResponse ret = new APIResponse();
	
		boolean result = adminProjectDao.deleteProjectPlan(planId);
		if(result){
			ret.setSucess(true);
			ret.setMessage("计划删除成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("计划删除失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse auditPassProject(String projectUuid) {
		APIResponse ret = new APIResponse();
		
		boolean result = adminProjectDao.auditPassProject(projectUuid);
		if(result){
			ProjectAdminResponse project = adminProjectDao.findProjectById(projectUuid);
			ret.setSucess(true);
			ret.setMessage("项目审核操作成功！");
			ret.setCode("0");
			
			try{
				/**
				 * 雇主
				 */
				//发送短信
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_SMS, 
						MsgConstants.PROJECT_APPROVE_PASS.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getRealName()), project.getEmployerId());
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}else {
			ret.setSucess(true);
			ret.setMessage("项目审核操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse auditNotPassProject(String prjectUuid,String reviewReason) {
		APIResponse ret = new APIResponse();
		
		boolean result = adminProjectDao.auditNotPassProject(prjectUuid,reviewReason);
		if(result){
			ProjectAdminResponse project = adminProjectDao.findProjectById(prjectUuid);
			
			ret.setSucess(true);
			ret.setMessage("项目审核操作成功！");
			ret.setCode("0");
			
			try{
				/**
				 * 雇主
				 */
				//发送短信
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_SMS, 
						MsgConstants.PROJECT_APPROVE_REFUSE.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getRealName()).replace("${reviewReason}", project.getReviewReason())
						, project.getEmployerId());
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}else {
			ret.setSucess(true);
			ret.setMessage("项目审核操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse auditCustomerConfirmProject(ProjectAdminResponse project,String auditUserId) {
		
		APIResponse ret = new APIResponse();
		//查找修改前project
		ProjectAdminResponse preProject = adminProjectDao.findProjectById(project.getProjectId());

		boolean result = adminProjectDao.auditCustomerConfirmProject(project);
		if(result){
			//插入修改记录
			sysModifiRecordService.insertModifiedRecord(project.getProjectId(), (ProjectEntity)preProject, ProjectEntity.class, auditUserId);
			
			ret.setSucess(true);
			ret.setMessage("项目待客户确认操作成功！");
			ret.setCode("0");
			
			try{
				/**
				 * 雇主
				 */
				//发送短信
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_SMS, 
						MsgConstants.PROJECT_TO_CONFIRM.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getRealName()), preProject.getEmployerId());
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}else {
			ret.setSucess(true);
			ret.setMessage("项目待客户确认操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse getProjectCategoryById(String prjectUuid) {
		APIResponse ret = new APIResponse();
		
		//查询项目
		List<CategoryEntity> categorys = adminProjectDao.findProjectCategoryById(prjectUuid);
		//进度不存在情况
		if(categorys == null){
			ret.setSucess(false);
			ret.setMessage("项目需求类别不存在！");
		}else {
			ret.setData(categorys);
			ret.setSucess(true);
			ret.setCode("0");
		}
		
		return ret;
	}
	@Override
	public APIResponse getProjectUpdateLogById(String prjectUuid) {
		APIResponse ret = new APIResponse();
		
		//查询项目
		List<SysModifiedRecordEntity> modifyLogs = adminProjectDao.queryUpdateLogByProjectId(prjectUuid);
		//项目不存在情况
		ret.setData(modifyLogs);
		ret.setSucess(true);
		ret.setCode("0");
		
		return ret;
	}
	@Override
	public APIResponse getUserByTelephone(String telephone) {
		
		APIResponse ret = new APIResponse();
		UcUserInfoEntity u = adminProjectDao.queryUserByTelephone(telephone);
		
		if(u == null){
			ret.setMessage("联系人不存在！");
			ret.setSucess(true);
			ret.setCode("-1");
		}else {
			
			ret.setData(u);
			ret.setSucess(true);
			ret.setCode("0");
		}
		
		return ret;
	}
	@Override
	public APIResponse inviteUserForProject(String[] inviteUsers,
			String projectId) {
		APIResponse ret = new APIResponse();
		ProjectAdminResponse project = adminProjectDao.findProjectById(projectId);
		
		if(project == null){
			ret.setMessage("项目不存在！");
			ret.setSucess(true);
			ret.setCode("-1");
		}else {
			
			try{
				
				for (String userId : inviteUsers) {
					
					UcUserInfoEntity user = adminProjectDao.queryUserByUserId(userId);
					if(user == null){
						ret.setMessage("邀标用户不存在！");
						ret.setSucess(false);
						ret.setCode("-1");
					}else {
						
						//发送短信
						messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_SMS, 
								MsgConstants.ADMIN_INVITE_PROJECT_MSGTO_RECEIVER.replace("${projectName}", project.getProjectName())
								.replace("${realName}", project.getRealName()), userId);
						//发送推送
						messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, 
								MsgConstants.ADMIN_INVITE_PROJECT_MSGTO_RECEIVER.replace("${projectName}", project.getProjectName())
								.replace("${realName}", project.getRealName()), userId);
					}
				}
				
				ret.setSucess(true);
				ret.setCode("0");
				
			}catch(Exception e){
				e.printStackTrace();
				ret.setMessage("邀标信息发送出错！");
				ret.setSucess(false);
				ret.setCode("-1");
			}
			
		}
		
		return ret;
	}
}
