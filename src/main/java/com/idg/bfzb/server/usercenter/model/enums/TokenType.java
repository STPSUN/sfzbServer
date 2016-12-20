package com.idg.bfzb.server.usercenter.model.enums;

/**
 * 令牌类型
 * @author Administrator
 *
 */
public enum TokenType {

    MAC_TOKEN("m"),
    BEARER_TOKEN("b"),
    REFRESH_TOKEN("r");

    private String identifier;
    // 构造方法
    private TokenType(String identifier) {
        this.setIdentifier(identifier);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public static TokenType parse(String token){
        for(TokenType tokenType : values()){
            if(tokenType.getIdentifier().equals(token)){
                return tokenType;
            }
        }
        return null;
    }
}