package com.idg.bfzb.server.authentication.service.impl;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.idg.bfzb.server.authentication.dao.AuthenticationDao;
import com.idg.bfzb.server.authentication.model.EnterpriceAuthRequest;
import com.idg.bfzb.server.authentication.model.UserAuthRequest;
import com.idg.bfzb.server.authentication.model.UserEnterpriceVo;
import com.idg.bfzb.server.authentication.model.UserPersonalVo;
import com.idg.bfzb.server.authentication.model.dto.UserEnterpriseEntity;
import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.authentication.service.AuthenticationService;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.MsgConstants;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.tools.MessageCommon;
import com.idg.bfzb.server.usercenter.dao.UserPersonalRepository;
import com.idg.bfzb.server.utility.cache.redis.ValueCacheService;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {
    @Autowired
    private ValueCacheService<String, String> valueCacheService;
    @Autowired
    private UserPersonalRepository userPersonalRepository;
    @Autowired
    private AuthenticationDao authDao;
    @Resource
    private MessageCommon messageCommon;
	@Override
	public Object getMyAuthenticationInfo(String userId) {
		APIResponse apiResponse = new APIResponse();
		//获取用户认证信息操作
		UserPersonalEntity user = authDao.queryUserAuthenticationInfo(userId);
		//用户未认证
		if(user == null){
			apiResponse.setCode("10002");
			apiResponse.setMessage("用户未提交认证！");
		}else {
			if(user instanceof UserPersonalVo){
				
				UserPersonalVo uvo = (UserPersonalVo)user;
				if(uvo.getFrontImageUrl() != null){
					uvo.setFrontImageUrl(ConfigFileUtils.HEAD_ICON_URL+uvo.getFrontImageUrl());
				}
				if(uvo.getBackImageUrl() != null){
					uvo.setBackImageUrl(ConfigFileUtils.HEAD_ICON_URL+uvo.getBackImageUrl());
				}
				if(uvo.getHoldImageUrl() != null){
					uvo.setHoldImageUrl(ConfigFileUtils.HEAD_ICON_URL+uvo.getHoldImageUrl());
				}
				
				user = uvo;
			}
			
			apiResponse.setCode(APIResponse.SUCESS_MSG);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("authentication", user);
			
			apiResponse.setData(map);
			apiResponse.setMessage("查询成功！");
		}
		
		return apiResponse;
	}

	@Override
	public Object getUserEnterpriceAuthenticationInfo(String userId) {
		APIResponse apiResponse = new APIResponse();
		//查询企业用户认证
		UserEnterpriseEntity user = authDao.queryUserEnterpriceAuthenticationInfo(userId);
		
		if(user == null){
			apiResponse.setCode("10002");
			apiResponse.setMessage("用户未提交企业认证申请！");
		}else {
				if(user instanceof UserEnterpriceVo){
				
				UserEnterpriceVo uvo = (UserEnterpriceVo)user;
				if(uvo.getBusinessLicenseImageUrl() != null){
					uvo.setBusinessLicenseImageUrl(ConfigFileUtils.HEAD_ICON_URL+uvo.getBusinessLicenseImageUrl());
				}
				
				user = uvo;
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("authentication", user);
			
			apiResponse.setData(map);
			apiResponse.setMessage("查询成功！");
			apiResponse.setCode(APIResponse.SUCESS_MSG);
		}
		
		return apiResponse;
	}

	@Override
	public Object personAuthenticationapply(String userId, UserAuthRequest user) {
		APIResponse apiResponse = new APIResponse();
//		//校验用户是否存在
//		UserBaseVo qUser = userDao.findUserVoByUserId(userId);
//		
//		if(qUser == null){
//			apiResponse.setCode("10001");
//			apiResponse.setMessage("用户不存在！");
//			
//			return apiResponse;
//		}
		//校验用户是否已经注册
		UserPersonalEntity queryUser = authDao.queryUserAuthenticationInfo(userId);
		//非空 只能进行update操作
		if(queryUser != null){
			//未提交认证或者认证被拒绝
			if(queryUser.getReviewState() == null || queryUser.getReviewState() == 3 || queryUser.getReviewState() == 4){
				int result = authDao.updatePersonAuthentication(userId,user);
				
				if(result > 0){
					apiResponse.setCode(APIResponse.SUCESS_MSG);
					apiResponse.setMessage("用户认证申请提交成功！");
				}else {
					apiResponse.setCode("10001");
					apiResponse.setMessage("系统异常！");
				}
			}
			//已认证
			else {
				apiResponse.setCode("10002");
				apiResponse.setMessage("用户已提交个人认证申请，无需重复提交！");
			}
		}
		//用户认证信息不存在（未插入认证信息）
		else {
			int result = authDao.addPersonAuthentication(userId,user);
			
			if(result > 0){
				apiResponse.setCode(APIResponse.SUCESS_MSG);
				apiResponse.setMessage("用户认证申请提交成功！");
			}else {
				apiResponse.setCode("10001");
				apiResponse.setMessage("系统异常！");
			}
		}
		
		return apiResponse;
	}

	@Override
	public Object enterpriseAuthenticationapply(String userId,
			EnterpriceAuthRequest enterprice) {
		APIResponse apiResponse = new APIResponse();
		
		UserEnterpriseEntity queryUser = authDao.queryUserEnterpriceAuthenticationInfo(userId);
		
		if(queryUser != null){
			if(queryUser.getReviewState() == 2){//审核拒绝也可提交
				int cnt = authDao.deleteEnterpriceAuthentication(queryUser.getEnterpriseId());
				if(cnt>0){
					authDao.addEnterpriseAuthentication(userId,enterprice);
					apiResponse.setCode(APIResponse.SUCESS_MSG);
					apiResponse.setMessage("企业认证申请提交成功！");
				}else{
					apiResponse.setCode("10001");
					apiResponse.setMessage("系统异常！");
				}
			}else{
				apiResponse.setCode("10002");
				apiResponse.setMessage("企业认证已提交，无需重复提交！");
			}
		}else {
			apiResponse.setCode(APIResponse.SUCESS_MSG);
			int result = authDao.addEnterpriseAuthentication(userId,enterprice);
			
			if(result > 0){
				apiResponse.setCode(APIResponse.SUCESS_MSG);
				apiResponse.setMessage("企业认证申请提交成功！");
			}else {
				apiResponse.setCode("10001");
				apiResponse.setMessage("系统异常！");
			}
			
		}
		
		return apiResponse;
	}

	@Override
	public Object personAuthenticationAudit(String userId, String audituserId,
			String action) {
		APIResponse apiResponse = new APIResponse();
		//校验用户是否已经注册
		 UserPersonalEntity queryUser = authDao.queryUserAuthenticationInfo(userId);
		
		if(queryUser == null){
			apiResponse.setCode("10002");
			apiResponse.setMessage("用户没有提交个人认证申请！");
		}else {
			int result = authDao.auditPersonAuthentication(userId,audituserId,action);
			
			if(result > 0){
				apiResponse.setCode(APIResponse.SUCESS_MSG);
				apiResponse.setMessage("个人认证申请提交成功！");
			}else {
				apiResponse.setCode("10001");
				apiResponse.setMessage("系统异常！");
			}
			
		}
		return apiResponse;
	}

	@Override
	public APIResponse enterpriseAuthenticationAudit(String userId,String enterpriseId,
			String audituserId, String action, String reason) {
		APIResponse apiResponse = new APIResponse();
		//校验用户是否已经通过实名认证
//		boolean queryUser = authDao.userIsAudith(userId);
//		
//		if(!queryUser){
//		    apiResponse.setErrorCode(ErrorCode.USER_NOT_REAL);
//		}else {
			int result = authDao.auditEnterpriceAuthentication(userId, enterpriseId,audituserId,action,reason);
			
			if(result > 0){
				//消息推送
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, "1".equals(action)?MsgConstants.ENTERPRISE_AUTH_PASS:MsgConstants.ENTERPRISE_AUTH_FAIL, userId);
				apiResponse.setMessage(APIResponse.SUCESS_MSG);
			}else {
			    apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			}
			
//		}

		return apiResponse;
	}

	@Override
	public APIResponse teamAuthenticationAudit(String userId, String teamId, String audituserId,
			String action,String reason) {
		APIResponse apiResponse = new APIResponse();
		
		//校验用户是否已经通过实名认证
        boolean queryUser = authDao.userIsAudith(userId);

		if(!queryUser){
		    apiResponse.setErrorCode(ErrorCode.USER_NOT_REAL);
		}else {
			int result = authDao.auditTeamAuthentication(userId,teamId,audituserId,action,reason);
			
			if(result > 0){
				//消息推送
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, "2".equals(action)?MsgConstants.TEAM_AUTH_PASS:MsgConstants.TEAM_AUTH_FAIL, userId);
			    apiResponse.setMessage(APIResponse.SUCESS_MSG);
			}else {
			    apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			}
		}
		return apiResponse;
	}

}
