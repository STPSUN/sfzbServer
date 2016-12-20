package com.idg.bfzb.server.project.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.dao.ProjectRepositoryCust;
import com.idg.bfzb.server.project.model.dto.ProjectEntity;
import com.idg.bfzb.server.project.model.response.ProjectStateTotalResponse;
import com.idg.bfzb.server.project.model.vo.ProjectListVo;
import com.idg.bfzb.server.project.model.vo.ProjectQryVo;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class ProjectRepositoryImpl implements ProjectRepositoryCust {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public PageInfo<ProjectListVo> findProjectByPageAndCondition(int start, int size,
			ProjectQryVo projectQryVo) {
		PageInfo<ProjectListVo> pageInfo = new PageInfo<ProjectListVo>();
		
        StringBuilder sqlStmt = new StringBuilder();
        
        sqlStmt.append(" select project_id,project_name,budget,category_ids,DATE_FORMAT(apply_deadline,'%Y-%m-%d') apply_deadline, ")
               .append(" region_province, region_city, region_area, region_detail, house_number,")
               .append(" DATE_FORMAT(submit_deadline,'%Y-%m-%d') submit_deadline,DATE_FORMAT(create_time,'%Y-%m-%d') create_time, ")
               .append(" sign_up_count,state project_state,project_type,tender_type,employer_id,tender_user_id")
               .append(" region_province, region_city, region_area, trade_money ")
        	   .append(" from t_project where 1=1 ");
        
        if(!StringUtil.isNull(projectQryVo.getProjectName())){
        	sqlStmt.append(" and project_name like '%").append(projectQryVo.getProjectName()).append("%' ");
        }
        if(!StringUtil.isNull(projectQryVo.getCategoryId())){
        	sqlStmt.append(" and category_ids like '%").append(projectQryVo.getCategoryId()).append("%' ");
        }
        if(projectQryVo.getState()!=null){
        	if(projectQryVo.getState()==Constants.PROJECT_STATE_APPROVE_PASS){//待确认状态需要一起返回审批通过跟待审核的数据
        		sqlStmt.append(" and state in(").append(Constants.PROJECT_STATE_APPROVE_PASS).append(",").append(Constants.PROJECT_STATE_NOT_APPROVE).append(")");
        	}else{
        		sqlStmt.append(" and state=").append(projectQryVo.getState());
        	}
        }
        if(projectQryVo.getDeadline()!=null){
        	sqlStmt.append(" and datediff(apply_deadline,CURDATE())>=").append(projectQryVo.getDeadline());
        }
        if(projectQryVo.getMinBudget()!=null){
        	sqlStmt.append(" and budget>=").append(projectQryVo.getMinBudget());
        }
        if(projectQryVo.getMaxBudget()!=null){
        	sqlStmt.append(" and budget<=").append(projectQryVo.getMaxBudget());
        }
        if(!StringUtil.isNull(projectQryVo.getEmployerId())){
        	sqlStmt.append(" and employer_id='").append(projectQryVo.getEmployerId()).append("'");
        }
        //not in 
//        if(!StringUtil.isNull(projectQryVo.getNotTendUserId())){
//        	sqlStmt.append(" and employer_id != '").append(projectQryVo.getNotTendUserId()).append("'");
//        }
        if(!StringUtil.isNull(projectQryVo.getTenderType())){
        	sqlStmt.append(" and tender_type='").append(projectQryVo.getTenderType()).append("'");
        }
        if(!StringUtil.isNull(projectQryVo.getRegionProvince())){
        	sqlStmt.append(" and region_province like '%").append(projectQryVo.getRegionProvince()).append("%'");
        }
        try {
        	pageInfo.setTotalRows(jdbcTemplate.query(sqlStmt.toString(), BeanPropertyRowMapper.newInstance(ProjectListVo.class)).size());
        	
        	sqlStmt.append(" order by create_time desc limit ").append(start).append(",").append(size);
        	
        	pageInfo.setPageData(this.jdbcTemplate.query(sqlStmt.toString(), BeanPropertyRowMapper.newInstance(ProjectListVo.class)));
        } catch (Exception e) {
        	pageInfo.setTotalRows(0);
			e.printStackTrace();
		}
        
        return pageInfo;
	}

	@Override
	public List<ProjectEntity> findProjectByStateAndTimer(Short state, Integer timer) {
		List<ProjectEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		
		sqlStmt.append(" select p.project_id,p.project_name,p.employer_id,p.tender_user_id,p.is_margin,p.margin_scale,p.trade_money,p.tender_user_id,p.margin_day,p.imprest_scale ")
			   .append(" from t_project p ");
		

		sqlStmt.append(" where p.state=:state ");
		sqlStmt.append(" and p.update_time<=DATE_SUB(SYSDATE(),INTERVAL :timer DAY)");
		
		sqlParameters.put("state", state);
		sqlParameters.put("timer", timer);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectEntity.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ProjectEntity> findProjectWarrantyByAdminReviewState(
			Short adminReviewState) {
		List<ProjectEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append(" select p.project_id,p.project_name,p.margin_scale,p.trade_money,p.employer_id,p.tender_user_id ")
			   .append(" from t_project p, t_project_warranty w ")
			   .append(" where p.project_id=w.project_id and w.admin_review_state=:adminReviewState and p.is_margin=:isMargin and p.employer_complete_time is not null and DATE_ADD(p.employer_complete_time,INTERVAL p.margin_day DAY)<=SYSDATE() ");
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("isMargin", Constants.PROJECT_IS_MARGIN_YES);
		sqlParameters.put("adminReviewState", adminReviewState);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectEntity.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public boolean updateProjectStateByProjectId(String projectId, Short state) {
		String sqlStmt = "update t_project set state=:state where project_id=:projectId";
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("state", state);
		sqlParameters.put("projectId", projectId);
		
		try{
			this.jdbcTemplate.update(sqlStmt, sqlParameters);
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean updateProjectWarranStateByProjectId(String projectId,
			Short adminReviewState) {
		String sqlStmt = "update t_project_warranty set admin_review_state=:adminReviewState where project_id=:projectId";
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("adminReviewState", adminReviewState);
		sqlParameters.put("projectId", projectId);
		
		try{
			this.jdbcTemplate.update(sqlStmt, sqlParameters);
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public List<ProjectEntity> findProjectRejectionByAdminReviewState(
			Short adminReviewState, Integer timer) {
		List<ProjectEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append(" select p.project_id,p.project_name,p.imprest_scale,p.trade_money,p.employer_id,p.tender_user_id ")
			   .append(" from t_project p, t_project_rejection w ")
			   .append(" where p.project_id=w.project_id and w.admin_review_state=:adminReviewState and p.state=:state and DATE_ADD(w.employer_apply_time,INTERVAL :timer DAY)<=SYSDATE() ");
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("state", Constants.PROJECT_STATE_CHECK);
		sqlParameters.put("adminReviewState", adminReviewState);
		sqlParameters.put("timer", timer);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectEntity.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public boolean updateProjectRejectionStateByProjectId(String projectId,
			Short adminReviewState) {
		String sqlStmt = "update t_project_rejection set admin_review_state=:adminReviewState where project_id=:projectId";
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("adminReviewState", adminReviewState);
		sqlParameters.put("projectId", projectId);
		
		try{
			this.jdbcTemplate.update(sqlStmt, sqlParameters);
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public List<ProjectEntity> findDelaySelectedProject(int day) {
		List<ProjectEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append(" select p.project_id,p.project_name,p.imprest_scale,p.trade_money,p.employer_id,p.tender_user_id ")
			   .append(" from t_project p  ")
			   .append(" where p.state=:state and DATE_ADD(p.submit_deadline,INTERVAL :day DAY)<SYSDATE() ")
			   .append(" and p.project_id not in (select project_id from t_project_reveal r where r.admin_review_state=:adminReviewState)");
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("state", Constants.PROJECT_STATE_SELECTED);
		sqlParameters.put("day", day);
		sqlParameters.put("adminReviewState", Constants.PROJECT_REVEALAPPLY_PASS);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectEntity.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ProjectEntity> findDelayToConfirmCommonProject() {
		List<ProjectEntity> list = null;
		
		String sqlStmt = "select project_id,project_name,employer_id from t_project where tender_type=:tenderType and state=:state and apply_deadline<SYSDATE()";
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("tenderType", Constants.PROJECT_TENDER_COMMON);
		sqlParameters.put("state", Constants.PROJECT_STATE_APPROVE_PASS);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectEntity.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ProjectEntity> findDelayToConfirmContainProject(int day) {
		List<ProjectEntity> list = null;
		
		String sqlStmt = "select project_id,project_name,employer_id from t_project where tender_type=:tenderType and state=:state and submit_deadline<DATE_ADD(SYSDATE(),INTERVAL :day DAY)";
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("tenderType", Constants.PROJECT_TENDER_CONTAIN);
		sqlParameters.put("state", Constants.PROJECT_STATE_APPROVE_PASS);
		sqlParameters.put("day", day);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectEntity.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ProjectEntity> findDelaySelectingProject(int day) {
		List<ProjectEntity> list = null;
		
		String sqlStmt = "select project_id,project_name,employer_id from t_project where state=:state and submit_deadline<DATE_ADD(SYSDATE(),INTERVAL :day DAY)";
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("state", Constants.PROJECT_STATE_SELECTING);
		sqlParameters.put("day", day);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectEntity.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ProjectStateTotalResponse> countStateByEmployerId(
			String employerId) {
		List<ProjectStateTotalResponse> list = null;
		
		String sqlStmt = "select count(project_id) total,state from t_project where employer_id=:employerId group by state";
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("employerId", employerId);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectStateTotalResponse.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
}
