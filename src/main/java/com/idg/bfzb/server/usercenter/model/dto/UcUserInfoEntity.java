package com.idg.bfzb.server.usercenter.model.dto;
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * UcUserInfoEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="uc_user_info")
public class UcUserInfoEntity  implements java.io.Serializable {


    // Fields    
     private String userId;
     private String orgId;
     private String realm;
     private String userName;
     private String password;
     private String realName;
     private String realFullSpell;
     private String realShortSpell;
     private String nickName;
     private String nickFullSpell;
     private String nickShortSpell;
     private String sex;
     private String mobile;
     private String iconAttchId;
     private String iconUrl;
     private String iconSmallAttchId;
     private String iconSmallUrl;
     private Short state;
     private Timestamp createTime;
     private Timestamp lastModified;
     private String weixinId;
     private String qqId;


    // Constructors

    /** default constructor */
    public UcUserInfoEntity() {
    }

	/** minimal constructor */
    public UcUserInfoEntity(String userId, String userName, String password, Timestamp createTime, Timestamp lastModified) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createTime = createTime;
        this.lastModified = lastModified;
    }
    
    /** full constructor */
    public UcUserInfoEntity(String userId, String orgId, String realm, String userName, String password, String realName, String realFullSpell, String realShortSpell, String nickName, String nickFullSpell, String nickShortSpell, String sex, String mobile, String iconAttchId, String iconUrl, String iconSmallAttchId, String iconSmallUrl, Short state, Timestamp createTime, Timestamp lastModified, String weixinId, String qqId) {
        this.userId = userId;
        this.orgId = orgId;
        this.realm = realm;
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.realFullSpell = realFullSpell;
        this.realShortSpell = realShortSpell;
        this.nickName = nickName;
        this.nickFullSpell = nickFullSpell;
        this.nickShortSpell = nickShortSpell;
        this.sex = sex;
        this.mobile = mobile;
        this.iconAttchId = iconAttchId;
        this.iconUrl = iconUrl;
        this.iconSmallAttchId = iconSmallAttchId;
        this.iconSmallUrl = iconSmallUrl;
        this.state = state;
        this.createTime = createTime;
        this.lastModified = lastModified;
        this.weixinId = weixinId;
        this.qqId = qqId;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="user_id", unique=true, nullable=false, length=64)

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Column(name="org_id", length=64)

    public String getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    @Column(name="realm", length=128)

    public String getRealm() {
        return this.realm;
    }
    
    public void setRealm(String realm) {
        this.realm = realm;
    }
    
    @Column(name="user_name", nullable=false, length=128)

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Column(name="password", nullable=false, length=256)

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="real_name", length=128)

    public String getRealName() {
        return this.realName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }
    
    @Column(name="real_full_spell", length=128)

    public String getRealFullSpell() {
        return this.realFullSpell;
    }
    
    public void setRealFullSpell(String realFullSpell) {
        this.realFullSpell = realFullSpell;
    }
    
    @Column(name="real_short_spell", length=128)

    public String getRealShortSpell() {
        return this.realShortSpell;
    }
    
    public void setRealShortSpell(String realShortSpell) {
        this.realShortSpell = realShortSpell;
    }
    
    @Column(name="nick_name", length=128)

    public String getNickName() {
        return this.nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    @Column(name="nick_full_spell", length=128)

    public String getNickFullSpell() {
        return this.nickFullSpell;
    }
    
    public void setNickFullSpell(String nickFullSpell) {
        this.nickFullSpell = nickFullSpell;
    }
    
    @Column(name="nick_short_spell", length=128)

    public String getNickShortSpell() {
        return this.nickShortSpell;
    }
    
    public void setNickShortSpell(String nickShortSpell) {
        this.nickShortSpell = nickShortSpell;
    }
    
    @Column(name="sex", length=10)

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    @Column(name="mobile", length=50)

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    @Column(name="icon_attch_id", length=64)

    public String getIconAttchId() {
        return this.iconAttchId;
    }
    
    public void setIconAttchId(String iconAttchId) {
        this.iconAttchId = iconAttchId;
    }
    
    @Column(name="icon_url", length=512)

    public String getIconUrl() {
        return this.iconUrl;
    }
    
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    
    @Column(name="icon_small_attch_id", length=64)

    public String getIconSmallAttchId() {
        return this.iconSmallAttchId;
    }
    
    public void setIconSmallAttchId(String iconSmallAttchId) {
        this.iconSmallAttchId = iconSmallAttchId;
    }
    
    @Column(name="icon_small_url", length=512)

    public String getIconSmallUrl() {
        return this.iconSmallUrl;
    }
    
    public void setIconSmallUrl(String iconSmallUrl) {
        this.iconSmallUrl = iconSmallUrl;
    }
    
    @Column(name="state")

    public Short getState() {
        return this.state;
    }
    
    public void setState(Short state) {
        this.state = state;
    }
    
    @Column(name="create_time", nullable=false, length=19)

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="last_modified", nullable=false, length=19)

    public Timestamp getLastModified() {
        return this.lastModified;
    }
    
    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
   
    @Column(name="weixin_id", length=64)

    public String getWeixinId() {
        return this.weixinId;
    }
    
    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    @Column(name="qq_id", length=64)

    public String getQqId() {
        return this.qqId;
    }
    
    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

}