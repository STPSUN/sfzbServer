package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;
import java.util.List;

import com.idg.bfzb.server.project.model.vo.EvaluateListVo;
import com.idg.bfzb.server.project.model.vo.ProjectProgressVo;

/**
 * 类名称：ReceiverProjectDetailResponse
 * 类描述：接包者项目详情
 * 创建人：ouzhb
 * 创建时间：2016/11/9
 */
public class ReceiverProjectDetailResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String projectName;
	
	private Double budget;
	
	private String categorys;
	
	private String applyDeadline;
	
	private String submitDeadline;
	
	private String createTime;
	
	private String updateTime;
	
	private Integer signUpCount;
	
	private Short projectState;
	
	private Short signUpState;
	
	private String projectType;
	
	private String tenderType;
	
	private String employerUserId;
	
	private String employerUserName;
	
	private String employerNickName;
	
	private String employerMobile;
	
	private Short isRefuse;
	
	private String refuseReason;
	
	private Short tenderReviewState;
	
	private String tenderReivewReason;
	
	private Short adminReviewState;
	
	private String adminReviewReason;
	
	private String description;
	
	private Boolean isMatchAbility; 
	
	private String matchAbilityMsg;
	
	private List<EvaluateListVo> evaluates;
	
	private Short isEvaluate;
	
	private String receiverCompleteTime;
	
	private String employerCompleteTime;
	
	private String regionDetail;
	
	private String houseNumber;
	
	private String regionProvince;
	
	private String regionCity;
	
	private String regionArea;
	
	private String longitude;
	
	private String latitude;
	
	private Double tradeMoney;
	
	private Float imprestScale;
	
	private Float marginScale;
	
	private Short isMargin;
	
	private Integer marginDay;
	
	private List<ProjectProgressVo> progress;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public String getApplyDeadline() {
		return applyDeadline;
	}

	public void setApplyDeadline(String applyDeadline) {
		this.applyDeadline = applyDeadline;
	}

	public Integer getSignUpCount() {
		return signUpCount;
	}

	public void setSignUpCount(Integer signUpCount) {
		this.signUpCount = signUpCount;
	}

	public String getSubmitDeadline() {
		return submitDeadline;
	}

	public void setSubmitDeadline(String submitDeadline) {
		this.submitDeadline = submitDeadline;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	public Short getProjectState() {
		return projectState;
	}

	public void setProjectState(Short projectState) {
		this.projectState = projectState;
	}

	public Short getSignUpState() {
		return signUpState;
	}

	public void setSignUpState(Short signUpState) {
		this.signUpState = signUpState;
	}

	public String getEmployerUserId() {
		return employerUserId;
	}

	public void setEmployerUserId(String employerUserId) {
		this.employerUserId = employerUserId;
	}

	public String getEmployerUserName() {
		return employerUserName;
	}

	public void setEmployerUserName(String employerUserName) {
		this.employerUserName = employerUserName;
	}

	public String getEmployerNickName() {
		return employerNickName;
	}

	public void setEmployerNickName(String employerNickName) {
		this.employerNickName = employerNickName;
	}

	public String getEmployerMobile() {
		return employerMobile;
	}

	public void setEmployerMobile(String employerMobile) {
		this.employerMobile = employerMobile;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<EvaluateListVo> getEvaluates() {
		return evaluates;
	}

	public void setEvaluates(List<EvaluateListVo> evaluates) {
		this.evaluates = evaluates;
	}

	public Boolean getIsMatchAbility() {
		return isMatchAbility;
	}

	public void setIsMatchAbility(Boolean isMatchAbility) {
		this.isMatchAbility = isMatchAbility;
	}

	public String getMatchAbilityMsg() {
		return matchAbilityMsg;
	}

	public void setMatchAbilityMsg(String matchAbilityMsg) {
		this.matchAbilityMsg = matchAbilityMsg;
	}

	public Short getIsRefuse() {
		return isRefuse;
	}

	public void setIsRefuse(Short isRefuse) {
		this.isRefuse = isRefuse;
	}

	public Short getTenderReviewState() {
		return tenderReviewState;
	}

	public void setTenderReviewState(Short tenderReviewState) {
		this.tenderReviewState = tenderReviewState;
	}

	public String getTenderReivewReason() {
		return tenderReivewReason;
	}

	public void setTenderReivewReason(String tenderReivewReason) {
		this.tenderReivewReason = tenderReivewReason;
	}

	public Short getAdminReviewState() {
		return adminReviewState;
	}

	public void setAdminReviewState(Short adminReviewState) {
		this.adminReviewState = adminReviewState;
	}

	public String getAdminReviewReason() {
		return adminReviewReason;
	}

	public void setAdminReviewReason(String adminReviewReason) {
		this.adminReviewReason = adminReviewReason;
	}

	public Short getIsEvaluate() {
		return isEvaluate;
	}

	public void setIsEvaluate(Short isEvaluate) {
		this.isEvaluate = isEvaluate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getReceiverCompleteTime() {
		return receiverCompleteTime;
	}

	public void setReceiverCompleteTime(String receiverCompleteTime) {
		this.receiverCompleteTime = receiverCompleteTime;
	}

	public String getEmployerCompleteTime() {
		return employerCompleteTime;
	}

	public void setEmployerCompleteTime(String employerCompleteTime) {
		this.employerCompleteTime = employerCompleteTime;
	}

	public String getRegionDetail() {
		return regionDetail;
	}

	public void setRegionDetail(String regionDetail) {
		this.regionDetail = regionDetail;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getRegionProvince() {
		return regionProvince;
	}

	public void setRegionProvince(String regionProvince) {
		this.regionProvince = regionProvince;
	}

	public String getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(String regionCity) {
		this.regionCity = regionCity;
	}

	public String getRegionArea() {
		return regionArea;
	}

	public void setRegionArea(String regionArea) {
		this.regionArea = regionArea;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public List<ProjectProgressVo> getProgress() {
		return progress;
	}

	public void setProgress(List<ProjectProgressVo> progress) {
		this.progress = progress;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Float getImprestScale() {
		return imprestScale;
	}

	public void setImprestScale(Float imprestScale) {
		this.imprestScale = imprestScale;
	}

	public Float getMarginScale() {
		return marginScale;
	}

	public void setMarginScale(Float marginScale) {
		this.marginScale = marginScale;
	}

	public Short getIsMargin() {
		return isMargin;
	}

	public void setIsMargin(Short isMargin) {
		this.isMargin = isMargin;
	}

	public Integer getMarginDay() {
		return marginDay;
	}

	public void setMarginDay(Integer marginDay) {
		this.marginDay = marginDay;
	}

}
