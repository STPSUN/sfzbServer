package com.idg.bfzb.server.adminuser.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.idg.bfzb.server.tools.SecurityConstants;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除  
    
    public void setAllowUrls(String[] allowUrls) {  
        this.allowUrls = allowUrls;  
    }  
    
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		boolean flag=false;
	    String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
	    if(null != allowUrls && allowUrls.length >= 1)  
            for(String url : allowUrls) {
                if(requestUrl.contains(url)) {    
                	  flag = true;
                      break; 
                }    
        }
	    if (!flag) {
			Object obj = request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
			if (null == obj) { // 未登录
				if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ //如果是ajax请求响应头会有，x-requested-with  
					response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
		            flag=false;
		        }else{
		        	response.sendRedirect("tologin");
		        	flag=false;
		        }
			}else{
				//如果有session这么这里就是要判断当前角色可以访问的action的页面  页面菜单配置在session里面
				flag=true;
			}
	    }
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
	
	
}