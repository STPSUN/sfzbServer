package com.idg.bfzb.server.controller.admin;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.adminproject.model.WarrantyAdminRequest;
import com.idg.bfzb.server.adminproject.model.WarrantyAdminResponse;
import com.idg.bfzb.server.adminproject.service.WarrantyAdminService;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.tools.SecurityConstants;
import com.idg.bfzb.server.utility.servlet.ServletUtil;
/**
 * 质保管理操作相关action请求
 * @author chen
 *
 */
@Controller
@RequestMapping(value="/admin/warrantyManager/action")
public class WarrantyManagerActionController {
	@Autowired
	private WarrantyAdminService warrantyService; 
	/**
     * 获取质保管理列表
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getWarrantyList(WarrantyAdminRequest projectRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
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
    	PageInfo<WarrantyAdminResponse> pageInfo = this.warrantyService.getWarrantyList(projectRequest, pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    /**
     * 审核质保
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/auditWarranty", method = RequestMethod.POST)
    @ResponseBody
    public void auditWarranty(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	//1通过 2不通过
    	String auditAction = servletRequest.getParameter("auditAction");
    	String projectId = servletRequest.getParameter("projectId");
    	String reason = servletRequest.getParameter("reason");
    	
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(auditAction) || StringUtils.isEmpty(projectId)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("数据不能为空！");
    	}else {
    		Object user = servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
			@SuppressWarnings("rawtypes")
			String userId = ((HashMap)user).get("admin_id") == null ? "" : ((HashMap)user).get("admin_id").toString();
			
    		if(auditAction.equals("1")){
    			
    			ret = warrantyService.auditPassWarranty(projectId,reason,userId);
    		}else{
    			ret = warrantyService.auditNotPassWarranty(projectId,reason,userId);
    		}
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    
}