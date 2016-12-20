package com.idg.bfzb.server.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.adminproject.model.ProjectAdminRequest;
import com.idg.bfzb.server.adminproject.model.ProjectAdminResponse;
import com.idg.bfzb.server.adminproject.service.AdminProjectService;
import com.idg.bfzb.server.common.URLConstants;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.utility.servlet.ServletUtil;
/**
 * 项目管理页面跳转Action
 * @author chen
 *
 */
@Controller
@RequestMapping(value="/admin/projectManager")
public class ProjectManagerController {
	@Autowired
	private AdminProjectService projectService;
	/**
	 * 项目管理跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.CONTENT_PROJECT_MANAGER,method = RequestMethod.GET)
	public String projectManager(ModelMap model) {
		return "projectManager/projectManager";
	}
	/**
	 * 质保管理跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.CONTENT_WARRANTY_MANAGER,method = RequestMethod.GET)
	public String warrantyManager(ModelMap model) {
		return "projectManager/warrantyManager";
	}
	/**
	 * 拒收管理跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.CONTENT_REJECTION_MANAGER,method = RequestMethod.GET)
	public String rejectionManager(ModelMap model) {
		return "projectManager/rejectionManager";
	}
	/**
	 * 投标收管理跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.CONTENT_TENRDER_MANAGER,method = RequestMethod.GET)
	public String tenderManager(ModelMap model) {
		return "projectManager/tenderManager";
	}
	/**
	 * 投标收管理跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.CONTENT_AFTERSALE_MANAGER,method = RequestMethod.GET)
	public String afterSaleManager(ModelMap model) {
		return "projectManager/afterSaleManager";
	}
	/**
	 * 兜底管理跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.CONTENT_FALLBACK_MANAGER,method = RequestMethod.GET)
	public String fallbackManager(ModelMap model) {
		return "projectManager/fallbackManager";
	}
    /**
     * 获取项目管理列表
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getProjectList(ProjectAdminRequest projectRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
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
    	PageInfo<ProjectAdminResponse> pageInfo = this.projectService.getProjectList(projectRequest,pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }

}