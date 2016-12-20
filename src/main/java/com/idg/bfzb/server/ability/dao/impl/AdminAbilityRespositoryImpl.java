package com.idg.bfzb.server.ability.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.ability.dao.AdminAbilityRespositoryCust;
import com.idg.bfzb.server.ability.model.request.AdminAbilityRequest;
import com.idg.bfzb.server.ability.model.response.AdminAbilityResponse;
import com.idg.bfzb.server.ability.model.response.UserAbilityResponse;
import com.idg.bfzb.server.ability.model.vo.ProjectAbilityVo;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class AdminAbilityRespositoryImpl implements AdminAbilityRespositoryCust{
    private Logger logger = LoggerFactory.getLogger(AdminAbilityRespositoryImpl.class);
    @Autowired
    private PageDao pageDao;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @Override
    public PageInfo findUserAbilityList(HttpServletRequest servletRequest,Pageable pageable){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        PageInfo pageInfo = new PageInfo();
        
        String userName = servletRequest.getParameter("userName");
        String nickName = servletRequest.getParameter("nickName");
        String mobile = servletRequest.getParameter("mobile");
        String state = servletRequest.getParameter("state");
        
        sqlStmt.append(" SELECT a.user_id,a.user_name,a.nick_name,b.abilitys,a.mobile,b.state,  ")
        .append(" DATE_FORMAT(b.update_time, '%Y-%m-%d %H:%i:%s') create_time FROM")
        .append(" (SELECT t1.user_id,group_concat(t2.ability_name) abilitys,t1.update_time,t1.state FROM")
        .append(" (SELECT user_id,ability,update_time,state FROM t_user_ability_assoc WHERE state = :state) t1")
        .append(" LEFT JOIN (SELECT ability_id,ability_name FROM t_ability WHERE state = 0")
        .append(" ) t2 ON t1.ability = t2.ability_id group by t1.user_id) b LEFT JOIN")
        .append(" (SELECT user_id,user_name,nick_name,mobile FROM uc_user_info WHERE state = 0) a")
        .append(" ON a.user_id = b.user_id where a.user_id IS NOT NULL and b.abilitys is not null");
        
        if(userName != null && !"null".equals(userName) && !userName.isEmpty()){
            sqlStmt.append(" and a.user_name like :userName");
            sqlParameters.put("userName", "%"+userName+"%");
        }
        
        if(nickName != null && !"null".equals(nickName) && !nickName.isEmpty()){
            sqlStmt.append(" and a.nick_name like :nickName");
            sqlParameters.put("nickName", "%"+nickName+"%");
        }
        
        if(mobile != null && !"null".equals(mobile) && !mobile.isEmpty()){
            sqlStmt.append(" and a.mobile like :mobile");
            sqlParameters.put("mobile", "%"+mobile+"%");
        }
        
        sqlParameters.put("state", state);
        System.out.println("查询语句："+sqlStmt.toString());
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,UserAbilityResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }
    
    /**
     * 根据用户Id修改技能状态
     */
    @Override
    public boolean userAbilityModify(String userId,String state,String reason){
        System.out.println("userId:"+userId+"state:"+state);
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlStmt.append("UPDATE t_user_ability_assoc SET state = :state");
        if(reason != null && !reason.isEmpty()){
            sqlStmt.append(",reason = :reason");
            sqlParameters.put("reason", reason);
        }
        sqlStmt.append(" WHERE user_id = :userId and state = 0");
        
        sqlParameters.put("state", state);
        sqlParameters.put("userId", userId);
        this.jdbcTemplate.update(sqlStmt.toString(), sqlParameters);
        return true;
    }
    
    /**
     * 查询技能列表
     */
    @Override
    public PageInfo findAbilityByCond(AdminAbilityRequest adminAbilityRequest,Pageable pageable){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        
        sqlStmt.append("SELECT ability_id,")
            .append(" ability_name,")
            .append(" description,")
            .append(" state,")
            .append(" create_time,")
            .append(" update_time")
            .append(" FROM t_ability WHERE state = 0");
        
        if(!StringUtil.isNull(adminAbilityRequest.getAbilityName())){
            sqlStmt.append(" and ability_name LIKE :abilityName");
            sqlParameters.put("abilityName", "%"+adminAbilityRequest.getAbilityName()+"%");
        }
        
        sqlStmt.append(" order by create_time desc");
        
        PageInfo<AdminAbilityResponse> pageInfo = new PageInfo<AdminAbilityResponse>();
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,AdminAbilityResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }
}
