package com.idg.bfzb.server.usercenter.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.usercenter.dao.ShortMessageRepositoryCust;
import com.idg.bfzb.server.usercenter.model.dto.UcAuthCodeEntity;

public class ShortMessageRepositoryImpl implements ShortMessageRepositoryCust{
	
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    /*select record_id,type,user_id,mobile,code,create_time 
    from uc_auth_code where create_time>DATE_SUB(SYSDATE(),INTERVAL 400 MINUTE) 
    and mobile='13295015141' order by create_time desc LIMIT 1*/
	/*@Override
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
	}*/

	@Override
	public List<UcAuthCodeEntity> selectVerificationCode(String mobile) {
		List<UcAuthCodeEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		
		sqlStmt.append(" select record_id,type,user_id,mobile,code,create_time ")
		       .append(" from uc_auth_code where create_time>DATE_SUB(SYSDATE(),INTERVAL :minute MINUTE)")
		       .append(" and mobile=:mobile order by create_time desc");
		
		sqlParameters.put("minute", Constants.UC_AUTHCODE_INVALID_MINUTE);
		sqlParameters.put("mobile", mobile);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(UcAuthCodeEntity.class));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int countByMobileAndCurrentDay(String mobile) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append(" select count(record_id) cnt from uc_auth_code ")
		       .append(" where mobile=:mobile and DATE_FORMAT(create_time,'%Y-%m-%d')=DATE_FORMAT(SYSDATE(),'%Y-%m-%d')");

		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("mobile", mobile);
		try {
        	Map<String, Object> map = this.jdbcTemplate.queryForMap(sqlStmt.toString(), sqlParameters);
        	Object cnt = map.get("cnt");
        	if(cnt!=null){
        		return Integer.valueOf(String.valueOf(cnt));
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}
