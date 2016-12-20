package com.idg.bfzb.server.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.adminfinance.model.request.FinanceRechargeRequest;
import com.idg.bfzb.server.adminfinance.model.request.FinanceTradeDetailRequest;
import com.idg.bfzb.server.adminfinance.service.FinanceRechargeService;
import com.idg.bfzb.server.adminfinance.service.ProjectFinanceService;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.pay.service.PayService;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.utility.servlet.ServletUtil;

/**
 * 
 * @author weibeifeng
 *
 */
@Controller
@RequestMapping("/admin/finance")
public class FinanceManagerController extends BaseController {

    private  static Logger log = Logger.getLogger(FinanceManagerController.class);

    @Resource
    private FinanceRechargeService financeRechargeService;
    @Resource
    private ProjectFinanceService projectFinanceService;
    @Resource
    private PayService payService;
    
    /**
     * 打开项目资金管理页面
     * @param model
     * @return
     */
    @RequestMapping(value ="/projectFinance",method = RequestMethod.GET)
	public String toProjectFinancePage(ModelMap model) {
		return "financeManager/projectFinance";
	}
    
    /**
     * 打开提现管理页面
     * @param model
     * @return
     */
    @RequestMapping(value ="/withraw",method = RequestMethod.GET)
	public String toWithrawPage(ModelMap model) {
		return "financeManager/withraw";
	}
    
    /**
     * 打开充值管理页面
     * @param model
     * @return
     */
    @RequestMapping(value ="/recharge",method = RequestMethod.GET)
	public String toRechargePage(ModelMap model) {
		return "financeManager/recharge";
	}
    
    /**
     * 充值记录列表
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "rechargeList/query",method = RequestMethod.POST)
    @ResponseBody
    public void rechargeList(FinanceRechargeRequest financeRechargeRequest, 
    		HttpServletRequest servletRequest, HttpServletResponse servletResponse){
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
    	PageInfo pageInfo = this.financeRechargeService.getRechargeList(financeRechargeRequest, pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    	
    }
    /**
     * 线下充值核对
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "recharge/audit",method = RequestMethod.POST)
    @ResponseBody
    public void auditOffline(FinanceRechargeRequest financeRechargeRequest, 
    		HttpServletRequest servletRequest, HttpServletResponse servletResponse){
    	APIResponse apiResponse = new APIResponse();
        apiResponse = this.financeRechargeService.auditOffline(financeRechargeRequest);
        ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    	
    }
    
    
    /**
     * 用户资金列表
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "userFinanceList/query",method = RequestMethod.POST)
    @ResponseBody
    public void userFinanceList(UserManagerRequest userManagerRequest, 
    		HttpServletRequest servletRequest, HttpServletResponse servletResponse){
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
    	PageInfo pageInfo = this.projectFinanceService.getUserFinanceList(userManagerRequest, pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    	
    }
    
    /**
     * 平台资金列表
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "platFinanceList/query",method = RequestMethod.POST)
    @ResponseBody
    public void platFinanceList(FinanceTradeDetailRequest tfdRequest,
    		HttpServletRequest servletRequest, HttpServletResponse servletResponse){
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
    	PageInfo pageInfo = this.projectFinanceService.getPlatFinanceList(tfdRequest, pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    	
    }
    
    
    /**
     * 用户项目交易明细
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "userProjectDetailList/query",method = RequestMethod.POST)
    @ResponseBody
    public void userProjectDetailList(UserManagerRequest userManagerRequest, 
    		HttpServletRequest servletRequest, HttpServletResponse servletResponse){
    	APIResponse apiResponse = new APIResponse();
    	PageRequest pageable = new PageRequest(userManagerRequest.getPageNum(), userManagerRequest.getPageSize());
    	FinanceTradeDetailEntity entity = new FinanceTradeDetailEntity();
    	entity.setPayUserId(userManagerRequest.getUserId());
    	entity.setIncomeUserId(userManagerRequest.getUserId());
    	PageInfo pageInfo = new PageInfo();
    	try {
    		pageInfo = this.projectFinanceService.userProjectDetailList(entity, pageable);
    		apiResponse.setData(pageInfo);
    		apiResponse.setSucess(true);
		} catch (Exception e) {
			apiResponse.setSucess(false);
		}
		ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    	
    }
    
    /**
     * 用户充值提现明细
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "userWRDetailList/query",method = RequestMethod.POST)
    @ResponseBody
    public void userWRDetailList(UserManagerRequest userManagerRequest, 
    		HttpServletRequest servletRequest, HttpServletResponse servletResponse){
    	APIResponse apiResponse = new APIResponse();
    	PageRequest pageable = new PageRequest(userManagerRequest.getPageNum(), userManagerRequest.getPageSize());
    	FinanceTradeDetailEntity entity = new FinanceTradeDetailEntity();
    	entity.setPayUserId(userManagerRequest.getUserId());
    	entity.setIncomeUserId(userManagerRequest.getUserId());
    	PageInfo pageInfo = new PageInfo();
    	try {
    		pageInfo = this.projectFinanceService.userWRDetailList(entity, pageable);
    		apiResponse.setData(pageInfo);
    		apiResponse.setSucess(true);
		} catch (Exception e) {
			apiResponse.setSucess(false);
		}
		ServletUtil.createSuccessResponse(200, apiResponse, servletResponse);
    	
    }
    
}
