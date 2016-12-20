package com.idg.bfzb.server.adminproject.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.idg.bfzb.server.adminproject.dao.TenderAdminDao;
import com.idg.bfzb.server.adminproject.model.TenderAdminRequest;
import com.idg.bfzb.server.adminproject.model.TenderAdminResponse;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;

@Repository
public class TenderAdminDaoImpl implements TenderAdminDao{

	@Autowired
	private PageDao pageDao;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<TenderAdminResponse> findProjectTenderListByCond(
			TenderAdminRequest projectRequest, PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select t.project_name,t.tender_type, t.state project_state, t.is_margin,"
				+ " (select user_name from uc_user_info u where u.user_id = "
				+ "			pr.tender_user_id) tender_user_name,"//接包人账号
				+ " (select user_name from uc_user_info u where u.user_id = "
				+ "			pr.tender_user_id) tender_nick_name,"//接包人昵称
				+ " (select user_name from uc_user_info u where u.user_id = t.employer_id) emploper_user_name,"
				+ " (select nick_name from uc_user_info u where u.user_id = t.employer_id) emploper_nick_name,"
				+ " pr.project_id,"
				+ " pr.record_id,"
				+ " pr.offer,"
				+ " pr.plan,"
				+ " pr.description,"
				+ " pr.complete_time,"
				+ " pr.state,"
				+ " pr.create_time,"
				+ " pr.update_time"
				+ " from t_project t, t_project_tender pr"
				+ " where t.state != '-1' AND pr.project_id = t.project_id ");
		Map<String, Object> sqlParameters = new HashMap<>();
		if(!StringUtils.isEmpty(projectRequest.getStateSea())){
			sqlStmt.append(" AND pr.state = :state ");
			sqlParameters.put("state", projectRequest.getStateSea());
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
		pageable.getPageNumber();
		pageable.getPageSize();
		
		PageInfo<TenderAdminResponse> pageInfo = new PageInfo<TenderAdminResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,TenderAdminResponse.class,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}

}
