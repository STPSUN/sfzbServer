package com.idg.bfzb.server.usercenter.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
public class UserBaseVo implements Serializable {

    private String userId;

    private String userName;

    private String realName;

    private String mobile;

    private String lastProvinceCode;

    private String lastCityCode;
    
    private String lastAreaCode;
    
    private String lastRole;

    private String ownTeamId;

    private Byte isTeamUser; //1 是  0 否

    private Byte isEnterpriseUser;//1 是  0 否

    private BigDecimal balance;

    private BigDecimal incoming;

    private BigDecimal expenditure;
    
    private String iconAttchId;
    
    private String iconUrl;
    
    private String iconSmallAttchId;
    
    private String iconSmallUrl;
    
    private String sex;
    
    private String nickName;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOwnTeamId() {
        return ownTeamId;
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

	public void setOwnTeamId(String ownTeamId) {
        this.ownTeamId = ownTeamId;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
