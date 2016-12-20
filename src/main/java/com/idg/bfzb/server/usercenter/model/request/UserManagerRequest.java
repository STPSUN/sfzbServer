package com.idg.bfzb.server.usercenter.model.request;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 类名：用户管理请求类
 * 描述：
 * @author weibeifeng
 * 时间：2016/11/2
 */
public class UserManagerRequest {

	//用户基本信息
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
    private Timestamp lastModified;//更新时间
    
    //用户认证信息
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
    
    //企业认证信息
    private String enterpriseId;
    private String enterpriseName;
    private String businessLicense;
    private String businessScope;
    private String corporate;
    private String businessLicenseImage;
    
    //更新时间
    private Timestamp updateTime;
    
    private String regionCode;//登录地区
    
    //审核信息
    private Short reviewState;//审核状态
    private String reviewAdminId;//审核人
    private Timestamp reviewTime;//审核时间
    
    //用户类型
    private String userAuthType;//认证类型 normal普通用户  enterprise企业用户  team 团队用户
    
    private String qryStartTime;//开始时间
    private String qryEndTime;//结束时间
    private String entCreateTime;//企业认证创建时间
    private String teamCreateTime;//团队认证创建时间
    
    private int pageSize;
    private int pageNum;
    private int totals;
    
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getTotals() {
		return totals;
	}
	public void setTotals(int totals) {
		this.totals = totals;
	}
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
}
