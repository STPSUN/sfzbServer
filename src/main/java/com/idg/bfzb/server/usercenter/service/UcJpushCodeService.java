package com.idg.bfzb.server.usercenter.service;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.usercenter.model.request.JpushRequest;

/**
 * 类名称：UcJpushCodeService
 * 类描述：用户极光业务处理类
 * 创建人：ouzhb
 * 创建日期：2016/12/8
 */
public interface UcJpushCodeService {
    /**
     * 用户注册极光ID
     * @param userId	用户ID
     * @param jpushRequest 请求对象
     * @return	APIResponse
     */
    APIResponse addJpushCode(String userId, JpushRequest jpushRequest);
}

