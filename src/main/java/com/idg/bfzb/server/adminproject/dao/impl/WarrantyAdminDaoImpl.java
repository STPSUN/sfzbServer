package com.idg.bfzb.server.adminproject.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.idg.bfzb.server.adminproject.dao.WarrantyAdminDao;
import com.idg.bfzb.server.adminproject.model.WarrantyAdminRequest;
import com.idg.bfzb.server.adminproject.model.WarrantyAdminResponse;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;

@Repository
public class WarrantyAdminDaoImpl implements WarrantyAdminDao{

	@Autowired
	private PageDao pageDao;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<WarrantyAdminResponse> findProjectWarrantyListByCond(
			WarrantyAdminRequest projectRequest, PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select t.project_name,t.tender_type, t.state project_state,"
				+ " (select user_name from uc_user_info u where u.user_id = "
				+ "			(select tend.tender_user_id from t_project_tender tend where tend.project_id = t.project_id AND tend.state = '2')) tender_user_name,"//接包人账号
				+ " (select user_name from uc_user_info u where u.user_id = "
				+ "			(select tend.tender_user_id from t_project_tender tend where tend.project_id = t.project_id AND tend.state = '2')) tender_nick_name,"//接包人昵称
				+ " (select user_name from uc_user_info u where u.user_id = t.employer_id) emploper_user_name,"
				+ " (select nick_name from uc_user_info u where u.user_id = t.employer_id) emploper_nick_name,"
				+ " pr.project_id,"
				+ " pr.employer_reason,"
				+ " pr.mobile,"
				+ " pr.admin_review_state,"
				+ " pr.admin_review_reason,"
				+ " (select u.admin_account from uc_admin u where u.admin_id = pr.admin_id) admin_id,"
				+ " pr.admin_review_time,"
				+ " pr.create_time,"
				+ " pr.update_time"
				+ " from t_project t, t_project_warranty pr, uc_user_info u "
				+ " where t.state != '-1' AND t.employer_id = u.user_id AND pr.project_id = t.project_id AND t.state = '7' ");
		Map<String, Object> sqlParameters = new HashMap<>();
		
		if(!StringUtils.isEmpty(projectRequest.getAdminReviewStateSea())){
			sqlStmt.append(" AND pr.admin_review_state = :admin_review_state ");
			sqlParameters.put("admin_review_state", projectRequest.getAdminReviewStateSea());
		}
		if(!StringUtils.isEmpty(projectRequest.getProjectNameSea())){
			sqlStmt.append(" AND t.project_name like '%" + projectRequest.getProjectNameSea() + "%' ");
		}
		if(!StringUtils.isEmpty(projectRequest.getQryBeginTime())){
			sqlStmt.append(" AND pr.create_time >= :start_time ");
			sqlParameters.put("start_time", projectRequest.getQryBeginTime());
		}
		if(!StringUtils.isEmpty(projectRequest.getQryEndTime())){
			sqlStmt.append(" AND date_add(pr.create_time, interval -1 day) <= :end_time ");
			sqlParameters.put("end_time", projectRequest.getQryEndTime());
		}
		
		if(!StringUtils.isEmpty(projectRequest.getUserKeyWordSea())){
			sqlStmt.append(" AND u.user_name like '%" + projectRequest.getUserKeyWordSea() + "%' ");
		}
		
		sqlStmt.append(" order by pr.create_time desc ");
		pageable.getPageNumber();
		pageable.getPageSize();
		
		PageInfo<WarrantyAdminResponse> pageInfo = new PageInfo<WarrantyAdminResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,WarrantyAdminResponse.class,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}
	@Override
	public boolean auditPassWarranty(String projectId, String reason,String userId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project_warranty set "
				+ " admin_review_state = '2',"
				+ " admin_id = :userId,"
				+ " admin_review_reason = :reason "
				+ " where project_id = :prjectUuid ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", projectId);
		sqlParameters.put("reason", reason);
		sqlParameters.put("userId", userId);
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
	public boolean auditNotPassWarranty(String projectId, String reason,String userId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project_warranty set "
				+ " admin_review_state = '1',"
				+ " admin_id = :userId,"
				+ " admin_review_reason = :reason "
				+ " where project_id = :prjectUuid ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", projectId);
		sqlParameters.put("reason", reason);
		sqlParameters.put("userId", userId);
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


}
