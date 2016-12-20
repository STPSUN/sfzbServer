package com.idg.bfzb.server.controller.admin;

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

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.adminuser.model.vo.LoginUserMsgVo;
import com.idg.bfzb.server.adminuser.service.AdminService;
import com.idg.bfzb.server.common.URLConstants;
import com.idg.bfzb.server.tools.SecurityConstants;
import com.idg.bfzb.server.utility.encrypt.MD5Util;
import com.idg.bfzb.server.utility.servlet.ServletUtil;

@Controller
@RequestMapping("/admin")
public class LoginController {
	@Resource
	private AdminService adminService;
	
	
	
	/**
	 * 默认登录这个首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/",method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		System.out.println("默认首页登录1");
		return "index";
	}
	@RequestMapping(value ="/tologin")
	public String tologin(ModelMap model) {
		System.out.println("默认首页登录2");
		return "login";
	}
	
	
	
	/**
	 * 验证码页面
	 * @return
	 */
	@RequestMapping(value=URLConstants.lOGINCONTROLLER_IMAGE,method = RequestMethod.GET)
	public String intoImageCode(){
		return "common/image";
	}
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value=URLConstants.lOGINCONTROLLER_LOGIN,method = RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response){
	     
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String captcha=request.getParameter("captcha");
		String rand = (String) request.getSession().getAttribute("rand");
		
		Boolean hasCode=request.getSession().getAttribute("hasCode")==null||"".equals(request.getSession().getAttribute("hasCode"))?false:(Boolean) request.getSession().getAttribute("hasCode");
		Map<String,Object> params=new HashMap<String,Object>();
		JSONObject jo=new JSONObject();
			if(hasCode&&!rand.toUpperCase().equals(captcha.toUpperCase())){
				System.out.println(rand+","+captcha);
				jo.put("restcode", "-1");
				jo.put("msg", "验证码错误");
				ServletUtil.createSuccessResponse(200, jo, response);
			}else{
				jo.put("restcode", "1");
				params.put("adminAccount", userName);
				params.put("password", MD5Util.MD5(password).toLowerCase());
				List<LoginUserMsgVo> userList=adminService.queryUserMsg(params);
				if(userList==null || userList.isEmpty()){
					int errorNum=adminService.queryUserErrorNum(userName);
					if(errorNum!=-1){
						errorNum++;
						if(errorNum>=3){
							hasCode=true;
							request.getSession().setAttribute("hasCode", hasCode);
						}
						Map<String,Object> eParams=new HashMap<String, Object>();
						eParams.put("errorNum", errorNum);
						eParams.put("adminAccount", userName);
						Boolean changeIf=adminService.changeErrorNum(eParams);
						jo.put("restcode", "-1");
						jo.put("msg", "密码错误"+errorNum+" 次");
						jo.put("hasCode", hasCode);
					}else{
						jo.put("restcode", "-1");
						jo.put("msg", "账号不准确或已被冻结");
					}
					ServletUtil.createSuccessResponse(200, jo, response);
				}else{
				    HashMap map=new HashMap();
				    map.put("admin_id", userList.get(0).getAdminId());
				    map.put("admin_account", userList.get(0).getAdminAccount());
				    map.put("password", userList.get(0).getPassword());
				    map.put("error_num", userList.get(0).getErrorNum());
				    map.put("create_time", userList.get(0).getCreateTime());
				    map.put("last_login_time", userList.get(0).getLastLoginTime());
				    map.put("last_out_time", userList.get(0).getLastOutTime());
				    map.put("role_name", userList.get(0).getRoleName());
				    map.put("role_id", userList.get(0).getRoleId());
					String roleName=userList.get(0).getRoleName();
					String user_role="0";
					if("系统管理员".equals(roleName)){
						user_role="1";
					}else if("审批员".equals(roleName)){
						user_role="2";
					}else if("配置管理员".equals(roleName)){
						user_role="3";
					}
//					Map<String,Object> eParams=new HashMap<String, Object>();
//					eParams.put("errorNum", 0);
//					eParams.put("adminAccount", userName);
//					Boolean changeIf=adminService.changeErrorNum(eParams);
					adminService.changeLastLoginTime(userList.get(0));
					request.getSession().setAttribute("hasCode", false);
					//添加日志
					//LogData(request,map,"登录操作",1);
					request.getSession().setAttribute(SecurityConstants.LOGIN_USER, map);
					request.getSession().setAttribute(SecurityConstants.LOGIN_USER_ROLE,user_role);
					ServletUtil.createSuccessResponse(200, jo, response);
				}
			}
	}
	
//	/**
//	 * 添加日志
//	 * @param adminUser 管理员对象
//	 * @param logDesc 日志描述
//	 * @param logType 操作内容，1：用户登录，2：功能操作 
//	 */
//	private void LogData(HttpServletRequest request,HashMap map, String logDesc, int logType) {
//		AdminLogEntity ale=new AdminLogEntity();
//		ale.setLogUuid(UUID.randomUUID().toString());
//		ale.setUserUuid(map.get("user_uuid").toString());
//		ale.setLogDesc(logDesc);
//		// 日期时间格式  
//		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Timestamp ts = Timestamp.valueOf(df.format(calendar.getTime()));
//		ale.setLogTime(ts);
//		ale.setLogType((short) logType);
//		ale.setIpAddr(getIpAddr(request));
//		adminLogService.addAdminLogInfo(ale);
//	}
//
//	/**
//	 * 获取IP地址
//	 * @param request 请求
//	 * @return
//	 */
//	public String getIpAddr(HttpServletRequest request) {      
//        String ip = request.getHeader("x-forwarded-for");      
//            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
//                ip = request.getHeader("Proxy-Client-IP");      
//            }     
//            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
//                ip = request.getHeader("WL-Proxy-Client-IP");     
//            }     
//            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
//                ip = request.getRemoteAddr();      
//            }   
//       return ip;     
//    } 
	
	/**
	 * 首页对应的主页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value=URLConstants.lOGINCONTROLLER_MAIN,method = RequestMethod.GET)
	public String mainPage(ModelMap model) {
		return "main";
	}
//	/**
//	 * 首页对应的主页面
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value=URLConstants.lOGINCONTROLLER_QRYMENUDATA,method = RequestMethod.GET)
//	public void qryMenuData(HttpServletRequest request,HttpServletResponse response) {
//		JSONObject jo=new JSONObject();
//		ServletUtil.createSuccessResponse(200, jo, response);
//	}
	@RequestMapping(value=URLConstants.lOGINCONTROLLER_LOGINOUT,method = RequestMethod.GET)
	public void loginOut(HttpServletRequest request,HttpServletResponse response){
	    HttpSession session = request.getSession(false);
	    if (session !=null){
	        HashMap<String,String> user = (HashMap)request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
            String adminUserId = user.get("admin_id");
            this.adminService.updateLastOutTime(adminUserId);
	        
	        session.invalidate();
	        session = null;
	        
	        JSONObject jo=new JSONObject();
	        jo.put("restcode", "1");
			jo.put("msg", "退出成功!");
			ServletUtil.createSuccessResponse(200, jo, response);
	    }else{
//	    	 ServletUtil.createErrorResponse(ErrorCode.LOGOUT_FAILURE, response);
	    }	   
	    System.out.println("登出操作");
	}
}