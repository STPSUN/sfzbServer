package com.idg.bfzb.server.usercenter.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idg.bfzb.server.authentication.model.dto.UserEnterpriseEntity;
import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.RedisKey;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.common.model.ResultBeanInfo;
import com.idg.bfzb.server.file.dao.AttachmentRepository;
import com.idg.bfzb.server.file.model.dto.AttachmentEntity;
import com.idg.bfzb.server.team.dao.UserTeamRepository;
import com.idg.bfzb.server.team.model.dto.UserTeamEntity;
import com.idg.bfzb.server.usercenter.dao.EvaluateManagerRepository;
import com.idg.bfzb.server.usercenter.dao.MessageManagerRepository;
import com.idg.bfzb.server.usercenter.dao.RegionRepository;
import com.idg.bfzb.server.usercenter.dao.UserEnterpriseRepository;
import com.idg.bfzb.server.usercenter.dao.UserInfoRepository;
import com.idg.bfzb.server.usercenter.dao.UserPersonalRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcRegionEntity;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.usercenter.model.enums.PlatformType;
import com.idg.bfzb.server.usercenter.model.enums.TokenFlag;
import com.idg.bfzb.server.usercenter.model.enums.ZBRoleEnum;
import com.idg.bfzb.server.usercenter.model.request.EvaluateManagerRequest;
import com.idg.bfzb.server.usercenter.model.request.LoginRequest;
import com.idg.bfzb.server.usercenter.model.request.MessageManagerRequest;
import com.idg.bfzb.server.usercenter.model.request.MobileRegisterRequest;
import com.idg.bfzb.server.usercenter.model.request.PlatformBindRequest;
import com.idg.bfzb.server.usercenter.model.request.PlatformLoginRequest;
import com.idg.bfzb.server.usercenter.model.request.PwdModifiedRequest;
import com.idg.bfzb.server.usercenter.model.request.RetrieveRequest;
import com.idg.bfzb.server.usercenter.model.request.UcUsersResumeRequest;
import com.idg.bfzb.server.usercenter.model.request.UserFinancialRequest;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.model.response.LoginResponse;
import com.idg.bfzb.server.usercenter.model.response.ThirdLoginResponse;
import com.idg.bfzb.server.usercenter.model.response.UserBaseResponse;
import com.idg.bfzb.server.usercenter.model.response.UserFinancialResponse;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;
import com.idg.bfzb.server.usercenter.model.vo.RegionAllVo;
import com.idg.bfzb.server.usercenter.model.vo.RegionVo;
import com.idg.bfzb.server.usercenter.model.vo.UserBaseVo;
import com.idg.bfzb.server.usercenter.service.ShortMessageService;
import com.idg.bfzb.server.usercenter.service.TokenService;
import com.idg.bfzb.server.usercenter.service.UserService;
import com.idg.bfzb.server.usercenter.thirdlogin.WeixinUtils;
import com.idg.bfzb.server.utility.cache.redis.ValueCacheService;
import com.idg.bfzb.server.utility.encrypt.MD5Util;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;
import com.idg.bfzb.server.utility.tools.StringUtil;
import com.idg.bfzb.server.utility.tools.ZhCN2Spell;
import com.idg.bfzb.server.utility.verification.Validator;

@Service
public class UserServiceImp implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    private ValueCacheService<String, String> valueCacheService;
    @Autowired
    private UserPersonalRepository userPersonalRepository;
    @Autowired
    private UserInfoRepository userInfoDao;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ShortMessageService shortMessageService;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private UserTeamRepository userTeamRepository;
    @Autowired
    private UserEnterpriseRepository userEnterpriseRepository;
    @Autowired
    private EvaluateManagerRepository evaluateManagerRepository;
    @Autowired
    private MessageManagerRepository messageManagerRepository;
    
    /**
     * 用户通过HTTP登陆，登陆成功后把token保存到缓存中
     *
     * @param loginRequest 登陆请求
     * @return 登陆结果
     */
    @Override
    public APIResponse doLogin(LoginRequest loginRequest) {
        APIResponse apiResponse = new APIResponse();
        UcUserInfoEntity userInfoEntity = this.userInfoDao.findByNameOrMobile(loginRequest.getLoginName(),loginRequest.getLoginName());
        if (userInfoEntity==null){
            apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
            logger.error(ErrorCode.UC_USER_NOT_EXIST.getMsg()+
                    String.format("[user_name:%1$s,mobile%2$s]",loginRequest.getLoginName(),loginRequest.getLoginName()));
            return apiResponse;
        }

        String encryptPwd = MD5Util.MD5(loginRequest.getPassword());
        if (!StringUtils.equals(encryptPwd,userInfoEntity.getPassword())){
            apiResponse.setErrorCode(ErrorCode.UC_MOBILE_LOGIN_FAIL);
            logger.error(ErrorCode.UC_MOBILE_LOGIN_FAIL.getMsg());
            return apiResponse;
        }

        ResultBeanInfo<String> resultBeanInfo = this.tokenService.addToken(userInfoEntity, TokenFlag.CLIENT_LOGIN);

        //获取用户个人信息
        UserBaseVo userBaseVo = userPersonalRepository.findUserVoByUserId(userInfoEntity.getUserId());
        
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(userInfoEntity.getUserId());
        loginResponse.setNickName(userInfoEntity.getNickName());
        loginResponse.setUserName(userInfoEntity.getUserName());
        loginResponse.setUserRole(userBaseVo.getLastRole());
        loginResponse.setMobile(userInfoEntity.getMobile());
        loginResponse.setToken(resultBeanInfo.getObject());
        loginResponse.setIconSmallUrl(userInfoEntity.getIconSmallUrl());
        apiResponse.setData(loginResponse);
        return apiResponse;
    }


    @Override
    public APIResponse registerByMobile(MobileRegisterRequest registerRequest) {
        APIResponse apiResponse = new APIResponse();

        if (!Validator.isMobile(registerRequest.getMobile())){
            apiResponse.setErrorCode(ErrorCode.UC_MOBILE_NOT_CORRECT);
            return apiResponse;
        }

        // todo: 8888 验证码是测试用要删除
        if (!registerRequest.getAuthCode().equals("8888")){
            APIResponse checkResponse = this.shortMessageService.checkVerificationCode(registerRequest.getMobile(),registerRequest.getAuthCode());
            if ( !checkResponse.getCode().equals(APIResponse.SUCESS_MSG) ){
                apiResponse.setErrorCode(ErrorCode.UC_PIN_NOT_CORRECT);
                return apiResponse;
            }
        }

        UcUserInfoEntity userInfoEntity = this.userInfoDao.findByNameOrMobile(registerRequest.getMobile(),registerRequest.getMobile());
        if (userInfoEntity!=null){
            logger.error(String.format("%1$s,mobile:%2$s",ErrorCode.UC_MOBILE_ALREADY_REGISTER.getMsg(), registerRequest.getMobile()));
            apiResponse.setErrorCode(ErrorCode.UC_MOBILE_ALREADY_REGISTER);
            return apiResponse;
        }
        
        userInfoEntity = new UcUserInfoEntity();
        if(StringUtils.isNotBlank(registerRequest.getPlatformType()) && StringUtils.isNotBlank(registerRequest.getPlatformCode())){
        	if(registerRequest.getPlatformType().equals(PlatformType.WEIXIN_LOGIN.getType())){
      			//获取openid
      			/*String openId = WeixinUtils.getOpenId(registerRequest.getPlatformCode());
      			if(StringUtils.isBlank(openId)){
      				apiResponse.setErrorCode(ErrorCode.UC_THIRD_LOGIN_WRONG);
      				return apiResponse;
      			}*/
      			userInfoEntity.setWeixinId(registerRequest.getPlatformCode());
      		}else if(registerRequest.getPlatformType().equals(PlatformType.QQ_LOGIN.getType())){
      			userInfoEntity.setQqId(registerRequest.getPlatformCode());
      		}
        }

        userInfoEntity.setUserId(UUID.randomUUID().toString());
        userInfoEntity.setUserName(registerRequest.getMobile());
        userInfoEntity.setMobile(registerRequest.getMobile());
        userInfoEntity.setPassword(MD5Util.MD5(registerRequest.getPassword()));
        userInfoEntity.setState(Constants.UC_DEFAULT_STATE);
        this.userInfoDao.save(userInfoEntity);

        UserPersonalEntity personalEntity = new UserPersonalEntity();
        personalEntity.setUserId(userInfoEntity.getUserId());
        personalEntity.setBalance(new BigDecimal(0));
        personalEntity.setIncoming(new BigDecimal(0));
        personalEntity.setIsEnterpriseUser((byte)0);
        personalEntity.setIsTeamUser((byte)0);
        personalEntity.setReviewState((byte)4);//未提交实名审核
        this.userPersonalRepository.save(personalEntity);
        apiResponse.setMessage(APIResponse.SUCESS_MSG);
        return apiResponse;
    }

    @Override
    public APIResponse getUserBaseInfo(String userId) {
        APIResponse apiResponse = new APIResponse();
        UserBaseVo userBaseVo = this.userPersonalRepository.findUserVoByUserId(userId);
        UserTeamEntity userTeamEntity = this.userTeamRepository.findUserTeamByUserId(userId);
        UserEnterpriseEntity userEnterpriseEntity = this.userEnterpriseRepository.findUserEnterpriseByUserId(userId);
        //获取企业认证图片的完整url
        if(userEnterpriseEntity!=null && userEnterpriseEntity.getBusinessLicenseImage()!=null){
        	AttachmentEntity attach = attachmentRepository.findAllByAttchId(userEnterpriseEntity.getBusinessLicenseImage());
        	if(attach!=null && attach.getAttchUrl()!=null){
        		userEnterpriseEntity.setBusinessLicenseImage(ConfigFileUtils.HEAD_ICON_URL+attach.getAttchUrl());
        	}
        }
        
        if (userBaseVo==null){
            apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
            return apiResponse;
        }
        UserBaseResponse userBaseResponse = new UserBaseResponse();
        userBaseResponse.setUserId(userBaseVo.getUserId());
        userBaseResponse.setUserName(userBaseVo.getUserName());
        userBaseResponse.setRealName(userBaseVo.getRealName());
        userBaseResponse.setMobile(userBaseVo.getMobile());
        userBaseResponse.setIsTeamUser(userBaseVo.getIsTeamUser());
        userBaseResponse.setIsEnterpriseUser(userBaseVo.getIsEnterpriseUser());
        userBaseResponse.setBalance(userBaseVo.getBalance());
        userBaseResponse.setIncoming(userBaseVo.getIncoming());
        userBaseResponse.setExpenditure(userBaseVo.getExpenditure());
        userBaseResponse.setIconAttchId(userBaseVo.getIconAttchId());
        userBaseResponse.setIconUrl(ConfigFileUtils.HEAD_ICON_URL+userBaseVo.getIconUrl());
        userBaseResponse.setIconSmallAttchId(userBaseVo.getIconSmallAttchId());
        userBaseResponse.setIconSmallUrl(ConfigFileUtils.HEAD_ICON_URL+userBaseVo.getIconSmallUrl());
        userBaseResponse.setUserTeamInfo(userTeamEntity);
        userBaseResponse.setUserEnterpriseInfo(userEnterpriseEntity);
        userBaseResponse.setSex(userBaseVo.getSex());
        userBaseResponse.setNickName(userBaseVo.getNickName());
        userBaseResponse.setRole(userBaseVo.getLastRole());

        UcRegionEntity provinceEntity = this.regionRepository.findByRegionCode(userBaseVo.getLastProvinceCode());
        UcRegionEntity cityEntity = this.regionRepository.findByRegionCode(userBaseVo.getLastCityCode());
        UcRegionEntity areaEntity = this.regionRepository.findByRegionCode(userBaseVo.getLastAreaCode());

        if (provinceEntity!=null){
            RegionVo province = new RegionVo();
            province.setRegionCode(provinceEntity.getRegionCode());
            province.setRegionName(provinceEntity.getRegionName());
            userBaseResponse.setProvince(province);
        }
        if (cityEntity!=null){
            RegionVo city = new RegionVo();
            city.setRegionCode(cityEntity.getRegionCode());
            city.setRegionName(cityEntity.getRegionName());
            userBaseResponse.setCity(city);
        }
        if (areaEntity!=null){
            RegionVo area = new RegionVo();
            area.setRegionCode(areaEntity.getRegionCode());
            area.setRegionName(areaEntity.getRegionName());
            userBaseResponse.setArea(area);
        }

        apiResponse.setData(userBaseResponse);
        return apiResponse;
    }

    @Override
    public APIResponse modifyUserPassword(String userId, PwdModifiedRequest pwdModifiedRequest) {
        APIResponse apiResponse = new APIResponse();

        String newPassword = encryptUserPassword(pwdModifiedRequest.getNewPassword());
        String oldPassword = encryptUserPassword(pwdModifiedRequest.getOldPassword());

        UcUserInfoEntity userInfoEntity = this.userInfoDao.findOne(userId);
        /* 1.判断数据库中的密码是否和旧密码一样 */
        if (!userInfoEntity.getPassword().equals(oldPassword)) {
            // todo 日志
            apiResponse.setErrorCode(ErrorCode.UC_PASSWORD_NOT_CORRECT);
            return apiResponse;
        }
        //* 2.判断新旧密码是否一样，一样则报错 *//*
        if (newPassword.equals(oldPassword)) {
            // todo 日志
            apiResponse.setErrorCode(ErrorCode.UC_NEWOLD_PASSWORD_EQUAL);
            return apiResponse;
        }
        /* 3.用新密码替换数据库中的旧密码 */
        int reslut = this.userInfoDao.updatePasswordById(userId,newPassword);
        /* 4.使得当前用户的token失效 */
        if (reslut==0){
            //todo: 没有这个账号
            logger.error(String.format("%1$s,user_id:%2$s",ErrorCode.UC_USER_NOT_EXIST.getMsg(),userId));
            apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
            return apiResponse;
        }
        return apiResponse;
    }

    @Override
    public APIResponse modifyUserPassword(String userId, String newPassword) {
        int reslut = this.userInfoDao.updatePasswordById(userId,newPassword);
        APIResponse apiResponse = new APIResponse();
        return apiResponse;
    }

    /**
     * 加密从客户端上来密码
     * @param passwordByClient 客户端传密码 md5+盐
     * @return 加密结果
     */
    private String encryptUserPassword(String passwordByClient){
        return MD5Util.MD5(passwordByClient);
    }

    @Override
    public APIResponse retrievePassword( RetrieveRequest retrieveRequest) {
        APIResponse apiResponse = new APIResponse();

        if (!retrieveRequest.getAuthCode().equals("8888")){
            APIResponse checkResponse = this.shortMessageService.checkVerificationCode(retrieveRequest.getMobile(),retrieveRequest.getAuthCode());
            if (!checkResponse.getCode().equals(APIResponse.SUCESS_MSG) ){
                apiResponse.setErrorCode(ErrorCode.UC_PIN_NOT_CORRECT);
                return apiResponse;
            }
        }

        UcUserInfoEntity userInfoEntity = this.userInfoDao.findByNameOrMobile(retrieveRequest.getMobile(),retrieveRequest.getMobile());
        if (userInfoEntity==null){
            apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
            logger.error(String.format("%1$s:user_id%2$s",ErrorCode.UC_USER_NOT_EXIST.getMsg(),retrieveRequest.getMobile()));
            return apiResponse;
        }
        String encryptedPwd = encryptUserPassword(retrieveRequest.getNewPassword());
        this.modifyUserPassword(userInfoEntity.getUserId(),encryptedPwd);
        logger.info(String.format("用户[id:%1$s],重置密码成功!!",userInfoEntity.getUserId()));
        return apiResponse;
    }

    @Override
    public APIResponse loginOut(String userId) {
        APIResponse responseState = new APIResponse();
        // todo : 完善流程
        //清除旧token
//        LoginTokenEntity loginToken = getAccessToken(strAccessToken);
//        if (null != loginToken) {
//            /*tokenDao.deleteLoginToken(strAccessToken);
//            tokenDao.deleteRefreshToken(loginToken.getRefreshToken());
//            tokenDao.deleteLoginRefreshTokenRelaton(loginToken.getAccessToken());
//            responseState.setObject(loginToken);
//            if(log.isDebugEnabled()){
//                log.debug("cassandra logout token:"+strAccessToken+", ok");
//            }*/
//        }

        //Reids缓存
        valueCacheService.delete(RedisKey.LOGIN_TOKEN_KEY + userId);
        logger.info(String.format("用户[id:%1$s],退出登录!!",userId));
        return responseState;
    }

    @Override
    public APIResponse queryCityMsg(List<Map<String,Object>> regionAllVoListSave,String parentRegionId) {
        APIResponse apiResponse = new APIResponse();
        Map<String,Object> provinceMap = new HashMap<String,Object>();
        List<Map<String,Object>> provinceList = new ArrayList<Map<String,Object>>();
        if(regionAllVoListSave == null || parentRegionId != null){
            List<RegionAllVo> regionAllVoList = userInfoDao.findAllRepository(parentRegionId);
            if(regionAllVoList == null || regionAllVoList.isEmpty()){
                apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
            }else{
                for(int i = 0; i < regionAllVoList.size(); i++){
                    if("1".equals(regionAllVoList.get(i).getLevel())){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("region_code", regionAllVoList.get(i).getRegionCode());
                        map.put("region_name", regionAllVoList.get(i).getRegionName());
                        map.put("full_spell", regionAllVoList.get(i).getFullSpell());
                        map.put("short_spell", regionAllVoList.get(i).getShortSpell());
                        map.put("region_id", regionAllVoList.get(i).getRegionId());
                        map.put("cities", new ArrayList<Map<String,Object>>());
                        provinceList.add(map);
                    }
                    if("2".equals(regionAllVoList.get(i).getLevel())){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("region_code", regionAllVoList.get(i).getRegionCode());
                        map.put("region_name", regionAllVoList.get(i).getRegionName());
                        map.put("full_spell", regionAllVoList.get(i).getFullSpell());
                        map.put("short_spell", regionAllVoList.get(i).getShortSpell());
                        map.put("region_id", regionAllVoList.get(i).getRegionId());
                        map.put("districts", new ArrayList());
                        for(int j = 0; j < provinceList.size(); j++){
                            if(provinceList.get(j).get("region_id").equals(regionAllVoList.get(i).getParentRegionId())){
                                List<Map<String,Object>> cityList = (List<Map<String,Object>>)provinceList.get(j).get("cities");
                                cityList.add(map);
                                break;
                            }
                        }
                    }
                    if("3".equals(regionAllVoList.get(i).getLevel())){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("region_code", regionAllVoList.get(i).getRegionCode());
                        map.put("region_name", regionAllVoList.get(i).getRegionName());
                        map.put("full_spell", regionAllVoList.get(i).getFullSpell());
                        map.put("short_spell", regionAllVoList.get(i).getShortSpell());
                        map.put("region_id", regionAllVoList.get(i).getRegionId());
                        for(int j = 0; j < provinceList.size(); j++){
                            List<Map<String,Object>> cityList = (List<Map<String,Object>>)provinceList.get(j).get("cities");
                            for(int k = 0; k < cityList.size(); k++){
                                if(cityList.get(k).get("region_id").equals(regionAllVoList.get(i).getParentRegionId())){
                                    List<Map<String,Object>> districtList = (List<Map<String,Object>>)cityList.get(k).get("districts");
                                    districtList.add(map);
                                    break;
                                }
                            }
                        }
                    }    
                }
                provinceMap.put("provinces", provinceList);
                ConfigFileUtils.regionAllVoList = provinceList;
                apiResponse.setData(provinceMap);
                apiResponse.setMessage(apiResponse.SUCESS_MSG);
            }
        }else{
            provinceMap.put("provinces", regionAllVoListSave);
            apiResponse.setData(provinceMap);
            apiResponse.setMessage(apiResponse.SUCESS_MSG);
        }
        
        return apiResponse;
    }


    @Override
    public APIResponse switchRole(String userId, String roleName) {
        APIResponse apiResponse = new APIResponse();

        if (!roleName.equals(ZBRoleEnum.EMPLOYER.getValue()) && !roleName.equals(ZBRoleEnum.WORKER.getValue())){
            logger.error(String.format("%1%s:roleName:%2$s",ErrorCode.INVALID_ARGUMENT.getMsg(),roleName));
            apiResponse.setErrorCode(ErrorCode.INVALID_ARGUMENT);
            return apiResponse;
        }

        int rowOfUpdate = this.userPersonalRepository.updateLastRoleByUserId(userId,roleName);
        if (rowOfUpdate==0){
            logger.error(String.format("%1%s:user_id:%2$s",ErrorCode.UC_USER_NOT_EXIST.getMsg(),userId));
            apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
            return apiResponse;
        }
        return apiResponse;
    }


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public APIResponse modifyUserBaseInfo(String userId, UcUsersResumeRequest ucUsersResumeRequest) {
		APIResponse apiResponse = new APIResponse();
		//生成昵称全拼和首拼
		String nickFullSpell = null;
		String nickShortSpell = null;
		if(!StringUtil.isNull(ucUsersResumeRequest.getNickName())){
			//判断昵称是否已使用
			boolean isExist = this.userInfoDao.isExistNickName(userId,ucUsersResumeRequest.getNickName());
			if(isExist){
				apiResponse.setSucess(false);
				apiResponse.setErrorCode(ErrorCode.UC_NICK_NAME_IS_EXIST);
				return apiResponse;
			}
			nickFullSpell = ZhCN2Spell.converterToSpell(ucUsersResumeRequest.getNickName());
			nickShortSpell = ZhCN2Spell.converterToFirstSpell(ucUsersResumeRequest.getNickName());
		}
		UserPersonalEntity  ue = this.userPersonalRepository.findOne(userId);
		if(ue == null) ue = new UserPersonalEntity();
		ue.setUserId(userId);
		if(ucUsersResumeRequest.getProvinceCode() != null) ue.setLastProvinceCode(ucUsersResumeRequest.getProvinceCode());
		if(ucUsersResumeRequest.getCityCode() != null) ue.setLastCityCode(ucUsersResumeRequest.getCityCode());
		if(ucUsersResumeRequest.getAreaCode() != null) ue.setLastAreaCode(ucUsersResumeRequest.getAreaCode());
		
		UcUserInfoEntity ui = this.userInfoDao.findOne(userId);
		if(!StringUtil.isNull(ucUsersResumeRequest.getNickName())) {
			ui.setNickName(ucUsersResumeRequest.getNickName());
			ui.setNickFullSpell(nickFullSpell);
			ui.setNickShortSpell(nickShortSpell);
		}
		ui.setIconAttchId(ucUsersResumeRequest.getHeadIconAttchId());
		if(ucUsersResumeRequest.getHeadIconAttchId() != null) ui.setIconAttchId(ucUsersResumeRequest.getHeadIconAttchId());
		if(ucUsersResumeRequest.getHeadIconAttchId() != null) {
			ui.setIconUrl(this.attachmentRepository.getOne(ucUsersResumeRequest.getHeadIconAttchId()).getAttchUrl());
		}
		if(ucUsersResumeRequest.getHeadIconSmallAttchId() != null) ui.setIconSmallAttchId(ucUsersResumeRequest.getHeadIconSmallAttchId());
		if(ucUsersResumeRequest.getHeadIconSmallAttchId() != null) {
			ui.setIconSmallUrl(this.attachmentRepository.getOne(ucUsersResumeRequest.getHeadIconSmallAttchId()).getAttchUrl());
		}
		if(ucUsersResumeRequest.getSex() != null)ui.setSex(ucUsersResumeRequest.getSex());
		this.userPersonalRepository.save(ue);
		this.userInfoDao.save(ui);
		apiResponse.setSucess(true);
		return apiResponse;
	}


	@Override
	public PageInfo getUserList(UserManagerRequest userManagerRequest,Pageable pageable) {
		PageInfo pageInfo = this.userInfoDao.findUserListByCond(
				userManagerRequest, pageable);
		return pageInfo;
	}
	
	@Override
    public PageInfo getUserAuthenticationList(UserManagerRequest userManagerRequest,Pageable pageable) {
        PageInfo pageInfo = this.userInfoDao.findUserAuthenticationListByCond(
                userManagerRequest, pageable);
        return pageInfo;
    }

	@Override
	public APIResponse getOneNormalUser(UserManagerRequest userManagerRequest) {
		APIResponse apiResponse = new APIResponse();
		UserManagerResponse userManagerResponse = this.userInfoDao.findOneNormalUser(userManagerRequest);
		userManagerResponse.setIdcardPhoto1Url(ConfigFileUtils.HEAD_ICON_URL + userManagerResponse.getIdcardPhoto1Url());
		userManagerResponse.setIdcardPhoto2Url(ConfigFileUtils.HEAD_ICON_URL + userManagerResponse.getIdcardPhoto2Url());
		userManagerResponse.setIdcardPhoto3Url(ConfigFileUtils.HEAD_ICON_URL + userManagerResponse.getIdcardPhoto3Url());
		if(userManagerResponse!=null){
			apiResponse.setData(userManagerResponse);
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}else{
			apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
		}
		
		return apiResponse;
	}


	@Override
	public APIResponse auditPersonUser(String userId,HttpServletRequest servletRequest) {
		APIResponse apiResponse = new APIResponse();
		try {
		    short reviewState = 0;
		    reviewState = Short.parseShort(servletRequest.getParameter("review_state"));
		    String reason = servletRequest.getParameter("reason");
			int rs = this.userPersonalRepository.auditPersonUserById(userId, reviewState, reason);
			if(rs==0){
				apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
				apiResponse.setSucess(false);
			}else{
				apiResponse.setMessage(APIResponse.SUCESS_MSG);
				apiResponse.setSucess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			apiResponse.setSucess(false);
			return apiResponse;
		}
		return apiResponse;
	}

	@Override
    public PageInfo financialDetail(UserFinancialRequest userFinancialRequest,Pageable pageable){
	    PageInfo pageInfo = this.userInfoDao.getFinancialDetail(userFinancialRequest, pageable);
	    return pageInfo;
    }
	
	@Override
	public APIResponse balanceAndImprest(UserFinancialRequest userFinancialRequest){
	    APIResponse apiResponse = new APIResponse();
        try {
            UserFinancialResponse userFinancialResponse =  this.userInfoDao.queryBalance(userFinancialRequest);
            List<UserFinancialResponse> userFinancialResponseList = this.userInfoDao.queryImprest(userFinancialRequest);
            if(userFinancialResponse == null || userFinancialResponseList == null){
                apiResponse.setErrorCode(ErrorCode.MESSAGE_NOT_EXIST);
                apiResponse.setSucess(false);
            }else{
            	Map<String,String> map = new HashMap<String,String>();
            	map.put("balance", userFinancialResponse.getBalance().toString());
            	
            	if(!userFinancialResponseList.isEmpty() && userFinancialResponseList.size() > 0){
            		float imprest = 0;
            		for(int i = 0; i < userFinancialResponseList.size(); i++){
            			float tradeMoney = userFinancialResponseList.get(i).getTradeMoney().toBigInteger().floatValue();
        				float imprestScale = userFinancialResponseList.get(i).getImprestScale();
        				float revealScale = userFinancialResponseList.get(i).getRevealScale();
            			if("1".equals(userFinancialResponseList.get(i).getIsReveal().toString())){
            				imprest += tradeMoney * (imprestScale + revealScale);
            			}else{
            				imprest += tradeMoney * imprestScale;
            			}
            		}
            		map.put("imprest", String.valueOf(imprest));
            	}else{
            		map.put("imprest", "0");
            	}
            	
                apiResponse.setData(map);
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
                apiResponse.setSucess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
            apiResponse.setSucess(false);
            return apiResponse;
        }
        return apiResponse;
	}
	
	@Override
	public PageInfo queryEvaluate(EvaluateManagerRequest evaluateManagerRequest,Pageable pageable){
	    return evaluateManagerRepository.queryEvaluateByCond(evaluateManagerRequest,pageable);
	}
	
	@Override
	public PageInfo queryMessageDetail(MessageManagerRequest messageManagerRequest,Pageable pageable){
	    return messageManagerRepository.queryMsgDetailByCond(messageManagerRequest,pageable);
	}
	
	@Override
    public APIResponse deletMessage(String messageId) {
        APIResponse apiResponse = new APIResponse();
        try {
            boolean res = this.messageManagerRepository.delMsgByMessageId(messageId);
            if(res){
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
                apiResponse.setSucess(true);
            }else{
                apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
                apiResponse.setSucess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
            apiResponse.setSucess(false);
            return apiResponse;
        }
        return apiResponse;
    }

	@Override
	public APIResponse loginPlatform(PlatformLoginRequest platformLoginRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String platformType = platformLoginRequest.getPlatformType();
		String platformCode = platformLoginRequest.getPlatformCode();
		String weixinId = platformLoginRequest.getWeixinId();
		String qqId = platformLoginRequest.getQqId();
		//参数空判断
		if(StringUtils.isBlank(platformType) || (StringUtils.isBlank(platformCode) 
				&& StringUtils.isBlank(qqId) && StringUtils.isBlank(weixinId))){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		//参数输入范围判断
		if(!(platformType.equals(PlatformType.WEIXIN_LOGIN.getType()) || platformType.equals(PlatformType.QQ_LOGIN.getType()))){
			apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
			return apiResponse;
		}
		if(StringUtils.isBlank(weixinId) && StringUtils.isBlank(qqId)){
			if(platformType.equals(PlatformType.WEIXIN_LOGIN.getType())){
				//获取openid
				String openId = WeixinUtils.getOpenId(platformCode);
				if(StringUtils.isBlank(openId)){
					apiResponse.setErrorCode(ErrorCode.UC_THIRD_LOGIN_WRONG);
					return apiResponse;
				}
				weixinId = openId;
			}else{
				qqId = platformCode;
			}
		}
		
        UcUserInfoEntity userInfoEntity = this.userInfoDao.findByWeixinIdOrQQId(weixinId, qqId);
        if (userInfoEntity==null){
        	ThirdLoginResponse thirdLoginResponse = new ThirdLoginResponse();
        	thirdLoginResponse.setWeixinId(weixinId);
        	thirdLoginResponse.setQqId(qqId);
        	
        	apiResponse.setData(thirdLoginResponse);
            apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
            logger.error(ErrorCode.UC_USER_NOT_EXIST.getMsg()+
                    String.format("[weixin_id:%1$s,qq_id%2$s]",weixinId, qqId));
            return apiResponse;
        }

        ResultBeanInfo<String> resultBeanInfo = this.tokenService.addToken(userInfoEntity, TokenFlag.CLIENT_LOGIN);
        //获取用户个人信息
        UserBaseVo userBaseVo = userPersonalRepository.findUserVoByUserId(userInfoEntity.getUserId());
        
        ThirdLoginResponse loginResponse = new ThirdLoginResponse();
        loginResponse.setUserId(userInfoEntity.getUserId());
        loginResponse.setNickName(userInfoEntity.getNickName());
        loginResponse.setUserName(userInfoEntity.getUserName());
        loginResponse.setUserRole(userBaseVo.getLastRole());
        loginResponse.setMobile(userInfoEntity.getMobile());
        loginResponse.setToken(resultBeanInfo.getObject());
        loginResponse.setIconSmallUrl(userBaseVo.getIconSmallUrl());
        loginResponse.setQqId(qqId);
        loginResponse.setWeixinId(weixinId);
        apiResponse.setData(loginResponse);
        return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse bindPlatform(PlatformBindRequest platformBindRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String mobile = platformBindRequest.getMobile();
		String authCode = platformBindRequest.getAuthCode();
		String platformType = platformBindRequest.getPlatformType();
		String platformCode = platformBindRequest.getPlatformCode();
		Short isForceBind = platformBindRequest.getIsForceBind();
		//参数空判断
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(authCode) 
				|| StringUtils.isBlank(platformType) || StringUtils.isBlank(platformCode) || isForceBind==null){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            return apiResponse;
		}
		//手机格式判断
        if (!Validator.isMobile(mobile)){
            apiResponse.setErrorCode(ErrorCode.UC_MOBILE_NOT_CORRECT);
            return apiResponse;
        }
        //参数输入范围判断
  		if(!(platformType.equals(PlatformType.WEIXIN_LOGIN.getType()) || platformType.equals(PlatformType.QQ_LOGIN.getType()))){
  			apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
  			return apiResponse;
  		}
        //手机验证码验证
        APIResponse checkResponse = this.shortMessageService.checkVerificationCode(mobile, authCode);
        if (!checkResponse.getCode().equals(APIResponse.SUCESS_MSG) ){
            apiResponse.setErrorCode(ErrorCode.UC_PIN_NOT_CORRECT);
            return apiResponse;
        }
        //根据手机号码判断用户是否存在
        UcUserInfoEntity userInfoEntity = this.userInfoDao.findByNameOrMobile(mobile, mobile);
        if (userInfoEntity==null){
        	logger.error(String.format("%1$s,mobile:%2$s",ErrorCode.UC_USER_NOT_EXIST.getMsg(), mobile));
            apiResponse.setErrorCode(ErrorCode.UC_USER_NOT_EXIST);
            return apiResponse;
        }
  		if(platformType.equals(PlatformType.WEIXIN_LOGIN.getType())){
  			//获取openid
  			/*String openId = WeixinUtils.getOpenId(platformCode);
  			if(StringUtils.isBlank(openId)){
  				apiResponse.setErrorCode(ErrorCode.UC_THIRD_LOGIN_WRONG);
  				return apiResponse;
  			}*/
  			//账号已绑定第三方登录，是否强制绑定
  			if(Constants.UC_THIRDLOGIN_IS_FORCE_BIND_YES==isForceBind || StringUtils.isBlank(userInfoEntity.getWeixinId())){
  	  			userInfoEntity.setWeixinId(platformCode);
  			}else{
  				apiResponse.setErrorCode(ErrorCode.UC_THIRD_LOGIN_IS_BIND);
				return apiResponse;
  			}
  		}else{
  			if(Constants.UC_THIRDLOGIN_IS_FORCE_BIND_YES==isForceBind || StringUtils.isBlank(userInfoEntity.getQqId())){
  				userInfoEntity.setQqId(platformCode);
  			}else{
  				apiResponse.setErrorCode(ErrorCode.UC_THIRD_LOGIN_IS_BIND);
				return apiResponse;
  			}
  		}
  		try{
  			userInfoDao.save(userInfoEntity);
  			ResultBeanInfo<String> resultBeanInfo = this.tokenService.addToken(userInfoEntity, TokenFlag.CLIENT_LOGIN);
  	        //获取用户个人信息
  	        UserBaseVo userBaseVo = userPersonalRepository.findUserVoByUserId(userInfoEntity.getUserId());
  	        
  	        ThirdLoginResponse loginResponse = new ThirdLoginResponse();
  	        loginResponse.setUserId(userInfoEntity.getUserId());
  	        loginResponse.setNickName(userInfoEntity.getNickName());
  	        loginResponse.setUserName(userInfoEntity.getUserName());
  	        loginResponse.setUserRole(userBaseVo.getLastRole());
  	        loginResponse.setMobile(userInfoEntity.getMobile());
  	        loginResponse.setToken(resultBeanInfo.getObject());
  	        loginResponse.setWeixinId(userInfoEntity.getWeixinId());
  	        loginResponse.setQqId(userInfoEntity.getQqId());
  	        loginResponse.setIconSmallUrl(userBaseVo.getIconSmallUrl());
  	        apiResponse.setData(loginResponse);
  		}catch(Exception e){
  			e.printStackTrace();
  			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
  		}
        
		return apiResponse;
	}
}
