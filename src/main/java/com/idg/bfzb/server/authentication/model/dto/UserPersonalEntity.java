package com.idg.bfzb.server.authentication.model.dto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
@Entity
@Table(name = "t_user_personal")
public class UserPersonalEntity {
    private String userId;
    private String idcardCode;
    private BigDecimal balance;
    private BigDecimal incoming;
    private BigDecimal expenditure;
    private String alipayCode;
    private String bankCardCode;
    private String wechatCode;
    private String idcardPhoto1Id;
    private String idcardPhoto2Id;
    private String idcardPhoto3Id;
    private String lastProvinceCode;
    private String lastCityCode;
    private String lastAreaCode;
    private String lastRole;
    private Byte isTeamUser;
    private Byte isEnterpriseUser;
    private Timestamp updateTime;
    private Byte reviewState;
    private String reviewAdminId;
    private Timestamp reviewTime;
    private String lastRegionCode;

    @Id
    @Column(name = "user_id", nullable = false, length = 64)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "idcard_code", nullable = true, length = 64)
    public String getIdcardCode() {
        return idcardCode;
    }

    public void setIdcardCode(String idcardCode) {
        this.idcardCode = idcardCode;
    }

    @Basic
    @Column(name = "balance", nullable = true, precision = 2)
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "incoming", nullable = true, precision = 2)
    public BigDecimal getIncoming() {
        return incoming;
    }

    public void setIncoming(BigDecimal incoming) {
        this.incoming = incoming;
    }

    @Basic
    @Column(name = "expenditure", nullable = true, precision = 2)
    public BigDecimal getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }

    @Basic
    @Column(name = "alipay_code", nullable = true, length = 64)
    public String getAlipayCode() {
        return alipayCode;
    }

    public void setAlipayCode(String alipayCode) {
        this.alipayCode = alipayCode;
    }

    @Basic
    @Column(name = "bank_card_code", nullable = true, length = 128)
    public String getBankCardCode() {
        return bankCardCode;
    }

    public void setBankCardCode(String bankCardCode) {
        this.bankCardCode = bankCardCode;
    }

    @Basic
    @Column(name = "wechat_code", nullable = true, length = 128)
    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    @Basic
    @Column(name = "idcard_photo1_id", nullable = true, length = 64)
    public String getIdcardPhoto1Id() {
        return idcardPhoto1Id;
    }

    public void setIdcardPhoto1Id(String idcardPhoto1Id) {
        this.idcardPhoto1Id = idcardPhoto1Id;
    }

    @Basic
    @Column(name = "idcard_photo2_id", nullable = true, length = 64)
    public String getIdcardPhoto2Id() {
        return idcardPhoto2Id;
    }

    public void setIdcardPhoto2Id(String idcardPhoto2Id) {
        this.idcardPhoto2Id = idcardPhoto2Id;
    }

    @Basic
    @Column(name = "idcard_photo3_id", nullable = true, length = 64)
    public String getIdcardPhoto3Id() {
        return idcardPhoto3Id;
    }

    public void setIdcardPhoto3Id(String idcardPhoto3Id) {
        this.idcardPhoto3Id = idcardPhoto3Id;
    }

    @Basic
    @Column(name = "last_province_code", nullable = true, length = 16)
    public String getLastProvinceCode() {
        return lastProvinceCode;
    }

    public void setLastProvinceCode(String lastProvinceCode) {
        this.lastProvinceCode = lastProvinceCode;
    }

    @Basic
    @Column(name = "last_city_code", nullable = true, length = 16)
    public String getLastCityCode() {
        return lastCityCode;
    }

    public void setLastCityCode(String lastCityCode) {
        this.lastCityCode = lastCityCode;
    }

    @Basic
    @Column(name = "last_area_code", nullable = true, length = 16)
    public String getLastAreaCode() {
        return lastAreaCode;
    }

    public void setLastAreaCode(String lastAreaCode) {
        this.lastAreaCode = lastAreaCode;
    }

    @Basic
    @Column(name = "last_role", nullable = true, length = 16)
    public String getLastRole() {
        return lastRole;
    }

    public void setLastRole(String lastRole) {
        this.lastRole = lastRole;
    }

    @Basic
    @Column(name = "is_team_user", nullable = true)
    public Byte getIsTeamUser() {
        return isTeamUser;
    }

    public void setIsTeamUser(Byte isTeamUser) {
        this.isTeamUser = isTeamUser;
    }

    @Basic
    @Column(name = "is_enterprise_user", nullable = true)
    public Byte getIsEnterpriseUser() {
        return isEnterpriseUser;
    }

    public void setIsEnterpriseUser(Byte isEnterpriseUser) {
        this.isEnterpriseUser = isEnterpriseUser;
    }

    @Basic
    @Column(name = "update_time", nullable = false)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "review_state", nullable = true)
    public Byte getReviewState() {
        return reviewState;
    }

    public void setReviewState(Byte reviewState) {
        this.reviewState = reviewState;
    }

    @Basic
    @Column(name = "review_admin_id", nullable = true, length = 64)
    public String getReviewAdminId() {
        return reviewAdminId;
    }

    public void setReviewAdminId(String reviewAdminId) {
        this.reviewAdminId = reviewAdminId;
    }

    @Basic
    @Column(name = "review_time", nullable = false)
    public Timestamp getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Timestamp reviewTime) {
        this.reviewTime = reviewTime;
    }

    @Basic
    @Column(name = "last_region_code", nullable = true, length = 16)
    public String getLastRegionCode() {
        return lastRegionCode;
    }

    public void setLastRegionCode(String lastRegionCode) {
        this.lastRegionCode = lastRegionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPersonalEntity that = (UserPersonalEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (idcardCode != null ? !idcardCode.equals(that.idcardCode) : that.idcardCode != null) return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        if (incoming != null ? !incoming.equals(that.incoming) : that.incoming != null) return false;
        if (expenditure != null ? !expenditure.equals(that.expenditure) : that.expenditure != null) return false;
        if (alipayCode != null ? !alipayCode.equals(that.alipayCode) : that.alipayCode != null) return false;
        if (bankCardCode != null ? !bankCardCode.equals(that.bankCardCode) : that.bankCardCode != null) return false;
        if (wechatCode != null ? !wechatCode.equals(that.wechatCode) : that.wechatCode != null) return false;
        if (idcardPhoto1Id != null ? !idcardPhoto1Id.equals(that.idcardPhoto1Id) : that.idcardPhoto1Id != null)
            return false;
        if (idcardPhoto2Id != null ? !idcardPhoto2Id.equals(that.idcardPhoto2Id) : that.idcardPhoto2Id != null)
            return false;
        if (idcardPhoto3Id != null ? !idcardPhoto3Id.equals(that.idcardPhoto3Id) : that.idcardPhoto3Id != null)
            return false;
        if (lastProvinceCode != null ? !lastProvinceCode.equals(that.lastProvinceCode) : that.lastProvinceCode != null)
            return false;
        if (lastCityCode != null ? !lastCityCode.equals(that.lastCityCode) : that.lastCityCode != null) return false;
        if (lastAreaCode != null ? !lastAreaCode.equals(that.lastAreaCode) : that.lastAreaCode != null) return false;
        if (lastRole != null ? !lastRole.equals(that.lastRole) : that.lastRole != null) return false;
        if (isTeamUser != null ? !isTeamUser.equals(that.isTeamUser) : that.isTeamUser != null) return false;
        if (isEnterpriseUser != null ? !isEnterpriseUser.equals(that.isEnterpriseUser) : that.isEnterpriseUser != null)
            return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (reviewState != null ? !reviewState.equals(that.reviewState) : that.reviewState != null) return false;
        if (reviewAdminId != null ? !reviewAdminId.equals(that.reviewAdminId) : that.reviewAdminId != null)
            return false;
        if (reviewTime != null ? !reviewTime.equals(that.reviewTime) : that.reviewTime != null) return false;
        if (lastRegionCode != null ? !lastRegionCode.equals(that.lastRegionCode) : that.lastRegionCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (idcardCode != null ? idcardCode.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (incoming != null ? incoming.hashCode() : 0);
        result = 31 * result + (expenditure != null ? expenditure.hashCode() : 0);
        result = 31 * result + (alipayCode != null ? alipayCode.hashCode() : 0);
        result = 31 * result + (bankCardCode != null ? bankCardCode.hashCode() : 0);
        result = 31 * result + (wechatCode != null ? wechatCode.hashCode() : 0);
        result = 31 * result + (idcardPhoto1Id != null ? idcardPhoto1Id.hashCode() : 0);
        result = 31 * result + (idcardPhoto2Id != null ? idcardPhoto2Id.hashCode() : 0);
        result = 31 * result + (idcardPhoto3Id != null ? idcardPhoto3Id.hashCode() : 0);
        result = 31 * result + (lastProvinceCode != null ? lastProvinceCode.hashCode() : 0);
        result = 31 * result + (lastCityCode != null ? lastCityCode.hashCode() : 0);
        result = 31 * result + (lastAreaCode != null ? lastAreaCode.hashCode() : 0);
        result = 31 * result + (lastRole != null ? lastRole.hashCode() : 0);
        result = 31 * result + (isTeamUser != null ? isTeamUser.hashCode() : 0);
        result = 31 * result + (isEnterpriseUser != null ? isEnterpriseUser.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (reviewState != null ? reviewState.hashCode() : 0);
        result = 31 * result + (reviewAdminId != null ? reviewAdminId.hashCode() : 0);
        result = 31 * result + (reviewTime != null ? reviewTime.hashCode() : 0);
        result = 31 * result + (lastRegionCode != null ? lastRegionCode.hashCode() : 0);
        return result;
    }
}
