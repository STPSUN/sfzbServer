package com.idg.bfzb.server.usercenter.service.impl;

import com.idg.bfzb.server.common.RedisKey;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.ResultBeanInfo;
import com.idg.bfzb.server.usercenter.model.TokenConstant;
import com.idg.bfzb.server.usercenter.model.dto.UcLoginTokenEntity;
import com.idg.bfzb.server.usercenter.model.enums.TokenFlag;
import com.idg.bfzb.server.usercenter.model.vo.TokenInfo;
import com.idg.bfzb.server.tools.I18nProvider;
import com.idg.bfzb.server.usercenter.dao.UcTokenRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.usercenter.model.enums.TokenType;
import com.idg.bfzb.server.usercenter.service.TokenService;
import com.idg.bfzb.server.usercenter.service.TokenUtil;
import com.idg.bfzb.server.utility.cache.redis.ValueCacheService;
import com.idg.bfzb.server.utility.tools.CalculateUtil;
import com.idg.bfzb.server.utility.tools.JacksonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImp implements TokenService {
    private final Logger logger = Logger.getLogger(TokenServiceImp.class);

    @Resource
    ValueCacheService<String, String> valueCacheService;
    @Autowired
    private UcTokenRepository tokenRepository;

    /**
     * 获取LoginToken,可以是Access Token或者Bearer Token
     *
     * @param strAccessToken 登陆的token
     * @return 从token中获取到登陆信息
     */
    @Override
    public UcLoginTokenEntity getAccessToken(String strAccessToken) {
        if (StringUtils.isBlank(strAccessToken)) {
            return null;
        }

        TokenInfo tokenInfo = TokenUtil.getTokenInfo(strAccessToken);
        if (tokenInfo==null){
            logger.error(I18nProvider.getMessage("common.error","getAccessToken",""));
            return null;
        }
        UcLoginTokenEntity cacheTokenEntity = null;
        try {
            String tokenJsonString = valueCacheService.get(RedisKey.LOGIN_TOKEN_KEY + tokenInfo.getUserId());
            if (StringUtils.isNotBlank(tokenJsonString)) {
                cacheTokenEntity = JacksonUtils.parseToObject(tokenJsonString,UcLoginTokenEntity.class);
            }else{
                /* 缓存里面不存在这样的token,说明token过期,不能使用 */
                logger.warn(I18nProvider.getMessage("usercenter.warn.token.notexist",tokenInfo.getUserId(),strAccessToken));
                return null;
            }
        } catch (Exception e) {
            logger.error("getAccessToken valueCacheService.get strAccessToken:" + strAccessToken, e);
        }

        if (cacheTokenEntity==null || !StringUtils.equals(cacheTokenEntity.getAccessToken(), strAccessToken)){
            logger.warn(I18nProvider.getMessage("usercenter.warn.token.notexist",tokenInfo.getUserId(),strAccessToken));
            return null;
        }
        /* 重新计算缓存剩余的有效时间 */
        try {
            Long ttl = cacheTokenEntity.getExpireTime().getTime() - System.currentTimeMillis();
            putLoginTokenToRedis(cacheTokenEntity, ttl);
        } catch (Exception e) {
            logger.error("Redis connection error：", e);
        }
        return cacheTokenEntity;
    }

    /**
     * 将登录令牌放入缓存
     *
     * @param cacheLoginToken 要缓存的登陆信息对象
     * @param ttl    缓存过期时间
     */
    private void putLoginTokenToRedis(UcLoginTokenEntity cacheLoginToken, Long ttl) {
        try {
            if (null == ttl) {
                valueCacheService.set(RedisKey.LOGIN_TOKEN_KEY + cacheLoginToken.getUserId(),
                        JacksonUtils.toJsonString(cacheLoginToken), TokenConstant.TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
            }else{
                valueCacheService.set(RedisKey.LOGIN_TOKEN_KEY + cacheLoginToken.getUserId(),
                        JacksonUtils.toJsonString(cacheLoginToken), ttl / 1000, TimeUnit.SECONDS);
            }
            logger.debug("insert redis token:" + cacheLoginToken.getAccessToken() + ", ok");
        } catch (Exception e) {
            logger.error("putLoginTokenToRedis loginToken:" + cacheLoginToken.getAccessToken(), e);
        }
    }

    @Override
    public boolean checkTokenAndMac(String accessToken, String tokenCheckSum) {
//        String newCheckSum = EncryptUtil.encryptStdMD5(accessToken);
//        logger.debug(String.format("accessToken:[%1$s],check_sum:[%2$s],new_check_sum:[%3$s]",
//                accessToken, tokenCheckSum, newCheckSum));
//        if (!newCheckSum.equalsIgnoreCase(tokenCheckSum)) {
//            logger.info(I18nProvider.getMessage("usercenter.error.tokenmac.wrong"));
//            logger.info(String.format("accessToken:[%1$s],check_sum:[%2$s],new_check_sum:[%3$s]",
//                    accessToken, tokenCheckSum, newCheckSum));
//            return false;
//        }
        return true;
    }


    @Override
    public APIResponse logout(String userId) {
        APIResponse responseState = new APIResponse();
        // todo : 完善流程
        //清除旧token
//        LoginTokenEntity loginToken = getAccessToken(strAccessToken);
//        if (null != loginToken) {
//            /*tokenDao.deleteLoginToken(strAccessToken);
//            tokenDao.deleteRefreshToken(loginToken.getRefreshToken());
//            tokenDao.deleteLoginRefreshTokenRelaton(loginToken.getAccessToken());
//            responseState.setObject(loginToken);
//            if(log.isDebugEnabled()){
//                log.debug("cassandra logout token:"+strAccessToken+", ok");
//            }*/
//        }

        //Reids缓存
        valueCacheService.delete(RedisKey.LOGIN_TOKEN_KEY + userId);
//        if (logger.isDebugEnabled()) {
//            logger.debug("redis logout token:" + strAccessToken + ", ok");
//        }

        return responseState;
    }

    @Override
    public ResultBeanInfo checkLoginToken(String accessToken, TokenType tokenType) {
        return new ResultBeanInfo();
    }

    @Override
    public boolean isTokenExpire(String token) {
        boolean flag = false;
        TokenInfo tokenInfo = TokenUtil.getTokenInfo(token);
        if (null != tokenInfo) {
            if (System.currentTimeMillis() > tokenInfo.getExpireTime()) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public ResultBeanInfo<String> addToken(UcUserInfoEntity user, TokenFlag tokenFlag) {
        return insertToken2DBAndRedis(user, tokenFlag.getType(), null, null);
    }

    /**
     * 插入Login和Refresh令牌
     *
     * @param userEntity 用户实体
     * @param flag,      0：普通用户登录，1：接入应用登录
     * @param deviceType 设备类型
     * @param deviceDesc 设备型号
     * @return 登录令牌插入结果
     */
    private ResultBeanInfo<String> insertToken2DBAndRedis(UcUserInfoEntity userEntity, int flag, String deviceType, String deviceDesc) {
        ResultBeanInfo<String> resultBean = new ResultBeanInfo<String>();
        resultBean.setSuccess(true);
        Timestamp expireDateForLogin;
        String strLoginToken;
        String strMacKey = CalculateUtil.generateMixRandomCode(TokenConstant.MAC_KEY_SUM);

        long expireTimeL = TokenConstant.ACCESS_TOKEN_EXPIRE_TIME;
        expireDateForLogin = new Timestamp(System.currentTimeMillis() + expireTimeL * 1000);

        strLoginToken = TokenUtil.createMacToken(userEntity.getUserId(), expireDateForLogin.getTime());
        UcLoginTokenEntity loginTokenEntity = new UcLoginTokenEntity();
        loginTokenEntity.setMacKey(strMacKey);
        loginTokenEntity.setMacAlgorithm(TokenConstant.MAC_ALGORITHM_HMAC_SHA_256);
        loginTokenEntity.setUserId(userEntity.getUserId());
        loginTokenEntity.setAccessToken(strLoginToken);
        loginTokenEntity.setMacKey(strMacKey);
        loginTokenEntity.setDeviceDesc(deviceDesc);
        loginTokenEntity.setDeviceType(deviceType);
        loginTokenEntity.setExpireTime(expireDateForLogin);
        loginTokenEntity.setState((byte)0);
        try {
            this.tokenRepository.save(loginTokenEntity);
            logger.debug("tokenDao insert mac token:" + strLoginToken + ", ok");
        } catch (Exception e) {
            resultBean.setSuccess(false);
            logger.error("tokenDao.insertAccessToken token:" + strLoginToken, e);
            return resultBean;
        }
        putLoginTokenToRedis(loginTokenEntity, null);
        resultBean.setObject(strLoginToken);
        return resultBean;
    }
}
