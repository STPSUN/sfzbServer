package com.idg.bfzb.server.ability.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.ability.dao.AbilityRespository;
import com.idg.bfzb.server.ability.dao.AdminAbilityRespository;
import com.idg.bfzb.server.ability.model.dto.TAbilityEntity;
import com.idg.bfzb.server.ability.model.dto.TUserAbilityAssoc;
import com.idg.bfzb.server.ability.model.dto.TUserAbilityAssocId;
import com.idg.bfzb.server.ability.model.request.AbilityAddRequest;
import com.idg.bfzb.server.ability.model.request.AbilityModifyRequest;
import com.idg.bfzb.server.ability.model.request.AdminAbilityRequest;
import com.idg.bfzb.server.ability.model.request.PageInfoRequest;
import com.idg.bfzb.server.ability.model.vo.AbilityVo;
import com.idg.bfzb.server.ability.model.vo.ProjectAbilityVo;
import com.idg.bfzb.server.ability.service.AbilityService;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.usercenter.model.dto.UcAuthCodeEntity;

@Service
@SuppressWarnings("Duplicates")
public class AbilityServiceImpl implements AbilityService{
    private final Logger logger = LoggerFactory.getLogger(AbilityServiceImpl.class);
    
    //添加Dao
    @Autowired
    private AbilityRespository abilityRespository;
    @Autowired
    private AdminAbilityRespository adminAbilityRespository;
    /**
     * 
     * .查询审核通过的我的技能列表
     * 
     * @param userId
     * @return 审核通过的我的技能列表
     */
    @Override
    public APIResponse myAuditAbility(String userId){
        APIResponse apiResponse = new APIResponse();
        if(userId == null || userId.isEmpty()){
            apiResponse.setErrorCode(ErrorCode.UC_ERR_NOT_LOGIN);
        }else{
            List<AbilityVo> auditAbility = abilityRespository.queryAuditAbility(userId);
            apiResponse.setData(auditAbility);
            apiResponse.setMessage(APIResponse.SUCESS_MSG);
        }
        
        return apiResponse;
    }
    
    /**
     * 
     * .未提交审核,审核失败的我的技能列表查询
     * 
     * @param userId
     * @return 未提交审核,审核失败的我的技能列表
     */
    public APIResponse myNoAuditAbility(String userId){
        APIResponse apiResponse = new APIResponse();
        if(userId == null || userId.isEmpty()){
            apiResponse.setErrorCode(ErrorCode.UC_ERR_NOT_LOGIN);
        }else{
            List<AbilityVo> auditAbility = abilityRespository.queryNoAuditAbility(userId);
            apiResponse.setData(auditAbility);
            apiResponse.setMessage(APIResponse.SUCESS_MSG);
        }
        
        return apiResponse;
    }
    
    /**
     * 
     * .我的技能新增
     * 
     * @param userId
     * @param abilityIdList
     * @return 新增结果
     */
    public APIResponse myAbilityAdd(String userId, List<Map<String,String>> abilityIdList){
        APIResponse apiResponse = new APIResponse();
        if(userId == null || userId.isEmpty()){
            apiResponse.setErrorCode(ErrorCode.UC_ERR_NOT_LOGIN);
        }else{
            if(abilityIdList == null || abilityIdList.isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
                List<TUserAbilityAssoc> tUserAbilityAssocList = new ArrayList<TUserAbilityAssoc>();
                for(int i = 0; i < abilityIdList.size(); i++){
                    TUserAbilityAssocId tUserAbilityAssocId = new TUserAbilityAssocId();
                    TUserAbilityAssoc tUserAbilityAssoc = new TUserAbilityAssoc();
                    tUserAbilityAssocId.setUserId(userId);
                    tUserAbilityAssocId.setAbility(abilityIdList.get(i).get("ability_id"));
                    tUserAbilityAssoc.setId(tUserAbilityAssocId);
                    tUserAbilityAssoc.setState((short) 0);
                    tUserAbilityAssocList.add(tUserAbilityAssoc);
                }
                abilityRespository.save(tUserAbilityAssocList);
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
            }
        }
        return apiResponse;
    }
    
    /**
     * 
     * .项目需求技能查询
     * 
     * @param userId
     * @param projectId
     * @return 查询结果
     */
    public APIResponse myProjectAbility(String projectId){
        APIResponse apiResponse = new APIResponse();

        if(projectId == null || projectId.isEmpty()){
            apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
        }else{
            List<ProjectAbilityVo> auditAbility = abilityRespository.queryProjectAbility(projectId);
            apiResponse.setData(auditAbility);
            apiResponse.setMessage(APIResponse.SUCESS_MSG);
        }    
        
        return apiResponse;
    }
    
    /**
     * 
     * .查询技能列表
     * 
     * @param pageInfoRequest
     * @return
     */
    public PageInfo queryAbility(AdminAbilityRequest adminAbilityRequest,Pageable pageable){
        PageInfo pageInfo = this.adminAbilityRespository.findAbilityByCond(adminAbilityRequest, pageable);
        return pageInfo;
    }
    
    /**
     * 
     * .技能列表中新增技能
     * 
     * @param abilityAddRequest
     * @return
     */
    public APIResponse addAbility(AbilityAddRequest abilityAddRequest){
        APIResponse apiResponse = new APIResponse();
        try {
            if(abilityAddRequest.getAbilityName() == null || abilityAddRequest.getAbilityName().isEmpty() 
                || abilityAddRequest.getDescription() == null|| abilityAddRequest.getDescription().isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
                TAbilityEntity tabilityEntity = new TAbilityEntity();
                tabilityEntity.setAbilityId(UUID.randomUUID().toString());
                tabilityEntity.setAbilityName(abilityAddRequest.getAbilityName());
                tabilityEntity.setDescription(abilityAddRequest.getDescription());
                tabilityEntity.setState((short) 0);
                adminAbilityRespository.save(tabilityEntity);
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage());
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    /**
     * 
     * .修改技能列表中的技能
     * 
     * @param abilityModifyRequest
     * @return
     */
    public APIResponse modifyAbility(AbilityModifyRequest abilityModifyRequest){
        APIResponse apiResponse = new APIResponse();
        try {
            if(abilityModifyRequest.getAbilityName() == null || abilityModifyRequest.getAbilityName().isEmpty() 
                || abilityModifyRequest.getDescription() == null|| abilityModifyRequest.getDescription().isEmpty()
                || abilityModifyRequest.getAbilityId() == null || abilityModifyRequest.getAbilityId().isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
                TAbilityEntity tabilityEntity = new TAbilityEntity();
                tabilityEntity.setAbilityId(abilityModifyRequest.getAbilityId());
                tabilityEntity.setAbilityName(abilityModifyRequest.getAbilityName());
                tabilityEntity.setDescription(abilityModifyRequest.getDescription());
                tabilityEntity.setState((short) 0);
                adminAbilityRespository.save(tabilityEntity);
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage());
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    /**
     * 
     * .删除技能列表中的技能
     * 
     * @param abilityId
     * @return
     */
    public APIResponse delAbility(String abilityId){
        APIResponse apiResponse = new APIResponse();
        try {
            if(abilityId == null || abilityId.isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
                TAbilityEntity tabilityEntity = new TAbilityEntity();
                tabilityEntity = adminAbilityRespository.findOne(abilityId);
                tabilityEntity.setState((short) -1);
                adminAbilityRespository.save(tabilityEntity);
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage());
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    /**
     * 获取普通用户列表（后台）
     * @param userManagerRequest
     * @return
     */
    public PageInfo getUserAbilityList(HttpServletRequest servletRequest,Pageable pageable){
        PageInfo pageInfo = adminAbilityRespository.findUserAbilityList(servletRequest, pageable);
        return pageInfo;
    }
    
    /**
     * 获取普通用户列表（后台）
     * @param servletRequest
     * @param state
     * @return
     */
    public APIResponse userAbilityModify(HttpServletRequest servletRequest,String state){
        APIResponse apiResponse = new APIResponse();
        try {
            if(servletRequest.getParameter("userId") == null || servletRequest.getParameter("userId").isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
                adminAbilityRespository.userAbilityModify(servletRequest.getParameter("userId"),state,servletRequest.getParameter("reason"));
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage());
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
}
