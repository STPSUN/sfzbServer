package com.idg.bfzb.server.project.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.ability.dao.AbilityRespository;
import com.idg.bfzb.server.ability.model.vo.AbilityVo;
import com.idg.bfzb.server.authentication.dao.AuthenticationDao;
import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.MsgConstants;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.ArrayResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.common.tools.MessageCommon;
import com.idg.bfzb.server.common.tools.ProjectCommon;
import com.idg.bfzb.server.file.dao.AttachmentRepository;
import com.idg.bfzb.server.file.model.dto.AttachmentEntity;
import com.idg.bfzb.server.pay.dao.FinanceTradeDetailRepository;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.project.dao.CategoryRepository;
import com.idg.bfzb.server.project.dao.EvaluateRecordRepository;
import com.idg.bfzb.server.project.dao.ProjectCategoryRepository;
import com.idg.bfzb.server.project.dao.ProjectPlanRepository;
import com.idg.bfzb.server.project.dao.ProjectProgressRepository;
import com.idg.bfzb.server.project.dao.ProjectRejectionRepository;
import com.idg.bfzb.server.project.dao.ProjectRepository;
import com.idg.bfzb.server.project.dao.ProjectRevealRepository;
import com.idg.bfzb.server.project.dao.ProjectTenderRepository;
import com.idg.bfzb.server.project.dao.ProjectWarrantyRepository;
import com.idg.bfzb.server.project.model.dto.CategoryEntity;
import com.idg.bfzb.server.project.model.dto.EvaluateRecordEntity;
import com.idg.bfzb.server.project.model.dto.ProjectCategoryEntity;
import com.idg.bfzb.server.project.model.dto.ProjectCategoryEntityId;
import com.idg.bfzb.server.project.model.dto.ProjectEntity;
import com.idg.bfzb.server.project.model.dto.ProjectProgressEntity;
import com.idg.bfzb.server.project.model.dto.ProjectRejectionEntity;
import com.idg.bfzb.server.project.model.dto.ProjectRevealEntity;
import com.idg.bfzb.server.project.model.dto.ProjectTenderEntity;
import com.idg.bfzb.server.project.model.dto.ProjectWarrantyEntity;
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
import com.idg.bfzb.server.project.model.response.CategoryResponse;
import com.idg.bfzb.server.project.model.response.ConfirmProjectResponse;
import com.idg.bfzb.server.project.model.response.EmployeeProjectDetailResponse;
import com.idg.bfzb.server.project.model.response.EmployeeProjectListResponse;
import com.idg.bfzb.server.project.model.response.EmployeeResultResponse;
import com.idg.bfzb.server.project.model.response.ProjectPublishResponse;
import com.idg.bfzb.server.project.model.response.ProjectSelectTenderResponse;
import com.idg.bfzb.server.project.model.response.ProjectStateTotalResponse;
import com.idg.bfzb.server.project.model.response.ReceiverBaseProjectDetailResponse;
import com.idg.bfzb.server.project.model.response.ReceiverProjectDetailResponse;
import com.idg.bfzb.server.project.model.response.ReceiverProjectListResponse;
import com.idg.bfzb.server.project.model.response.TenderUserDetailResponse;
import com.idg.bfzb.server.project.model.vo.EvaluateListVo;
import com.idg.bfzb.server.project.model.vo.ProjectCategoryAbilityVo;
import com.idg.bfzb.server.project.model.vo.ProjectListVo;
import com.idg.bfzb.server.project.model.vo.ProjectPlanVo;
import com.idg.bfzb.server.project.model.vo.ProjectProgressVo;
import com.idg.bfzb.server.project.model.vo.ProjectQryVo;
import com.idg.bfzb.server.project.model.vo.TenderUserDetailVo;
import com.idg.bfzb.server.project.service.ProjectService;
import com.idg.bfzb.server.usercenter.dao.UserInfoRepository;
import com.idg.bfzb.server.usercenter.dao.UserPersonalRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;
import com.idg.bfzb.server.utility.tools.DateUtil;
import com.idg.bfzb.server.utility.tools.StringUtil;
import com.idg.bfzb.server.utility.tools.ValidatorUtil;

@Service
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProjectRepository projectReponsitory;
	@Autowired
	private ProjectCategoryRepository projectCategoryReponsitory;
	@Autowired
	private ProjectTenderRepository projectTenderRepository;
	@Autowired
	private UserPersonalRepository userPersonalRepository;
	@Autowired
	private EvaluateRecordRepository evaluateRecordRepository;
	@Autowired
	private ProjectRevealRepository projectRevealRepository;
	@Autowired
	private AttachmentRepository attachmentRepository;
	@Autowired
	private ProjectProgressRepository projectProgressRepository;
	@Autowired
	private ProjectRejectionRepository projectRejectionRepository;
	@Autowired
	private ProjectWarrantyRepository projectWarrantyRepository;
	@Autowired 
	private UserInfoRepository userInfoRepository;
	@Autowired
	private AbilityRespository abilityRespository;
	@Autowired
	private ProjectPlanRepository projectPlanRepository;
	@Autowired
	private FinanceTradeDetailRepository financeTradeDetailRepository;
	@Resource
	private ProjectCommon projectCommon;
	@Resource
	private MessageCommon messageCommon;
	@Autowired
	private AuthenticationDao authenticationDao;

	@Override
	public APIResponse getAllProjectCategory() {
		APIResponse apiResponse = new APIResponse();
		
		try{
			List<CategoryResponse> list = new ArrayList<CategoryResponse>();
			
			List<CategoryEntity> categoryList = categoryRepository.findAllCategory();
			for(CategoryEntity ce:categoryList){
				CategoryResponse cResponse = new CategoryResponse();
				cResponse.setCategoryId(ce.getCategoryId());
				cResponse.setCategoryName(ce.getCategoryName());
				list.add(cResponse);
			}
			
			apiResponse.setData(list);
		} catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse recommendProject(RecommendRequest recommendRequest) {
		APIResponse apiResponse = new APIResponse();
		
		try{
			ProjectQryVo qryVo = new ProjectQryVo();
			qryVo.setState(Constants.PROJECT_STATE_SELECTING);
			qryVo.setNotTendUserId(recommendRequest.getNotUserId());
			qryVo.setTenderType(Constants.PROJECT_TENDER_COMMON);
			qryVo.setRegionProvince(recommendRequest.getRegion_province());
			if(!StringUtil.isNull(recommendRequest.getCategory_name())){
				qryVo.setCategoryId(URLDecoder.decode(recommendRequest.getCategory_name(), "UTF-8"));
			}
			if(!StringUtil.isNull(recommendRequest.getRegion_province())){
				qryVo.setRegionProvince(URLDecoder.decode(recommendRequest.getRegion_province(), "UTF-8"));
			}
			PageInfo<ProjectListVo> pageInfo = projectReponsitory.findProjectByPageAndCondition((recommendRequest.getOffset()-1)*recommendRequest.getSize(), recommendRequest.getSize(), qryVo);
			
			List<EmployeeProjectListResponse> list = new ArrayList<EmployeeProjectListResponse>();
			
			List<ProjectListVo> projectList = pageInfo.getPageData();
			for(ProjectListVo project:projectList){
				EmployeeProjectListResponse listResponse = new EmployeeProjectListResponse();
				listResponse.setProjectId(project.getProjectId());
				listResponse.setProjectName(project.getProjectName());
				listResponse.setCategorys(project.getCategoryIds());
				listResponse.setSignUpCount(project.getSignUpCount());
				listResponse.setBudget(project.getBudget());
				listResponse.setApplyDeadline(project.getApplyDeadline());
				listResponse.setSubmitDeadline(project.getSubmitDeadline());
				listResponse.setProjectType(project.getProjectType());
				listResponse.setTenderType(project.getTenderType());
				listResponse.setRegionProvince(project.getRegionProvince());
				listResponse.setRegionCity(project.getRegionCity());
				listResponse.setRegionArea(project.getRegionArea());
				listResponse.setRegionDetail(project.getRegionDetail());
				listResponse.setHouseNumber(project.getHouseNumber());
				list.add(listResponse);
			}
			ArrayResponse<EmployeeProjectListResponse> data = new ArrayResponse<EmployeeProjectListResponse>();
			data.setTotal((long) pageInfo.getTotalRows());
			data.setItems(list);
			apiResponse.setData(data);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse searchReceiverProject(ReceiverSearchRequest receiverSearchRequest) {
		APIResponse apiResponse = new APIResponse();
		
		List<EmployeeProjectListResponse> list = new ArrayList<EmployeeProjectListResponse>();
		
		try {
			ProjectQryVo qryVo = new ProjectQryVo();
			qryVo.setState(Constants.PROJECT_STATE_SELECTING);
			qryVo.setNotTendUserId(receiverSearchRequest.getNotUserId());
			qryVo.setTenderType(Constants.PROJECT_TENDER_COMMON);
			
			if(!StringUtil.isNull(receiverSearchRequest.getProject_name())){
				qryVo.setProjectName(URLDecoder.decode(receiverSearchRequest.getProject_name(), "UTF-8"));
			}
			qryVo.setDeadline(receiverSearchRequest.getDeadline());
			if(!StringUtil.isNull(receiverSearchRequest.getCategory_name())){
				qryVo.setCategoryId(URLDecoder.decode(receiverSearchRequest.getCategory_name(), "UTF-8"));
			}
			if(!StringUtil.isNull(receiverSearchRequest.getRegion_province())){
				qryVo.setRegionProvince(URLDecoder.decode(receiverSearchRequest.getRegion_province(), "UTF-8"));
			}
			String budget = receiverSearchRequest.getBudget();
			if(!StringUtil.isNull(budget)){
				if(budget.contains(Constants.PROJECT_BUDGET_SPLIT)){
					String[] budgets = budget.split(Constants.PROJECT_BUDGET_SPLIT);
					if(budgets.length!=2){
						apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
						return apiResponse;
					}else{
						if(StringUtil.isNumber(budgets[0]) && StringUtil.isNumber(budgets[1])){
							qryVo.setMinBudget(Double.valueOf(budgets[0]));
							qryVo.setMaxBudget(Double.valueOf(budgets[1]));
						}else{
							apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
							return apiResponse;
						}
					}
				}else{
					if(StringUtil.isNumber(receiverSearchRequest.getBudget())){
						qryVo.setMinBudget(Double.valueOf(receiverSearchRequest.getBudget()));
					}else{
						apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
						return apiResponse;
					}
				}
			}
			
			PageInfo<ProjectListVo> pageInfo = projectReponsitory.findProjectByPageAndCondition((receiverSearchRequest.getOffset()-1)*receiverSearchRequest.getSize(), receiverSearchRequest.getSize(), qryVo);
			
			List<ProjectListVo> projectList = pageInfo.getPageData();
			for(ProjectListVo project:projectList){
				EmployeeProjectListResponse listResponse = new EmployeeProjectListResponse();
				listResponse.setProjectId(project.getProjectId());
				listResponse.setProjectName(project.getProjectName());
				listResponse.setCategorys(project.getCategoryIds());
				listResponse.setSignUpCount(project.getSignUpCount());
				listResponse.setBudget(project.getBudget());
				listResponse.setApplyDeadline(project.getApplyDeadline());
				listResponse.setSubmitDeadline(project.getSubmitDeadline());
				listResponse.setProjectType(project.getProjectType());
				listResponse.setTenderType(project.getTenderType());
				listResponse.setRegionProvince(project.getRegionProvince());
				listResponse.setRegionCity(project.getRegionCity());
				listResponse.setRegionArea(project.getRegionArea());
				list.add(listResponse);
			}
			ArrayResponse<EmployeeProjectListResponse> data = new ArrayResponse<EmployeeProjectListResponse>();
			data.setTotal((long) pageInfo.getTotalRows());
			data.setItems(list);
			apiResponse.setData(data);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse employeeProjectList(EmployeeProjectListRequest projectListRequest) {
		APIResponse apiResponse = new APIResponse();
		
		try{
			List<EmployeeProjectListResponse> list = new ArrayList<EmployeeProjectListResponse>();
			
			ProjectQryVo qryVo = new ProjectQryVo();
			qryVo.setState(projectListRequest.getState());
			qryVo.setEmployerId(projectListRequest.getUserId());
			
			PageInfo<ProjectListVo> pageInfo = projectReponsitory.findProjectByPageAndCondition((projectListRequest.getOffset()-1)*projectListRequest.getSize(), projectListRequest.getSize(), qryVo);
			
			List<ProjectListVo> projectList = pageInfo.getPageData();
			for(ProjectListVo project:projectList){
				EmployeeProjectListResponse listResponse = new EmployeeProjectListResponse();
				listResponse.setProjectId(project.getProjectId());
				listResponse.setProjectName(project.getProjectName());
				listResponse.setState(project.getProjectState());
				listResponse.setProjectType(project.getProjectType());
				listResponse.setTenderType(project.getTenderType());
				listResponse.setCategorys(project.getCategoryIds());
				listResponse.setSignUpCount(project.getSignUpCount());
				listResponse.setBudget(project.getBudget());
				listResponse.setApplyDeadline(project.getApplyDeadline());
				listResponse.setSubmitDeadline(project.getSubmitDeadline());
				listResponse.setProjectType(project.getProjectType());
				listResponse.setTenderType(project.getTenderType());
				listResponse.setEmployerId(project.getEmployerId());
				listResponse.setTenderUserId(project.getTenderUserId());
				listResponse.setCreateTime(project.getCreateTime());
				listResponse.setTradeMoney(project.getTradeMoney());
				//查询用户基本信息
				UcUserInfoEntity userInfoEntity = userInfoRepository.findOne(project.getEmployerId());
				listResponse.setEmployerNickName(userInfoEntity.getNickName());
				listResponse.setEmployerMobile(userInfoEntity.getMobile());
				list.add(listResponse);
			}
			ArrayResponse<EmployeeProjectListResponse> data = new ArrayResponse<EmployeeProjectListResponse>();
			data.setTotal((long) pageInfo.getTotalRows());
			data.setItems(list);
			apiResponse.setData(data);
		} catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse publishProject(ProjectPublishRequest proPublishRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String categoryIds = proPublishRequest.getCategoryIds();//服务类别
		String projectName = proPublishRequest.getProjectName();//项目名称
		String address = proPublishRequest.getAddress();//工作地址
		String houseNumber = proPublishRequest.getHouseNumber();//门牌号
		String description = proPublishRequest.getDescription();//项目详细信息
		String applyDealine = proPublishRequest.getApplyDeadline();//招标截止日期
		String submitDealine = proPublishRequest.getSubmitDeadline();//完成日期
		String telephone = proPublishRequest.getTelephone();//联系电话
		Double budget = proPublishRequest.getBudget();//招标预算
		String tenderType = proPublishRequest.getTenderType();//招标类型
		String regionProvince = proPublishRequest.getRegionProvince();//省
		String regionCity = proPublishRequest.getRegionCity();//市
		String regionArea = proPublishRequest.getRegionArea();//区
		String longitude = proPublishRequest.getLongitude();//经度
		String latitude = proPublishRequest.getLatitude();//纬度
		//参数空判断
		if(StringUtil.isNull(tenderType) || StringUtil.isNull(categoryIds) 
				 || !(tenderType.equalsIgnoreCase(Constants.PROJECT_TENDER_COMMON)||tenderType.equalsIgnoreCase(Constants.PROJECT_TENDER_CONTAIN))
				 || StringUtil.isNull(projectName) || StringUtil.isNull(address) || StringUtil.isNull(telephone)
				 || StringUtil.isNull(houseNumber) || StringUtil.isNull(description) || StringUtil.isNull(submitDealine)
				 || StringUtil.isNull(regionProvince) || StringUtil.isNull(regionCity) || StringUtil.isNull(regionArea)
				 || StringUtil.isNull(longitude) || StringUtil.isNull(latitude)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		//参数长度限制
		if(projectName.length()>Constants.STRING_LENGTH_30){
			apiResponse.setErrorCode(ErrorCode.PROJECTS_NAME_TOO_LENGTH);
			return apiResponse;
		}else if(houseNumber.length()>Constants.STRING_LENGTH_100){
			apiResponse.setErrorCode(ErrorCode.PROJECTS_HOUSENUM_TOO_LENGTH);
			return apiResponse;
		}else if(description.length()>Constants.STRING_LENGTH_500){
			apiResponse.setErrorCode(ErrorCode.PROJECTS_DESCRIPTION_TOO_LENGTH);
			return apiResponse;
		}
		//手机号码格式验证
		if(!ValidatorUtil.isMobile(telephone)){
			apiResponse.setErrorCode(ErrorCode.UC_MOBILE_NOT_CORRECT);
			return apiResponse;
		}
		//时间格式验证(yyyyMMdd)
		if(submitDealine.length()!=8 || !StringUtil.isNumber(submitDealine)){
			apiResponse.setErrorCode(ErrorCode.INVALID_DATE_ARGUMENT);
			return apiResponse;
		}
		//招标时额外判断参数
		if(Constants.PROJECT_TENDER_COMMON.equalsIgnoreCase(tenderType)){
			//参数空判断
			if(budget==null|| StringUtil.isNull(applyDealine)){
				apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
				return apiResponse;
			}
			//时间格式验证(yyyyMMdd)
			if(applyDealine.length()!=8 || !StringUtil.isNumber(applyDealine)){
				apiResponse.setErrorCode(ErrorCode.INVALID_DATE_ARGUMENT);
				return apiResponse;
			}
			//完成时间与报名截止时间大小判断
			if(Integer.valueOf(submitDealine)<Integer.valueOf(applyDealine)){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_SUBMITDEADLINE_LOW_APPLYDEADLINE);
				return apiResponse;
			}
		}
		
		try{
			List<ProjectEntity> isNameExist = projectReponsitory.findByProjectName(projectName);
			if(isNameExist!=null && isNameExist.size()>0){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NAME_EXIST);
				return apiResponse;
			}
			String projectId = StringUtil.getUUID();//项目UUID
			StringBuffer categoryNames = new StringBuffer();
			List<ProjectCategoryEntity> proCategoryList = new ArrayList<ProjectCategoryEntity>();
			//判断服务类别ID是否存在
			String[] categoryIdArray = categoryIds.split(Constants.PROJECT_CATEGORY_SPLIT);
			for(String categoryId:categoryIdArray){
				CategoryEntity categoryEntity = categoryRepository.findOne(categoryId);
				if(categoryEntity==null){
					apiResponse.setErrorCode(ErrorCode.PROJECTS_CATEGORYID_NOT_EXIST);
					return apiResponse;
				}else{
					ProjectCategoryEntity projectCategoryEntity = new ProjectCategoryEntity();
					ProjectCategoryEntityId ids = new ProjectCategoryEntityId();
					ids.setRequireId(projectId);
					ids.setCategoryId(categoryId);
					projectCategoryEntity.setId(ids);
					proCategoryList.add(projectCategoryEntity);
					categoryNames.append(categoryEntity.getCategoryName()).append(Constants.PROJECT_CATEGORY_SPLIT);
				}
			}
			ProjectEntity projectEntity = new ProjectEntity();
			projectEntity.setProjectId(projectId);
			projectEntity.setEmployerId(proPublishRequest.getOwnerId());
			projectEntity.setProjectName(projectName);
			if(categoryNames.length()>0){
				categoryNames.deleteCharAt(categoryNames.length()-1);
				projectEntity.setCategoryIds(categoryNames.toString());
			}
			if(Constants.PROJECT_TENDER_COMMON.equalsIgnoreCase(tenderType)){
				projectEntity.setApplyDeadline(DateUtil.getTimestampByDateString(applyDealine, "yyyyMMdd"));
				projectEntity.setBudget(budget);
			}
			UserPersonalEntity userPersonalEntity = userPersonalRepository.findOne(proPublishRequest.getOwnerId());
			if(userPersonalEntity!=null && userPersonalEntity.getIsEnterpriseUser()!=null && userPersonalEntity.getIsEnterpriseUser()==1){
				projectEntity.setImprestScale(0.3f);
			}else{
				projectEntity.setImprestScale(1.0f);
			}
			projectEntity.setMarginScale(0f);
			projectEntity.setRevealScale(0f);
			projectEntity.setIsMargin(Constants.PROJECT_IS_MARGIN_NO);
			projectEntity.setIsReveal(Constants.PROJECT_IS_REVEAL_NO);
			projectEntity.setSubmitDeadline(DateUtil.getTimestampByDateString(submitDealine, "yyyyMMdd"));
			projectEntity.setDescription(description);
			projectEntity.setProjectType(Constants.PROJECT_TYPE_INDIVIDUAL);
			projectEntity.setTenderType(/*Constants.PROJECT_TENDER_COMMON*/tenderType);
			projectEntity.setRegionDetail(address);
			projectEntity.setHouseNumber(houseNumber);
			projectEntity.setTelephone(telephone);
			projectEntity.setSignUpCount(0);
			projectEntity.setState(Constants.PROJECT_STATE_NOT_APPROVE);
			projectEntity.setLatitude(latitude);
			projectEntity.setLongitude(longitude);
			projectEntity.setRegionArea(regionArea);
			projectEntity.setRegionCity(regionCity);
			projectEntity.setRegionProvince(regionProvince);
			projectReponsitory.save(projectEntity);
			projectCategoryReponsitory.save(proCategoryList);
			
			ProjectPublishResponse projectPublishResponse = new ProjectPublishResponse();
			projectPublishResponse.setProjectId(projectId);
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
			apiResponse.setData(projectPublishResponse);
		} catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;//抛出异常，事务进行回滚
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse receiverProjectList(EmployeeProjectListRequest projectListRequest) {
		APIResponse apiResponse = new APIResponse();
		
		try{
			List<ReceiverProjectListResponse> list = new ArrayList<ReceiverProjectListResponse>();
			
			ProjectQryVo qryVo = new ProjectQryVo();
			qryVo.setState(projectListRequest.getState());
			qryVo.setTenderUserId(projectListRequest.getUserId());
			//qryVo.setEmployerId(projectListRequest.getUserId());
			
			PageInfo<ProjectListVo> pageInfo = projectTenderRepository.findProjectTenderByPageAndCondition((projectListRequest.getOffset()-1)*projectListRequest.getSize(), projectListRequest.getSize(), qryVo);
			
			List<ProjectListVo> projectList = pageInfo.getPageData();
			for(ProjectListVo project:projectList){
				ReceiverProjectListResponse listResponse = new ReceiverProjectListResponse();
				listResponse.setProjectId(project.getProjectId());
				listResponse.setProjectName(project.getProjectName());
				listResponse.setProjectState(project.getProjectState());
				listResponse.setTenderState(project.getTenderState());
				listResponse.setProjectType(project.getProjectType());
				listResponse.setTenderType(project.getTenderType());
				listResponse.setCategorys(project.getCategoryIds());
				listResponse.setSignUpCount(project.getSignUpCount());
				listResponse.setBudget(project.getBudget());
				listResponse.setApplyDeadline(project.getApplyDeadline());
				listResponse.setSubmitDeadline(project.getSubmitDeadline());
				listResponse.setCreateTime(project.getCreateTime());
				listResponse.setTradeMoney(project.getTradeMoney());
				listResponse.setRegionProvince(project.getRegionProvince());
				listResponse.setRegionCity(project.getRegionCity());
				listResponse.setRegionArea(project.getRegionArea());
				listResponse.setIsMargin(project.getIsMargin());
				listResponse.setMarginDay(project.getMarginDay());
				listResponse.setMarginScale(project.getMarginScale());
				listResponse.setLongitude(project.getLongitude());
				listResponse.setLatitude(project.getLatitude());
				list.add(listResponse);
			}
			ArrayResponse<ReceiverProjectListResponse> data = new ArrayResponse<ReceiverProjectListResponse>();
			data.setTotal((long) pageInfo.getTotalRows());
			data.setItems(list);
			apiResponse.setData(data);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse employeeProjectDetail(String currentUser, String projectId) {
		APIResponse apiResponse = new APIResponse();
		
		try{
			ProjectEntity project = projectReponsitory.findOne(projectId);
			//判断项目是否存在
			if(project==null || !currentUser.equals(project.getEmployerId())){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			
			EmployeeProjectDetailResponse detailResponse = new EmployeeProjectDetailResponse();
			detailResponse.setProjectId(project.getProjectId());
			detailResponse.setProjectName(project.getProjectName());
			detailResponse.setState(project.getState());
			detailResponse.setProjectType(project.getProjectType());
			detailResponse.setTenderType(project.getTenderType());
			detailResponse.setCategorys(project.getCategoryIds());
			detailResponse.setSignUpCount(project.getSignUpCount());
			detailResponse.setBudget(project.getBudget());
			detailResponse.setApplyDeadline(DateUtil.formatTimestampDateString(project.getApplyDeadline(),"yyyy-MM-dd"));
			detailResponse.setSubmitDeadline(DateUtil.formatTimestampDateString(project.getSubmitDeadline(),"yyyy-MM-dd"));
			detailResponse.setCreateTime(DateUtil.formatTimestampDateString(project.getCreateTime(),"yyyy-MM-dd"));
			detailResponse.setUpdateTime(DateUtil.formatTimestampDateString(project.getUpdateTime(),"yyyy-MM-dd"));
			detailResponse.setProjectType(project.getProjectType());
			detailResponse.setTenderType(project.getTenderType());
			detailResponse.setDescription(project.getDescription());
			detailResponse.setImprestScale(project.getImprestScale());
			detailResponse.setRevealScale(project.getRevealScale());
			detailResponse.setProgress(null);
			detailResponse.setPlans(null);
			detailResponse.setAdminReviewState((short) -1);
			detailResponse.setIsEvaluate(Constants.PROJECT_EVALUATE_NO);
			detailResponse.setIsReveal(project.getIsReveal());
			detailResponse.setTradeMoney(project.getTradeMoney());
			detailResponse.setIsMargin(project.getIsMargin());
			detailResponse.setMarginDay(project.getMarginDay());
			detailResponse.setMarginScale(project.getMarginScale());
			detailResponse.setRegionDetail(project.getRegionDetail());
			detailResponse.setHouseNumber(project.getHouseNumber());
			detailResponse.setRegionProvince(project.getRegionProvince());
			detailResponse.setRegionCity(project.getRegionCity());
			detailResponse.setRegionArea(project.getRegionArea());
			detailResponse.setLongitude(project.getLongitude());
			detailResponse.setLatitude(project.getLatitude());
			//接包确认完成时间
			if(project.getReceiverCompleteTime()!=null){
				detailResponse.setReceiverCompleteTime(DateUtil.formatTimestampDateString(project.getReceiverCompleteTime(),"yyyy-MM-dd"));
			}
			//发包确认完成时间
			if(project.getEmployerCompleteTime()!=null){
				detailResponse.setEmployerCompleteTime(DateUtil.formatTimestampDateString(project.getEmployerCompleteTime(),"yyyy-MM-dd"));
			}
			//中标用户信息
			String tenderUserId = project.getTenderUserId();
			if(StringUtils.isNotBlank(tenderUserId)){
				UcUserInfoEntity userInfoEntity = userInfoRepository.findOne(tenderUserId);
				detailResponse.setTenderUserId(tenderUserId);
				detailResponse.setTenderUserName(userInfoEntity.getUserName());
				detailResponse.setTenderNickName(userInfoEntity.getNickName());
				detailResponse.setTenderMobile(userInfoEntity.getMobile());
			}
			//未提交兜底申请（默认）
			detailResponse.setRevealState(Short.valueOf("-1"));
			//进行中项目以及待验收项目
			if(Constants.PROJECT_STATE_SELECTED==project.getState() || Constants.PROJECT_STATE_CHECK == project.getState()){
				//中标用户工作进度
				List<ProjectProgressVo> progress = projectProgressRepository.findProjectProgressVoByProjectIdAndEventType(projectId, Constants.PROJECT_PROGRESS_TYPE_WORK);
				if(progress==null || progress.size()<=0){
					detailResponse.setProgress(null);
				}else{
					detailResponse.setProgress(progress);
				}
				//获取项目计划
				List<ProjectPlanVo> plans = projectPlanRepository.getProjectPlanByProjectId(projectId);
				if(plans==null || plans.size()<=0){
					detailResponse.setPlans(null);
				}else{
					detailResponse.setPlans(plans);
				}
				//是否购买兜底服务
				FinanceTradeDetailEntity entity = new FinanceTradeDetailEntity();
				entity.setTargetType(Constants.TARGET_TYPE_REVEALMONEY);
				entity.setTargetId(projectId);
				entity.setPayUserId(project.getEmployerId());
				if(financeTradeDetailRepository.countByCond(entity)<=0){
					detailResponse.setIsPayReveal(Constants.PROJECT_IS_REVEAL_NO);
				}else{
					detailResponse.setIsPayReveal(Constants.PROJECT_IS_REVEAL_YES);
				}
				//查询申请兜底状态
				ProjectRevealEntity reveal = projectRevealRepository.findOne(projectId);
				if(reveal != null){
					detailResponse.setRevealState(reveal.getAdminReviewState());
				}
			}
			if(Constants.PROJECT_STATE_CHECK==project.getState()){
				detailResponse.setRemainMoney(project.getTradeMoney()*(1-project.getImprestScale()));
				//待验收中的项目返回雇主拒绝信息
				ProjectRejectionEntity projectRejectionEntity = projectRejectionRepository.findOne(projectId);
				if(projectRejectionEntity!=null){//拒收
					detailResponse.setRefuseReason(projectRejectionEntity.getEmployerReason());
					detailResponse.setRefuseTenderReviewState(projectRejectionEntity.getTenderReviewState());
					detailResponse.setRefuseTenderReivewReason(projectRejectionEntity.getTenderReivewReason());
					detailResponse.setRefuseAdminReviewState(projectRejectionEntity.getAdminReviewState());
					detailResponse.setRefuseAdminReviewReason(projectRejectionEntity.getAdminReviewReason());
					detailResponse.setIsRefuse(Constants.PROJECT_REFUSE_EMPLOYEE_YES);
				}else{
					detailResponse.setIsRefuse(Constants.PROJECT_REFUSE_EMPLOYEE_NO);//未拒收
				}
			}else if(Constants.PROJECT_STATE_OVER==project.getState()){//已完成的项目
				ProjectWarrantyEntity projectWarrantyEntity = projectWarrantyRepository.findOne(projectId);
				//用户信息
				UcUserInfoEntity userInfoEntity = userInfoRepository.findOne(project.getTenderUserId());
				if(userInfoEntity!=null){
					detailResponse.setTenderUserId(userInfoEntity.getUserId());
					detailResponse.setTenderUserName(userInfoEntity.getUserName());
					detailResponse.setTenderNickName(userInfoEntity.getNickName());
					detailResponse.setTenderMobile(userInfoEntity.getMobile());
				}
				if(projectWarrantyEntity!=null){
					detailResponse.setAdminReviewState(projectWarrantyEntity.getAdminReviewState());
				}else{
					detailResponse.setAdminReviewState((short) -1);
				}
				EvaluateRecordEntity evaluateEntity = new EvaluateRecordEntity();
				evaluateEntity.setTargetId(projectId);
				evaluateEntity.setTargetOwnerId(project.getTenderUserId());
				int cnt = evaluateRecordRepository.countByCondition(evaluateEntity);
				if(cnt>0){
					detailResponse.setIsEvaluate(Constants.PROJECT_EVALUATE_YES);
				}
			}
			apiResponse.setData(detailResponse);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse confirmProject(String currentUser, String projectId, String result) {
		APIResponse apiResponse = new APIResponse();
		//参数空判断
		if(StringUtil.isNull(projectId) || StringUtil.isNull(result)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		//判断参数格式是否合法
		if(!Constants.PROJECT_PUBLISH_OK.equalsIgnoreCase(result) && !Constants.PROJECT_PUBLISH_GIVEUP.equalsIgnoreCase(result)){
			apiResponse.setErrorCode(ErrorCode.INVALID_ARGUMENT);
			return apiResponse;
		}
		ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
		//判断项目是否存在
		if(projectEntity==null || !currentUser.equals(projectEntity.getEmployerId())){
			apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
			return apiResponse;
		}
		//判断确认的项目是否是审批通过状态
		if(projectEntity.getState()!=Constants.PROJECT_STATE_APPROVE_PASS){
			apiResponse.setErrorCode(ErrorCode.PROJECTS_STATE_NOT_APPROVE_PASS);
			return apiResponse;
		}
		try{
			//确认发布
			if(Constants.PROJECT_PUBLISH_OK.equalsIgnoreCase(result)){
				//确认发布，状态变为选标中
				projectEntity.setState(Constants.PROJECT_STATE_SELECTING);
			}else if(Constants.PROJECT_PUBLISH_GIVEUP.equalsIgnoreCase(result)){
				//确认不发布，状态变为已失效
				projectEntity.setState(Constants.PROJECT_STATE_INVALID);
			}
			projectReponsitory.save(projectEntity);
			
			ConfirmProjectResponse confirmProjectResponse = new ConfirmProjectResponse();
			confirmProjectResponse.setProjectId(projectEntity.getProjectId());
			confirmProjectResponse.setState(projectEntity.getState());
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
			apiResponse.setData(confirmProjectResponse);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse receiverProjectDetail(String currentUser, String projectId) {
		APIResponse apiResponse = new APIResponse();
		//参数空判断
		if(StringUtil.isNull(projectId)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
		//判断项目是否存在
		if(projectEntity==null){
			apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
			return apiResponse;
		}
		
		try{
			ReceiverProjectDetailResponse detailResponse = new ReceiverProjectDetailResponse();
			detailResponse.setProjectId(projectEntity.getProjectId());
			detailResponse.setProjectName(projectEntity.getProjectName());
			detailResponse.setProjectState(projectEntity.getState());
			detailResponse.setProjectType(projectEntity.getProjectType());
			detailResponse.setTenderType(projectEntity.getTenderType());
			detailResponse.setCategorys(projectEntity.getCategoryIds());
			detailResponse.setSignUpCount(projectEntity.getSignUpCount());
			detailResponse.setBudget(projectEntity.getBudget());
			detailResponse.setApplyDeadline(DateUtil.formatTimestampDateString(projectEntity.getApplyDeadline(),"yyyy-MM-dd"));
			detailResponse.setSubmitDeadline(DateUtil.formatTimestampDateString(projectEntity.getSubmitDeadline(),"yyyy-MM-dd"));
			detailResponse.setCreateTime(DateUtil.formatTimestampDateString(projectEntity.getCreateTime(),"yyyy-MM-dd"));
			detailResponse.setUpdateTime(DateUtil.formatTimestampDateString(projectEntity.getUpdateTime(),"yyyy-MM-dd"));
			detailResponse.setProjectType(projectEntity.getProjectType());
			detailResponse.setTenderType(projectEntity.getTenderType());
			detailResponse.setDescription(projectEntity.getDescription());
			detailResponse.setIsEvaluate(Constants.PROJECT_EVALUATE_NO);
			detailResponse.setRegionDetail(projectEntity.getRegionDetail());
			detailResponse.setRegionProvince(projectEntity.getRegionProvince());
			detailResponse.setRegionCity(projectEntity.getRegionCity());
			detailResponse.setRegionArea(projectEntity.getRegionArea());
			detailResponse.setHouseNumber(projectEntity.getHouseNumber());
			detailResponse.setLatitude(projectEntity.getLatitude());
			detailResponse.setLongitude(projectEntity.getLongitude());
			detailResponse.setTradeMoney(projectEntity.getTradeMoney());
			detailResponse.setIsMargin(projectEntity.getIsMargin());
			detailResponse.setMarginDay(projectEntity.getMarginDay());
			detailResponse.setMarginScale(projectEntity.getMarginScale());
			detailResponse.setImprestScale(projectEntity.getImprestScale());
			//接包确认完成时间
			if(projectEntity.getReceiverCompleteTime()!=null){
				detailResponse.setReceiverCompleteTime(DateUtil.formatTimestampDateString(projectEntity.getReceiverCompleteTime(),"yyyy-MM-dd"));
			}
			//发包确认完成时间
			if(projectEntity.getEmployerCompleteTime()!=null){
				detailResponse.setEmployerCompleteTime(DateUtil.formatTimestampDateString(projectEntity.getEmployerCompleteTime(),"yyyy-MM-dd"));
			}
			//接包者报名状态
			if(projectEntity.getState()!=Constants.PROJECT_STATE_SELECTING){
				detailResponse.setSignUpState(Constants.PROJECT_SIGNUP_STATE_NO);//不可报名
			}else{
				if(StringUtil.isNull(projectEntity.getTenderUserId())){
					ProjectTenderEntity projectTenderEntity = new ProjectTenderEntity();
					projectTenderEntity.setProjectId(projectId);
					projectTenderEntity.setTenderUserId(currentUser);
					if(projectTenderRepository.countByCondition(projectTenderEntity)>0){
						detailResponse.setSignUpState(Constants.PROJECT_SIGNUP_STATE_ALREADY);//已报名
					}else{
						detailResponse.setSignUpState(Constants.PROJECT_SIGNUP_STATE_OK);//可报名
					}
				}else if(currentUser.equals(projectEntity.getTenderUserId())){
					detailResponse.setSignUpState(Constants.PROJECT_SIGNUP_STATE_ALREADY);//已报名
				}else{
					detailResponse.setSignUpState(Constants.PROJECT_SIGNUP_STATE_NO);//不可报名
				}
			}
			//雇主信息
			String employerId = projectEntity.getEmployerId();
			UcUserInfoEntity userInfoEntity = userInfoRepository.findOne(employerId);
			if(userInfoEntity!=null){
				detailResponse.setEmployerUserId(employerId);
				detailResponse.setEmployerUserName(userInfoEntity.getUserName());
				detailResponse.setEmployerNickName(userInfoEntity.getNickName());
				detailResponse.setEmployerMobile(projectEntity.getTelephone());
			}
			//待验收中的项目返回雇主拒绝信息
			if(Constants.PROJECT_STATE_CHECK==projectEntity.getState()){
				ProjectRejectionEntity projectRejectionEntity = projectRejectionRepository.findOne(projectId);
				if(projectRejectionEntity!=null){//拒收
					detailResponse.setRefuseReason(projectRejectionEntity.getEmployerReason());
					detailResponse.setTenderReviewState(projectRejectionEntity.getTenderReviewState());
					detailResponse.setTenderReivewReason(projectRejectionEntity.getTenderReivewReason());
					detailResponse.setAdminReviewState(projectRejectionEntity.getAdminReviewState());
					detailResponse.setAdminReviewReason(projectRejectionEntity.getAdminReviewReason());
					detailResponse.setIsRefuse(Constants.PROJECT_REFUSE_EMPLOYEE_YES);
				}else{
					detailResponse.setIsRefuse(Constants.PROJECT_REFUSE_EMPLOYEE_NO);//未拒收
				}
			}
			//报名中给返回评价信息以及技能信息
			if(Constants.PROJECT_STATE_SELECTING==projectEntity.getState()){
				//评价信息
				EvaluateRecordEntity evaluateRecordEntity = new EvaluateRecordEntity();
				evaluateRecordEntity.setTargetOwnerId(projectEntity.getEmployerId());
				evaluateRecordEntity.setTargetType(Constants.PROJECT_EVALUATE_TARGET_TYPE_EMPLOYER);
				PageInfo<EvaluateListVo> pageInfo = evaluateRecordRepository.findEvaluateListVoByCondition(0, 2, evaluateRecordEntity);
				detailResponse.setEvaluates(pageInfo.getPageData());
				//技能信息
				if(Constants.PROJECT_SIGNUP_STATE_OK==detailResponse.getSignUpState()){
					List<AbilityVo> abilityVoList = abilityRespository.queryAuditAbility(currentUser);
					List<ProjectCategoryAbilityVo> proCategoryAbilityVoList = projectCategoryReponsitory.findProjectCategoryAbilityByProjectId(projectId);
					String matchAbilityMsg = "无法竞标，你缺少";//匹配技能提示信息
					for(ProjectCategoryAbilityVo projectCategoryAbilityVo:proCategoryAbilityVoList){
						String abilityId = projectCategoryAbilityVo.getAbilityId();
						boolean isMatchAbility = false;
						for(AbilityVo abilityVo:abilityVoList){
							if(!StringUtil.isNull(abilityId) && abilityId.equals(abilityVo.getAbilityId())){
								isMatchAbility = true;
								break;
							}
						}
						if(!isMatchAbility){
							matchAbilityMsg += (projectCategoryAbilityVo.getAbilityName()+",");
						}
					}
					if(!"无法竞标，你缺少".equals(matchAbilityMsg)){
						detailResponse.setIsMatchAbility(false);
						detailResponse.setMatchAbilityMsg(matchAbilityMsg.substring(0, matchAbilityMsg.length()-1)+"技能");
					}else{
						detailResponse.setIsMatchAbility(true);
					}
				}
			}
			//已完成的项目返回是否评价
			if(Constants.PROJECT_STATE_OVER==projectEntity.getState()){
				EvaluateRecordEntity evaluateEntity = new EvaluateRecordEntity();
				evaluateEntity.setTargetId(projectId);
				evaluateEntity.setTargetOwnerId(projectEntity.getEmployerId());
				int cnt = evaluateRecordRepository.countByCondition(evaluateEntity);
				if(cnt>0){
					detailResponse.setIsEvaluate(Constants.PROJECT_EVALUATE_YES);
				}
			}
			//进行中项目或待验收项目
			if(Constants.PROJECT_STATE_SELECTED==projectEntity.getState() || Constants.PROJECT_STATE_CHECK==projectEntity.getState()){
				//中标用户工作进度
				List<ProjectProgressVo> progress = projectProgressRepository.findProjectProgressVoByProjectIdAndEventType(projectId, Constants.PROJECT_PROGRESS_TYPE_WORK);
				if(progress==null || progress.size()<=0){
					detailResponse.setProgress(null);
				}else{
					detailResponse.setProgress(progress);
				}
			}
				
			apiResponse.setData(detailResponse);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse signUpProject(ProjectSignUpRequest projectSignUpRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String projectId = projectSignUpRequest.getProjectId();
		Double offer = projectSignUpRequest.getOffer();
		String description = projectSignUpRequest.getDescription();
		String mobile = projectSignUpRequest.getMobile();
		String tenderUserId = projectSignUpRequest.getUserId();
		//参数空判断
		if(StringUtil.isNull(projectId) || offer==null || StringUtil.isNull(mobile)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		//手机格式判断
		if(!ValidatorUtil.isMobile(mobile)){
			apiResponse.setErrorCode(ErrorCode.UC_MOBILE_NOT_CORRECT);
			return apiResponse;
		}
		try{
			ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
			//项目是否存在
			if(projectEntity==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}else if(Constants.PROJECT_STATE_SELECTING!=projectEntity.getState()){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_STATE_NOT_SELECTING);
				return apiResponse;
			}else if(!StringUtil.isNull(projectEntity.getTenderUserId()) && projectEntity.getTenderUserId().equals(projectSignUpRequest.getUserId())){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_ALREADY_SIGNUP);
				return apiResponse;
			}else{
				ProjectTenderEntity query = new ProjectTenderEntity();
				query.setProjectId(projectId);
				query.setTenderUserId(tenderUserId);
				if(projectTenderRepository.countByCondition(query)>0){
					apiResponse.setErrorCode(ErrorCode.PROJECTS_ALREADY_SIGNUP);
					return apiResponse;
				}
			}
			
			ProjectTenderEntity projectTenderEntity = new ProjectTenderEntity();
			projectTenderEntity.setProjectId(projectId);
			projectTenderEntity.setTenderUserId(tenderUserId);
			projectTenderEntity.setOffer(offer);
			projectTenderEntity.setDescription(description);
			projectTenderEntity.setMobile(mobile);
			projectTenderEntity.setState(Constants.PROJECT_TENDER_STATE_LAUNCH);
			projectTenderRepository.save(projectTenderEntity);
			projectEntity.setSignUpCount(projectEntity.getSignUpCount()+1);
			projectReponsitory.save(projectEntity);
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse searchTenders(TendersSearchRequest tendersSearchRequest) {
		APIResponse apiResponse = new APIResponse();
		
		List<TenderUserDetailResponse> list = new ArrayList<TenderUserDetailResponse>();
		
		Integer offset = tendersSearchRequest.getOffset();
		Integer size = tendersSearchRequest.getSize();
		ProjectTenderEntity projectTenderEntity = new ProjectTenderEntity();
		projectTenderEntity.setProjectId(tendersSearchRequest.getProjectId());
		projectTenderEntity.setState(Constants.PROJECT_TENDER_STATE_LAUNCH);
		
		try{
			PageInfo<TenderUserDetailVo> pageInfo = projectTenderRepository.findTenderUserByCondition((offset-1)*size, size, projectTenderEntity);
			
			List<TenderUserDetailVo> tenderUsers = pageInfo.getPageData();
			projectTenderEntity.setProjectId(null);
			
			for(TenderUserDetailVo tenderUser:tenderUsers){
				TenderUserDetailResponse tenderUserDetailResponse = new TenderUserDetailResponse();
				
				tenderUserDetailResponse.setTenderUserId(tenderUser.getTenderUserId());
				tenderUserDetailResponse.setTenderUserName(tenderUser.getUserName());
				tenderUserDetailResponse.setTenderNickName(tenderUser.getNickName());
				tenderUserDetailResponse.setIconSmallAttchId(tenderUser.getIconSmallAttchId());
				tenderUserDetailResponse.setIconSmallUrl(ConfigFileUtils.HEAD_ICON_URL + tenderUser.getIconSmallUrl());
				tenderUserDetailResponse.setOffer(tenderUser.getOffer());
				tenderUserDetailResponse.setDescription(tenderUser.getDescription());
				tenderUserDetailResponse.setStar(evaluateRecordRepository.findAverageStar(tenderUser.getTenderUserId()));
				projectTenderEntity.setTenderUserId(tenderUser.getTenderUserId());
				tenderUserDetailResponse.setListNum(projectTenderRepository.countByCondition(projectTenderEntity));
				
				list.add(tenderUserDetailResponse);
			}
			
			ArrayResponse<TenderUserDetailResponse> data = new ArrayResponse<TenderUserDetailResponse>();
			data.setTotal((long) pageInfo.getTotalRows());
			data.setItems(list);
			apiResponse.setData(data);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse selectTender(EmployeeSelectTenderRequest employeeSelectTenderRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String projectId = employeeSelectTenderRequest.getProjectId();
		String tenderUserId = employeeSelectTenderRequest.getTenderUserId();
		String employerId = employeeSelectTenderRequest.getEmployerId();
		Short isReveal = employeeSelectTenderRequest.getIsReveal();
		//参数空判断
		if(StringUtil.isNull(projectId) || StringUtil.isNull(tenderUserId) || isReveal==null){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		//参数格式判断
		if(!(Constants.PROJECT_REVEAL_OK==isReveal || Constants.PROJECT_REVEAL_NO==isReveal)){
			apiResponse.setErrorCode(ErrorCode.INVALID_ARGUMENT);
			return apiResponse;
		}
		
		try{
			ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
			//项目是否存在
			if(projectEntity==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			//是否是该项目发起人的项目
			if(!employerId.equals(projectEntity.getEmployerId())){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_USER);
				return apiResponse;
			}
			//项目状态是否是报名中状态
			if(Constants.PROJECT_STATE_SELECTING!=projectEntity.getState()){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_STATE_NOT_SELECTING);
				return apiResponse;
			}
			//判断后台配置了兜底服务，前台却传了支持兜底
			if(Constants.PROJECT_REVEAL_OK==isReveal && isReveal!=projectEntity.getIsReveal()){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_SUPPORT_REVEAL);
				return apiResponse;
			}
			List<ProjectTenderEntity> projectTenderList = projectTenderRepository.findProjectTenderByProjectId(projectId);
			Double offer = null;//当前中标人投标金额
			for(ProjectTenderEntity projectTenderEntity:projectTenderList){
				//接包者报名状态判断
				if(projectTenderEntity.getState()==null || Constants.PROJECT_TENDER_STATE_LAUNCH!=projectTenderEntity.getState()){
					apiResponse.setErrorCode(ErrorCode.PROJECTS_TENDER_USER_STATE_INVALID);
					return apiResponse;
				}else{
					if(tenderUserId.equals(projectTenderEntity.getTenderUserId())){
						projectTenderEntity.setState(Constants.PROJECT_TENDER_STATE_ADOPT);
						projectTenderEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						offer = projectTenderEntity.getOffer();
					}else{
						projectTenderEntity.setState(Constants.PROJECT_TENDER_STATE_REFUSE);
						projectTenderEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					}
				}
			}
			if(offer==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_TENDER_NOT_EXIST);
				return apiResponse;
			}
			//更新项目信息表
			Float imprestScale = projectEntity.getImprestScale();//预付款比例
			Float revealScale = projectEntity.getRevealScale();//兜底比例
			Double imprestMoney = offer*imprestScale;//预付款金额
			Double revealMoney = offer*revealScale;//兜底服务费
			Double payMoney = imprestMoney + revealMoney;//支付金额
			//判断中标人账户余额是否足够
			UserPersonalEntity userPersonalEntity = userPersonalRepository.findOne(employerId);
			if(userPersonalEntity==null){
				apiResponse.setErrorCode(ErrorCode.BALANCE_NOT_ENOUGH);
				return apiResponse;
			}else{
				BigDecimal balance = userPersonalEntity.getBalance();//账户余额
				//判断余额是否不足
				if(balance==null || balance.doubleValue()<payMoney){
					apiResponse.setErrorCode(ErrorCode.BALANCE_NOT_ENOUGH);
					return apiResponse;
				}else{
					//扣除余额
					//authenticationDao.updatePersonBalance(employerId, -payMoney);
					//插入资金流水明细
					UcUserInfoEntity userInfoEntity = userInfoRepository.findOne(employerId);
					if(userInfoEntity!=null){
						//预付款流水明细
						projectCommon.userPayToPlatform(employerId, imprestMoney, projectId,
								(userPersonalEntity.getIsEnterpriseUser()!=null && userPersonalEntity.getIsEnterpriseUser()==1)? Constants.TARGET_TYPE_IMPRESTMONEY:Constants.TARGET_TYPE_FULLMONEY);
						//兜底费用流水明细
						if(isReveal==Constants.PROJECT_IS_REVEAL_YES){
							projectCommon.userPayToPlatform(employerId, revealMoney, projectId, Constants.TARGET_TYPE_REVEALMONEY);
						}
					}
				}
			}
			projectEntity.setTradeMoney(offer);//成交金额，即投标金额
			projectEntity.setTenderUserId(tenderUserId);
			projectEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			projectEntity.setState(Constants.PROJECT_STATE_SELECTED);
			projectReponsitory.save(projectEntity);
			//更新项目招标表信息
			projectTenderRepository.save(projectTenderList);
			
			ProjectSelectTenderResponse projectSelectTenderResponse = new ProjectSelectTenderResponse();
			projectSelectTenderResponse.setProjectId(projectId);
			projectSelectTenderResponse.setTenderUserId(tenderUserId);
			projectSelectTenderResponse.setImprestMoney(imprestMoney);
			projectSelectTenderResponse.setRevealMoney(revealMoney);
			projectSelectTenderResponse.setImprestScale(imprestScale);
			projectSelectTenderResponse.setRevealScale(revealScale);
			
			apiResponse.setData(projectSelectTenderResponse);
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse applyActivateReveal(ProjectActiveRevealRequest projectActiveRevealRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String projectId = projectActiveRevealRequest.getProjectId();
		String mobile = projectActiveRevealRequest.getMobile();
		String applyContent = projectActiveRevealRequest.getApplyContent();
		String employerId = projectActiveRevealRequest.getEmployerId();
		//参数空判断
		if(StringUtil.isNull(projectId) || StringUtil.isNull(mobile)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		//手机号码格式判断
		if(!ValidatorUtil.isMobile(mobile)){
			apiResponse.setErrorCode(ErrorCode.UC_MOBILE_NOT_CORRECT);
			return apiResponse;
		}
		try{
			//判断项目是否存在
			ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
			if(projectEntity==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			//判断是否项目本人激活
			if(!projectActiveRevealRequest.getEmployerId().equals(projectEntity.getEmployerId())){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_USER);
				return apiResponse;
			}
			//判断用户是否购买过兜底服务
			FinanceTradeDetailEntity entity = new FinanceTradeDetailEntity();
			entity.setTargetType(Constants.TARGET_TYPE_REVEALMONEY);
			entity.setTargetId(projectId);
			entity.setPayUserId(employerId);
			if(financeTradeDetailRepository.countByCond(entity)<=0){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_PAY_REVEALMONEY);
				return apiResponse;
			}
			
			ProjectRevealEntity projectRevealEntity = new ProjectRevealEntity();
			projectRevealEntity.setProjectId(projectId);
			projectRevealEntity.setMobile(mobile);
			projectRevealEntity.setApplyContent(applyContent);
			projectRevealEntity.setAdminReviewState(Constants.PROJECT_REVEALAPPLY_SUBMIT);
			projectRevealRepository.save(projectRevealEntity);
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse receiverReport(ReceiverReportRequest receiverReportRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String projectId = receiverReportRequest.getProjectId();
		String location = receiverReportRequest.getLocation();
		String workDesc = receiverReportRequest.getWorkDesc();
		String workPic = receiverReportRequest.getWorkPic();
		String userId = receiverReportRequest.getUserId();
		//参数空判断
		if(StringUtil.isNull(projectId) || StringUtil.isNull(location) || StringUtil.isNull(workDesc) || StringUtil.isNull(workPic)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		
		try{
			ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
			//判断项目是否存在
			if(projectEntity==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			String[] attchIdArray = workPic.split(Constants.PROJECT_REPORTPHOTO_SPLIT);
			JSONArray jsonArray = new JSONArray();
			for(String attchId:attchIdArray){
				AttachmentEntity attachmentEntity = attachmentRepository.findOne(attchId);
				if(attachmentEntity==null){
					apiResponse.setErrorCode(ErrorCode.PROJECTS_REPORT_PHOTO_WRONG);
					return apiResponse;
				}else{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("attch_id", attchId);
					jsonObject.put("attch_url", attachmentEntity.getAttchUrl());
					jsonArray.add(jsonObject);
				}
			}
			
			ProjectProgressEntity projectProgressEntity = new ProjectProgressEntity();
			projectProgressEntity.setProjectId(projectId);
			projectProgressEntity.setPhotos(jsonArray.toJSONString());
			projectProgressEntity.setEventAddress(location);
			projectProgressEntity.setEventContent(workDesc);
			projectProgressEntity.setEventUserId(userId);
			projectProgressEntity.setUserId(userId);
			projectProgressEntity.setEventType(Constants.PROJECT_PROGRESS_TYPE_WORK);
			String eventSource = Constants.PROJECT_PROGRESS_SOURCE_BFZB;
			if(Constants.PROJECT_TENDER_CONTAIN.equals(projectEntity.getTenderType())){
				eventSource = Constants.PROJECT_PROGRESS_SOURCE_SDR;
			}
			projectProgressEntity.setEventSource(eventSource);
			
			projectProgressRepository.save(projectProgressEntity);
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse receiverResult(String currentUser, String projectId) {
		APIResponse apiResponse = new APIResponse();
		//参数空判断
		if(StringUtil.isNull(projectId)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		
		try{
			ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
			//项目是否存在
			if(projectEntity==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			//是否是进行中的项目
			if(Constants.PROJECT_STATE_SELECTED!=projectEntity.getState()){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_STATE_NOT_SELECTED);
				return apiResponse;
			}
			//是否是当前用户的项目
			if(!currentUser.equals(projectEntity.getTenderUserId())){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_USER);
				return apiResponse;
			}
			projectEntity.setState(Constants.PROJECT_STATE_CHECK);
			projectEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			projectEntity.setReceiverCompleteTime(new Timestamp(System.currentTimeMillis()));
			projectReponsitory.save(projectEntity);
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse employeeResult(String currentUser, String projectId) {
		APIResponse apiResponse = new APIResponse();
		//参数空判断
		if(StringUtil.isNull(projectId)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		
		try{
			ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
			//项目是否存在
			if(projectEntity==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			//是否是待验收的项目
			if(Constants.PROJECT_STATE_CHECK!=projectEntity.getState()){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_STATE_NOT_CHECK);
				return apiResponse;
			}
			//是否是当前用户的项目
			if(!currentUser.equals(projectEntity.getEmployerId())){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_USER);
				return apiResponse;
			}
			double tradeMoney = projectEntity.getTradeMoney();//项目交易全款
			double remainMoney = tradeMoney*(1-projectEntity.getImprestScale());//剩下未交的钱
			if(remainMoney>0){
				/*//先判断是否交过尾款，已确认验收
				FinanceTradeDetailEntity entity = new FinanceTradeDetailEntity();
				entity.setTargetId(projectId);
				entity.setTargetType(Constants.TARGET_TYPE_REMAINMONEY);
				List<FinanceTradeDetailEntity> findFinanceDetailByCond = financeTradeDetailRepository.findFinanceDetailByCond(entity);
				if(findFinanceDetailByCond!=null&&findFinanceDetailByCond.size()>0){
					apiResponse.setErrorCode(ErrorCode.PROJECTS_EMPLOYEE_IS_CHECK);
					return apiResponse;
				}*/
				//发包方付款
				boolean payToPlatformFlag = projectCommon.userPayToPlatform(currentUser, remainMoney, projectId, Constants.TARGET_TYPE_REMAINMONEY);
				if(!payToPlatformFlag){
					apiResponse.setErrorCode(ErrorCode.BALANCE_NOT_ENOUGH);
					return apiResponse;
				}
			}
			//打款给接包方
			short isMargin = projectEntity.getIsMargin();
			double payToReceiverMoney = tradeMoney;
			boolean payToReceiverMoneyFlag = false;
			String msgStr = "";
			//是否申请质保，支付时候扣留质保金
			if(isMargin==Constants.PROJECT_IS_MARGIN_YES){
				payToReceiverMoney -= projectEntity.getMarginScale()*tradeMoney;
				payToReceiverMoneyFlag = projectCommon.platformPayToUser(projectEntity.getTenderUserId(), payToReceiverMoney, projectId, Constants.TARGET_TYPE_IMPRESTMONEY);
				if(payToReceiverMoneyFlag){
					msgStr = MsgConstants.RECEIVER_GETMONEY_IMPREST.replace("${projectName}", projectEntity.getProjectName())
							 .replace("${imprestScale}", String.valueOf((1-projectEntity.getMarginScale())*100))
							 .replace("${marginScale}", String.valueOf(projectEntity.getMarginScale()*100))
							 .replace("${marginDay}", String.valueOf(projectEntity.getMarginDay()));
				}
			}else{
				payToReceiverMoneyFlag = projectCommon.platformPayToUser(projectEntity.getTenderUserId(), payToReceiverMoney, projectId, Constants.TARGET_TYPE_FULLMONEY);
				if(payToReceiverMoneyFlag){
					msgStr = MsgConstants.RECEIVER_GETMONEY_FULL.replace("${projectName}", projectEntity.getProjectName())
							.replace("${money}", String.valueOf(payToReceiverMoney));
				}
			}
			//插入消息表
			if(payToReceiverMoneyFlag){
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, msgStr, projectEntity.getTenderUserId());
			}
			projectEntity.setState(Constants.PROJECT_STATE_OVER);
			projectEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			projectEntity.setEmployerCompleteTime(new Timestamp(System.currentTimeMillis()));
			projectReponsitory.save(projectEntity);
			
			EmployeeResultResponse employeeResultResponse = new EmployeeResultResponse();
			employeeResultResponse.setProjectId(projectId);
			employeeResultResponse.setState(Constants.PROJECT_STATE_OVER);
			employeeResultResponse.setRemainMoney(remainMoney);
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
			apiResponse.setData(employeeResultResponse);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse employeeRefuse(EmployeeRefuseRequest employeeRefuseRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String projectId = employeeRefuseRequest.getProjectId();
		String employerId = employeeRefuseRequest.getEmployerId();
		String refuseReason = employeeRefuseRequest.getRefuseReason();
		//参数空判断
		if(StringUtil.isNull(projectId) || StringUtil.isNull(refuseReason)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		
		try{
			ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
			//项目是否存在
			if(projectEntity==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			//是否是待验收的项目
			if(Constants.PROJECT_STATE_CHECK!=projectEntity.getState()){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_STATE_NOT_CHECK);
				return apiResponse;
			}
			//是否是当前用户的项目
			if(!employerId.equals(projectEntity.getEmployerId())){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_USER);
				return apiResponse;
			}
			
			ProjectRejectionEntity projectRejectionEntity = new ProjectRejectionEntity();
			projectRejectionEntity.setProjectId(projectId);
			projectRejectionEntity.setEmployerReason(refuseReason);
			projectRejectionEntity.setTenderReviewState(Constants.PROJECT_REFUSE_RECEIVER_UNTREATED);
			projectRejectionEntity.setAdminReviewState(Constants.PROJECT_REFUSE_ADMIN_UNTREATED);
			
			projectRejectionRepository.save(projectRejectionEntity);
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse evaluateUser(EvaluateUserRequest evaluateUserRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String projectId = evaluateUserRequest.getProjectId();
		Integer star = evaluateUserRequest.getStar();
		String content = evaluateUserRequest.getContent();
		String userId = evaluateUserRequest.getUserId();
		String targetType = evaluateUserRequest.getTargetType();
		//空判断
		if(StringUtil.isNull(projectId) || star==null || StringUtil.isNull(targetType)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		//targetType取值判断
		if(!(Constants.PROJECT_EVALUATE_TARGET_TYPE_EMPLOYER.equals(targetType) 
				|| Constants.PROJECT_EVALUATE_TARGET_TYPE_RECEIVER.equals(targetType))){
			apiResponse.setErrorCode(ErrorCode.PROJECTS_EVALUATE_TYPE_WRONG);
			return apiResponse;
		}
		try{
			ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
			//项目是否存在
			if(projectEntity==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			//是否是已完成的项目
			if(Constants.PROJECT_STATE_OVER!=projectEntity.getState()){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_STATE_NOT_OVER);
				return apiResponse;
			}
			String targetUserId = null;
			if(Constants.PROJECT_EVALUATE_TARGET_TYPE_EMPLOYER.equals(targetType)){
				targetUserId = projectEntity.getEmployerId();
			}else if(Constants.PROJECT_EVALUATE_TARGET_TYPE_RECEIVER.equals(targetType)){
				targetUserId = projectEntity.getTenderUserId();
			}
			
			EvaluateRecordEntity evaluateRecordEntity = new EvaluateRecordEntity();
			evaluateRecordEntity.setTargetType(targetType);
			evaluateRecordEntity.setTargetOwnerId(targetUserId);
			evaluateRecordEntity.setUserId(userId);
			evaluateRecordEntity.setTargetId(projectId);
			evaluateRecordEntity.setContent(content);
			evaluateRecordEntity.setStar(star);
			
			int cnt = evaluateRecordRepository.countByCondition(evaluateRecordEntity);
			if(cnt>0){
				apiResponse.setErrorCode(ErrorCode.USER_ALREADY_EVALUATE);
			}else{
				evaluateRecordRepository.save(evaluateRecordEntity);
				apiResponse.setMessage(APIResponse.SUCESS_MSG);
			}
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse employeeAftersale(EmployeeAftersaleRequest employeeAftersaleRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String mobile = employeeAftersaleRequest.getMobile();
		String projectId = employeeAftersaleRequest.getProjectId();
		String content = employeeAftersaleRequest.getContent();
		//参数空判断
		if(StringUtil.isNull(mobile) || StringUtil.isNull(projectId)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		//手机格式验证
		if(!ValidatorUtil.isMobile(mobile)){
			apiResponse.setErrorCode(ErrorCode.UC_MOBILE_NOT_CORRECT);
			return apiResponse;
		}
		try{
			//项目是否存在
			if(!projectReponsitory.exists(projectId)){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			
			ProjectWarrantyEntity projectWarrantyEntity = new ProjectWarrantyEntity();
			projectWarrantyEntity.setProjectId(projectId);
			projectWarrantyEntity.setEmployerReason(content);
			projectWarrantyEntity.setMobile(mobile);
			projectWarrantyEntity.setAdminReviewState(Constants.PROJECT_WARRANTY_UNTREATED);
			
			projectWarrantyRepository.save(projectWarrantyEntity);
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse receiverAppeal(ReceiverAppealRequest receiverAppealRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String projectId = receiverAppealRequest.getProjectId();
		String content = receiverAppealRequest.getContent();
		String appealAttachs = receiverAppealRequest.getAppealAttachs();
		//参数空判断
		if(StringUtil.isNull(projectId) || StringUtil.isNull(appealAttachs) || StringUtil.isNull(content)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		
		try{
			ProjectRejectionEntity projectRejectionEntity = projectRejectionRepository.findOne(projectId);
			//项目是否存在
			if(projectRejectionEntity==null){
				apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
				return apiResponse;
			}
			
			String[] attchIdArray = appealAttachs.split(Constants.PROJECT_REPORTPHOTO_SPLIT);
			JSONArray jsonArray = new JSONArray();
			for(String attchId:attchIdArray){
				AttachmentEntity attachmentEntity = attachmentRepository.findOne(attchId);
				if(attachmentEntity==null){
					apiResponse.setErrorCode(ErrorCode.PROJECTS_APPEAL_ATTACH_WRONG);
					return apiResponse;
				}else{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("attch_id", attchId);
					jsonObject.put("attch_url", attachmentEntity.getAttchUrl());
					jsonArray.add(jsonObject);
				}
			}
			
			projectRejectionEntity.setTenderReivewReason(content);
			projectRejectionEntity.setTenderReivewTime(new Timestamp(System.currentTimeMillis()));
			projectRejectionEntity.setTenderReviewState(Constants.PROJECT_REFUSE_RECEIVER_REFUSE);
			projectRejectionEntity.setTenderAttachs(jsonArray.toJSONString());
			
			projectRejectionRepository.save(projectRejectionEntity);
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse evaluateList(EvaluateListRequest evaluateListRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String targetOwnerId = evaluateListRequest.getTargetOwnerId();
		String targetType = evaluateListRequest.getTargetType();
		Integer offset = evaluateListRequest.getOffset();
		Integer size = evaluateListRequest.getSize();
		//空参数判断
		if(StringUtil.isNull(targetOwnerId)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		try{
			EvaluateRecordEntity evaluateRecordEntity = new EvaluateRecordEntity();
			evaluateRecordEntity.setTargetOwnerId(targetOwnerId);
			evaluateRecordEntity.setTargetType(targetType);
			PageInfo<EvaluateListVo> pageInfo = evaluateRecordRepository.findEvaluateListVoByCondition((offset-1)*size, size, evaluateRecordEntity);
			
			ArrayResponse<EvaluateListVo> data = new ArrayResponse<EvaluateListVo>();
			data.setTotal((long) pageInfo.getTotalRows());
			data.setItems(pageInfo.getPageData());
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
			apiResponse.setData(data);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			throw e;
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse receiverBaseProjectDetail(String projectId) {
		APIResponse apiResponse = new APIResponse();
		//参数空判断
		if(StringUtil.isNull(projectId)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		ProjectEntity projectEntity = projectReponsitory.findOne(projectId);
		//判断项目是否存在
		if(projectEntity==null){
			apiResponse.setErrorCode(ErrorCode.PROJECTS_NOT_EXIST);
			return apiResponse;
		}
		
		try{
			ReceiverBaseProjectDetailResponse detailResponse = new ReceiverBaseProjectDetailResponse();
			detailResponse.setProjectId(projectEntity.getProjectId());
			detailResponse.setProjectName(projectEntity.getProjectName());
			detailResponse.setProjectState(projectEntity.getState());
			detailResponse.setProjectType(projectEntity.getProjectType());
			detailResponse.setTenderType(projectEntity.getTenderType());
			detailResponse.setCategorys(projectEntity.getCategoryIds());
			detailResponse.setSignUpCount(projectEntity.getSignUpCount());
			detailResponse.setBudget(projectEntity.getBudget());
			detailResponse.setApplyDeadline(DateUtil.formatTimestampDateString(projectEntity.getApplyDeadline(),"yyyy-MM-dd"));
			detailResponse.setSubmitDeadline(DateUtil.formatTimestampDateString(projectEntity.getSubmitDeadline(),"yyyy-MM-dd"));
			detailResponse.setCreateTime(DateUtil.formatTimestampDateString(projectEntity.getCreateTime(),"yyyy-MM-dd"));
			detailResponse.setProjectType(projectEntity.getProjectType());
			detailResponse.setTenderType(projectEntity.getTenderType());
			detailResponse.setDescription(projectEntity.getDescription());
			detailResponse.setRegionDetail(projectEntity.getRegionDetail());
			detailResponse.setHouseNumber(projectEntity.getHouseNumber());
			detailResponse.setRegionProvince(projectEntity.getRegionProvince());
			detailResponse.setRegionCity(projectEntity.getRegionCity());
			detailResponse.setRegionArea(projectEntity.getRegionArea());
			detailResponse.setLatitude(projectEntity.getLatitude());
			detailResponse.setLongitude(projectEntity.getLongitude());
			//雇主信息
			String employerId = projectEntity.getEmployerId();
			UcUserInfoEntity userInfoEntity = userInfoRepository.findOne(employerId);
			if(userInfoEntity!=null){
				detailResponse.setEmployerUserId(employerId);
				detailResponse.setEmployerUserName(userInfoEntity.getUserName());
				detailResponse.setEmployerNickName(userInfoEntity.getNickName());
				detailResponse.setEmployerMobile(projectEntity.getTelephone());
			}
				
			apiResponse.setData(detailResponse);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	public APIResponse countProjectStateByUserId(String userId, String userRole) {
		APIResponse apiResponse = new APIResponse();
		
		try{
			List<ProjectStateTotalResponse> list = null;
			if(Constants.UC_USER_ROLE_EMPLOYER.equals(userRole)){
				list = projectReponsitory.countStateByEmployerId(userId);
			}else{
				list = projectTenderRepository.countStateByTenderUserId(userId);
			}
			
			apiResponse.setData(list);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}
}
