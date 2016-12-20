package com.idg.bfzb.server.controller.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.ability.model.request.AbilityAddRequest;
import com.idg.bfzb.server.ability.model.request.AbilityIdRequest;
import com.idg.bfzb.server.ability.model.request.AbilityModifyRequest;
import com.idg.bfzb.server.ability.model.request.AdminAbilityRequest;
import com.idg.bfzb.server.ability.model.request.PageInfoRequest;
import com.idg.bfzb.server.ability.service.AbilityService;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.model.request.AdminCategoryRequest;
import com.idg.bfzb.server.configure.model.request.MsgConfigureRequest;
import com.idg.bfzb.server.configure.model.request.RegionRequest;
import com.idg.bfzb.server.configure.service.ConfigureManagerService;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.usercenter.model.request.LoginRequest;
import com.idg.bfzb.server.utility.servlet.ServletUtil;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 创建日期：2016/11/1
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/admin/configure")
public class ConfigureManagerController extends BaseController{
    //声明Service
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private ConfigureManagerService configureManagerService;
    //添加日志
    private Logger logger = LoggerFactory.getLogger(ConfigureManagerController.class);
    
    //具体接口
    
    /**
     * 
     * .技能配置页面入口
     * 
     * @param model
     * @return
     */
    @RequestMapping(value ="/ability",method = RequestMethod.GET)
    public String printAbilityConfigure(ModelMap model) {
        return "configureManager/abilityConfigure";
    }
    
    /**
     * 
     * .类别配置页面入口
     * 
     * @param model
     * @return
     */
    @RequestMapping(value ="/category",method = RequestMethod.GET)
    public String printCategoryConfigure(ModelMap model) {
        return "configureManager/categoryConfigure";
    }
    
    /**
     * 
     * .包招城市配置页面入口
     * 
     * @param model
     * @return
     */
    @RequestMapping(value ="/region",method = RequestMethod.GET)
    public String printRegionConfigure(ModelMap model) {
        return "configureManager/regionConfigure";
    }
    
    /**
     * 
     * .短信提醒配置页面入口
     * 
     * @param model
     * @return
     */
    @RequestMapping(value ="/message",method = RequestMethod.GET)
    public String printMessageConfigure(ModelMap model) {
        return "configureManager/messageConfigure";
    }
    
    /**
     * 
     * 查询技能列表
     * 
     * @param pageInfoRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/ability/actions/search" , method = RequestMethod.GET)
    @ResponseBody
    public void queryAbility(AdminAbilityRequest adminAbilityRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        JSONObject jo=new JSONObject();
        Integer pageNum = 0;
        Integer pageSize = 0;
        try {
            pageNum = Integer.parseInt(servletRequest.getParameter("page"));
        } catch (Exception e) {
            pageNum = 1;
        }
        try {
            pageSize = Integer.parseInt(servletRequest.getParameter("rows"));
        } catch (Exception e) {
            pageSize = 10;
        }
        PageRequest pageable = new PageRequest(pageNum-1, pageSize);
        PageInfo pageInfo = this.abilityService.queryAbility(adminAbilityRequest,pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    /**
     * 
     * 技能列表中新增技能
     * 
     * @param abilityAddRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/ability/actions/add" , method = RequestMethod.POST)
    @ResponseBody
    public void myNoAuditAbility(AbilityAddRequest abilityAddRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        APIResponse apiResponse = abilityService.addAbility(abilityAddRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * 修改技能列表中的技能
     * 
     * @param abilityIdRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/ability/actions/modify" , method = RequestMethod.POST)
    @ResponseBody
    public void myAbilityAdd(AbilityModifyRequest abilityModifyRequest, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }

        APIResponse apiResponse = abilityService.modifyAbility(abilityModifyRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .3.3.5.9 删除技能列表中的技能
     * 
     * @param abilityId
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/ability/{ability_id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public void myAbilityProject(@PathVariable("ability_id") String abilityId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        APIResponse apiResponse = abilityService.delAbility(abilityId);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * 查询类别列表
     * 
     * @param pageInfoRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/category/actions/search" , method = RequestMethod.GET)
    @ResponseBody
    public void queryCategory(AdminCategoryRequest adminCategoryRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        JSONObject jo=new JSONObject();
        Integer pageNum = 0;
        Integer pageSize = 0;
        try {
            pageNum = Integer.parseInt(servletRequest.getParameter("page"));
        } catch (Exception e) {
            pageNum = 1;
        }
        try {
            pageSize = Integer.parseInt(servletRequest.getParameter("rows"));
        } catch (Exception e) {
            pageSize = 10;
        }
        PageRequest pageable = new PageRequest(pageNum-1, pageSize);
        PageInfo pageInfo = this.configureManagerService.queryCategory(adminCategoryRequest,pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    /**
     * 
     * 查询类别关联的技能
     * 
     * @param abilityAddRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/category/link/ability" , method = RequestMethod.GET)
    @ResponseBody
    public void queryCategoryLinkAbility(AdminCategoryRequest adminCategoryRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        APIResponse apiResponse = this.configureManagerService.queryCategoryLinkAbility(adminCategoryRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .新增类别
     * 
     * @param adminCategoryRequest
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/category/action/add" , method = RequestMethod.POST)
    @ResponseBody
    public void addCategory(AdminCategoryRequest adminCategoryRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }

        APIResponse apiResponse = this.configureManagerService.addCategory(adminCategoryRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .分类修改
     * 
     * @param adminCategoryRequest
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/category/action/modify" , method = RequestMethod.POST)
    @ResponseBody
    public void modifyCategory(AdminCategoryRequest adminCategoryRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }

        APIResponse apiResponse = this.configureManagerService.modifyCategory(adminCategoryRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * 删除分类
     * 
     * @param categoryId
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/category/del/{category_id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public void delCategory(@PathVariable("category_id") String categoryId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        APIResponse apiResponse = this.configureManagerService.delCategory(categoryId);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * 查询包招城市
     * 
     * @param pageInfoRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/region/actions/search" , method = RequestMethod.GET)
    @ResponseBody
    public void queryRegion(HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        JSONObject jo=new JSONObject();
        Integer pageNum = 0;
        Integer pageSize = 0;
        try {
            pageNum = Integer.parseInt(servletRequest.getParameter("page"));
        } catch (Exception e) {
            pageNum = 1;
        }
        try {
            pageSize = Integer.parseInt(servletRequest.getParameter("rows"));
        } catch (Exception e) {
            pageSize = 10;
        }
        PageRequest pageable = new PageRequest(pageNum-1, pageSize);
        PageInfo pageInfo = this.configureManagerService.queryRegion(pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    /**
     * 
     * 删除包招城市
     * 
     * @param abilityId
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/region/{configure_id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public void delRegionConfigure(@PathVariable("configure_id") String configureId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        APIResponse apiResponse = configureManagerService.delRegion(configureId);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .添加包招城市
     * 
     * @param adminCategoryRequest
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/region/action/add" , method = RequestMethod.POST)
    @ResponseBody
    public void addRegion(RegionRequest regionRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }

        APIResponse apiResponse = this.configureManagerService.addRegion(regionRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * 查询短信配置
     * 
     * @param pageInfoRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/message/actions/search" , method = RequestMethod.GET)
    @ResponseBody
    public void queryMessageConfigure(MsgConfigureRequest msgConfigureRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        JSONObject jo=new JSONObject();
        Integer pageNum = 0;
        Integer pageSize = 0;
        try {
            pageNum = Integer.parseInt(servletRequest.getParameter("page"));
        } catch (Exception e) {
            pageNum = 1;
        }
        try {
            pageSize = Integer.parseInt(servletRequest.getParameter("rows"));
        } catch (Exception e) {
            pageSize = 10;
        }
        PageRequest pageable = new PageRequest(pageNum-1, pageSize);
        PageInfo pageInfo = this.configureManagerService.queryMsgConfigure(msgConfigureRequest,pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    /**
     * 
     * 删除短信配置
     * 
     * @param abilityId
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/message/{configure_id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public void delMsgConfigure(@PathVariable("configure_id") String configureId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        APIResponse apiResponse = configureManagerService.changeMsgConfigureState(configureId,(short)-1);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * 启用短信配置
     * 
     * @param abilityId
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/message/start/{configure_id}" , method = RequestMethod.GET)
    @ResponseBody
    public void startMsgConfigure(@PathVariable("configure_id") String configureId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        APIResponse apiResponse = configureManagerService.changeMsgConfigureState(configureId,(short)1);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * 停用短信配置
     * 
     * @param abilityId
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/message/stop/{configure_id}" , method = RequestMethod.GET)
    @ResponseBody
    public void stopMsgConfigure(@PathVariable("configure_id") String configureId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        APIResponse apiResponse = configureManagerService.changeMsgConfigureState(configureId,(short)0);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .添加短信配置
     * 
     * @param adminCategoryRequest
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/message/action/add" , method = RequestMethod.POST)
    @ResponseBody
    public void addMsgConfigure(MsgConfigureRequest msgConfigureRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }

        APIResponse apiResponse = this.configureManagerService.addMsgConfigure(msgConfigureRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
}
