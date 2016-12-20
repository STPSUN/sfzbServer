package com.idg.bfzb.server.usercenter.model;

public class TokenConstant {
    //Mac Key长度
    public static final int MAC_KEY_SUM = 10;
    // hmac-sha-256加密算法
    public static final String MAC_ALGORITHM_HMAC_SHA_256 = "hmac-sha-256";
    //令牌分割符
    public static final String TOKEN_SEPARATOR = "_";
    //令牌对应的user_id
    public static final String TOKEN_USER_ID = "token_user_id";
    //令牌对应的用户org_id
    public static final String TOKEN_USER_ORG_ID = "token_user_org_id";

    //token在redis中存放时间,单位天
    public final static int TOKEN_EXPIRE_DAYS = 180;
    public static final int ACCESS_TOKEN_EXPIRE_TIME = TOKEN_EXPIRE_DAYS*24*3600;  //ACCESS Token过期时间（秒）
    public final static String TOKEN_MAC_SALT = "qFkgnEyPlw";
}
