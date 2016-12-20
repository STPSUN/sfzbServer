package com.idg.bfzb.server.adminuser.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.adminuser.dao.AdminUserRespositoryCust;

public class AdminUserRespositoryImpl implements AdminUserRespositoryCust{
    private Logger logger = LoggerFactory.getLogger(AdminUserRespositoryImpl.class);
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public int changeErrorNum(Map<String,Object> params){
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("UPDATE uc_admin SET error_num=:errorNum WHERE admin_account=:adminAccount");

        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("errorNum", params.get("errorNum"));
        sqlParameters.put("adminAccount", params.get("adminAccount"));
        
        return this.jdbcTemplate.update(sqlStmt.toString(), sqlParameters);
    }
    
    public int queryUserErrorNum(String adminAccount){
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT a.error_num FROM uc_admin a WHERE a.admin_account=:adminAccount and a.status=0");   
        
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("adminAccount", adminAccount);
        
        logger.info("语句："+sqlStmt);
        List<Map<String,Object>> result =this.jdbcTemplate.queryForList(sqlStmt.toString(), sqlParameters);
        if(result == null || result.isEmpty()){
            return -1;
        }else{
            return (int)result.get(0).get("error_num");
        }
    }
    
    public boolean updateLastOutTime(String adminUserId){
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update uc_admin set last_out_time = current_timestamp() WHERE admin_id=:adminUserId and status=0");   
        
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("adminUserId", adminUserId);
        
        int result =this.jdbcTemplate.update(sqlStmt.toString(), sqlParameters);
        if(result != 1){
            return false;
        }else{
            return true;
        }
    }
}
