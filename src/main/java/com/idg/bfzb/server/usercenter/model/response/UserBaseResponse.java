package com.idg.bfzb.server.usercenter.model.response;

import java.math.BigDecimal;

import com.idg.bfzb.server.authentication.model.dto.UserEnterpriseEntity;
import com.idg.bfzb.server.team.model.dto.UserTeamEntity;
import com.idg.bfzb.server.usercenter.model.vo.RegionVo;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 */
public class UserBaseResponse {
    private String userId;

    private String userName;

    private String realName;

    private String mobile;

    private RegionVo province;

    private RegionVo city;
    
    private RegionVo area;
    
    private String role;

    //private String ownTeamId;

    private Byte isTeamUser;

    private Byte isEnterpriseUser;

    private BigDecimal balance;

    private BigDecimal incoming;

    private BigDecimal expenditure;
    
    private String iconAttchId;
    
    private String iconUrl;
    
    private String iconSmallAttchId;
    
    private String iconSmallUrl;
    
    private UserTeamEntity userTeamInfo;
    
    private UserEnterpriseEntity userEnterpriseInfo;
    
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

    public RegionVo getProvince() {
        return province;
    }

    public void setProvince(RegionVo province) {
        this.province = province;
    }

    public RegionVo getCity() {
        return city;
    }

    public void setCity(RegionVo city) {
        this.city = city;
    }

    public RegionVo getArea() {
		return area;
	}

	public void setArea(RegionVo area) {
		this.area = area;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public UserTeamEntity getUserTeamInfo() {
		return userTeamInfo;
	}

	public void setUserTeamInfo(UserTeamEntity userTeamInfo) {
		this.userTeamInfo = userTeamInfo;
	}

	public UserEnterpriseEntity getUserEnterpriseInfo() {
		return userEnterpriseInfo;
	}

	public void setUserEnterpriseInfo(UserEnterpriseEntity userEnterpriseInfo) {
		this.userEnterpriseInfo = userEnterpriseInfo;
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
