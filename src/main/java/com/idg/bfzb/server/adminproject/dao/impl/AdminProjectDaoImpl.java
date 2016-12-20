package com.idg.bfzb.server.adminproject.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.idg.bfzb.server.ability.model.vo.ProjectAbilityVo;
import com.idg.bfzb.server.adminproject.dao.AdminProjectDao;
import com.idg.bfzb.server.adminproject.model.ProjectAdminRequest;
import com.idg.bfzb.server.adminproject.model.ProjectAdminResponse;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.model.dto.CategoryEntity;
import com.idg.bfzb.server.project.model.dto.ProjectPlanEntity;
import com.idg.bfzb.server.project.model.dto.ProjectProgressEntity;
import com.idg.bfzb.server.project.model.dto.SysModifiedRecordEntity;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;

@Repository
public class AdminProjectDaoImpl implements AdminProjectDao{

	@Autowired
	private PageDao pageDao;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<ProjectAdminResponse> findProjectListByCond(
			ProjectAdminRequest projectRequest, PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select project_id,project_name,"
				+ " employer_id,"
//				+ " (select user_name from uc_user_info u where u.user_id = t.employer_id) user_name,"
//				+ " (select nick_name from uc_user_info u where u.user_id = t.employer_id) nick_name,"
				+ " user_name,nick_name,"
				+ " budget,category_ids,apply_deadline,sign_up_count,"
				+ " submit_deadline,description,project_type,tender_type,region_detail,region_code,region_name,t.state,"
				+ " t.review_time,t.create_time,trade_money,imprest_scale,margin_scale,reveal_scale,is_reveal,telephone "
				+ " from t_project t left join uc_user_info u on u.user_id = t.employer_id "
				+ " where t.state != '-1' ");
		Map<String, Object> sqlParameters = new HashMap<>();
		
		if(!StringUtils.isEmpty(projectRequest.getReviewStateSea())){
			sqlStmt.append(" AND t.state = :state ");
			sqlParameters.put("state", projectRequest.getReviewStateSea());
		}
		if(!StringUtils.isEmpty(projectRequest.getUserKeyWordSea())){
			sqlStmt.append(" AND (u.nick_name like '%" + projectRequest.getUserKeyWordSea() + "%' "
					+ " OR u.user_name like '%" + projectRequest.getUserKeyWordSea() + "%' )");
		}
		if(!StringUtils.isEmpty(projectRequest.getAreaSea())){
			sqlStmt.append(" AND t.region_detail like '%" + projectRequest.getAreaSea() + "%' ");
		}
		if(!StringUtils.isEmpty(projectRequest.getTenderTypeSea())){
			sqlStmt.append(" AND t.tender_type = :tender_type ");
			sqlParameters.put("tender_type", projectRequest.getTenderTypeSea());
		}
		if(!StringUtils.isEmpty(projectRequest.getStartTimeSea())){
			sqlStmt.append(" AND t.create_time >= :start_time ");
			sqlParameters.put("start_time", projectRequest.getStartTimeSea());
		}
		if(!StringUtils.isEmpty(projectRequest.getEndTimeSea())){
			sqlStmt.append(" AND date_add(t.create_time, interval -1 day) <= :end_time ");
			sqlParameters.put("end_time", projectRequest.getEndTimeSea());
		}
		
		sqlStmt.append(" order by t.create_time DESC ");
		pageable.getPageNumber();
		pageable.getPageSize();
		
		PageInfo<ProjectAdminResponse> pageInfo = new PageInfo<ProjectAdminResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,ProjectAdminResponse.class,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}

	@Override
	public ProjectAdminResponse findProjectById(String prjectUuid) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select t.project_id,t.project_name,t.review_reason, "
				+ " t.employer_id,"
				+ " user_name,nick_name,real_name,"
				+ " u.mobile ,"
				+ " (select up.idcard_code from t_user_personal up where up.user_id = t.tender_user_id) idcard_code,"
				+ " (select count(1) from t_evaluate_record tr where tr.target_id = t.project_id) his_order_count,"
				+ " (select sum(tr.star) from t_evaluate_record tr where tr.target_id = t.project_id) count_stars,"
				+ " (select u.mobile from uc_user_info u where u.user_id = t.tender_user_id) user_mobel,"
				+ " (select user_name from uc_user_info u where u.user_id = t.tender_user_id) tender_user_name,"
				+ " (select nick_name from uc_user_info u where u.user_id = t.tender_user_id) tender_nick_name,"
				+ " (select real_name from uc_user_info u where u.user_id = t.tender_user_id) tender_real_name,"
				+ " budget,category_ids,apply_deadline,sign_up_count,"
				+ " t.submit_deadline,t.description,t.project_type,tender_type,region_detail,region_code,region_name,t.state,"
				+ " review_time,t.create_time,trade_money,imprest_scale,margin_scale,reveal_scale,is_reveal,t.telephone,t.update_time,is_margin,margin_day "
				+ " from t_project t left join uc_user_info u on u.user_id = t.employer_id "
				+ " where t.state != '-1' "
				+ " AND project_id=:prjectUuid");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", prjectUuid);
		
		ProjectAdminResponse project = new ProjectAdminResponse();
		try {
			project = jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParameters, BeanPropertyRowMapper.newInstance(ProjectAdminResponse.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return project;
	}

	@Override
	public boolean addProjectPlan(ProjectPlanEntity plan) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("insert into t_project_plan "
				+ " (plan_id,project_id,plan_start_time,plan_end_time,plan_content) "
				+ " values ("
				+ " :plan_id,"
				+ " :project_id,"
				+ " :plan_start_time,"
				+ " :plan_end_time,"
				+ " :plan_content"
				+ ")");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("plan_id", plan.getPlanId());
		sqlParameters.put("project_id", plan.getProjectId());
		sqlParameters.put("plan_start_time", plan.getPlanStartTime());
		sqlParameters.put("plan_end_time", plan.getPlanEndTime());
		sqlParameters.put("plan_content", plan.getPlanContent());
		
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;
	}

	@Override
	public boolean updateProjectPlan(ProjectPlanEntity plan) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project_plan set "
				+ " plan_start_time = :plan_start_time,"
				+ " plan_end_time = :plan_end_time,"
				+ " plan_content = :plan_content"
				+ " where plan_id = :plan_id ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("plan_id", plan.getPlanId());
		sqlParameters.put("plan_start_time", plan.getPlanStartTime());
		sqlParameters.put("plan_end_time", plan.getPlanEndTime());
		sqlParameters.put("plan_content", plan.getPlanContent());
		
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;
	}

	@Override
	public List<ProjectPlanEntity> queryPlansByProjectId(String prjectUuid) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select plan_id,plan_content,plan_start_time,plan_end_time "
				+ " from t_project_plan t where t.state != '-1' "
				+ " AND project_id=:prjectUuid");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", prjectUuid);
		
		List<ProjectPlanEntity> plan = new ArrayList<ProjectPlanEntity>();
		try {
			plan = jdbcTemplate.query(sqlStmt.toString(),sqlParameters, BeanPropertyRowMapper.newInstance(ProjectPlanEntity.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return plan;
	}

	@Override
	public boolean deleteProjectPlan(String planId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project_plan set "
				+ " state = '-1' "
				+ " where plan_id = :plan_id ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("plan_id", planId);
		
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;
	}

	@Override
	public boolean auditPassProject(String prjectUuid) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project set "
				+ " state = '5' "
				+ " where project_id = :prjectUuid ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", prjectUuid);
		
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;

	}

	@Override
	public boolean auditNotPassProject(String prjectUuid,String reviewReason) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project set "
				+ " state = '9' ,"
				+ " review_reason = :reviewReason "
				+ " where project_id = :prjectUuid ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", prjectUuid);
		sqlParameters.put("reviewReason", reviewReason);
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;
	}

	@Override
	public boolean auditCustomerConfirmProject(ProjectAdminResponse project) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project set "
				+ " state = '3',"
				+ " project_name = :projectName,"
				+ " budget = :budget,"
				+ " tender_type = :tenderType ,"
				+ " region_detail = :regionDetail ,"
				+ " telephone = :telephone ,"
				+ " description = :description ,"
				+ " apply_deadline = :applyDeadline ,"
				+ " submit_deadline = :submitDeadline ,"
				+ " margin_scale = :marginScale ,"
				+ " reveal_scale = :revealScale ,"
				+ " margin_day = :marginDay ,"
				+ " is_reveal = :isReveal ,"
				+ " is_margin = :isMargin "
				+ " where project_id = :prjectUuid ");
		
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", project.getProjectId());
		sqlParameters.put("projectName", project.getProjectName());
		sqlParameters.put("budget", project.getBudget());
		sqlParameters.put("tenderType", project.getTenderType());
		sqlParameters.put("regionDetail", project.getRegionDetail());
		sqlParameters.put("telephone", project.getTelephone());
		sqlParameters.put("description", project.getDescription());
		sqlParameters.put("applyDeadline", project.getApplyDeadline());
		sqlParameters.put("submitDeadline", project.getSubmitDeadline());
		sqlParameters.put("marginScale", project.getMarginScale());
		sqlParameters.put("revealScale", project.getRevealScale());
		sqlParameters.put("marginDay", project.getMarginDay());
		sqlParameters.put("isReveal", project.getIsReveal());
		sqlParameters.put("isMargin", project.getIsMargin());
		
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;
	}

	@Override
	public List<CategoryEntity> findProjectCategoryById(String prjectUuid) {
		StringBuilder sqlStmt = new StringBuilder();
		sqlStmt.append("SELECT "
				+" 	c.category_id, "
				+" 	c.category_name "
				+" FROM "
				+" 	t_project_category pc, "
				+" 	t_category c "
				+" WHERE "
				+" 	pc.require_id = :prjectUuid  "
				+" AND pc.category_id = c.category_id AND c.state != '-1' ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", prjectUuid);
		
		List<CategoryEntity> cates = new ArrayList<CategoryEntity>();
		try {
			cates = jdbcTemplate.query(sqlStmt.toString(),sqlParameters, BeanPropertyRowMapper.newInstance(CategoryEntity.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return cates;
	}

	@Override
	public boolean updateProjectState(String prjectUuid, String state) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project set "
				+ " state = :state "
				+ " where project_id = :prjectUuid ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", prjectUuid);
		sqlParameters.put("state", state);
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;
	}
    @Override
    public List<ProjectAbilityVo> queryProjectAbility(String projectId){
        StringBuilder sqlStmt = new StringBuilder();
        
        sqlStmt.append(" select cb.ability_id,a.ability_name,a.description ")
            .append(" from t_project_category pc,t_category c, t_category_ability cb , t_ability a ")
            .append(" where pc.require_id=:projectId "
            		+ " AND pc.category_id=c.category_id"
            		+ " AND cb.state = 0 "
            		+ " AND c.category_id=cb.category_id "
            		+ " AND cb.ability_id=a.ability_id"
            		+ " order by a.ability_name");
        
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("projectId", projectId);

        List<ProjectAbilityVo> results = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(ProjectAbilityVo.class));
        return results;
    }

	@Override
	public List<SysModifiedRecordEntity> queryUpdateLogByProjectId(
			String prjectUuid) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select target_object,target_object_id,property,property_alias,content,modifier_id,modifier_real_name,create_date "
				+ " from t_sys_modified_record t "
				+ " where target_object_id=:prjectUuid");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", prjectUuid);
		
		List<SysModifiedRecordEntity> plan = new ArrayList<SysModifiedRecordEntity>();
		try {
			plan = jdbcTemplate.query(sqlStmt.toString(),sqlParameters, BeanPropertyRowMapper.newInstance(SysModifiedRecordEntity.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return plan;
	}
	@Override
	public List<ProjectProgressEntity> findProjectProgressByProjectIdAndEventType(
			String projectId, String eventType) {
		List<ProjectProgressEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		String COLUMN = "record_id,user_id,project_id,progress,event_content,event_type,event_address,photos,create_time,update_time,event_source,external_event_id,external_event_content,"
				+ " (select u.real_name from uc_user_info u where u.user_id = t.event_user_id) event_user_id ";
		sqlStmt.append(" select ").append(COLUMN)
        	   .append(" from t_project_progress t where project_id=:projectId and event_type=:eventType order by create_time desc ");

		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("projectId", projectId);
		sqlParameters.put("eventType", eventType);
		
        try {
        	list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectProgressEntity.class));
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public UcUserInfoEntity queryUserByTelephone(String telephone) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select current_timestamp() create_time,t.mobile,t.real_name,t.user_id "
				+ " from uc_user_info t"
				+ " where t.state != '-1' "
				+ " AND t.mobile =:telephone limit 1");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("telephone", telephone);
		
		UcUserInfoEntity user = new UcUserInfoEntity();
		try {
			user = jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParameters, BeanPropertyRowMapper.newInstance(UcUserInfoEntity.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return user;
	}

	@Override
	public UcUserInfoEntity queryUserByUserId(String userId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select create_time,t.mobile,t.real_name,t.user_id "
				+ " from uc_user_info t"
				+ " where t.state != '-1' "
				+ " AND t.user_id =:userId limit 1");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("userId", userId);
		
		UcUserInfoEntity user = new UcUserInfoEntity();
		try {
			user = jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParameters, BeanPropertyRowMapper.newInstance(UcUserInfoEntity.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return user;
	}
}
