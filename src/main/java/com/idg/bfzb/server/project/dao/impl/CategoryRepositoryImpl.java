package com.idg.bfzb.server.project.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.project.dao.CategoryRepositoryCust;
import com.idg.bfzb.server.project.model.dto.CategoryEntity;

public class CategoryRepositoryImpl implements CategoryRepositoryCust{
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Override
	public List<CategoryEntity> findAllCategory() {
		List<CategoryEntity> list = null;
		
		String sql = "select category_id,category_name,create_time,last_modified from t_category where state=0 and parent_id=0";
		
		try{
			list = this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(CategoryEntity.class));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

}
