package com.idg.bfzb.server.project.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.project.dao.ProjectPlanRepositoryCust;
import com.idg.bfzb.server.project.model.vo.ProjectPlanVo;

public class ProjectPlanRepositoryImpl implements ProjectPlanRepositoryCust{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<ProjectPlanVo> getProjectPlanByProjectId(String projectId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select plan_id,plan_content,plan_start_time,plan_end_time "
				+ " from t_project_plan t where t.state != '-1' "
				+ " AND project_id=:prjectUuid");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("prjectUuid", projectId);
		
		List<ProjectPlanVo> plan = new ArrayList<ProjectPlanVo>();
		try {
			plan = jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectPlanVo.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return plan;
	}

}
