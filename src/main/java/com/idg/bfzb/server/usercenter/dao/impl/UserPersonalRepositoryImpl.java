package com.idg.bfzb.server.usercenter.dao.impl;

import com.idg.bfzb.server.usercenter.dao.UserPersonalRepositoryCust;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.model.vo.UserBaseAndPersonVo;
import com.idg.bfzb.server.usercenter.model.vo.UserBaseVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public class UserPersonalRepositoryImpl implements UserPersonalRepositoryCust {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UserBaseVo findUserVoByUserId(String userId) {
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT uc.user_id,uc.real_name,uc.nick_name,uc.user_name,uc.mobile,IFNULL(personal.balance,0) balance,IFNULL(personal.incoming,0) incoming," +
                "IFNULL(personal.expenditure,0) expenditure,personal.is_team_user,personal.is_enterprise_user,personal.last_province_code, personal.last_city_code,personal.last_area_code,personal.last_role," +
        		"uc.icon_attch_id,uc.icon_url,uc.icon_small_attch_id,uc.icon_small_url,uc.sex "+
                "FROM uc_user_info uc LEFT JOIN t_user_personal personal ON uc.user_id = personal.user_id " +
                "WHERE uc.user_id=:userId");

        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("userId",userId);

        return this.jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParamters, BeanPropertyRowMapper.newInstance(UserBaseVo.class));
    }

	@Override
	public List<UserBaseAndPersonVo> findUserPersonByCond(UserManagerRequest userManagerRequest) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT uc.user_id,uc.real_name,uc.nick_name,uc.user_name,uc.mobile,IFNULL(personal.balance,0),IFNULL(personal.incoming,0)," +
                "IFNULL(personal.expenditure,0),personal.is_team_user,personal.is_enterprise_user,personal.last_province_code, personal.last_city_code,personal.last_area_code," +
        		"uc.icon_attch_id,uc.icon_url,uc.icon_small_attch_id,uc.icon_small_url,uc.sex "+
                "FROM uc_user_info uc LEFT JOIN t_user_personal personal ON uc.user_id = personal.user_id " +
                "WHERE uc.user_id=:userId");

        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("userId","");

        return this.jdbcTemplate.query(sqlStmt.toString(),sqlParamters, 
        		BeanPropertyRowMapper.newInstance(UserBaseAndPersonVo.class));
	}

	@Override
	public int updateBalanceByUserId(String userId, Double fee) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update t_user_personal set balance = :balance " +
                "WHERE user_id=:userId");
        Map<String,Object> sqlParamters = new HashMap<String,Object>();
        sqlParamters.put("userId",userId);
        sqlParamters.put("balance",fee);
        return this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
	}
}
