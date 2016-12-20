package com.idg.bfzb.server.usercenter.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.usercenter.dao.UcJpushCodeRepositoryCust;
import com.idg.bfzb.server.usercenter.model.dto.UcJpushCodeEntity;

public class UcJpushCodeRepositoryImpl implements UcJpushCodeRepositoryCust{
	
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
	@Override
	public List<UcJpushCodeEntity> findByCondition(UcJpushCodeEntity entity) {
		List<UcJpushCodeEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		
		sqlStmt.append(" select rec_id, user_uuid, jpush_code, create_time from uc_jpush_code where 1=1 ");
		if(StringUtils.isNotBlank(entity.getUserUuid())){
			sqlStmt.append(" and user_uuid=:userUuid");
			sqlParameters.put("userUuid", entity.getUserUuid());
		}
		if(StringUtils.isNotBlank(entity.getJpushCode())){
			sqlStmt.append(" and jpush_code=:jpushCode");
			sqlParameters.put("jpushCode", entity.getJpushCode());
		}
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(UcJpushCodeEntity.class));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
}
