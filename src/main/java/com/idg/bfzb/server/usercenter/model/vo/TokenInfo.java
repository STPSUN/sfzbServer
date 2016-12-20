package com.idg.bfzb.server.usercenter.model.vo;


import com.idg.bfzb.server.usercenter.model.enums.TokenType;

public class TokenInfo {

    private String userId;
    private Long expireTime;
    private TokenType tokenType;
    private String token;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getExpireTime() {
        return expireTime;
    }
    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
    public TokenType getTokenType() {
        return tokenType;
    }
    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
