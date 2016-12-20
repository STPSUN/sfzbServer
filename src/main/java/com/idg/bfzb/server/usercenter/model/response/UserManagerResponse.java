package com.idg.bfzb.server.usercenter.model.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 类名：用户管理请求类
 * 描述：
 * @author weibeifeng
 * 时间：2016/11/2
 */
public class UserManagerResponse {

	//用户基本信息
	private String userId;//账号ID
    private String orgId;
    private String realm;
    private String userName;//注册账号
    private String password;
    private String realName;
    private String realFullSpell;
    private String realShortSpell;
    private String nickName;//昵称
    private String nickFullSpell;
    private String nickShortSpell;
    private String sex;
    private String mobile;
    private String iconAttchId;
    private String iconUrl;
    private String iconSmallAttchId;
    private String iconSmallUrl;
    private Short state;
    private Timestamp createTime;//注册时间
    private Timestamp lastModified;//更新时间
    
    //用户认证信息
    private String idcardCode;//身份证号码
    private BigDecimal balance;//账户金额
    private BigDecimal incoming;//总收入
    private BigDecimal expenditure;//总支出
    private String alipayCode;//支付宝账号
    private String bankCardCode;//银行卡号
    private String wechatCode;//微信号
    private String idcardPhoto1Id;//身份正面照片附件id
    private String idcardPhoto1Url;//身份正面照片附件url
    private String idcardPhoto2Id;//身份反面照片附件id
    private String idcardPhoto2Url;//身份反面照片附件url
    private String idcardPhoto3Id;//手持身份证正面照片附件id
    private String idcardPhoto3Url;//手持身份证正面照片附件url
    private String lastProvinceCode;
    private String lastProvinceName;
    private String lastCityCode;
    private String lastCityName;
    private String lastAreaCode;
    private String lastAreaName;
    private String lastRole;//账号类型
    private Byte isTeamUser;
    private Byte isEnterpriseUser;
    private String lastRegionCode;//上次登录地区
    
    //团队认证信息
    private String teamId;
    private String teamLeaderId;//等于用户ID
    private String teamName;
    private String teamNickName;
    private String teamSkills;
    private String serviceContent;
    private String experience;
    private String contactsMobile;
    private String contactsIdcardCode;
    private String contactsRealName;
    
    //团队成员
    private String teamMemberName;
    
    //企业认证信息
    private String enterpriseId;
    private String enterpriseName;
    private String businessLicense;
    private String businessScope;
    private String corporate;
    private String businessLicenseImage;
    
    //更新时间（个人、团队、企业）
    private Timestamp updateTime;
    
    private String regionCode;//登录地区
    
    //审核信息（个人、团队、企业）
    private Short reviewState;//审核状态  -1删除 0待审核 1禁用 2审核通过 3审核拒绝
    private String reviewAdminId;//审核人
    private Timestamp reviewTime;//审核时间
    
    //用户类型
    private String userAuthType;//认证类型 normal普通用户  enterprise企业用户  team 团队用户
    
    private String entCreateTime;//企业认证创建时间
    private String teamCreateTime;//团队认证创建时间
    
    //用户技能
    private String abilitysName;
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getRealm() {
		return realm;
	}
	public void setRealm(String realm) {
		this.realm = realm;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRealFullSpell() {
		return realFullSpell;
	}
	public void setRealFullSpell(String realFullSpell) {
		this.realFullSpell = realFullSpell;
	}
	public String getRealShortSpell() {
		return realShortSpell;
	}
	public void setRealShortSpell(String realShortSpell) {
		this.realShortSpell = realShortSpell;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getNickFullSpell() {
		return nickFullSpell;
	}
	public void setNickFullSpell(String nickFullSpell) {
		this.nickFullSpell = nickFullSpell;
	}
	public String getNickShortSpell() {
		return nickShortSpell;
	}
	public void setNickShortSpell(String nickShortSpell) {
		this.nickShortSpell = nickShortSpell;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIconAttchId() {
		return iconAttchId;
	}
	public void setIconAttchId(String iconAttchId) {
		this.iconAttchId = iconAttchId;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getIconSmallAttchId() {
		return iconSmallAttchId;
	}
	public void setIconSmallAttchId(String iconSmallAttchId) {
		this.iconSmallAttchId = iconSmallAttchId;
	}
	public String getIconSmallUrl() {
		return iconSmallUrl;
	}
	public void setIconSmallUrl(String iconSmallUrl) {
		this.iconSmallUrl = iconSmallUrl;
	}
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getLastModified() {
		return lastModified;
	}
	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}
	public String getIdcardCode() {
		return idcardCode;
	}
	public void setIdcardCode(String idcardCode) {
		this.idcardCode = idcardCode;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getIncoming() {
		return incoming;
	}
	public void setIncoming(BigDecimal incoming) {
		this.incoming = incoming;
	}
	public BigDecimal getExpenditure() {
		return expenditure;
	}
	public void setExpenditure(BigDecimal expenditure) {
		this.expenditure = expenditure;
	}
	public String getAlipayCode() {
		return alipayCode;
	}
	public void setAlipayCode(String alipayCode) {
		this.alipayCode = alipayCode;
	}
	public String getBankCardCode() {
		return bankCardCode;
	}
	public void setBankCardCode(String bankCardCode) {
		this.bankCardCode = bankCardCode;
	}
	public String getWechatCode() {
		return wechatCode;
	}
	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}
	public String getIdcardPhoto1Id() {
		return idcardPhoto1Id;
	}
	public void setIdcardPhoto1Id(String idcardPhoto1Id) {
		this.idcardPhoto1Id = idcardPhoto1Id;
	}
	public String getIdcardPhoto2Id() {
		return idcardPhoto2Id;
	}
	public void setIdcardPhoto2Id(String idcardPhoto2Id) {
		this.idcardPhoto2Id = idcardPhoto2Id;
	}
	public String getIdcardPhoto3Id() {
		return idcardPhoto3Id;
	}
	public void setIdcardPhoto3Id(String idcardPhoto3Id) {
		this.idcardPhoto3Id = idcardPhoto3Id;
	}
	public String getLastProvinceCode() {
		return lastProvinceCode;
	}
	public void setLastProvinceCode(String lastProvinceCode) {
		this.lastProvinceCode = lastProvinceCode;
	}
	public String getLastCityCode() {
		return lastCityCode;
	}
	public void setLastCityCode(String lastCityCode) {
		this.lastCityCode = lastCityCode;
	}
	public String getLastAreaCode() {
		return lastAreaCode;
	}
	public void setLastAreaCode(String lastAreaCode) {
		this.lastAreaCode = lastAreaCode;
	}
	public String getLastRole() {
		return lastRole;
	}
	public void setLastRole(String lastRole) {
		this.lastRole = lastRole;
	}
	public Byte getIsTeamUser() {
		return isTeamUser;
	}
	public void setIsTeamUser(Byte isTeamUser) {
		this.isTeamUser = isTeamUser;
	}
	public Byte getIsEnterpriseUser() {
		return isEnterpriseUser;
	}
	public void setIsEnterpriseUser(Byte isEnterpriseUser) {
		this.isEnterpriseUser = isEnterpriseUser;
	}
	public String getLastRegionCode() {
		return lastRegionCode;
	}
	public void setLastRegionCode(String lastRegionCode) {
		this.lastRegionCode = lastRegionCode;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamLeaderId() {
		return teamLeaderId;
	}
	public void setTeamLeaderId(String teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamNickName() {
		return teamNickName;
	}
	public void setTeamNickName(String teamNickName) {
		this.teamNickName = teamNickName;
	}
	public String getTeamSkills() {
		return teamSkills;
	}
	public void setTeamSkills(String teamSkills) {
		this.teamSkills = teamSkills;
	}
	public String getServiceContent() {
		return serviceContent;
	}
	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getContactsMobile() {
		return contactsMobile;
	}
	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}
	public String getContactsIdcardCode() {
		return contactsIdcardCode;
	}
	public void setContactsIdcardCode(String contactsIdcardCode) {
		this.contactsIdcardCode = contactsIdcardCode;
	}
	public String getContactsRealName() {
		return contactsRealName;
	}
	public void setContactsRealName(String contactsRealName) {
		this.contactsRealName = contactsRealName;
	}
	
	public String getTeamMemberName() {
        return teamMemberName;
    }
    public void setTeamMemberName(String teamMemberName) {
        this.teamMemberName = teamMemberName;
    }
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getCorporate() {
		return corporate;
	}
	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}
	public String getBusinessLicenseImage() {
		return businessLicenseImage;
	}
	public void setBusinessLicenseImage(String businessLicenseImage) {
		this.businessLicenseImage = businessLicenseImage;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public Short getReviewState() {
		return reviewState;
	}
	public void setReviewState(Short reviewState) {
		this.reviewState = reviewState;
	}
	public String getReviewAdminId() {
		return reviewAdminId;
	}
	public void setReviewAdminId(String reviewAdminId) {
		this.reviewAdminId = reviewAdminId;
	}
	public Timestamp getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}
	public String getUserAuthType() {
		return userAuthType;
	}
	public void setUserAuthType(String userAuthType) {
		this.userAuthType = userAuthType;
	}
	public String getLastProvinceName() {
		return lastProvinceName;
	}
	public void setLastProvinceName(String lastProvinceName) {
		this.lastProvinceName = lastProvinceName;
	}
	public String getLastCityName() {
		return lastCityName;
	}
	public void setLastCityName(String lastCityName) {
		this.lastCityName = lastCityName;
	}
	public String getLastAreaName() {
		return lastAreaName;
	}
	public void setLastAreaName(String lastAreaName) {
		this.lastAreaName = lastAreaName;
	}
	public String getEntCreateTime() {
		return entCreateTime;
	}
	public void setEntCreateTime(String entCreateTime) {
		this.entCreateTime = entCreateTime;
	}
	public String getTeamCreateTime() {
		return teamCreateTime;
	}
	public void setTeamCreateTime(String teamCreateTime) {
		this.teamCreateTime = teamCreateTime;
	}
	public String getIdcardPhoto1Url() {
		return idcardPhoto1Url;
	}
	public void setIdcardPhoto1Url(String idcardPhoto1Url) {
		this.idcardPhoto1Url = idcardPhoto1Url;
	}
	public String getIdcardPhoto2Url() {
		return idcardPhoto2Url;
	}
	public void setIdcardPhoto2Url(String idcardPhoto2Url) {
		this.idcardPhoto2Url = idcardPhoto2Url;
	}
	public String getIdcardPhoto3Url() {
		return idcardPhoto3Url;
	}
	public void setIdcardPhoto3Url(String idcardPhoto3Url) {
		this.idcardPhoto3Url = idcardPhoto3Url;
	}
	public String getAbilitysName() {
        return abilitysName;
    }
    public void setAbilitysName(String abilitysName) {
        this.abilitysName = abilitysName;
    }
}
