package com.idg.bfzb.server.ability.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.ability.model.request.AbilityAddRequest;
import com.idg.bfzb.server.ability.model.request.AbilityModifyRequest;
import com.idg.bfzb.server.ability.model.request.AdminAbilityRequest;
import com.idg.bfzb.server.ability.model.request.PageInfoRequest;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;


/**
 * 技能Service层
 *
 */
public interface AbilityService {
    /**
     * 
     * .查询审核通过的我的技能列表
     * 
     * @param userId
     * @return 审核通过的我的技能列表
     */
    APIResponse myAuditAbility(String userId);
    
    /**
     * 
     * .未提交审核,审核失败的我的技能列表查询
     * 
     * @param userId
     * @return 未提交审核,审核失败的我的技能列表
     */
    APIResponse myNoAuditAbility(String userId);
    
    /**
     * 
     * .我的技能新增
     * 
     * @param userId
     * @param abilityIdList
     * @return 新增结果
     */
    APIResponse myAbilityAdd(String userId, List<Map<String,String>> abilityIdList);
    
    /**
     * 
     * .项目需求技能查询
     * 
     * @param userId
     * @param projectId
     * @return 查询结果
     */
    APIResponse myProjectAbility(String projectId);
    
    /**
     * 
     * .查询技能列表
     * 
     * @param pageInfoRequest
     * @return
     */
    PageInfo queryAbility(AdminAbilityRequest adminAbilityRequest,Pageable pageable);
    
    /**
     * 
     * .技能列表中新增技能
     * 
     * @param abilityAddRequest
     * @return
     */
    APIResponse addAbility(AbilityAddRequest abilityAddRequest);
    
    /**
     * 
     * .修改技能列表中的技能
     * 
     * @param abilityModifyRequest
     * @return
     */
    APIResponse modifyAbility(AbilityModifyRequest abilityModifyRequest);
    
    /**
     * 
     * .删除技能列表中的技能
     * 
     * @param abilityId
     * @return
     */
    APIResponse delAbility(String abilityId);
    
    /**
     * 获取普通用户列表（后台）
     * @param servletRequest
     * @param pageable
     * @return
     */
    PageInfo getUserAbilityList(HttpServletRequest servletRequest,Pageable pageable);
    
    /**
     * 获取普通用户列表（后台）
     * @param servletRequest
     * @param state
     * @return
     */
    APIResponse userAbilityModify(HttpServletRequest servletRequest,String state);
}
