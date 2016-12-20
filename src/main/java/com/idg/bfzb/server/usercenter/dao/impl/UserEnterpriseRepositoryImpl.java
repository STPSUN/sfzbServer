package com.idg.bfzb.server.usercenter.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.authentication.model.dto.UserEnterpriseEntity;
import com.idg.bfzb.server.usercenter.dao.UserEnterpriseRepositoryCust;

/**
 * 类名称：
 * 类描述：
 * 创建人：weibeifeng
 * 创建日期：2016/10/30
 */
public class UserEnterpriseRepositoryImpl implements UserEnterpriseRepositoryCust {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    private final static String COLUMN = "user_id,enterprise_id,enterprise_name,region_code,business_license,business_scope,corporate,"+
    						"mobile,business_license_image,review_state,review_admin_id,review_time,create_time,update_time";
    @Override
    public UserEnterpriseEntity findUserEnterpriseByUserId(String userId) {
    	UserEnterpriseEntity userEnterpriseEntity = new UserEnterpriseEntity();
    	StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT ").append(COLUMN).append(" FROM t_user_enterprise " +
                " WHERE user_id=:userId order by review_time desc limit 1");

        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("userId",userId);
        try {
        	userEnterpriseEntity = this.jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParamters, BeanPropertyRowMapper.newInstance(UserEnterpriseEntity.class));
		} catch (EmptyResultDataAccessException e) {
			System.out.println("企业认证数据为空");
			return null;
		}
        return userEnterpriseEntity;
    }
}
