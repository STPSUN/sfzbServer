package com.idg.bfzb.server.adminuser.model.vo;

import java.sql.Timestamp;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 创建日期：2016/10/26
 */
public class LoginUserMsgVo {
    private String adminId;
    private String adminAccount;
    private String password;
    private Byte status;
    private Integer errorNum;
    private Timestamp createTime;
    private Timestamp lastLoginTime;
    private Timestamp lastOutTime;
    private String roleName;
    private String roleId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public Timestamp getLastOutTime() {
        return lastOutTime;
    }

    public void setLastOutTime(Timestamp lastOutTime) {
        this.lastOutTime = lastOutTime;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
