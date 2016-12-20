package com.idg.bfzb.server.configure.service;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.model.request.AdminCategoryRequest;
import com.idg.bfzb.server.configure.model.request.MsgConfigureRequest;
import com.idg.bfzb.server.configure.model.request.RegionRequest;

/**
 * 
 * .后台配置管理
 * 
 * @author Administrator
 * @version Revision 1.0.0
 *
 */
public interface ConfigureManagerService {
    /**
     * 
     * .查询技能列表
     * 
     * @param pageInfoRequest
     * @return
     */
    PageInfo queryCategory(AdminCategoryRequest adminCategoryRequest,Pageable pageable);
    
    /**
     * 
     * .查询与类别关联的技能
     * 
     * @param adminCategoryRequest
     * @return
     */
    APIResponse queryCategoryLinkAbility(AdminCategoryRequest adminCategoryRequest);
    
    /**
     * 
     * .新增类别
     * 
     * @param adminCategoryRequest
     * @return
     */
    APIResponse addCategory(AdminCategoryRequest adminCategoryRequest);
    
    /**
     * 
     * .修改类别
     * 
     * @param adminCategoryRequest
     * @return
     */
    APIResponse modifyCategory(AdminCategoryRequest adminCategoryRequest);
    
    /**
     * 
     * .删除类别
     * 
     * @param categoryId
     * @return
     */
    APIResponse delCategory(String categoryId);
    
    /**
     * 
     * .查询包招城市
     * 
     * @param pageInfoRequest
     * @return
     */
    PageInfo queryRegion(Pageable pageable);
    
    /**
     * 
     * .删除包招城市
     * 
     * @param configureId
     * @return
     */
    APIResponse delRegion(String configureId);
    
    /**
     * 
     * .添加包招城市
     * 
     * @param adminCategoryRequest
     * @return
     */
    APIResponse addRegion(RegionRequest regionRequest);
    
    /**
     * 
     * .查询短信配置
     * 
     * @param pageInfoRequest
     * @return
     */
    PageInfo queryMsgConfigure(MsgConfigureRequest msgConfigureRequest,Pageable pageable);
    
    /**
     * 
     * .修改短信配置状态
     * 
     * @param configureId
     * @return
     */
    APIResponse changeMsgConfigureState(String configureId,short state);
    
    /**
     * 
     * .添加包招城市
     * 
     * @param adminCategoryRequest
     * @return
     */
    APIResponse addMsgConfigure(MsgConfigureRequest msgConfigureRequest);
}
