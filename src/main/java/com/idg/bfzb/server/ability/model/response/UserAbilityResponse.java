package com.idg.bfzb.server.ability.model.response;

public class UserAbilityResponse {
    private String userId;
    private String userName;
    private String nickName;
    private String mobile;
    private String abilitys;
    private String createTime;
    private String state;
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getAbilitys() {
        return abilitys;
    }
    public void setAbilitys(String abilitys) {
        this.abilitys = abilitys;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
