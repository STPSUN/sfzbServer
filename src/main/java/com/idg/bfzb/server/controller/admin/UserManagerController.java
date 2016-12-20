package com.idg.bfzb.server.controller.admin;

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
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.usercenter.model.request.EvaluateManagerRequest;
import com.idg.bfzb.server.usercenter.model.request.MessageManagerRequest;
import com.idg.bfzb.server.usercenter.model.request.UserFinancialRequest;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.service.UserService;
import com.idg.bfzb.server.utility.servlet.ServletUtil;

/**
 * 类名称：
 * 类描述：
 * 创建人：weibeifeng
 * 创建日期：2016/11/2
 */
@Controller
@RequestMapping("/admin/users")
public class UserManagerController extends BaseController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserManagerController.class);

    /**
     * 打开普通用户页面
     * @param model
     * @return
     */
    @RequestMapping(value ="/userPerson",method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		return "userManager/userPerson";
	}
    
    /**
     * 打开企业用户页面
     * @param model
     * @return
     */
    @RequestMapping(value ="/userEnterprise",method = RequestMethod.GET)
    public String printUserEnterprise(ModelMap model) {
        return "userManager/userEnterprise";
    }
    
    /**
     * 打开团队用户页面
     * @param model
     * @return
     */
    @RequestMapping(value ="/userTeam",method = RequestMethod.GET)
    public String printUserTeam(ModelMap model) {
        return "userManager/userTeam";
    }
    
    /**
     * 打开评价管理页面
     * @param model
     * @return
     */
    @RequestMapping(value ="/evaluateManager",method = RequestMethod.GET)
    public String printEvaluateManager(ModelMap model) {
        return "userManager/evaluateManager";
    }
    
    /**
     * 打开消息管理页面
     * @param model
     * @return
     */
    @RequestMapping(value ="/messageManager",method = RequestMethod.GET)
    public String printMessageManager(ModelMap model) {
        return "userManager/messageManager";
    }
    
    /**
     * 普通用户详情查询 [GET] 
     * @param servletRequest
     */
    @RequestMapping(value = "/{user_id}/resume", method = RequestMethod.GET)
    @ResponseBody
    public void getOneNormalUser(@PathVariable("user_id") String userId,
    		HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
        APIResponse apiResponse = new APIResponse();
        UserManagerRequest userManagerRequest = new UserManagerRequest();
        userManagerRequest.setUserId(userId);
        apiResponse = this.userService.getOneNormalUser(userManagerRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 用户列表查询
     * @param userManagerRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getUserList(UserManagerRequest userManagerRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	//String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
		//String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
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
    	PageInfo pageInfo = this.userService.getUserList(userManagerRequest,pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    /**
     * 用户审核列表查询
     * @param userManagerRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/authenticationList", method = RequestMethod.POST)
    @ResponseBody
    public void getUserAuthenticationList(UserManagerRequest userManagerRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
        //String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
        //String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
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
        PageInfo pageInfo = this.userService.getUserAuthenticationList(userManagerRequest,pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
        
    }
    
    /**
     * 个人认证审核 [POST] 
     * @param servletRequest
     */
    @RequestMapping(value = "/person/{user_id}/audit", method = RequestMethod.POST)
    @ResponseBody
    public void auditPersonUser(@PathVariable("user_id") String userId,
    		HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	APIResponse apiResponse = new APIResponse();
    	try {
			apiResponse = this.userService.auditPersonUser(userId,servletRequest);
		} catch (Exception e) {
			apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
		}
        
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 资金明显流水，
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "/financialDetail/query",method = RequestMethod.POST)
    @ResponseBody
    public void financialDetail(UserFinancialRequest userFinancialRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
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
            pageSize = 15;
        }
        PageRequest pageable = new PageRequest(pageNum-1, pageSize);
        //PageRequest pageable = new PageRequest(pageNum-1, pageSize, new Sort(Direction.DESC,"tradeTime"));
        
        if(userFinancialRequest.getUserId() == null){
            ServletUtil.createSuccessResponse(200, jo, servletResponse);
        }else{
        	System.out.println("查询资金流水："+userFinancialRequest.getUserId()+";"+pageNum+";"+pageSize);
            PageInfo pageInfo = this.userService.financialDetail(userFinancialRequest, pageable);
            jo.put("rows", pageInfo.getPageData());
            jo.put("records", pageInfo.getTotalRows());
            jo.put("total", pageInfo.getTotalPages());
            ServletUtil.createSuccessResponse(200, jo, servletResponse);
        }
    }
    
    /**
     * 账户余额，账户项目预付款
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "/balanceandimprest/query",method = RequestMethod.POST)
    @ResponseBody
    public void balanceAndImprest(UserFinancialRequest userFinancialRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
        APIResponse apiResponse = new APIResponse();
        
        try {
            apiResponse = this.userService.balanceAndImprest(userFinancialRequest);
        } catch (Exception e) {
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
    
    /**
     * 
     * .评价信息查询
     * 
     * @param evaluateManagerRequest
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/evaluate/query",method = RequestMethod.GET)
    @ResponseBody
    public void queryEvaluate(EvaluateManagerRequest evaluateManagerRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
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
            pageSize = 15;
        }

        PageRequest pageable = new PageRequest(pageNum-1, pageSize);
        
        PageInfo pageInfo = this.userService.queryEvaluate(evaluateManagerRequest, pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    @RequestMapping(value = "/message/query",method = RequestMethod.GET)
    @ResponseBody
    public void queryMessageDetial(MessageManagerRequest messageManagerRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
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
            pageSize = 15;
        }

        PageRequest pageable = new PageRequest(pageNum-1, pageSize);
        
        PageInfo pageInfo = this.userService.queryMessageDetail(messageManagerRequest, pageable);
        jo.put("rows", pageInfo.getPageData());
        jo.put("records", pageInfo.getTotalRows());
        jo.put("total", pageInfo.getTotalPages());
        ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    
    /**
     * 
     * .删除消息记录
     * 
     * @param messageId
     * @param servletRequest
     * @param servletResponse
     */
    @RequestMapping(value = "/message/{message_id}/delete", method = RequestMethod.GET)
    @ResponseBody
    public void deletMessage(@PathVariable("message_id") String messageId,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
        APIResponse apiResponse = new APIResponse();
        System.out.println("messageId:"+messageId);
        apiResponse = this.userService.deletMessage(messageId);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    }
}
