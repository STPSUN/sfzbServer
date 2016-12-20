package com.idg.bfzb.server.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.adminuser.service.AdminService;
import com.idg.bfzb.server.common.URLConstants;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.tools.SecurityConstants;
import com.idg.bfzb.server.usercenter.model.dto.UcAdminEntity;
import com.idg.bfzb.server.utility.encrypt.MD5Util;
import com.idg.bfzb.server.utility.servlet.ServletUtil;
import com.idg.bfzb.server.utility.tools.StringUtil;


@Controller
@RequestMapping(value="/admin/index")
public class IndexController {
	@Resource
	private AdminService adminService;
	
	/**
     * 打开管理员个人信息页面
     * @param model
     * @return
     */
    @RequestMapping(value ="/adminUser",method = RequestMethod.GET)
    public String printAdminWelcome(ModelMap model) {
        return "userManager/adminUser";
    }
    
	/**
	 * 获取左边菜单列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.INDEXCONTROLLER_QUERYMENUS,method = RequestMethod.GET)
	public void queryMenus(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession(false);
		Map userInfo=(Map) session.getAttribute(SecurityConstants.LOGIN_USER);
		String roleId=(String) userInfo.get("role_id");
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("roleId", roleId);
		List menuList=adminService.queryMenus(params);
		if(menuList!=null && !menuList.isEmpty()){
			JSONObject jo=new JSONObject();
			jo.put("restcode", "1");
			jo.put("menuList", menuList);
			ServletUtil.createSuccessResponse(200, jo, response);
		}else{
//	    	 ServletUtil.createErrorResponse(ErrorCode.LOGOUT_FAILURE, response);
		}	   
	}
	
	/**
     * 修改用户密码
     * @param request
     * @param response
     */
    @RequestMapping(value="/AdminPassword",method = RequestMethod.POST)
    @ResponseBody
    public void modPassword(HttpServletRequest request ,HttpServletResponse response){
        
        JSONObject jo=new JSONObject();
        //获取当前登录用户
        HashMap<String,String> user = (HashMap)request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
        String adminId = user.get("admin_id");
        String adminAccount = user.get("admin_account");
        //获取经前台加密过一次的密码
        String oldPassword = request.getParameter("oldPassword");//原密码
        String newPassword = request.getParameter("newPassword");//新密码
        if(StringUtil.isNull(oldPassword)){
            jo.put("message","原密码不能为空!");
            jo.put("success",false);
            
            ServletUtil.createSuccessResponse(200, jo, response);
            return ;
        }
        if(StringUtil.isNull(newPassword)){
            jo.put("message","新密码不能为空!");
            jo.put("success",false);
            ServletUtil.createSuccessResponse(200, jo, response);
            return ;
        }
        //校验旧密码是否正确
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("adminAccount", adminAccount);
        params.put("password", MD5Util.MD5(oldPassword).toLowerCase());
        List userList= new ArrayList();
        try {
            userList=adminService.queryUserMsg(params);
        } catch (Exception e) {
            jo.put("message", "数据库错误！");
            jo.put("success", false);
            ServletUtil.createSuccessResponse(200, jo, response);
            return;
        }
        if(userList==null || userList.isEmpty()){//旧密码错误
                jo.put("message", "输入的旧密码错误");
                jo.put("success", false);
        }else{
            UcAdminEntity entity = new UcAdminEntity();
            entity.setAdminId(adminId);
            entity.setAdminAccount(adminAccount);
            entity.setStatus((byte) 0);
            entity.setErrorNum(0);
            entity.setPassword(MD5Util.MD5(newPassword).toLowerCase());
            //修改操作
            if(adminService.updateAdminUser(entity)){
                jo.put("success", true);
                jo.put("message", "密码修改成功！");
            }else{
                jo.put("success", false);
                jo.put("message", "密码修改失败！");
            }
        }
        ServletUtil.createSuccessResponse(200, jo, response);
    }
	
	/**
	 * 查询个人信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value=URLConstants.INDEXCONTROLLER_QRYSELFMSG,method = RequestMethod.GET)
	@ResponseBody
	public Object qrySelfMsg(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession(false);
		Map userInfo=(Map) session.getAttribute(SecurityConstants.LOGIN_USER);
		String adminId=(String) userInfo.get("admin_id");
		String adminAccount=(String) userInfo.get("admin_account");
		UcAdminEntity entity = new UcAdminEntity();
		entity.setAdminId(adminId);
		entity.setAdminAccount(adminAccount);
		APIResponse apiResponse = new APIResponse();
		apiResponse.setData(entity);
		apiResponse.setMessage(APIResponse.SUCESS_MSG);;
		return apiResponse;   
	}
//	/**
//	 * 查询快捷方式列表
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value=URLConstants.INDEXCONTROLLER_QRYFASTMENULIST,method = RequestMethod.GET)
//	public void qryFastMenuList(HttpServletRequest request,HttpServletResponse response){
//		HttpSession session = request.getSession(false);
//		Map userInfo=(Map) session.getAttribute(SecurityConstants.LOGIN_USER);
////	    String userUuid=(String) userInfo.get("user_uuid");
//		String roleUuid=(String) userInfo.get("role_uuid");
//		Map<String,Object> params=new HashMap<String,Object>();
////	    params.put("userUuid", userUuid);
//		params.put("role_uuid", roleUuid);
//		ResultBeanInfo rbi =indexService.qryFastMenuList(params);
//		JSONObject result=new JSONObject();
//		result.put("fastMenuList",rbi.getObjInfo());
//		result.put("message",rbi.getMsg());
//		result.put("success",rbi.isSuccess());
//		ServletUtil.createSuccessResponse(200, result, response);   
//	}
//	/**
//	 * 查询快捷方式列表
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value=URLConstants.INDEXCONTROLLER_QRYSELFFASTMENU,method = RequestMethod.GET)
//	public void qryselfFastMenu(HttpServletRequest request,HttpServletResponse response){
//	    HttpSession session = request.getSession(false);
//	    Map userInfo=(Map) session.getAttribute(SecurityConstants.LOGIN_USER);
//	    String userUuid=(String) userInfo.get("user_uuid");
//	    String roleUuid=(String) userInfo.get("role_uuid");
//	    Map<String,Object> params=new HashMap<String,Object>();
//	    params.put("user_uuid", userUuid);
//	    params.put("role_uuid", roleUuid);
//	    ResultBeanInfo rbi =indexService.qryselfFastMenu(params);
//	    JSONObject result=new JSONObject();
//	    result.put("selfFastMenuList",rbi.getObjInfo());
//		result.put("message",rbi.getMsg());
//		result.put("success",rbi.isSuccess());
//		ServletUtil.createSuccessResponse(200, result, response);
//	}
//	
//	/**
//	 * 保存快捷方式
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value=URLConstants.INDEXCONTROLLER_ADDFASTMENU,method = RequestMethod.POST)
//	@ResponseBody
//	public void addFastMenu(@PathVariable("menuIds")String menuIds,HttpServletRequest request,HttpServletResponse response){
//		String[] mis = menuIds.split(",");
//		List<AdminFastMenuEntity> adminFastMenuList=new ArrayList<AdminFastMenuEntity>();
//		HttpSession session = request.getSession(false);
//		Map userInfo=(Map) session.getAttribute(SecurityConstants.LOGIN_USER);
//		String userUuid=(String) userInfo.get("user_uuid");
//		for (int i = 0; i < mis.length; i++) {
//			AdminFastMenuEntity afme=new AdminFastMenuEntity();
//			afme.setMenuId(Short.valueOf(mis[i]));
//			afme.setUserUuid(userUuid);
//			afme.setCreateTime(new Date());
//			afme.setState((byte) 0);
//			adminFastMenuList.add(afme);
//		}
////		pfe.setCreateTime(new Date());
////		pfe.setState((byte) 0);
//		Map<String,String> params=new HashMap<String,String>();
//		params.put("userUuid", userUuid);
//		ResultBeanInfo rbi = indexService.deleteFastMenuByUserUUid(params);
//		if(rbi.isSuccess()){
//			rbi = indexService.addFastMenu(adminFastMenuList);
//			JSONObject result=new JSONObject();
//			result.put("message",rbi.getMsg());
//			result.put("success",rbi.isSuccess());
//			ServletUtil.createSuccessResponse(200, result, response);
//		}else{
//			JSONObject result=new JSONObject();
//			result.put("message",rbi.getMsg());
//			result.put("success",rbi.isSuccess());
//			ServletUtil.createSuccessResponse(200, result, response);
//		}
//		
//	}
//	
//	
//	/**
//	 * 删除个人快捷方式
//	 */
//	@RequestMapping(value=URLConstants.INDEXCONTROLLER_DELETEFASTMENU,method = RequestMethod.POST)
//	@ResponseBody
//	public void deleteFastMenu(@PathVariable("menuId")String menuId,HttpServletRequest request,HttpServletResponse response){
//		HttpSession session = request.getSession(false);
//	    Map userInfo=(Map) session.getAttribute(SecurityConstants.LOGIN_USER);
//	    String userUuid=(String) userInfo.get("user_uuid");
//	    Map<String,String> params=new HashMap<String, String>();
//	    params.put("menuId", menuId);
//	    params.put("userUuid", userUuid);
//		ResultBeanInfo rbi = indexService.deleteFastMenu(params);
//		JSONObject result=new JSONObject();
//		result.put("message",rbi.getMsg());
//		result.put("success",rbi.isSuccess());
//		ServletUtil.createSuccessResponse(200, result, response);
//	}
}