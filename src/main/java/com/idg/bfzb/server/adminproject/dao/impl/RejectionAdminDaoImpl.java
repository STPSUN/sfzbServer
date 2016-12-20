package com.idg.bfzb.server.adminproject.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.idg.bfzb.server.adminproject.dao.RejectionAdminDao;
import com.idg.bfzb.server.adminproject.model.RejectionAdminRequest;
import com.idg.bfzb.server.adminproject.model.RejectionAdminResponse;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.file.model.dto.AttachmentEntity;

@Repository
public class RejectionAdminDaoImpl implements RejectionAdminDao{

	@Autowired
	private PageDao pageDao;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<RejectionAdminResponse> findProjectRejectionListByCond(
			RejectionAdminRequest projectRequest, PageRequest pageable) {
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
				+ " pr.employer_apply_time,"
				+ " pr.employer_attachs,"
				+ " pr.tender_review_state,"
				+ " pr.tender_reivew_reason,"
				+ " pr.tender_reivew_time,"
				+ " pr.tender_attachs,"
				+ " pr.admin_review_state,"
				+ " pr.create_time "
				+ " from t_project t, t_project_rejection pr"
				+ " where t.state != '-1' AND pr.project_id = t.project_id AND t.state = '8' ");
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
		sqlStmt.append(" order by pr.create_time desc ");
		pageable.getPageNumber();
		pageable.getPageSize();
		
		PageInfo<RejectionAdminResponse> pageInfo = new PageInfo<RejectionAdminResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,RejectionAdminResponse.class,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}
	
	@Override
	public boolean auditPassRejection(String projectId, String reason) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project_rejection set "
				+ " admin_review_state = '2',"
				+ " admin_review_reason = :reason "
				+ " where project_id = :prjectUuid ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", projectId);
		sqlParameters.put("reason", reason);
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
	public boolean auditNotPassRejection(String projectId, String reason) {
		
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_project_rejection set "
				+ " admin_review_state = '1',"
				+ " admin_review_reason = :reason "
				+ " where project_id = :prjectUuid ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", projectId);
		sqlParameters.put("reason", reason);
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
	public List<AttachmentEntity> getAttachsPath(String[] attachIds) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select attch.attch_url,attch.file_type,attch.attch_name "
				+ " from t_attachment attch"
				+ " where attch.attch_id in "
				+ "(");
		
		for (int i = 0; i < attachIds.length; i++) {
			if(i != 0){
				sqlStmt.append(",");
			}
			sqlStmt.append("'" + attachIds[i] + "'");
		}
		sqlStmt.append(")");
		Map<String, Object> sqlParameters = new HashMap<>();
		return this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters,BeanPropertyRowMapper.newInstance(AttachmentEntity.class));
	}

}
