package com.idg.bfzb.server.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.service.SysCodeService;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.project.model.request.EmployeeAftersaleRequest;
import com.idg.bfzb.server.project.model.request.EmployeeProjectListRequest;
import com.idg.bfzb.server.project.model.request.EmployeeRefuseRequest;
import com.idg.bfzb.server.project.model.request.EmployeeSelectTenderRequest;
import com.idg.bfzb.server.project.model.request.EvaluateListRequest;
import com.idg.bfzb.server.project.model.request.EvaluateUserRequest;
import com.idg.bfzb.server.project.model.request.ProjectActiveRevealRequest;
import com.idg.bfzb.server.project.model.request.ProjectPublishRequest;
import com.idg.bfzb.server.project.model.request.ProjectSignUpRequest;
import com.idg.bfzb.server.project.model.request.ReceiverAppealRequest;
import com.idg.bfzb.server.project.model.request.ReceiverReportRequest;
import com.idg.bfzb.server.project.model.request.ReceiverSearchRequest;
import com.idg.bfzb.server.project.model.request.RecommendRequest;
import com.idg.bfzb.server.project.model.request.TendersSearchRequest;
import com.idg.bfzb.server.project.service.ProjectService;

/**
 * 类名称：ProjectController
 * 类描述：项目管理controller
 * 创建人：ouzhb
 * 创建时间：2016/11/3
 */
@Controller
@RequestMapping("/api/projects")
public class ProjectController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(ProjectController.class);
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SysCodeService sysCodeService;
	
	/**
	 * 项目分类查询 [GET] /api/projects/actions/category
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/actions/category" , method = RequestMethod.GET)
    @ResponseBody
    public Object getAllProjectCategory(HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
        return projectService.getAllProjectCategory();
    }
	/**
	 * 获取截止时间配置 [GET] /api/projects/actions/config/deadline
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/actions/config/deadline" , method = RequestMethod.GET)
    @ResponseBody
    public Object getProjectConfig(HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
        return sysCodeService.getSysCodeListByClassify(Constants.PROJECT_CLASSIFY_DEADLINE);
    }
	/**
	 * 获取预算金额配置 [GET] /api/projects/actions/config/budget
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/actions/config/budget" , method = RequestMethod.GET)
    @ResponseBody
    public Object getProjectBudget(HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
        return sysCodeService.getSysCodeListByClassify(Constants.PROJECT_CLASSIFY_BUDGET);
    }
	/**
	 * 获取项目推荐列表 [GET] /api/projects/receiver/actions/recommend
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/actions/recommend" , method = RequestMethod.GET)
    @ResponseBody
    public Object recommendProject(HttpServletRequest servletRequest, RecommendRequest recommendRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
//		String userId = super.getCurrentUser();
//		recommendRequest.setNotUserId(userId);
        return projectService.recommendProject(recommendRequest);
    }
	/**
	 * 接包者搜索项目任务列表 [GET] /api/projects/receiver/actions/search
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/actions/search" , method = RequestMethod.GET)
    @ResponseBody
    public Object searchReceiverProject(HttpServletRequest servletRequest, ReceiverSearchRequest receiverSearchRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		String userId = super.getCurrentUser();
		receiverSearchRequest.setNotUserId(userId);
        return projectService.searchReceiverProject(receiverSearchRequest);
    }
	/**
	 * 发包者项目列表 [GET] /api/projects/employee/actions/list
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/actions/list" , method = RequestMethod.GET)
    @ResponseBody
    public Object employeeProjectList(EmployeeProjectListRequest projectListRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		projectListRequest.setUserId(super.getCurrentUser()/*"884d551c-0196-454b-bd3a-8bcadce0f260"*/);
		
        return projectService.employeeProjectList(projectListRequest);
    }
	/**
	 * 接包者项目列表 [GET] /api/projects/receiver/actions/list
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/actions/list" , method = RequestMethod.GET)
    @ResponseBody
    public Object receiverProjectList(EmployeeProjectListRequest projectListRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		projectListRequest.setUserId(super.getCurrentUser()/*"884d551c-0196-454b-bd3a-8bcadce0f260"*/);
		
        return projectService.receiverProjectList(projectListRequest);
    }
	/**
	 * 发包者发布项目 [POST] /api/projects/employee/actions/publish
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/actions/publish" , method = RequestMethod.POST)
    @ResponseBody
    public Object publishProject(@RequestBody ProjectPublishRequest projectPublishRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		projectPublishRequest.setOwnerId(super.getCurrentUser());
		
        return projectService.publishProject(projectPublishRequest);
    } 
	/**
	 * 发包者查看项目详情 [GET] /api/projects/employee/actions/{projectId}/detail
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/actions/{projectId}/detail" , method = RequestMethod.GET)
    @ResponseBody
    public Object employeeProjectDetail(@PathVariable("projectId") String projectId,HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
        return projectService.employeeProjectDetail(super.getCurrentUser(), projectId);
    } 
	/**
	 * 接包者查看项目详情 [GET] /api/projects/receiver/actions/{projectId}/detail
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/actions/{projectId}/detail" , method = RequestMethod.GET)
    @ResponseBody
    public Object receiverProjectDetail(@PathVariable("projectId") String projectId,HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
        return projectService.receiverProjectDetail(super.getCurrentUser(), projectId);
    } 
	/**
	 * 接包者查看基本项目详情（无需登录） [GET] /api/projects/receiver/nolog/{projectId}/actions/detail
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/nolog/{projectId}/actions/detail" , method = RequestMethod.GET)
    @ResponseBody
    public Object receiverBaseProjectDetail(@PathVariable("projectId") String projectId,HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
        return projectService.receiverBaseProjectDetail(projectId);
    } 
	/**
	 * 发包者待确认订单处理 [GET] /api/projects/employee/{projectId}/actions/confirm/{result}
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/{projectId}/actions/confirm/{result}" , method = RequestMethod.GET)
    @ResponseBody
    public Object confirmProject(@PathVariable("projectId") String projectId, @PathVariable("result") String result, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }

		return projectService.confirmProject(super.getCurrentUser(), projectId, result);
    }
	/**
	 * 获取项目协议页内容 [GET] /api/projects/employee/agreement
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/agreement" , method = RequestMethod.GET)
    @ResponseBody
    public Object getAgreement(HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }

		return sysCodeService.getSysCodeOneByClassify(Constants.PROJECT_CLASSIFY_AGREEMENT);
    }
	/**
	 * 项目报名 [POST] /api/projects/receiver/actions/signup
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/actions/signup" , method = RequestMethod.POST)
    @ResponseBody
    public Object signUpProject(@RequestBody ProjectSignUpRequest projectSignUpRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		projectSignUpRequest.setUserId(super.getCurrentUser());

		return projectService.signUpProject(projectSignUpRequest);
    }
	/**
	 * 获取报名项目用户列表 [GET] /api/projects/employee/{projectId}/tenders/actions/search?offset=1&size=10
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/{projectId}/tenders/actions/search" , method = RequestMethod.GET)
    @ResponseBody
    public Object searchTenders(@PathVariable("projectId") String projectId, TendersSearchRequest tendersSearchRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		tendersSearchRequest.setProjectId(projectId);

		return projectService.searchTenders(tendersSearchRequest);
    }
	/**
	 * 项目选标 [POST] /api/projects/employee/actions/select
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/actions/select" , method = RequestMethod.POST)
    @ResponseBody
    public Object selectTender(@RequestBody EmployeeSelectTenderRequest employeeSelectTenderRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		employeeSelectTenderRequest.setEmployerId(super.getCurrentUser());

		return projectService.selectTender(employeeSelectTenderRequest);
    }
	/**
	 * 激活兜底服务 [POST] /api/projects/employee/actions/reveal
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/actions/reveal" , method = RequestMethod.POST)
    @ResponseBody
    public Object applyActivateReveal(@RequestBody ProjectActiveRevealRequest projectActiveRevealRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		projectActiveRevealRequest.setEmployerId(super.getCurrentUser());

		return projectService.applyActivateReveal(projectActiveRevealRequest);
    }
	/**
	 * 提交工作内容 [POST] /api/projects/receiver/actions/report
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/actions/report" , method = RequestMethod.POST)
    @ResponseBody
    public Object receiverReport(@RequestBody ReceiverReportRequest receiverReportRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		receiverReportRequest.setUserId(super.getCurrentUser());
		
		return projectService.receiverReport(receiverReportRequest);
    }
	/**
	 * 确认完成工作 [GET] /api/projects/receiver/{projectId}/actions/result
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/{projectId}/actions/result" , method = RequestMethod.GET)
    @ResponseBody
    public Object receiverResult(@PathVariable("projectId") String projectId, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		return projectService.receiverResult(super.getCurrentUser(), projectId);
    }
	/**
	 * 雇主确认完成 [GET] /api/projects/employee/{projectId}/actions/result
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/{projectId}/actions/result" , method = RequestMethod.GET)
    @ResponseBody
    public Object employeeResult(@PathVariable("projectId") String projectId, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		return projectService.employeeResult(super.getCurrentUser(), projectId);
    }
	/**
	 * 雇主拒绝完成 [POST] /api/projects/employee/{projectId}/actions/refuse
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/actions/refuse" , method = RequestMethod.POST)
    @ResponseBody
    public Object employeeRefuse(@RequestBody EmployeeRefuseRequest employeeRefuseRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		employeeRefuseRequest.setEmployerId(super.getCurrentUser());
		
		return projectService.employeeRefuse(employeeRefuseRequest);
    }
	/**
	 * 评价 [POST] /api/projects/actions/evaluate
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/actions/evaluate" , method = RequestMethod.POST)
    @ResponseBody
    public Object evaluateUser(@RequestBody EvaluateUserRequest evaluateUserRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		evaluateUserRequest.setUserId(super.getCurrentUser());
		
		return projectService.evaluateUser(evaluateUserRequest);
    }
	/**
	 * 质保问题申述 [POST] /api/projects/employee/actions/aftersale
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/actions/aftersale" , method = RequestMethod.POST)
    @ResponseBody
    public Object employeeAftersale(@RequestBody EmployeeAftersaleRequest employeeAftersaleRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		return projectService.employeeAftersale(employeeAftersaleRequest);
    }
	/**
	 * 向客服提起申述 [POST] /api/projects/receiver/actions/appeal
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/actions/appeal" , method = RequestMethod.POST)
    @ResponseBody
    public Object receiverAppeal(@RequestBody ReceiverAppealRequest receiverAppealRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		return projectService.receiverAppeal(receiverAppealRequest);
    }
	/**
	 * 查看雇主的所有评价 [GET] /api/projects/receiver/{employerId}/actions/evaluatelist?offset=1&size=10
	 * @param employerId
	 * @param evaluateListRequest
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/receiver/{employerId}/actions/evaluatelist" , method = RequestMethod.GET)
    @ResponseBody
    public Object receiverEvaluateList(@PathVariable("employerId") String employerId, EvaluateListRequest evaluateListRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		evaluateListRequest.setTargetOwnerId(employerId);
		evaluateListRequest.setTargetType(Constants.PROJECT_EVALUATE_TARGET_TYPE_EMPLOYER);
		
		return projectService.evaluateList(evaluateListRequest);
    }
	/**
	 * 查看接包者的所有评价 [GET] /api/projects/employee/{receiverId}/actions/evaluatelist?offset=1&size=10
	 * @param receiverId
	 * @param evaluateListRequest
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/employee/{receiverId}/actions/evaluatelist" , method = RequestMethod.GET)
    @ResponseBody
    public Object employeeEvaluateList(@PathVariable("receiverId") String receiverId, EvaluateListRequest evaluateListRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		evaluateListRequest.setTargetOwnerId(receiverId);
		evaluateListRequest.setTargetType(Constants.PROJECT_EVALUATE_TARGET_TYPE_RECEIVER);
		
		return projectService.evaluateList(evaluateListRequest);
    }
	/**
	 * 雇主各项目状态数量统计
	 * @return
	 */
	@RequestMapping(value = "/employee/actions/state/total" , method = RequestMethod.GET)
    @ResponseBody
    public Object employeeProjectStateTotal(){
		
        return projectService.countProjectStateByUserId(super.getCurrentUser(), Constants.UC_USER_ROLE_EMPLOYER);
    }
	/**
	 * 雇主各项目状态数量统计
	 * @return
	 */
	@RequestMapping(value = "/receiver/actions/state/total" , method = RequestMethod.GET)
    @ResponseBody
    public Object receiverProjectStateTotal(){
		
        return projectService.countProjectStateByUserId(super.getCurrentUser(), Constants.UC_USER_ROLE_WORKER);
    }
}
