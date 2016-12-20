package com.idg.bfzb.server.usercenter.model.request;

import java.io.Serializable;

/**
 * 
 * .消息管理请求
 * 
 * @author Administrator
 * @version Revision 1.0.0
 *
 */
public class MessageManagerRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userName;
    private String nickName;
    
    private String qryStartTime;//开始时间
    private String qryEndTime;//结束时间
    
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
    public String getQryStartTime() {
        return qryStartTime;
    }
    public void setQryStartTime(String qryStartTime) {
        this.qryStartTime = qryStartTime;
    }
    public String getQryEndTime() {
        return qryEndTime;
    }
    public void setQryEndTime(String qryEndTime) {
        this.qryEndTime = qryEndTime;
    }
}
