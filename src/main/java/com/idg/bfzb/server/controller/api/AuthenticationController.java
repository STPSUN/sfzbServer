package com.idg.bfzb.server.controller.api;


import com.idg.bfzb.server.authentication.model.EnterpriceAuthRequest;
import com.idg.bfzb.server.authentication.model.UserAuthRequest;
import com.idg.bfzb.server.authentication.service.AuthenticationService;
import com.idg.bfzb.server.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 类名称：
 * 类描述：
 * 创建人：chen
 * 创建日期：2016/10/25
 */
@Controller
@RequestMapping("/api/authentication")
public class AuthenticationController extends BaseController {

    @Autowired
    private AuthenticationService authService;

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    /**
     * 获取我的认证进度  [GET]
     * @param servletRequest
     */
    @RequestMapping(value = "person/query", method = RequestMethod.GET)
    @ResponseBody
    public Object getMyAuthenticationInfo(HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        String userId = super.getCurrentUser();
        //测试用
//        String userId = null;
//        if(userId == null ){
//        	userId = "6b9bc3d8-bd1d-46a1-8cb4-9d0448891ed3";
//        }
        return this.authService.getMyAuthenticationInfo(userId);
    }
    /**
     * 个人认证申请  [POST]
     * @param servletRequest
     */
    @RequestMapping(value = "person/apply", method = RequestMethod.POST)
    @ResponseBody
    public Object personAuthenticationApply(@RequestBody UserAuthRequest user,HttpServletRequest servletRequest) {
    	if (logger.isDebugEnabled()) {
    		logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
    	}
    	
        
//        UserAuthRequest user = new UserAuthRequest();
//        String userRealName = servletRequest.getParameter("user_real_name");
//        String idCardNumber = servletRequest.getParameter("id_card_number");
//        String idNumberFrontBatchId = servletRequest.getParameter("front_batch_id");
//        String idNumberBackBatchId = servletRequest.getParameter("back_batch_id");
//        String idNumberHoldBatchId = servletRequest.getParameter("hold_batch_id");
//        
//        user.setIdCardNumber(idCardNumber);
//        user.setUserRealName(userRealName);
//        user.setIdNumberBackBatchId(idNumberBackBatchId);
//        user.setIdNumberFrontBatchId(idNumberFrontBatchId);
//        user.setIdNumberHoldBatchId(idNumberHoldBatchId);
        
    	String userId = super.getCurrentUser();
        //测试用
//        String userId = null;
//        if(userId == null ){
//        	userId = "fc5b57d2-f189-4e65-9f02-2248d66ba3b5";
//        }
    	return this.authService.personAuthenticationapply(userId,user);
    }
    /**
     * 企业认证申请  [POST]
     * @param servletRequest
     */
    @RequestMapping(value = "enterprise/apply", method = RequestMethod.POST)
    @ResponseBody
    public Object enterpriseAuthenticationApply(@RequestBody EnterpriceAuthRequest enterprice,HttpServletRequest servletRequest) {
    	if (logger.isDebugEnabled()) {
    		logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
    	}
    	
//    	EnterpriceAuthRequest enterprice = new EnterpriceAuthRequest();
//    	
//    	String enterpriseName = servletRequest.getParameter("enterprise_name");
//        String enterpriseCity = servletRequest.getParameter("enterprise_city");
//        String phoneNumber = servletRequest.getParameter("phone_number");
//        String businessEntity = servletRequest.getParameter("business_entity");
//        String businessScope = servletRequest.getParameter("business_scope");
//        String enterpriseNumber = servletRequest.getParameter("enterprise_number");
//        String businessLicenseImage = servletRequest.getParameter("business_license_image");
//        
//        enterprice.setEnterpriseName(enterpriseName);
//        enterprice.setBusinessEntity(businessEntity);
//        enterprice.setBusinessScope(businessScope);
//        enterprice.setEnterpriseCity(enterpriseCity);
//        enterprice.setEnterpriseNumber(enterpriseNumber);
//        enterprice.setPhoneNumber(phoneNumber);
//        enterprice.setBusinessLicenseImage(businessLicenseImage);
        
    	String userId = super.getCurrentUser();
    	//测试用
//        String userId = null;
//        if(userId == null ){
//        	userId = "fc5b57d2-f189-4e65-9f02-2248d66ba3b578";
//        }
    	return this.authService.enterpriseAuthenticationapply(userId,enterprice);
    }
    /**
     * 获取认证企业进度  [GET]
     * @param servletRequest
     */
    @RequestMapping(value = "/enterprise/query", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserEnterpriceAuthenticationInfo(HttpServletRequest servletRequest) {
    	if (logger.isDebugEnabled()) {
    		logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
    	}
    	
    	String userId = super.getCurrentUser();
    	//测试用
//        String userId = null;
//        if(userId == null ){
//        	userId = "3f8869d7-7b75-48ff-b7d1-1ccb9847eca9";
//        }
    	return this.authService.getUserEnterpriceAuthenticationInfo(userId);
    }
    
}
