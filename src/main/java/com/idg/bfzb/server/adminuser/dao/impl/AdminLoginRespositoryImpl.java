package com.idg.bfzb.server.adminuser.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.adminuser.dao.AdminLoginRespositoryCust;
import com.idg.bfzb.server.adminuser.model.vo.LoginUserMsgVo;

public class AdminLoginRespositoryImpl implements AdminLoginRespositoryCust{
    private Logger logger = LoggerFactory.getLogger(AdminLoginRespositoryImpl.class);
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public List<LoginUserMsgVo> queryUserInfoByParmas(Map<String, Object> params){
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT a.admin_id,a.admin_account,a.password,a.error_num,a.create_time,a.last_login_time,a.last_out_time,c.role_name,c.role_id")
            .append(" FROM uc_admin a LEFT JOIN (uc_user_role b LEFT JOIN uc_role c ON b.role_id=c.role_id)")
            .append(" ON a.admin_id=b.user_id WHERE a.admin_account=:adminAccount AND a.password=:password")
            .append(" and a.status=0");
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("adminAccount",params.get("adminAccount"));
        sqlParameters.put("password",params.get("password"));
        
        logger.info("语句："+sqlStmt);

        List<LoginUserMsgVo> results = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(LoginUserMsgVo.class));
        return results;
    }
}
