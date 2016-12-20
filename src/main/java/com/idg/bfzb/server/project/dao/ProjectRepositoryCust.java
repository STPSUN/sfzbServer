package com.idg.bfzb.server.project.dao;

import java.util.List;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.model.dto.ProjectEntity;
import com.idg.bfzb.server.project.model.response.ProjectStateTotalResponse;
import com.idg.bfzb.server.project.model.vo.ProjectListVo;
import com.idg.bfzb.server.project.model.vo.ProjectQryVo;

/**
 * 类名称：ProjectRepositoryCust
 * 类描述：项目Repository
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectRepositoryCust {
	/**
	 * 根据条件分页查询項目信息
	 * @param start	起始索引
	 * @param size	记录数
	 * @param projectQryVo	查询条件
	 * @return	PageInfo<ProjectListVo>
	 */
	PageInfo<ProjectListVo> findProjectByPageAndCondition(int start, int size, ProjectQryVo projectQryVo);
	/**
	 * 根据项目状态和时间间隔查询项目信息
	 * @param state	项目状态
	 * @param timer			时间间隔
	 * @return	List<ProjectEntity>
	 */
	List<ProjectEntity> findProjectByStateAndTimer(Short state, Integer timer);
	/**
	 * 查询已申请质保的项目
	 * @param adminReviewState	质保申请状态
	 * @return	List<ProjectEntity>
	 */
	List<ProjectEntity> findProjectWarrantyByAdminReviewState(Short adminReviewState);
	/**
	 * 修改项目状态
	 * @param state	项目状态
	 * @return	
	 */
	boolean updateProjectStateByProjectId(String projectId, Short state);
	/**
	 * 修改项目质保状态
	 * @param projectId
	 * @param adminReviewState
	 * @return
	 */
	boolean updateProjectWarranStateByProjectId(String projectId, Short adminReviewState);
	/**
	 * 查询某个间隔已申请拒收的项目
	 * @param adminReviewState	质保申请状态
	 * @param timer	时间间隔
	 * @return	List<ProjectEntity>
	 */
	List<ProjectEntity> findProjectRejectionByAdminReviewState(Short adminReviewState, Integer timer);
	/**
	 * 修改项目拒收状态
	 * @param projectId
	 * @param adminReviewState
	 * @return
	 */
	boolean updateProjectRejectionStateByProjectId(String projectId, Short adminReviewState);
	/**
	 * 查询进行中已延期的项目
	 * @param day
	 * @return	List<ProjectEntity>
	 */
	List<ProjectEntity> findDelaySelectedProject(int day);
	/**
	 * 查询过期的待确认招标项目
	 * @return
	 */
	List<ProjectEntity> findDelayToConfirmCommonProject();
	/**
	 * 查询过期的待确认包招项目
	 * @param day
	 * @return
	 */
	List<ProjectEntity> findDelayToConfirmContainProject(int day);
	/**
	 * 查询过期的选标中项目
	 * @param day
	 * @return
	 */
	List<ProjectEntity> findDelaySelectingProject(int day);
	/**
	 * 统计雇主各项目状态数量
	 * @param employerId
	 * @return
	 */
	List<ProjectStateTotalResponse> countStateByEmployerId(String employerId);
}