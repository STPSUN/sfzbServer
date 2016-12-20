package com.idg.bfzb.server.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.adminproject.model.RejectionAdminRequest;
import com.idg.bfzb.server.adminproject.model.RejectionAdminResponse;
import com.idg.bfzb.server.adminproject.service.RejectionAdminService;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.utility.servlet.ServletUtil;
/**
 * 拒收管理操作相关action请求
 * @author chen
 *
 */
@Controller
@RequestMapping(value="/admin/rejectionManager/action")
public class RejectionManagerActionController {
	@Autowired
	private RejectionAdminService rejectionService; 
	/**
     * 获取兜底管理列表
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getRejectionList(RejectionAdminRequest projectRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
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
    	PageInfo<RejectionAdminResponse> pageInfo = this.rejectionService.getRejectionList(projectRequest, pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    /**
     * 审核拒收
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/auditRejection", method = RequestMethod.POST)
    @ResponseBody
    public void auditRejection(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
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
    		
    		if(auditAction.equals("1")){
    			
    			ret = rejectionService.auditPassRejection(projectId,reason);
    		}else{
    			ret = rejectionService.auditNotPassRejection(projectId,reason);
    		}
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 获取附件地址
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/getAttachsPath", method = RequestMethod.POST)
    @ResponseBody
    public void getAttachsPath(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	//1通过 2不通过
    	String[] attachIds = servletRequest.getParameterValues("attachIds");
    	
    	APIResponse ret = null;
    	if(attachIds == null){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("数据不能为空！");
    	}else {
    		
			ret = rejectionService.getAttachsPath(attachIds);
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
}