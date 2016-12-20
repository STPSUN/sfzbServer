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
@Table(name = "uc_login_token")
public class UcLoginTokenEntity {
    private long id;
    private String userId;
    private String accessToken;
    private String macKey;
    private String macAlgorithm;
    private Timestamp expireTime;
    private Timestamp logoutTime;
    private byte state;
    private String deviceType;
    private String deviceDesc;
    private Timestamp createTime;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false, length = 64)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "access_token", nullable = false, length = 128)
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Basic
    @Column(name = "mac_key", nullable = false, length = 128)
    public String getMacKey() {
        return macKey;
    }

    public void setMacKey(String macKey) {
        this.macKey = macKey;
    }

    @Basic
    @Column(name = "mac_algorithm", nullable = false, length = 128)
    public String getMacAlgorithm() {
        return macAlgorithm;
    }

    public void setMacAlgorithm(String macAlgorithm) {
        this.macAlgorithm = macAlgorithm;
    }

    @Basic
    @Column(name = "expire_time", nullable = false)
    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    @Basic
    @Column(name = "logout_time", nullable = false)
    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    @Basic
    @Column(name = "state", nullable = false)
    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    @Basic
    @Column(name = "device_type", nullable = true, length = 20)
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Basic
    @Column(name = "device_desc", nullable = true, length = 50)
    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UcLoginTokenEntity that = (UcLoginTokenEntity) o;

        if (id != that.id) return false;
        if (state != that.state) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (accessToken != null ? !accessToken.equals(that.accessToken) : that.accessToken != null) return false;
        if (macKey != null ? !macKey.equals(that.macKey) : that.macKey != null) return false;
        if (macAlgorithm != null ? !macAlgorithm.equals(that.macAlgorithm) : that.macAlgorithm != null) return false;
        if (expireTime != null ? !expireTime.equals(that.expireTime) : that.expireTime != null) return false;
        if (logoutTime != null ? !logoutTime.equals(that.logoutTime) : that.logoutTime != null) return false;
        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (deviceDesc != null ? !deviceDesc.equals(that.deviceDesc) : that.deviceDesc != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        result = 31 * result + (macKey != null ? macKey.hashCode() : 0);
        result = 31 * result + (macAlgorithm != null ? macAlgorithm.hashCode() : 0);
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        result = 31 * result + (logoutTime != null ? logoutTime.hashCode() : 0);
        result = 31 * result + (int) state;
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (deviceDesc != null ? deviceDesc.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
