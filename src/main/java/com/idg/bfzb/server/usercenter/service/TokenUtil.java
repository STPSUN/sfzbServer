package com.idg.bfzb.server.usercenter.service;


import com.idg.bfzb.server.tools.I18nProvider;
import com.idg.bfzb.server.usercenter.model.TokenConstant;
import com.idg.bfzb.server.usercenter.model.vo.TokenInfo;
import com.idg.bfzb.server.utility.encrypt.DESUtil;
import com.idg.bfzb.server.utility.tools.CalculateUtil;
import com.idg.bfzb.server.usercenter.model.enums.TokenType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenUtil {
    private final static Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    /**
     * 生成Mac Token
     * @param userId 用户id
     * @param expireTime 过期时间,单位秒
     * @return 生成的mactoken字符串
     */
    public static String createMacToken(String userId, Long expireTime){
        return createToken(userId, expireTime, TokenType.MAC_TOKEN);
    }

    /**
     * 生成Token
     * @param userId 用户id
     * @param expireTime  过期时间,单位秒
     * @return 生成的token字符串
     */
    public static String createToken(String userId, Long expireTime, TokenType tokenType){
        String token="";

        StringBuilder sb = new StringBuilder();
        sb.append(userId);
        sb.append(TokenConstant.TOKEN_SEPARATOR);
        sb.append(expireTime);
        sb.append(TokenConstant.TOKEN_SEPARATOR);
        sb.append(tokenType.getIdentifier());
        sb.append(TokenConstant.TOKEN_SEPARATOR);
        sb.append(CalculateUtil.generateDigitRandomCode(5));

        try {
            token = DESUtil.encrypt(sb.toString());
        } catch (Exception e) {
            logger.error(I18nProvider.getMessage("common.error","createToken",e.getMessage()),e);
        }
        return token;
    }

    /**
     * 获取Token信息
     * @param token 加密后的token字符串
     * @return token数据
     */
    public static TokenInfo getTokenInfo(String token){
        TokenInfo tokenInfo = null;
        try {
            if(StringUtils.isNotBlank(token)){
                String decode = DESUtil.decrypt(token);
                Pattern p = Pattern.compile("([0-9A-Za-z]{8}-[0-9A-Za-z]{4}-[0-9A-Za-z]{4}-[0-9A-Za-z]{4}-[0-9A-Za-z]{12})_([0-9]*)_([mbr])_([0-9]*)");
                Matcher m = p.matcher(decode);
                if(m.find()){
                    tokenInfo = new TokenInfo();
                    tokenInfo.setUserId(m.group(1));
                    tokenInfo.setExpireTime(Long.valueOf(m.group(2)));
                    tokenInfo.setTokenType(TokenType.parse(m.group(3)));
                    tokenInfo.setToken(token);
                }
            }
        } catch (Exception e) {
            logger.error("getTokenInfo token:" + token, e);
        }
        return tokenInfo;
    }
}
