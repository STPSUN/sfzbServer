package com.idg.bfzb.server.project.service;

import com.idg.bfzb.server.common.model.APIResponse;
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

/**
 * 类名称：ProjectService
 * 类描述：项目管理业务类
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectService {
	/**
	 * 获取所有项目分类
	 * @return	APIResponse
	 */
	APIResponse getAllProjectCategory();
	/**
	 * 获取项目推荐列表
	 * @param recommendRequest
	 * @return	APIResponse
	 */
	APIResponse recommendProject(RecommendRequest recommendRequest);
	/**
	 * 搜索接包者项目任务列表 
	 * @param receiverSearchRequest
	 * @return	APIResponse
	 */
	APIResponse searchReceiverProject(ReceiverSearchRequest receiverSearchRequest);
	/**
	 * 发包者项目列表
	 * @param projectListRequest
	 * @return	APIResponse
	 */
	APIResponse employeeProjectList(EmployeeProjectListRequest projectListRequest);
	/**
	 * 发包者项目发布
	 * @param projectListRequest
	 * @return	APIResponse
	 */
	APIResponse publishProject(ProjectPublishRequest projectPublishRequest);
	/**
	 * 接包者项目列表
	 * @param projectListRequest
	 * @return	APIResponse
	 */
	APIResponse receiverProjectList(EmployeeProjectListRequest projectListRequest);
	/**
	 * 发包者项目详情
	 * @param currentUser
	 * @param projectId
	 * @return
	 */
	APIResponse employeeProjectDetail(String currentUser, String projectId);
	/**
	 * 发包者项目确认
	 * @param currentUser
	 * @param projectId
	 * @param result
	 * @return
	 */
	APIResponse confirmProject(String currentUser, String projectId, String result);
	/**
	 * 接包者项目详情
	 * @param currentUser
	 * @param projectId
	 * @return
	 */
	APIResponse receiverProjectDetail(String currentUser, String projectId);
	/**
	 * 项目报名
	 * @param projectSignUpRequest
	 * @return
	 */
	APIResponse signUpProject(ProjectSignUpRequest projectSignUpRequest);
	/**
	 * 查询投标用户详情
	 * @param tendersSearchRequest
	 * @return
	 */
	APIResponse searchTenders(TendersSearchRequest tendersSearchRequest);
	/**
	 * 雇主项目选标
	 * @param employeeSelectTenderRequest
	 * @return
	 */
	APIResponse selectTender(EmployeeSelectTenderRequest employeeSelectTenderRequest);
	/**
	 * 申请激活兜底服务
	 * @param projectActiveRevealRequest
	 * @return
	 */
	APIResponse applyActivateReveal(ProjectActiveRevealRequest projectActiveRevealRequest);
	/**
	 * 提交工作内容
	 * @param receiverReportRequest
	 * @return
	 */
	APIResponse receiverReport(ReceiverReportRequest receiverReportRequest);
	/**
	 * 确认完成工作
	 * @param currentUser
	 * @param projectId
	 * @return
	 */
	APIResponse receiverResult(String currentUser, String projectId);
	/**
	 * 雇主确认完成
	 * @param currentUser
	 * @param projectId
	 * @return
	 */
	APIResponse employeeResult(String currentUser, String projectId);
	/**
	 * 雇主拒绝完成
	 * @param employeeRefuseRequest
	 * @return
	 */
	APIResponse employeeRefuse(EmployeeRefuseRequest employeeRefuseRequest);
	/**
	 * 评价用户
	 * @param evaluateUserRequest
	 * @return
	 */
	APIResponse evaluateUser(EvaluateUserRequest evaluateUserRequest);
	/**
	 * 质保问题申述
	 * @param employeeAftersaleRequest
	 * @return
	 */
	APIResponse employeeAftersale(EmployeeAftersaleRequest employeeAftersaleRequest);
	/**
	 * 向客服提起申诉
	 * @param receiverAppealRequest
	 * @return
	 */
	APIResponse receiverAppeal(ReceiverAppealRequest receiverAppealRequest);
	/**
	 * 获取评价列表
	 * @param evaluateListRequest
	 * @return
	 */
	APIResponse evaluateList(EvaluateListRequest evaluateListRequest);
	/**
	 * 获取项目基本详情
	 * @param evaluateListRequest
	 * @return
	 */
	APIResponse receiverBaseProjectDetail(String projectId);
	/**
	 * 统计各项目状态数量
	 * @param userId
	 * @param userRole
	 * @return
	 */
	APIResponse countProjectStateByUserId(String userId, String userRole);
}
