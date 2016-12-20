package com.idg.bfzb.server.usercenter.model.vo;
// default package

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class UserBaseAndPersonVo {

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


}