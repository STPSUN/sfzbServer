package com.idg.bfzb.server.usercenter.service;


import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.usercenter.model.dto.UcLoginTokenEntity;
import com.idg.bfzb.server.usercenter.model.enums.TokenFlag;
import com.idg.bfzb.server.usercenter.model.enums.TokenType;
import com.idg.bfzb.server.common.model.ResultBeanInfo;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;

public interface TokenService {
    /**
     * 登录后添加Token
     *
     * @param userEntity 当前登录用户对象
     * @param tokenFlag, 0：普通用户登录，1：接入应用登录
     * @return 添加token到redis和数据库结果
     */
    ResultBeanInfo<String> addToken(UcUserInfoEntity userEntity, TokenFlag tokenFlag);
    /**
     * 检验token 和 mac签名是否一致
     * @param accessToken token值
     * @param tokenCheckSum mac签名
     * @return 校验结果
     */
    boolean checkTokenAndMac(String accessToken, String tokenCheckSum);

    /**
     * 获取LoginToken,可以是Access Token或者Bearer Token
     *
     * @param strLoginToken 登录token字符串
     * @return 登录的token 实体
     */
    UcLoginTokenEntity getAccessToken(String strLoginToken);

    /**
     * 退出
     * @param userId 当前登录用户id
     * @return 登出处理结果
     */
    APIResponse logout(String userId);

    /**
     * 检查登陆Token有效性
     *
     * @param strLoginToken 登录token字符串
     * @return 失败时ResponseState.setObj包含ErrorCode，成功时ResponseState.setObj包含LoginToken对象
     */
    ResultBeanInfo checkLoginToken(String strLoginToken, TokenType tokenType);


    /**
     * 通过token字符串判断token是否过期
     *
     * @param token 登录token字符串
     * @return true过期；false未过期
     */
    boolean isTokenExpire(String token);
}

