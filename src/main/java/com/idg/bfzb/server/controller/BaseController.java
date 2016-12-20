package com.idg.bfzb.server.controller;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SuppressWarnings("unused")
public class BaseController {
    protected String getCurrentUser(){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication==null)	return null;
        Object session = authentication.getPrincipal();
        if(session==null)	return null;
        return (String) session;
    }

    /**
     * 抛出参数不存在的错误，并且写日志
     *
     * @param paramName 参数名称
     * @param logger    日志对象
     * @return 接口返回对象
     */
    protected APIResponse httpArgumentRequire(String paramName, Logger logger) {
        APIResponse apiResponse = new APIResponse();
        String errlog = String.format("%1$s,需要参数 :%2$s", ErrorCode.REQUIRE_ARGUMENT.getMsg(),paramName);
        apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
        logger.error(errlog);
        return apiResponse;
    }

    /**
     * 抛出参数个数不对的异常
     * @param paramName 参数名
     * @param paramValue 参数值
     * @param logger 日志对象
     * @return APIResponse 异常结果
     */
    protected APIResponse httpArgumentInvalid(String paramName, String paramValue, Logger logger){
        APIResponse apiResponse = new APIResponse();
        String errlog = String.format("%1$s %2$s:%3$s", ErrorCode.INVALID_ARGUMENT.getMsg(),paramName,paramValue);
        apiResponse.setErrorCode(ErrorCode.INVALID_ARGUMENT);
        logger.error(errlog);
        return apiResponse;
    }

}
