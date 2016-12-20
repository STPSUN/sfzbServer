package com.idg.bfzb.server.controller.admin;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.adminproject.model.ProjectAdminRequest;
import com.idg.bfzb.server.adminproject.model.ProjectAdminResponse;
import com.idg.bfzb.server.adminproject.service.AdminProjectService;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.model.dto.ProjectPlanEntity;
import com.idg.bfzb.server.tools.SecurityConstants;
import com.idg.bfzb.server.utility.servlet.ServletUtil;
/**
 * 项目操作相关action请求
 * @author chen
 *
 */
@Controller
@RequestMapping(value="/admin/projectManager/action")
public class ProjectManagerActionController {
	@Autowired
	private AdminProjectService projectService; 
	/**
     * 获取项目管理列表
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getProjectList(ProjectAdminRequest projectRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	//String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
		//String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
		JSONObject jo=new JSONObject();
    	Integer pageNum = 0;
    	Integer pageSize = 0;
    	try {
    		pageNum = Integer.parseInt(servletRequest.getParameter("page"));
		} catch (Exception e) {
			pageNum = 1;
		}
    	try {
    		pageSize = Integer.parseInt(servletRequest.getParameter("rows"));
		} catch (Exception e) {
			pageSize = 10;
		}
    	PageRequest pageable = new PageRequest(pageNum-1, pageSize);
    	PageInfo<ProjectAdminResponse> pageInfo = this.projectService.getProjectList(projectRequest,pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo,"yyyy-MM-dd", servletResponse);
    }
    /**
     * 获取项目管理详情
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/getProjectById", method = RequestMethod.GET)
    @ResponseBody
    public void getProjectById(ProjectAdminRequest projectRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	
    	String prjectUuid = servletRequest.getParameter("prjectUuid");
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(prjectUuid)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("项目ID不能为空！");
    	}else {
    		
    		ret = this.projectService.getProjectById(prjectUuid);
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, "yyyy-MM-dd", servletResponse);
    }
    /**
     * 获取项目需求列表
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/getProjectCategoryById", method = RequestMethod.GET)
    @ResponseBody
    public void getProjectCategoryById(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	
    	String prjectUuid = servletRequest.getParameter("prjectUuid");
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(prjectUuid)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("项目ID不能为空！");
    	}else {
    		
    		ret = this.projectService.getProjectCategoryById(prjectUuid);
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 获取项目技能详情
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/getProjectAblitisById", method = RequestMethod.GET)
    @ResponseBody
    public void getProjectAblitisById(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	
    	String prjectUuid = servletRequest.getParameter("prjectUuid");
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(prjectUuid)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("项目ID不能为空！");
    	}else {
    		
    		ret = this.projectService.getProjectAblitisById(prjectUuid);
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 获取项目计划详情
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/getProjectPlansById", method = RequestMethod.GET)
    @ResponseBody
    public void getProjectPlansById(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	
    	String prjectUuid = servletRequest.getParameter("prjectUuid");
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(prjectUuid)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("项目ID不能为空！");
    	}else {
    		
    		ret = this.projectService.getProjectPlansById(prjectUuid);
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, "yyyy/MM/dd",servletResponse);
    }
    /**
     * 获取项目修改日志
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/getProjectUpdateLogById", method = RequestMethod.GET)
    @ResponseBody
    public void getProjectUpdateLogById(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	
    	String prjectUuid = servletRequest.getParameter("projectUuid");
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(prjectUuid)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("项目ID不能为空！");
    	}else {
    		
    		ret = this.projectService.getProjectUpdateLogById(prjectUuid);
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 获取项目进度
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/getProjectProgressById", method = RequestMethod.GET)
    @ResponseBody
    public void getProjectProgressById(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	
    	String prjectUuid = servletRequest.getParameter("prjectUuid");
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(prjectUuid)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("项目ID不能为空！");
    	}else {
    		
    		ret = this.projectService.getProjectProgressById(prjectUuid);
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 根据手机号码获取用户
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/getUserByTelephone", method = RequestMethod.GET)
    @ResponseBody
    public void getUserByTelephone(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	
    	String telephone = servletRequest.getParameter("telephone");
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(telephone)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("联系人号码不能为空！");
    	}else {
    		
    		ret = this.projectService.getUserByTelephone(telephone);
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 保存项目计划
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/saveProjectPlan", method = RequestMethod.POST)
    @ResponseBody
    public void saveProjectPlan(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	String planId = servletRequest.getParameter("planId");
    	String projectId = servletRequest.getParameter("prjectUuid");
    	String planContent = servletRequest.getParameter("planContent");
    	
    	
    	String stS = servletRequest.getParameter("planStartTime");
    	String enS = servletRequest.getParameter("planEndTime");
    	Timestamp planStartTime = null;
    	Timestamp planEndTime = null;
    	if(stS.contains("/")){
    		
    		try {
    			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    			DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			planStartTime = Timestamp.valueOf(sdf2.format(sdf.parse(stS)));
    			planEndTime = Timestamp.valueOf(sdf2.format(sdf.parse(enS)));
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    		
    	}else {
    		
    		planStartTime = Timestamp.valueOf(stS);
			planEndTime = Timestamp.valueOf(enS);
    	}
    	ProjectPlanEntity plan = new ProjectPlanEntity();
    	plan.setProjectId(projectId);
		plan.setPlanStartTime(planStartTime);
		plan.setPlanEndTime(planEndTime);
		plan.setPlanContent(planContent);
    	plan.setState(Short.valueOf("0"));
    	
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(projectId)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("项目ID不能为空！");
    	}else {
    		
    		if(StringUtils.isEmpty(planId)){
    			//新增
    			plan.setPlanId(UUID.randomUUID().toString());
    			ret = this.projectService.saveProjectPlan(plan,"add");
    		}else {
    			//编辑
    			plan.setPlanId(planId);
    			ret = this.projectService.saveProjectPlan(plan,"edit");
    		}
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 删除项目计划
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/deleteProjectPlan", method = RequestMethod.POST)
    @ResponseBody
    public void deleteProjectPlan(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	String planId = servletRequest.getParameter("planId");
    	
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(planId)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("项目计划ID不能为空！");
    	}else {
    		ret = projectService.deleteProjectPlan(planId);
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 审核项目
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/auditProjectById", method = RequestMethod.POST)
    @ResponseBody
    public void auditProjectById(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	//1通过 2不通过
    	String auditAction = servletRequest.getParameter("auditAction");
    	String prjectUuid = servletRequest.getParameter("prjectUuid");
    	
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(auditAction) || StringUtils.isEmpty(prjectUuid)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("数据不能为空！");
    	}else {
    		//审核通过
    		if(auditAction.equals("1")){
    			
    			ret = projectService.auditPassProject(prjectUuid);
    		}
    		//审核拒绝
    		else if(auditAction.equals("2")){
    			String reviewReason = servletRequest.getParameter("reviewReason");
    			ret = projectService.auditNotPassProject(prjectUuid,reviewReason);
    		}
    		//客户确认
    		else {
    			
    			String _budget = servletRequest.getParameter("budget");
    			String _applyDeadline = servletRequest.getParameter("applyDeadline");
    			String _submitDeadline = servletRequest.getParameter("submitDeadline");
    			String _marginScale = servletRequest.getParameter("marginScale");
    			String _revealScale = servletRequest.getParameter("revealScale");
    			String _marginDay = servletRequest.getParameter("marginDay");
    			String _isReveal = servletRequest.getParameter("isReveal");
    			String _isMargin = servletRequest.getParameter("isMargin");
    			
    			String projectName = servletRequest.getParameter("projectName");
    			String tenderType = servletRequest.getParameter("tenderType");
    			String regionDetail = servletRequest.getParameter("regionDetail");
    			String realName = servletRequest.getParameter("realName");
    			String telephone = servletRequest.getParameter("telephone");
    			String description = servletRequest.getParameter("description");
				
    			Double budget = null;
    			if(!StringUtils.isEmpty(_budget)){
    				budget = Double.parseDouble(_budget);
    			}
    			
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			Timestamp applyDeadline = null;
    			Timestamp submitDeadline = null;
				try {
					if(!StringUtils.isEmpty(_applyDeadline)){
						applyDeadline = Timestamp.valueOf(sdf.format(sdf.parse(_applyDeadline)) + " 00:00:00");
					}
					if(!StringUtils.isEmpty(_submitDeadline)){
						submitDeadline = Timestamp.valueOf(sdf.format(sdf.parse(_submitDeadline)) + " 00:00:00");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
    			
    			Float marginScale = null;
    			if(!StringUtils.isEmpty(_marginScale)){
    				marginScale = Float.parseFloat(_marginScale);
    			}
    			Float revealScale = null;
    			if(!StringUtils.isEmpty(_revealScale)){
    				revealScale = Float.parseFloat(_revealScale);
    			}
    			Integer marginDay = null;
    			if(!StringUtils.isEmpty(_marginDay)){
    				marginDay = Integer.parseInt(_marginDay);
    			}
    			Short isReveal = null;
    			if(!StringUtils.isEmpty(_isReveal)){
    				isReveal = Short.parseShort(_isReveal);
    			}
    			Short isMargin = null;
    			if(!StringUtils.isEmpty(_isMargin)){
    				isMargin = Short.parseShort(_isMargin);
    			}
    			
    			if(submitDeadline == null){
    				ret = new APIResponse();
		    		ret.setSucess(false);
		    		ret.setMessage("项目截至日期不能为空，请检查是否日期格式错误！");
    			}else if(applyDeadline == null){
    				ret = new APIResponse();
		    		ret.setSucess(false);
		    		ret.setMessage("报名截止时间不能为空，请检查是否日期格式错误！");
    			}
    				
    			//判断时间
    			else {
    				if(applyDeadline.compareTo(submitDeadline) > 0){
    					ret = new APIResponse();
    		    		ret.setSucess(false);
    		    		ret.setMessage("报名截止时间不能大于项目截至日期！");
    				}else {
        				
        				ProjectAdminResponse project = new ProjectAdminResponse();
        				project.setProjectId(prjectUuid);
        				project.setProjectName(projectName);
        				project.setBudget(budget);
        				project.setTenderType(tenderType);
        				project.setRegionDetail(regionDetail);
        				project.setRealName(realName);
        				project.setTelephone(telephone);
        				project.setDescription(description);
        				project.setApplyDeadline(applyDeadline);
        				project.setSubmitDeadline(submitDeadline);
        				project.setMarginScale(marginScale);
        				project.setRevealScale(revealScale);
        				project.setMarginDay(marginDay);
        				project.setIsReveal(isReveal);
        				project.setIsMargin(isMargin);
        				
        				Object user = servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
        				@SuppressWarnings("rawtypes")
        				String userId = ((HashMap)user).get("admin_id") == null ? "" : ((HashMap)user).get("admin_id").toString();
        				ret = projectService.auditCustomerConfirmProject(project,userId);
        			}
    			}
    		}
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 发送邀标请求
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/inviteUserForProject", method = RequestMethod.POST)
    @ResponseBody
    public void inviteUserForProject(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	String projectId = servletRequest.getParameter("projectId");
    	String[] inviteUsers = servletRequest.getParameterValues("inviteUsers");
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(projectId) || inviteUsers == null || inviteUsers.length <= 0){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("数据不能为空！");
    	}else {
		
			ret = this.projectService.inviteUserForProject(inviteUsers,projectId);
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
}