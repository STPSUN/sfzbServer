package com.idg.bfzb.server.usercenter.model.request;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * .评价管理请求
 * 
 * @author Administrator
 * @version Revision 1.0.0
 *
 */

public class EvaluateManagerRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String targetUserName;
    
    private String qryStartTime;//开始时间
    private String qryEndTime;//结束时间
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getTargetUserName() {
        return targetUserName;
    }
    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
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
