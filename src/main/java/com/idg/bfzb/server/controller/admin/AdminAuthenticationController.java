package com.idg.bfzb.server.controller.admin;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.ability.service.AbilityService;
import com.idg.bfzb.server.authentication.service.AuthenticationService;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.tools.SecurityConstants;
import com.idg.bfzb.server.utility.servlet.ServletUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类名称：后台管理认证类
 * 类描述：
 * 创建人：chen
 * 创建日期：2016/11/08
 */
@Controller
@RequestMapping("/admin/authentication")
public class AdminAuthenticationController extends BaseController {

    @Autowired
    private AuthenticationService authService;
    @Autowired
    private AbilityService abilityService;

    private Logger logger = LoggerFactory.getLogger(AdminAuthenticationController.class);
    
    /**
     * 
     * .技能认证
     * 
     * @param model
     * @return
     */
    @RequestMapping(value ="/abilityReview",method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        System.out.println("技能审核页面");
        return "reviewManager/abilityReview";
    }
    
    /**
     * 实名认证
     * @param model
     * @return
     */
    @RequestMapping(value ="/userAuthentication",method = RequestMethod.GET)
    public String printUserAuthentication(ModelMap model) {
        return "reviewManager/userAuthentication";
    }
    
    /**
     * 企业认证
     * @param model
     * @return
     */
    @RequestMapping(value ="/userEnterpriseAuthentication",method = RequestMethod.GET)
    public String printUserEnterpriseAuthentication(ModelMap model) {
        return "reviewManager/userEnterpriseAuthentication";
    }
    
    /**
     * 团队认证
     * @param model
     * @return
     */
    @RequestMapping(value ="/userTeamAuthentication",method = RequestMethod.GET)
    public String printUserTeamAuthentication(ModelMap model) {
        return "reviewManager/userTeamAuthentication";
    }
    
    /**
     * 个人认证申请审核  [POST]
     * @param servletRequest
     */
    @RequestMapping(value = "person/audit", method = RequestMethod.POST)
    @ResponseBody
    public Object personAuthenticationAudit(HttpServletRequest servletRequest) {
    	if (logger.isDebugEnabled()) {
    		logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
    	}
        
        String action = servletRequest.getParameter("action");
        
    	String audituserId = super.getCurrentUser();
//        String audituserId = "";
    	String userId = servletRequest.getParameter("user_id");
    	
    	return this.authService.personAuthenticationAudit(userId,audituserId,action);
    }
    /**
     * 企业证申请审核  [POST]
     * @param servletRequest
     */
    @RequestMapping(value = "enterprise/audit", method = RequestMethod.POST)
    @ResponseBody
    public void enterpriseAuthenticationAudit(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	if (logger.isDebugEnabled()) {
    		logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
    	}
    	APIResponse apiResponse = new APIResponse();
        String action = servletRequest.getParameter("review_state");
        
        HashMap<String,String> user = (HashMap)servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
        String audituserId = user.get("admin_id");

    	String userId = servletRequest.getParameter("user_id");
    	String enterpriseId = servletRequest.getParameter("enterprise_id");
    	String reason = servletRequest.getParameter("reason");
    	apiResponse = this.authService.enterpriseAuthenticationAudit(userId,enterpriseId,audituserId,action,reason);
    	ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    /**
     * 团队认证审核  [POST]
     * @param servletRequest
     */
    @RequestMapping(value = "team/audit", method = RequestMethod.POST)
    @ResponseBody
    public void teamAuthenticationAudit(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	if (logger.isDebugEnabled()) {
    		logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
    	}
    	APIResponse apiResponse = new APIResponse();
        String action = servletRequest.getParameter("review_state");
        
    	HashMap<String,String> user = (HashMap)servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
    	String audituserId = user.get("admin_id");

    	String teamId = servletRequest.getParameter("team_id");
    	String userId = servletRequest.getParameter("user_id");
    	String reason = servletRequest.getParameter("reason");
    	
    	apiResponse = this.authService.teamAuthenticationAudit(userId,teamId,audituserId,action,reason);
    	ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * 待审核用户技能查询
     * 
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/ability/user/search" , method = RequestMethod.GET)
    @ResponseBody
    public void myUserAbility(HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        JSONObject jo=new JSONObject();
        Map<String,String> requestParameter = new HashMap<String,String>();
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
            pageSize = 15;
        }
        PageRequest pageable = new PageRequest(pageNum-1, pageSize, new Sort(Direction.DESC,"createTime"));
        
        requestParameter.put("userName", servletRequest.getParameter("userName"));
        requestParameter.put("nickName", servletRequest.getParameter("nickName"));
        requestParameter.put("mobile", servletRequest.getParameter("mobile"));
        requestParameter.put("state", servletRequest.getParameter("state"));
        System.out.println("请求参数："+requestParameter);
        
        PageInfo pageInfo = this.abilityService.getUserAbilityList(servletRequest,pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    /**
     * 
     * .3.3.5.11 用户技能审核修改
     * 
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/ability/user/auditpass" , method = RequestMethod.GET)
    @ResponseBody
    public Object userAbilityAuditPass(HttpServletRequest servletRequest){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        return abilityService.userAbilityModify(servletRequest,"1");
    }
    
    /**
     * 
     * .3.3.5.12 用户技能审核不通过
     * 
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/ability/user/notapproved" , method = RequestMethod.POST)
    @ResponseBody
    public Object userAbilityNotApproved(HttpServletRequest servletRequest){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        return abilityService.userAbilityModify(servletRequest,"2");
    }
}
