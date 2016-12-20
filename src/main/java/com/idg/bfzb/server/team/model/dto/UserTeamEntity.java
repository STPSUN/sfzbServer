package com.idg.bfzb.server.team.model.dto;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * UserTeamEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_user_team")

public class UserTeamEntity  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields    
     private String teamId;
     private String teamLeaderId;
     private String teamName;
     private String teamNickName;
     private String regionCode;
     private String teamSkills;
     private String serviceContent;
     private String experience;
     private String contactsMobile;
     private String contactsIdcardCode;
     private String contactsRealName;
     private Short reviewState;
     private String reviewAdminId;
     private Timestamp reviewTime;


    // Constructors

    /** default constructor */
    public UserTeamEntity() {
    }

	/** minimal constructor */
    public UserTeamEntity(String teamId, String teamLeaderId, Timestamp reviewTime) {
        this.teamId = teamId;
        this.teamLeaderId = teamLeaderId;
        this.reviewTime = reviewTime;
    }
    
    /** full constructor */
    public UserTeamEntity(String teamId, String teamLeaderId, String teamName, String teamNickName, String regionCode, String teamSkills, String serviceContent, String experience, String contactsMobile, String contactsIdcardCode, String contactsRealName, Short reviewState, String reviewAdminId, Timestamp reviewTime) {
        this.teamId = teamId;
        this.teamLeaderId = teamLeaderId;
        this.teamName = teamName;
        this.teamNickName = teamNickName;
        this.regionCode = regionCode;
        this.teamSkills = teamSkills;
        this.serviceContent = serviceContent;
        this.experience = experience;
        this.contactsMobile = contactsMobile;
        this.contactsIdcardCode = contactsIdcardCode;
        this.contactsRealName = contactsRealName;
        this.reviewState = reviewState;
        this.reviewAdminId = reviewAdminId;
        this.reviewTime = reviewTime;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="team_id", unique=true, nullable=false, length=64)

    public String getTeamId() {
        return this.teamId;
    }
    
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
    
    @Column(name="team_leader_id", nullable=false, length=64)

    public String getTeamLeaderId() {
        return this.teamLeaderId;
    }
    
    public void setTeamLeaderId(String teamLeaderId) {
        this.teamLeaderId = teamLeaderId;
    }
    
    @Column(name="team_name", length=64)

    public String getTeamName() {
        return this.teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    @Column(name="team_nick_name", length=64)

    public String getTeamNickName() {
        return this.teamNickName;
    }
    
    public void setTeamNickName(String teamNickName) {
        this.teamNickName = teamNickName;
    }
    
    @Column(name="region_code", length=16)

    public String getRegionCode() {
        return this.regionCode;
    }
    
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    
    @Column(name="team_skills", length=256)

    public String getTeamSkills() {
        return this.teamSkills;
    }
    
    public void setTeamSkills(String teamSkills) {
        this.teamSkills = teamSkills;
    }
    
    @Column(name="service_content", length=1024)

    public String getServiceContent() {
        return this.serviceContent;
    }
    
    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }
    
    @Column(name="experience", length=1024)

    public String getExperience() {
        return this.experience;
    }
    
    public void setExperience(String experience) {
        this.experience = experience;
    }
    
    @Column(name="contacts_mobile", length=16)

    public String getContactsMobile() {
        return this.contactsMobile;
    }
    
    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }
    
    @Column(name="contacts_idcard_code", length=64)

    public String getContactsIdcardCode() {
        return this.contactsIdcardCode;
    }
    
    public void setContactsIdcardCode(String contactsIdcardCode) {
        this.contactsIdcardCode = contactsIdcardCode;
    }
    
    @Column(name="contacts_real_name", length=16)

    public String getContactsRealName() {
        return this.contactsRealName;
    }
    
    public void setContactsRealName(String contactsRealName) {
        this.contactsRealName = contactsRealName;
    }
    
    @Column(name="review_state")

    public Short getReviewState() {
        return this.reviewState;
    }
    
    public void setReviewState(Short reviewState) {
        this.reviewState = reviewState;
    }
    
    @Column(name="review_admin_id", length=64)

    public String getReviewAdminId() {
        return this.reviewAdminId;
    }
    
    public void setReviewAdminId(String reviewAdminId) {
        this.reviewAdminId = reviewAdminId;
    }
    
    @Column(name="review_time", nullable=false, length=19)

    public Timestamp getReviewTime() {
        return this.reviewTime;
    }
    
    public void setReviewTime(Timestamp reviewTime) {
        this.reviewTime = reviewTime;
    }
   








}