package com.idg.bfzb.server.configure.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.ability.model.dto.TAbilityEntity;
import com.idg.bfzb.server.ability.service.impl.AbilityServiceImpl;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.dao.CategoryRespository;
import com.idg.bfzb.server.configure.dao.MsgConfigureRespository;
import com.idg.bfzb.server.configure.model.dto.TContMsgConfigureEntity;
import com.idg.bfzb.server.configure.model.dto.UcRegionConfigureEntity;
import com.idg.bfzb.server.configure.model.request.AdminCategoryRequest;
import com.idg.bfzb.server.configure.model.request.MsgConfigureRequest;
import com.idg.bfzb.server.configure.model.request.RegionRequest;
import com.idg.bfzb.server.configure.model.response.CategoryLinkAbilityResponse;
import com.idg.bfzb.server.configure.service.ConfigureManagerService;
import com.idg.bfzb.server.project.model.dto.CategoryEntity;
import com.idg.bfzb.server.configure.dao.RegionRespository;

@Service
@SuppressWarnings("Duplicates")
public class ConfigureManagerServiceImpl implements ConfigureManagerService{
    private final Logger logger = LoggerFactory.getLogger(AbilityServiceImpl.class);
    @Autowired
    private CategoryRespository categoryRespository;
    @Autowired
    private RegionRespository regionRespository;
    @Autowired
    private MsgConfigureRespository msgConfigureRespository;
    
    /**
     * 查询分类
     * 
     */
    public PageInfo queryCategory(AdminCategoryRequest adminCategoryRequest,Pageable pageable){
        PageInfo pageInfo = this.categoryRespository.queryCategoryByCond(adminCategoryRequest, pageable);
        return pageInfo;
    }
    
    /**
     * 获取类别技能关系
     */
    public APIResponse queryCategoryLinkAbility(AdminCategoryRequest adminCategoryRequest){
        APIResponse apiResponse = new APIResponse();
        try {
            List<CategoryLinkAbilityResponse> categoryAbilityList = this.categoryRespository.queryCategoryAbilityByCategoryId(adminCategoryRequest);
            apiResponse.setData(categoryAbilityList);
            apiResponse.setMessage(apiResponse.SUCESS_MSG);
        } catch (Exception e) {
            // TODO: handle exception
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    public APIResponse addCategory(AdminCategoryRequest adminCategoryRequest){
        APIResponse apiResponse = new APIResponse();
        try {
            this.categoryRespository.addCategory(adminCategoryRequest);
            apiResponse.setMessage(apiResponse.SUCESS_MSG);
        } catch (Exception e) {
            // TODO: handle exception
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    public APIResponse modifyCategory(AdminCategoryRequest adminCategoryRequest){
        APIResponse apiResponse = new APIResponse();
        try {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategoryId(adminCategoryRequest.getCategoryId());
            categoryEntity.setCategoryName(adminCategoryRequest.getCategoryName());
            categoryEntity.setParentId("0");
            categoryEntity.setState((short) 0);
            this.categoryRespository.save(categoryEntity);
            
            this.categoryRespository.updateCategoryAbility(adminCategoryRequest);
            
            apiResponse.setMessage(apiResponse.SUCESS_MSG);
        } catch (Exception e) {
            // TODO: handle exception
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    public APIResponse delCategory(String categoryId){
        APIResponse apiResponse = new APIResponse();
        try {
            if(categoryId == null || categoryId.isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity = this.categoryRespository.findOne(categoryId);
                categoryEntity.setState((short) -1);
                this.categoryRespository.save(categoryEntity);
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
     * 查询包招城市
     * 
     */
    public PageInfo queryRegion(Pageable pageable){
    	PageInfo pageInfo = this.regionRespository.findRegionAll(pageable);
        return pageInfo;
    }
    
    /**
     * 删除包招城市配置
     */
    public APIResponse delRegion(String configureId){
        APIResponse apiResponse = new APIResponse();
        try {
            if(configureId == null || configureId.isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
                UcRegionConfigureEntity ucRegionConfigureEntity = new UcRegionConfigureEntity();
                ucRegionConfigureEntity = this.regionRespository.findOne(Long.parseLong(configureId));
                ucRegionConfigureEntity.setState((short) -1);
                this.regionRespository.save(ucRegionConfigureEntity);
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
     * 添加包招城市
     */
    public APIResponse addRegion(RegionRequest regionRequest){
        APIResponse apiResponse = new APIResponse();
        try {
        	UcRegionConfigureEntity ucRegionConfigureEntity = new UcRegionConfigureEntity();
        	ucRegionConfigureEntity.setCityId(regionRequest.getCityId());
        	ucRegionConfigureEntity.setCityCode(regionRequest.getCityCode());
        	ucRegionConfigureEntity.setCityName(regionRequest.getCityName());
        	ucRegionConfigureEntity.setRegionId(regionRequest.getRegionId());
        	ucRegionConfigureEntity.setRegionCode(regionRequest.getRegionCode());
        	ucRegionConfigureEntity.setRegionName(regionRequest.getRegionName());
        	ucRegionConfigureEntity.setState((short) 0);
        	this.regionRespository.save(ucRegionConfigureEntity);
            apiResponse.setMessage(apiResponse.SUCESS_MSG);
        } catch (Exception e) {
            // TODO: handle exception
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    /**
     * 查询短信配置
     * 
     */
    public PageInfo queryMsgConfigure(MsgConfigureRequest msgConfigureRequest,Pageable pageable){
    	PageInfo pageInfo = this.msgConfigureRespository.findMsgConfigureByCond(msgConfigureRequest,pageable);
        return pageInfo;
    }
    
    /**
     * 修改短信配置状态
     */
    public APIResponse changeMsgConfigureState(String configureId,short state){
        APIResponse apiResponse = new APIResponse();
        try {
            if(configureId == null || configureId.isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
            	TContMsgConfigureEntity tContMsgConfigureEntity = new TContMsgConfigureEntity();
            	tContMsgConfigureEntity = this.msgConfigureRespository.findOne(Long.parseLong(configureId));
            	tContMsgConfigureEntity.setState(state);
                this.msgConfigureRespository.save(tContMsgConfigureEntity);
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
     * 添加包招城市
     */
    public APIResponse addMsgConfigure(MsgConfigureRequest msgConfigureRequest){
        APIResponse apiResponse = new APIResponse();
        try {
        	TContMsgConfigureEntity tContMsgConfigureEntity = new TContMsgConfigureEntity();
        	tContMsgConfigureEntity.setAlertStyle(msgConfigureRequest.getAlertStyle());
        	tContMsgConfigureEntity.setRealName(msgConfigureRequest.getRealName());
        	tContMsgConfigureEntity.setMobile(msgConfigureRequest.getMobile());
        	tContMsgConfigureEntity.setCreateTime(new Timestamp(new Date().getTime()));
        	tContMsgConfigureEntity.setState((short) 1);
        	this.msgConfigureRespository.save(tContMsgConfigureEntity);
            apiResponse.setMessage(apiResponse.SUCESS_MSG);
        } catch (Exception e) {
            // TODO: handle exception
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
}
