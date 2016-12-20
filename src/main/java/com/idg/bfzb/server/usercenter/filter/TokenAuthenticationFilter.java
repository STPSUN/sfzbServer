package com.idg.bfzb.server.usercenter.filter;


import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.tools.I18nProvider;
import com.idg.bfzb.server.usercenter.model.dto.UcLoginTokenEntity;
import com.idg.bfzb.server.usercenter.service.TokenService;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationFilter extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
    @Resource
    private TokenService tokenService;

    public String[] allowUrls;//不需要进过拦截器的接口
    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler) throws Exception {
        String requestUrl = servletRequest.getRequestURI().replace(servletRequest.getContextPath(), "");
        if(null != allowUrls && allowUrls.length >= 1) {
            for (String url : allowUrls) {
                if (requestUrl.contains(url)) {
                    return true;
                }
            }
        }
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        /* 1.获取Authentication头中的token */
        String authorization = servletRequest.getHeader("Authorization");
        if(authorization==null){
            //不存在Authentication的HTTP头字段
            logger.error(I18nProvider.getMessage("usercenter.error.token.require"));
            return false;
        }
        //authorization=(String)servletRequest.getSession().getAttribute(SecurityConstants.LOGIN_USER);
        logger.debug("Authorization:" + authorization);
        String accessToken;
        try{
            /* 2.判断token是否失效，如果没有失效则用这个token去查询用户信息*/
            accessToken = authorization.split("_s=")[1];
           /* String tokenMac = authorization.split(",")[1].split("_c=")[0];
            if (!this.tokenService.checkTokenAndMac(accessToken,tokenMac)){
                return false;
            }*/
        }catch (NullPointerException nullExp){
            APIResponse apiResponse = new APIResponse();
            apiResponse.setErrorCode(ErrorCode.UC_SESSION_EXPIRE);
            logger.error(ErrorCode.UC_SESSION_EXPIRE.getMsg());
            JSONObject jsonObject = JSONObject.fromObject(apiResponse);
            jsonObject.put("msg", jsonObject.get("message"));
            jsonObject.remove("message");
            servletResponse.getWriter().write(jsonObject.toString());
            return false;
        }

        /* 3.用户信息查询好后放在缓存中 */
        UcLoginTokenEntity token=tokenService.getAccessToken(accessToken);
        if (token==null){
            APIResponse apiResponse = new APIResponse();
            apiResponse.setErrorCode(ErrorCode.UC_SESSION_EXPIRE);
            logger.error(ErrorCode.UC_SESSION_EXPIRE.getMsg());
            JSONObject jsonObject = JSONObject.fromObject(apiResponse);
            jsonObject.put("msg", jsonObject.get("message"));
            jsonObject.remove("message");
            servletResponse.getWriter().write(jsonObject.toString());
            return false;
        }
        Authentication successAuthentication = new UsernamePasswordAuthenticationToken(token.getUserId(), token.getUserId());
        SecurityContextHolder.getContext().setAuthentication(successAuthentication);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //super.afterCompletion(request, response, handler, ex);
    }
}
