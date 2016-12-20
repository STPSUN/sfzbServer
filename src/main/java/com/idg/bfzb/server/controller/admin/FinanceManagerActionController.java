package com.idg.bfzb.server.controller.admin;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.adminfinance.model.request.FinanceWithdrawRequest;
import com.idg.bfzb.server.adminfinance.model.response.FinanceWithdrawResponse;
import com.idg.bfzb.server.adminfinance.service.AdminFinanceFinanceWithdrawService;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.tools.SecurityConstants;
import com.idg.bfzb.server.utility.servlet.ServletUtil;
/**
 * 
 */
@Controller
@RequestMapping("/admin/finance/action")
public class FinanceManagerActionController extends BaseController {

    private  static Logger log = Logger.getLogger(FinanceManagerActionController.class);

    @Resource
    private AdminFinanceFinanceWithdrawService adminFinanceWithdrawService;
    
    /**
     * 提现记录列表
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    public void getWithdrawList(FinanceWithdrawRequest financeRechargeRequest, 
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
    	PageInfo<FinanceWithdrawResponse> pageInfo = this.adminFinanceWithdrawService.getWithdrawList(financeRechargeRequest, pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    	
    }
    
    /**
     * 提现操作
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/withrawAction", method = RequestMethod.POST)
    @ResponseBody
    public void withrawAction(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	//1通过 2不通过
    	String auditAction = servletRequest.getParameter("type");
    	String recordId = servletRequest.getParameter("recordId");
    	String reason = servletRequest.getParameter("reason");
    	
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(auditAction) || StringUtils.isEmpty(recordId)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("数据不能为空！");
    	}else {
    		
    		if(auditAction.equals("1")){
    			//成功
    			ret = adminFinanceWithdrawService.withrawActionSuccess(recordId,reason);
    		}else{
    			//失败
    			HashMap<String,String> user = (HashMap)servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
    	        String audituserId = user.get("admin_id");
    			ret = adminFinanceWithdrawService.withrawActionFailure(recordId,reason,audituserId);
    		}
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
}
