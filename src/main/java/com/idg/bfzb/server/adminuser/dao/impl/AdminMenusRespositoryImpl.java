package com.idg.bfzb.server.adminuser.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.adminuser.dao.AdminMenusRespositoryCust;

public class AdminMenusRespositoryImpl implements AdminMenusRespositoryCust{
    private Logger logger = LoggerFactory.getLogger(AdminMenusRespositoryImpl.class);
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * 
     * .查询一级菜单
     * 
     * @param params
     * @return
     */
    public List levelOneMenus(Map<String, Object> params){
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT B.MENU_ID,B.MENU_NAME,B.URL,B.ICON_CLASS FROM uc_admin_menus B")
        .append(" WHERE B.STATE=0 AND B.LEVEL=1");
        
        Map<String, Object> sqlParameters = new HashMap<>();
        //sqlParameters.put("roleId", params.get("roleId"));
        
        logger.info("语句："+sqlStmt);
        return this.jdbcTemplate.queryForList(sqlStmt.toString(), sqlParameters);
    }
    
    /**
     * 
     * .查询二级菜单
     * 
     * @param params
     * @return
     */
    public List childrenMenus(Map<String, Object> params){
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT B.MENU_ID,B.MENU_NAME,B.URL,B.ICON_CLASS,B.P_MENU_ID FROM uc_admin_menus B")
            .append(" WHERE B.STATE=0 AND B.LEVEL=2");   
        
        Map<String, Object> sqlParameters = new HashMap<>();
        //sqlParameters.put("roleId", params.get("roleId"));
        
        logger.info("语句："+sqlStmt);
        return this.jdbcTemplate.queryForList(sqlStmt.toString(), sqlParameters);
    }
   
}
