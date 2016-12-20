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
import com.idg.bfzb.server.adminproject.model.FallbackAdminRequest;
import com.idg.bfzb.server.adminproject.model.FallbackAdminResponse;
import com.idg.bfzb.server.adminproject.service.FallbackAdminService;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.utility.servlet.ServletUtil;
/**
 * 兜底服务操作相关action请求
 * @author chen
 *
 */
@Controller
@RequestMapping(value="/admin/fallbackManager/action")
public class FallbackManagerActionController {
	@Autowired
	private FallbackAdminService fallbackService; 
	/**
     * 获取兜底管理列表
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getProjectList(FallbackAdminRequest projectRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
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
    	PageInfo<FallbackAdminResponse> pageInfo = this.fallbackService.getProjectList(projectRequest, pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    /**
     * 审核项目
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/auditFallback", method = RequestMethod.POST)
    @ResponseBody
    public void auditFallback(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	//1通过 2不通过
    	String auditAction = servletRequest.getParameter("auditAction");
    	String prjectUuid = servletRequest.getParameter("projectId");
    	
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(auditAction) || StringUtils.isEmpty(prjectUuid)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("数据不能为空！");
    	}else {
    		
    		if(auditAction.equals("1")){
    			
    			ret = fallbackService.auditPassFallBack(prjectUuid);
    		}else{
    			ret = fallbackService.auditNotPassFallBack(prjectUuid);
    		}
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 启动兜底服务
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/startFallback", method = RequestMethod.POST)
    @ResponseBody
    public void startFallback(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	String contact = servletRequest.getParameter("contact");
    	String projectId = servletRequest.getParameter("projectId");
    	
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(projectId) || StringUtils.isEmpty(contact)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("数据不能为空！");
    	}else {
    		ret = fallbackService.startFallback(projectId,contact);
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    
}