package com.idg.bfzb.server.ability.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.ability.dao.AbilityRespositoryCust;
import com.idg.bfzb.server.ability.model.vo.AbilityVo;
import com.idg.bfzb.server.ability.model.vo.ProjectAbilityVo;
import com.idg.bfzb.server.common.dao.PageDao;


public class AbilityRespositoryImpl implements AbilityRespositoryCust{
    private Logger logger = LoggerFactory.getLogger(AbilityRespositoryImpl.class);
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PageDao pageDao;
    
    @Override
    public List<AbilityVo> queryAuditAbility(String userId){
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT t2.ability_id,t2.ability_name,t2.description,t1.state FROM ")
            .append(" (SELECT user_id,ability,state FROM t_user_ability_assoc WHERE user_id=:userId and state in (0,1)) t1 LEFT JOIN")
            .append(" (SELECT ability_id,ability_name,description FROM t_ability WHERE state = 0) t2")
            .append(" ON t1.ability = t2.ability_id where t2.ability_id is not null order by t2.ability_name");
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("userId",userId);
        
        logger.info("语句："+sqlStmt);

        List<AbilityVo> results = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(AbilityVo.class));
        return results;
    }
    
    @Override
    public List<AbilityVo> queryNoAuditAbility(String userId){
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT t.* FROM (")
            .append(" SELECT t1.user_id,t2.ability_id,t2.ability_name,t2.description,t1.state FROM ")
            .append(" (SELECT ability_id,ability_name,description FROM t_ability WHERE state = 0) t2 LEFT JOIN")
            .append(" (SELECT user_id,ability,state FROM t_user_ability_assoc WHERE user_id=:userId and state != -1) t1")
            .append(" ON t1.ability = t2.ability_id")
            .append(" ) t WHERE t.user_id IS NULL or t.state = 2 order by t.ability_name");
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("userId",userId);

        List<AbilityVo> results = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(AbilityVo.class));
        return results;
    }
    
    @Override
    public List<ProjectAbilityVo> queryProjectAbility(String projectId){
        StringBuilder sqlStmt = new StringBuilder();
//        sqlStmt.append("SELECT t2.ability_id,t2.ability_name,t2.description FROM")
//            .append(" (SELECT project_id,ability_id FROM t_project_ability WHERE project_id=:projectId) t1 LEFT JOIN")
//            .append(" (SELECT ability_id,ability_name,description FROM t_ability WHERE state = 0) t2")
//            .append(" ON t1.ability_id = t2.ability_id order by t2.ability_name");
        sqlStmt.append(" select cb.ability_id,a.ability_name,a.description ")
            .append(" from t_project_category pc left join t_category c on pc.category_id=c.category_id")
            .append(" left join t_category_ability cb on cb.state = 0 and c.category_id=cb.category_id ")
            .append(" left join t_ability a on cb.ability_id=a.ability_id")
            .append(" where pc.require_id=:projectId order by a.ability_name");
        
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("projectId", projectId);

        List<ProjectAbilityVo> results = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(ProjectAbilityVo.class));
        return results;
    }
    
}
