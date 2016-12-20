package com.idg.bfzb.server.team.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.team.dao.UserTeamRepositoryCust;
import com.idg.bfzb.server.team.model.dto.UserTeamEntity;

public class UserTeamRepositoryImpl implements UserTeamRepositoryCust {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    private final static String COLUMN = "team_id,team_leader_id,team_name,team_nick_name,region_code,team_skills,service_content,"+
    							"experience,contacts_mobile,contacts_idcard_code,contacts_real_name,review_state,review_admin_id,review_time";
    @Override
    public UserTeamEntity findUserTeamByUserId(String teamLeaderId) {
    	UserTeamEntity userTeamEntity = new UserTeamEntity();
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT ").append(COLUMN).append(" FROM t_user_team " +
                " WHERE team_leader_id=:teamLeaderId order by review_time desc limit 1");

        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("teamLeaderId",teamLeaderId);
        try {
        	userTeamEntity = this.jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParamters, BeanPropertyRowMapper.newInstance(UserTeamEntity.class));
		} catch (Exception e) {
			System.out.println("团队认证数据为空");
			return null;
		}
        return userTeamEntity;
    }
	@Override
	public UserTeamEntity findUserTeamByTeamName(String teamName) {
		UserTeamEntity userTeamEntity = new UserTeamEntity();
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT ").append(COLUMN).append(" FROM t_user_team " +
                " WHERE team_name=:teamName and review_state in (0,2,3) order by review_time desc limit 1");

        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("teamName", teamName);
        try {
        	userTeamEntity = this.jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParamters, BeanPropertyRowMapper.newInstance(UserTeamEntity.class));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
        return userTeamEntity;
	}
}
