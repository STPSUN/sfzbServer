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

import com.idg.bfzb.server.ability.model.request.AbilityIdRequest;
import com.idg.bfzb.server.ability.service.AbilityService;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.usercenter.model.request.LoginRequest;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 创建日期：2016/10/31
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/api/ability")
public class AbilityController extends BaseController{
    //声明Service
    @Autowired
    private AbilityService abilityService;
    //添加日志
    private Logger logger = LoggerFactory.getLogger(AbilityController.class);
    
    //具体接口
    /*
     * 3.3.5.1 审核中,审核通过的我的技能列表查询
     */
    @RequestMapping(value = "/my/audit/list" , method = RequestMethod.GET)
    @ResponseBody
    public Object myAuditAbility(HttpServletRequest servletRequest){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        //token中获取userId。
        String userId = super.getCurrentUser();
        return abilityService.myAuditAbility(userId);
    }
    
    /*
     * 3.3.5.2 未提交审核,审核失败的我的技能列表查询
     */
    @RequestMapping(value = "/my/noaudit/list" , method = RequestMethod.GET)
    @ResponseBody
    public Object myNoAuditAbility(HttpServletRequest servletRequest){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        //token中获取userId。
        String userId = super.getCurrentUser();
        
        return abilityService.myNoAuditAbility(userId);
    }
    
    /*
     * 3.3.5.3 我的技能新增
     */
    @RequestMapping(value = "/my/add" , method = RequestMethod.POST)
    @ResponseBody
    public Object myAbilityAdd(@RequestBody AbilityIdRequest abilityIdRequest,HttpServletRequest servletRequest){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        //token中获取userId。
        String userId = super.getCurrentUser();
        return abilityService.myAbilityAdd(userId, abilityIdRequest.getAbility());
    }
    
    /*
     * 3.3.5.5 项目需求技能查询
     */
    @RequestMapping(value = "/project/{project_id}/search" , method = RequestMethod.GET)
    @ResponseBody
    public Object myAbilityProject(@PathVariable("project_id") String projectId, HttpServletRequest servletRequest){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        return abilityService.myProjectAbility(projectId);
    }
    
}
