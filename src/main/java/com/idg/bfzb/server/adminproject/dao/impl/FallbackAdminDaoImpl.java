package com.idg.bfzb.server.adminproject.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.idg.bfzb.server.adminproject.dao.FallbackAdminDao;
import com.idg.bfzb.server.adminproject.model.FallbackAdminRequest;
import com.idg.bfzb.server.adminproject.model.FallbackAdminResponse;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;

@Repository
public class FallbackAdminDaoImpl implements FallbackAdminDao{

	@Autowired
	private PageDao pageDao;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FallbackAdminResponse> findProjectRevelListByCond(
			FallbackAdminRequest projectRequest, PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select t.project_name, t.budget, t.is_reveal,t.reveal_scale,"
				+ " (select user_name from uc_user_info u where u.user_id = "
				+ "			(select tend.tender_user_id from t_project_tender tend where tend.project_id = t.project_id AND tend.state = '2')) tender_user_name,"//接包人账号
//				+ " (select user_name from uc_user_info u where u.user_id = t.employer_id) user_name,"
//				+ " (select nick_name from uc_user_info u where u.user_id = t.employer_id) nick_name,"
				+ " u.user_name,"
				+ " u.nick_name,"
				+ " pr.project_id,"
				+ " pr.mobile,"
				+ " pr.apply_content,"
				+ " pr.apply_time,"
				+ " pr.admin_review_state,"
				+ " pr.create_time "
				+ " from t_project t left join uc_user_info u on u.user_id = t.employer_id, t_project_reveal pr"
				+ " where t.state != '-1' AND pr.project_id = t.project_id ");
		Map<String, Object> sqlParameters = new HashMap<>();
		
		if(!StringUtils.isEmpty(projectRequest.getAdminReviewStateSea())){
			sqlStmt.append(" AND pr.admin_review_state = :admin_review_state ");
			sqlParameters.put("admin_review_state", projectRequest.getAdminReviewStateSea());
		}
		if(!StringUtils.isEmpty(projectRequest.getUserSea())){
			sqlStmt.append(" AND (u.nick_name like '%" + projectRequest.getUserSea() + "%' "
					+ " OR u.user_name like '%" + projectRequest.getUserSea() + "%' )");
		}
		if(!StringUtils.isEmpty(projectRequest.getStartTimeSea())){
			sqlStmt.append(" AND pr.create_time >= :start_time ");
			sqlParameters.put("start_time", projectRequest.getStartTimeSea());
		}
		if(!StringUtils.isEmpty(projectRequest.getEndTimeSea())){
			sqlStmt.append(" AND date_add(pr.create_time, interval -1 day) <= :end_time ");
			sqlParameters.put("end_time", projectRequest.getEndTimeSea());
		}
		
		pageable.getPageNumber();
		pageable.getPageSize();
		
		PageInfo<FallbackAdminResponse> pageInfo = new PageInfo<FallbackAdminResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,FallbackAdminResponse.class,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}
	@Override
	public boolean auditPassFallBack(String prjectUuid) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project_reveal set "
				+ " admin_review_state = '1' "
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
	public boolean auditNotPassFallBack(String prjectUuid) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project_reveal set "
				+ " admin_review_state = '2' "
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
	public boolean startFallback(String projectId, String contact) {
		return false;
	}
	@Override
	public void copyProjectCalatasByProjectUuid(String projectId,
			String newProjectId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("Insert into t_project_category(require_id,category_id) "
				+ " select '"+ newProjectId +"',t.category_id from  t_project_category t where t.require_id = :oldProjectId");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("oldProjectId", projectId);
		
		try {
			jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void copyProjectAbilitysByProjectUuid(String projectId,
			String newProjectId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("Insert into t_project_ability (project_id,ability_id) "
				+ " select '"+ newProjectId +"',t.ability_id from  t_project_ability t where t.project_id = :oldProjectId");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("oldProjectId", projectId);
		
		try {
			jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void copyProjectTendUsersByProjectUuid(String projectId,
			String newProjectId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("Insert into t_project_tender (project_id,tender_user_id,offer,plan,description,complete_time,state,opinion,mobile) "
				+ " select '"+ newProjectId +"',tender_user_id,offer,plan,description,complete_time,state,opinion,mobile from  t_project_tender t where t.project_id = :oldProjectId");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("oldProjectId", projectId);
		
		try {
			jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void copyProjectWarrantyByProjectUuid(String projectId,
			String newProjectId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("Insert into t_project_warranty (project_id,employer_reason,mobile,admin_review_state,admin_review_reason,admin_id,admin_review_time) "
				+ " select '"+ newProjectId +"',employer_reason,mobile,admin_review_state,admin_review_reason,admin_id,admin_review_time from  t_project_warranty t where t.project_id = :oldProjectId");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("oldProjectId", projectId);
		
		try {
			jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void copyProjectProgressByProjectUuid(String projectId,
			String newProjectId) {
		
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("Insert into t_project_progress (project_id,user_id,progress,event_user_id,event_content,event_type,event_address,photos,event_source,external_event_id,external_event_content) "
				+ " select '"+ newProjectId +"',user_id,progress,event_user_id,event_content,event_type,event_address,photos,event_source,external_event_id,external_event_content from  t_project_progress t where t.project_id = :oldProjectId");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("oldProjectId", projectId);
		
		try {
			jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
