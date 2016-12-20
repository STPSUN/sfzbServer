package com.idg.bfzb.server.project.dao.impl;

import com.idg.bfzb.server.project.dao.SysModifiedRecordRepositoryCust;
import com.idg.bfzb.server.project.model.dto.SysModifiedRecordEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：SysModifiedRecordRepositoryImpl
 * 类描述：修改记录实现类
 * 创建人：jiangdong
 * 创建日期：2016/12/4
 */
public class SysModifiedRecordRepositoryImpl implements SysModifiedRecordRepositoryCust {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public <T> T findEntityByKey(String tableName, String keyField, Object keyValue, Class<T> entityClass) {
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT * FROM ").append(tableName)
                .append(" WHERE ").append(keyField).append("=:keyValue");
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("keyValue", keyValue);

        return jdbcTemplate.queryForObject(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(entityClass));
    }

	@Override
	public void insertModifiedRecord(SysModifiedRecordEntity mdEntry) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("insert into t_sys_modified_record "
				+ " (target_object,target_object_id,property,property_alias,content,modifier_id,modifier_real_name) "
				+ " values ("
				+ " :target_object,"
				+ " :target_object_id,"
				+ " :property,"
				+ " :property_alias,"
				+ " :content,"
				+ " :modifier_id,"
				+ " :modifier_real_name"
				+ ")");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("target_object", mdEntry.getTargetObject());
		sqlParameters.put("target_object_id", mdEntry.getTargetObjectId());
		sqlParameters.put("property", mdEntry.getProperty());
		sqlParameters.put("property_alias", mdEntry.getPropertyAlias());
		sqlParameters.put("content", mdEntry.getContent());
		sqlParameters.put("modifier_id", mdEntry.getModifierId());
		sqlParameters.put("modifier_real_name", mdEntry.getModifierRealName());
		try {
			jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
