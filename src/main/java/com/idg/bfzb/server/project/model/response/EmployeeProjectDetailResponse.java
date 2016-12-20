package com.idg.bfzb.server.project.model.response;

import java.io.Serializable;
import java.util.List;

import com.idg.bfzb.server.project.model.vo.ProjectPlanVo;
import com.idg.bfzb.server.project.model.vo.ProjectProgressVo;

/**
 * 类名称：EmployeeProjectDetailResponse
 * 类描述：发包者项目详情
 * 创建人：ouzhb
 * 创建时间：2016/11/9
 */
public class EmployeeProjectDetailResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	//兜底状态 -1 0已提交未审核；1审核通过；2审核拒绝
	private Short revealState;
	
	private String projectId;
	
	private String projectName;
	
	private Double budget;
	
	private String categorys;
	
	private String applyDeadline;
	
	private String submitDeadline;
	
	private String createTime;
	
	private String updateTime;
	
	private int signUpCount;
	
	private Short state;
	
	private String projectType;
	
	private String tenderType;
	
	private String description;
	
	private String tenderUserId;
	
	private String tenderUserName;
	
	private String tenderNickName;
	
	private String tenderMobile;
	
	private Short isReveal;
	
	private Double tradeMoney;
	
	private Double remainMoney;
	
	private Float imprestScale;
	
	private Float revealScale;
	
	private Short adminReviewState;
	
	private List<ProjectProgressVo> progress;
	
	private List<ProjectPlanVo> plans;
	
	private Short isEvaluate;
	
	private Short isRefuse;
	
	private String refuseReason;
	
	private Short refuseTenderReviewState;
	
	private String refuseTenderReivewReason;
	
	private Short refuseAdminReviewState;
	
	private String refuseAdminReviewReason;
	
	private String receiverCompleteTime;
	
	private String employerCompleteTime;
	
	private Short isMargin;
	
	private Integer marginDay;
	
	private Float marginScale;
	
	private String regionDetail;
	
	private String houseNumber;
	
	private String regionProvince;
	
	private String regionCity;
	
	private String regionArea;
	
	private String longitude;
	
	private String latitude;
	
	private Short isPayReveal;

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

	public int getSignUpCount() {
		return signUpCount;
	}

	public void setSignUpCount(int signUpCount) {
		this.signUpCount = signUpCount;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public String getTenderUserName() {
		return tenderUserName;
	}

	public void setTenderUserName(String tenderUserName) {
		this.tenderUserName = tenderUserName;
	}

	public String getTenderNickName() {
		return tenderNickName;
	}

	public void setTenderNickName(String tenderNickName) {
		this.tenderNickName = tenderNickName;
	}

	public String getTenderMobile() {
		return tenderMobile;
	}

	public void setTenderMobile(String tenderMobile) {
		this.tenderMobile = tenderMobile;
	}

	public Short getIsReveal() {
		return isReveal;
	}

	public void setIsReveal(Short isReveal) {
		this.isReveal = isReveal;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public List<ProjectProgressVo> getProgress() {
		return progress;
	}

	public void setProgress(List<ProjectProgressVo> progress) {
		this.progress = progress;
	}

	public Double getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	public Float getImprestScale() {
		return imprestScale;
	}

	public void setImprestScale(Float imprestScale) {
		this.imprestScale = imprestScale;
	}

	public Float getRevealScale() {
		return revealScale;
	}

	public void setRevealScale(Float revealScale) {
		this.revealScale = revealScale;
	}

	public List<ProjectPlanVo> getPlans() {
		return plans;
	}

	public void setPlans(List<ProjectPlanVo> plans) {
		this.plans = plans;
	}

	public Short getAdminReviewState() {
		return adminReviewState;
	}

	public void setAdminReviewState(Short adminReviewState) {
		this.adminReviewState = adminReviewState;
	}

	public Short getIsEvaluate() {
		return isEvaluate;
	}

	public void setIsEvaluate(Short isEvaluate) {
		this.isEvaluate = isEvaluate;
	}

	public Short getIsRefuse() {
		return isRefuse;
	}

	public void setIsRefuse(Short isRefuse) {
		this.isRefuse = isRefuse;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Short getRefuseTenderReviewState() {
		return refuseTenderReviewState;
	}

	public void setRefuseTenderReviewState(Short refuseTenderReviewState) {
		this.refuseTenderReviewState = refuseTenderReviewState;
	}

	public String getRefuseTenderReivewReason() {
		return refuseTenderReivewReason;
	}

	public void setRefuseTenderReivewReason(String refuseTenderReivewReason) {
		this.refuseTenderReivewReason = refuseTenderReivewReason;
	}

	public Short getRefuseAdminReviewState() {
		return refuseAdminReviewState;
	}

	public void setRefuseAdminReviewState(Short refuseAdminReviewState) {
		this.refuseAdminReviewState = refuseAdminReviewState;
	}

	public String getRefuseAdminReviewReason() {
		return refuseAdminReviewReason;
	}

	public void setRefuseAdminReviewReason(String refuseAdminReviewReason) {
		this.refuseAdminReviewReason = refuseAdminReviewReason;
	}

	public Short getRevealState() {
		return revealState;
	}

	public void setRevealState(Short revealState) {
		this.revealState = revealState;
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

	public Float getMarginScale() {
		return marginScale;
	}

	public void setMarginScale(Float marginScale) {
		this.marginScale = marginScale;
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

	public Short getIsPayReveal() {
		return isPayReveal;
	}

	public void setIsPayReveal(Short isPayReveal) {
		this.isPayReveal = isPayReveal;
	}

}
