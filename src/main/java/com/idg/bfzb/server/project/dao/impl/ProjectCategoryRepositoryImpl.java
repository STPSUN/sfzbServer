package com.idg.bfzb.server.project.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.project.dao.ProjectCategoryRepositoryCust;
import com.idg.bfzb.server.project.model.vo.ProjectCategoryAbilityVo;

public class ProjectCategoryRepositoryImpl implements ProjectCategoryRepositoryCust {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<ProjectCategoryAbilityVo> findProjectCategoryAbilityByProjectId(String projectId) {
		List<ProjectCategoryAbilityVo> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();

		sqlStmt.append(" select pc.require_id project_id,pc.category_id,cb.ability_id,a.ability_name")
		       .append(" from t_project_category pc, t_category c, t_category_ability cb, t_ability a ")
		       .append(" where pc.category_id=c.category_id and cb.state = 0 and c.category_id=cb.category_id and cb.ability_id=a.ability_id and pc.require_id=:projectId");

		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("projectId", projectId);
		
        try {
        	list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectCategoryAbilityVo.class));
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
