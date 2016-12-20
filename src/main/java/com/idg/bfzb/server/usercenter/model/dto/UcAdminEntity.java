package com.idg.bfzb.server.usercenter.model.dto;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
@Entity
@Table(name = "uc_admin")
public class UcAdminEntity {
    private String adminId;
    private String adminAccount;
    private String password;
    private Byte status;
    private Integer errorNum;
    private Timestamp createTime;
    private Timestamp lastLoginTime;
    private Timestamp lastOutTime;

    @Id
    @Column(name = "admin_id")
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "admin_account")
    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "error_num")
    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "last_login_time")
    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    @Basic
    @Column(name = "last_out_time")
    public Timestamp getLastOutTime() {
        return lastOutTime;
    }

    public void setLastOutTime(Timestamp lastOutTime) {
        this.lastOutTime = lastOutTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UcAdminEntity that = (UcAdminEntity) o;

        if (adminId != null ? !adminId.equals(that.adminId) : that.adminId != null) return false;
        if (adminAccount != null ? !adminAccount.equals(that.adminAccount) : that.adminAccount != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (errorNum != null ? !errorNum.equals(that.errorNum) : that.errorNum != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(that.lastLoginTime) : that.lastLoginTime != null)
            return false;
        if (lastOutTime != null ? !lastOutTime.equals(that.lastOutTime) : that.lastOutTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adminId != null ? adminId.hashCode() : 0;
        result = 31 * result + (adminAccount != null ? adminAccount.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (errorNum != null ? errorNum.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (lastOutTime != null ? lastOutTime.hashCode() : 0);
        
        return result;
    }
}
