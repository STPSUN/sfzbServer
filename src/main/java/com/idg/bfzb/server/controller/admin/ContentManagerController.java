package com.idg.bfzb.server.controller.admin;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.ability.model.request.AdminAbilityRequest;
import com.idg.bfzb.server.common.URLConstants;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.model.request.AdminCategoryRequest;
import com.idg.bfzb.server.content.model.request.ContentRequest;
import com.idg.bfzb.server.content.service.ContentManagerService;
import com.idg.bfzb.server.tools.SecurityConstants;
import com.idg.bfzb.server.utility.servlet.ServletUtil;

@Controller
@RequestMapping(value="/admin/contentManager")
public class ContentManagerController {
    private Logger logger = LoggerFactory.getLogger(ContentManagerController.class);
    @Autowired
    private ContentManagerService contentManagerService;
    
	/**
	 * banners管理跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.CONTENT_AD_BANNERS,method = RequestMethod.GET)
	public String adBanners(ModelMap model) {
		return "contentManager/adBanners";
	}
	
	/**
     * 友情链接跳转
     * @param model
     * @return
     */
    @RequestMapping(value=URLConstants.CONTENT_FRIENDSHIP_LINK,method = RequestMethod.GET)
    public String adFriendshipLink(ModelMap model) {
        return "contentManager/friendshipLink";
    }
	
	/**
	 * 网站热点新闻管理跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.CONTENT_WEB_NEWS,method = RequestMethod.GET)
	public String webNewsManager(ModelMap model) {
		return "contentManager/webNewsManager";
	}
	
	/**
     * 
     * 查询广告列表
     * 
     * @param pageInfoRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/adbanners/actions/search" , method = RequestMethod.POST)
    @ResponseBody
    public void queryAdBanners(ContentRequest contentRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
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
        contentRequest.setAdvType("adv_banner");
        PageInfo pageInfo = this.contentManagerService.queryAdBanners(contentRequest,pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    /**
     * 
     * 查询友情链接列表
     * 
     * @param pageInfoRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/friendshiplink/actions/search" , method = RequestMethod.POST)
    @ResponseBody
    public void queryFriendshipLink(ContentRequest contentRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
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
        contentRequest.setAdvType("friendship_link");
        PageInfo pageInfo = this.contentManagerService.queryAdBanners(contentRequest,pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    /**
     * 
     * .查询广告剩余序列
     * 
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/adbanners/search/advSort" , method = RequestMethod.GET)
    @ResponseBody
    public void queryAdvSort(HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        ContentRequest contentRequest = new ContentRequest();
        contentRequest.setAdvType("adv_banner");
        APIResponse apiResponse = this.contentManagerService.queryAdvSort(contentRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .查询友情链接剩余序列
     * 
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/friendshiplink/search/advSort" , method = RequestMethod.GET)
    @ResponseBody
    public void queryFriendshipLinkAdvSort(HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        ContentRequest contentRequest = new ContentRequest();
        contentRequest.setAdvType("friendship_link");
        APIResponse apiResponse = this.contentManagerService.queryAdvSort(contentRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .新增广告
     * 
     * @param contentRequest
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/adbanners/action/add" , method = RequestMethod.POST)
    @ResponseBody
    public void addAdbanners(ContentRequest contentRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        contentRequest.setAdvType("adv_banner");
        APIResponse apiResponse = this.contentManagerService.saveAdvertisement(contentRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .修改广告
     * 
     * @param contentRequest
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/adbanners/action/modify" , method = RequestMethod.POST)
    @ResponseBody
    public void modifyAdbanners(ContentRequest contentRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        contentRequest.setAdvType("adv_banner");
        APIResponse apiResponse = this.contentManagerService.saveAdvertisement(contentRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .删除广告
     * 
     * @param advId
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/adbanners/{adv_id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAdbanners(@PathVariable("adv_id") String advId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        HashMap<String,String> user = (HashMap)servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
        String updateUserId = user.get("admin_id");
        APIResponse apiResponse = this.contentManagerService.delAdvertisement(advId,updateUserId);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .广告停用
     * 
     * @param advId
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/adbanners/stop/{adv_id}" , method = RequestMethod.GET)
    @ResponseBody
    public void stopAdbanners(@PathVariable("adv_id") String advId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        HashMap<String,String> user = (HashMap)servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
        String updateUserId = user.get("admin_id");
        APIResponse apiResponse = this.contentManagerService.changeAdvertisementType(advId,updateUserId,(short) 1);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .广告启用
     * 
     * @param advId
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/adbanners/start/{adv_id}" , method = RequestMethod.GET)
    @ResponseBody
    public void startAdbanners(@PathVariable("adv_id") String advId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        HashMap<String,String> user = (HashMap)servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
        String updateUserId = user.get("admin_id");
        APIResponse apiResponse = this.contentManagerService.changeAdvertisementType(advId,updateUserId,(short) 0);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .新增友情链接
     * 
     * @param contentRequest
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/friendshipLink/action/add" , method = RequestMethod.POST)
    @ResponseBody
    public void addFriendshipLink(ContentRequest contentRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }

        contentRequest.setAdvType("friendship_link");
        APIResponse apiResponse = this.contentManagerService.saveAdvertisement(contentRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .修改友情链接
     * 
     * @param contentRequest
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/friendshipLink/action/modify" , method = RequestMethod.POST)
    @ResponseBody
    public void modifyFriendshipLink(ContentRequest contentRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }

        contentRequest.setAdvType("friendship_link");
        APIResponse apiResponse = this.contentManagerService.saveAdvertisement(contentRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .删除友情链接
     * 
     * @param advId
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/friendshiplink/{adv_id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteFriendshipLink(@PathVariable("adv_id") String advId, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        HashMap<String,String> user = (HashMap)servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
        String updateUserId = user.get("admin_id");
        APIResponse apiResponse = this.contentManagerService.delAdvertisement(advId,updateUserId);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
}