package com.idg.bfzb.server.usercenter.model.response;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * .消息管理返回
 * 
 * @author Administrator
 * @version Revision 1.0.0
 *
 */
public class MessageManagerResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long messgeId;
    private String content;
    private String channel;
    private Timestamp createTime;
    
    private String userId;
    private String userName;
    private String nickName;
    private Short isRead;
    private Timestamp readTime; 
    
    public Long getMessgeId() {
        return this.messgeId;
    }
    public void setMessgeId(Long messgeId) {
        this.messgeId = messgeId;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getChannel() {
        return this.channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public Timestamp getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
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
    public Short getIsRead() {
        return this.isRead;
    }
    public void setIsRead(Short isRead) {
        this.isRead = isRead;
    }
    public Timestamp getReadTime() {
        return this.readTime;
    }
    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }
}
