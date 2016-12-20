package com.idg.bfzb.server.team.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.idg.bfzb.server.authentication.dao.AuthenticationDao;
import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.team.dao.UserTeamRepository;
import com.idg.bfzb.server.team.model.dto.UserTeamEntity;
import com.idg.bfzb.server.team.model.request.TeamModifyRequest;
import com.idg.bfzb.server.team.model.request.TeamSetRequest;
import com.idg.bfzb.server.team.model.response.TeamDetailResponse;
import com.idg.bfzb.server.team.model.response.TeamSetResponse;
import com.idg.bfzb.server.team.service.TeamService;
import com.idg.bfzb.server.utility.tools.StringUtil;

@Service
public class TeamServiceImpl implements TeamService{
	
	private final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);
	
	@Autowired
    private UserTeamRepository userTeamRepository;
	@Autowired
	private AuthenticationDao authenticationDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public APIResponse setTeam(String userId, TeamSetRequest teamSetRequest) {
		APIResponse apiResponse = new APIResponse();
		
		//参数空判断
		if(StringUtil.isNull(teamSetRequest.getTeamName()) /*|| StringUtil.isNull(teamSetRequest.getTeamDesc())*/
				 /*|| StringUtil.isNull(teamSetRequest.getCity()) || StringUtil.isNull(teamSetRequest.getServiceContent())*/
				 || StringUtil.isNull(teamSetRequest.getServiceExp()) || StringUtil.isNull(teamSetRequest.getTelephone())
				 /*|| StringUtil.isNull(teamSetRequest.getTelephone()) || StringUtil.isNull(teamSetRequest.getIdentifyNum())*/
				 /*|| StringUtil.isNull(teamSetRequest.getUserName())*/){
			logger.error(String.format("%1$s", ErrorCode.PARAM_INPUT_WRONG.getMsg()));
			apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
			return apiResponse;
		}
		
		UserPersonalEntity userPersonalEntity = authenticationDao.queryUserAuthenticationInfo(userId);
		if(userPersonalEntity==null){
			apiResponse.setErrorCode(ErrorCode.TEAMS_USER_NOT_AUTHENTICATION);
			return apiResponse;
		}else{
			Byte reviewState = userPersonalEntity.getReviewState();
			if(reviewState==null || reviewState!=2){
				apiResponse.setErrorCode(ErrorCode.TEAMS_USER_NOT_AUTHENTICATION);
				return apiResponse;
			}
		}
		UserTeamEntity userTeamByUserId = userTeamRepository.findUserTeamByUserId(userId);
		if(userTeamByUserId!=null){
			Short reviewState = userTeamByUserId.getReviewState();
			if(reviewState!=Constants.TEAM_CHECKREFUSE_STATE){
				apiResponse.setErrorCode(ErrorCode.TEAMS_NOT_SUBMIT);
				return apiResponse;
			}else{
				userTeamRepository.delete(userTeamByUserId);
			}
		}
		
		UserTeamEntity userTeamEntity = userTeamRepository.findUserTeamByTeamName(teamSetRequest.getTeamName());
		if(userTeamEntity!=null){
			logger.error(String.format("%1$s:%2$s", ErrorCode.TEAMS_NAME_EXIST.getMsg(), teamSetRequest.getTeamName()));
			apiResponse.setErrorCode(ErrorCode.TEAMS_NAME_EXIST);
			return apiResponse;
		}
		
		userTeamEntity = new UserTeamEntity();
		userTeamEntity.setTeamId(UUID.randomUUID().toString());
		userTeamEntity.setTeamName(HtmlUtils.htmlEscape(teamSetRequest.getTeamName()));
		//userTeamEntity.setTeamNickName(HtmlUtils.htmlEscape(teamSetRequest.getTeamDesc()));
		userTeamEntity.setTeamLeaderId(userId);
		//userTeamEntity.setRegionCode(teamSetRequest.getCity());
		//userTeamEntity.setServiceContent(HtmlUtils.htmlEscape(teamSetRequest.getServiceContent()));
		userTeamEntity.setExperience(HtmlUtils.htmlEscape(teamSetRequest.getServiceExp()));
		userTeamEntity.setContactsMobile(HtmlUtils.htmlEscape(teamSetRequest.getTelephone()));
		//userTeamEntity.setContactsIdcardCode(HtmlUtils.htmlEscape(teamSetRequest.getIdentifyNum()));
		//userTeamEntity.setContactsRealName(HtmlUtils.htmlEscape(teamSetRequest.getUserName()));
		userTeamEntity.setReviewState(Constants.TEAM_NOTCHECK_STATE);
		
		this.userTeamRepository.save(userTeamEntity);
		
		TeamSetResponse teamSetResponse = new TeamSetResponse();
		teamSetResponse.setTeamId(userTeamEntity.getTeamId());
		
		apiResponse.setMessage(APIResponse.SUCESS_MSG);
		apiResponse.setData(teamSetResponse);
		
		return apiResponse;
	}

	@Override
	public APIResponse detail(String teamId) {
		APIResponse apiResponse = new APIResponse();
		
		//参数空判断
		if(StringUtil.isNull(teamId)){
			logger.error(String.format("%1$s:%2$s", ErrorCode.PARAM_INPUT_WRONG.getMsg(),"teamId"));
			apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
			return apiResponse;
		}
		
		UserTeamEntity userTeamEntity = this.userTeamRepository.findOne(teamId);
		if(userTeamEntity==null){
			apiResponse.setErrorCode(ErrorCode.TEAMS_ID_NOT_EXIST);
			return apiResponse;
		}
		
		TeamDetailResponse teamDetailResponse = new TeamDetailResponse();
		teamDetailResponse.setTeamId(userTeamEntity.getTeamId());
		teamDetailResponse.setTeamName(userTeamEntity.getTeamName());
		teamDetailResponse.setTeamDesc(userTeamEntity.getTeamNickName());
		teamDetailResponse.setServiceContent(userTeamEntity.getServiceContent());
		teamDetailResponse.setServiceExp(userTeamEntity.getExperience());
		teamDetailResponse.setCity(userTeamEntity.getRegionCode());
		teamDetailResponse.setUserName(userTeamEntity.getContactsRealName());
		teamDetailResponse.setTelephone(userTeamEntity.getContactsMobile());
		teamDetailResponse.setState(userTeamEntity.getReviewState());
		
		apiResponse.setData(teamDetailResponse);
		
		return apiResponse;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public APIResponse modifyTeam(String userId, TeamModifyRequest teamModifyRequest) {
		APIResponse apiResponse = new APIResponse();
		
		/*//参数空判断
		if(StringUtil.isNull(teamModifyRequest.getTeamName()) || StringUtil.isNull(teamModifyRequest.getTeamDesc())
				 || StringUtil.isNull(teamModifyRequest.getCity()) || StringUtil.isNull(teamModifyRequest.getServiceContent())
				 || StringUtil.isNull(teamModifyRequest.getServiceExp()) || StringUtil.isNull(teamModifyRequest.getTelephone())
				 || StringUtil.isNull(teamModifyRequest.getTelephone()) || StringUtil.isNull(teamModifyRequest.getIdentifyNum())
				 || StringUtil.isNull(teamModifyRequest.getUserName()) || StringUtil.isNull(teamModifyRequest.getTeamId())){
			logger.error(String.format("%1$s", ErrorCode.PARAM_INPUT_WRONG.getMsg()));
			apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
			return apiResponse;
		}*/
		//判断团队存在不
		UserTeamEntity userTeamEntity = this.userTeamRepository.findOne(teamModifyRequest.getTeamId());
		if(userTeamEntity==null){
			apiResponse.setErrorCode(ErrorCode.TEAMS_ID_NOT_EXIST);
			return apiResponse;
		}
		//判断是否团队创建者修改
		if(!userId.equals(userTeamEntity.getTeamLeaderId())){
			apiResponse.setErrorCode(ErrorCode.TEAMS_NOT_UPDATE);
			return apiResponse;
		}
		//判断名称是否已存在
		UserTeamEntity ut = this.userTeamRepository.findUserTeamByTeamName(teamModifyRequest.getTeamName());
		if(ut!=null && !ut.getTeamId().equals(teamModifyRequest.getTeamId())){
			logger.error(String.format("%1$s:%2$s", ErrorCode.TEAMS_NAME_EXIST.getMsg(), teamModifyRequest.getTeamName()));
			apiResponse.setErrorCode(ErrorCode.TEAMS_NAME_EXIST);
			return apiResponse;
		}
		
		if(!StringUtil.isNull(teamModifyRequest.getTeamName())){
			userTeamEntity.setTeamName(HtmlUtils.htmlEscape(teamModifyRequest.getTeamName()));
		}
		if(!StringUtil.isNull(teamModifyRequest.getTeamDesc())){
			userTeamEntity.setTeamNickName(HtmlUtils.htmlEscape(teamModifyRequest.getTeamDesc()));
		}
		if(!StringUtil.isNull(teamModifyRequest.getCity())){
			userTeamEntity.setRegionCode(HtmlUtils.htmlEscape(teamModifyRequest.getCity()));
		}
		if(!StringUtil.isNull(teamModifyRequest.getServiceContent())){
			userTeamEntity.setServiceContent(HtmlUtils.htmlEscape(teamModifyRequest.getServiceContent()));
		}
		if(!StringUtil.isNull(teamModifyRequest.getServiceExp())){
			userTeamEntity.setExperience(HtmlUtils.htmlEscape(teamModifyRequest.getServiceExp()));
		}
		if(!StringUtil.isNull(teamModifyRequest.getTelephone())){
			userTeamEntity.setContactsMobile(HtmlUtils.htmlEscape(teamModifyRequest.getTelephone()));
		}
		if(!StringUtil.isNull(teamModifyRequest.getIdentifyNum())){
			userTeamEntity.setContactsIdcardCode(HtmlUtils.htmlEscape(teamModifyRequest.getIdentifyNum()));
		}
		if(!StringUtil.isNull(teamModifyRequest.getUserName())){
			userTeamEntity.setContactsRealName(HtmlUtils.htmlEscape(teamModifyRequest.getUserName()));
		}
		
		this.userTeamRepository.save(userTeamEntity);
		
		apiResponse.setMessage(APIResponse.SUCESS_MSG);
		
		return apiResponse;
	}
	
}
