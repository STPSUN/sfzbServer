package com.idg.bfzb.server.project.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.project.dao.ProjectProgressRepositoryCust;
import com.idg.bfzb.server.project.model.dto.ProjectProgressEntity;
import com.idg.bfzb.server.project.model.vo.ProjectProgressVo;

public class ProjectProgressRepositoryImpl implements ProjectProgressRepositoryCust {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private final static String COLUMN = "record_id,user_id,project_id,progress,event_user_id,event_content,event_type,event_address,photos,create_time,update_time,event_source,external_event_id,external_event_content ";

	@Override
	public List<ProjectProgressEntity> findProjectProgressByProjectIdAndEventType(
			String projectId, String eventType) {
		List<ProjectProgressEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();

		sqlStmt.append(" select ").append(COLUMN)
        	   .append(" from t_project_progress where project_id=:projectId and event_type=:eventType order by create_time desc ");

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
	public List<ProjectProgressVo> findProjectProgressVoByProjectIdAndEventType(
			String projectId, String eventType) {
		List<ProjectProgressVo> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();

		sqlStmt.append(" select event_content work_desc, create_time work_time, photos")
        	   .append(" from t_project_progress where project_id=:projectId and event_type=:eventType order by create_time desc ");

		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("projectId", projectId);
		sqlParameters.put("eventType", eventType);
		
        try {
        	list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectProgressVo.class));
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
