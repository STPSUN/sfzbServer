package com.idg.bfzb.server.configure.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.model.request.AdminCategoryRequest;
import com.idg.bfzb.server.configure.model.response.AdminCategoryResponse;
import com.idg.bfzb.server.configure.model.response.CategoryLinkAbilityResponse;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class CategoryRespositoryImpl {
    private Logger logger = LoggerFactory.getLogger(CategoryRespositoryImpl.class);
    @Autowired
    private PageDao pageDao;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public PageInfo queryCategoryByCond(AdminCategoryRequest adminCategoryRequest,Pageable pageable){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        PageInfo<AdminCategoryResponse> pageInfo = new PageInfo<AdminCategoryResponse>();
        
        sqlStmt.append("SELECT tc.category_id,")
            .append(" tc.category_name,")
            .append(" tc.create_time,")
            .append(" tc.last_modified,")
            .append(" tc.parent_id,")
            .append(" (SELECT tc2.category_name FROM t_category tc2 WHERE tc.parent_id = tc2.category_id AND tc2.state = 0) parent_name,")
            .append(" (SELECT GROUP_CONCAT(ta.ability_name) FROM t_category_ability tca2")
            .append(" LEFT JOIN t_ability ta ON tca2.state = 0 AND ta.state = 0 and tca2.ability_id = ta.ability_id")
            .append(" WHERE tca2.category_id = tc.category_id ) abilityNames")
            .append(" FROM t_category tc WHERE tc.state = 0 AND parent_id IS NOT NULL");
        
        if(!StringUtil.isNull(adminCategoryRequest.getCategoryName())){
            sqlStmt.append(" and tc.category_name LIKE :categoryName");
            sqlParameters.put("categoryName", "%"+adminCategoryRequest.getCategoryName()+"%");
        }
        sqlStmt.append(" order by tc.create_time desc");
        
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,AdminCategoryResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }
    
    public List<CategoryLinkAbilityResponse> queryCategoryAbilityByCategoryId(AdminCategoryRequest adminCategoryRequest){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        
        if(!StringUtil.isNull(adminCategoryRequest.getCategoryId())){
            sqlStmt.append("SELECT ta.ability_id,")
            .append(" ta.ability_name,")
            .append(" (CASE WHEN tca.category_id IS NOT NULL THEN 1 ELSE 0 END) is_link")
            .append(" FROM t_ability ta LEFT JOIN t_category_ability tca ON tca.state = 0 and tca.category_id = :categoryId")
            .append(" AND tca.ability_id = ta.ability_id WHERE ta.state = 0");
            
            sqlParameters.put("categoryId", adminCategoryRequest.getCategoryId());
        }else{
            sqlStmt.append("SELECT ta.ability_id,")
            .append(" ta.ability_name,")
            .append(" '0' is_link")
            .append(" FROM t_ability ta ")
            .append(" WHERE ta.state = 0");
        }
        
        List<CategoryLinkAbilityResponse> categoryAbilityList = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters,
            BeanPropertyRowMapper.newInstance(CategoryLinkAbilityResponse.class));
        
        return categoryAbilityList;
    }
    
    public boolean addCategory(AdminCategoryRequest adminCategoryRequest){
        boolean flag = false;
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        List<Map<String, Object>> sqlParametersList = new ArrayList<Map<String, Object>>();
        //分类目录表插入数据
        String categoryId = UUID.randomUUID().toString();
        sqlStmt.append("insert into t_category (category_id,category_name,parent_id)")
        .append(" values (:categoryId,:categoryName,0)");
        sqlParameters.put("categoryId", categoryId);
        sqlParameters.put("categoryName", adminCategoryRequest.getCategoryName());
        try {
            this.jdbcTemplate.update(sqlStmt.toString(), sqlParameters);
            flag = true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        //分类技能关联表插入数据
        if(flag){
            sqlStmt = new StringBuilder();
            sqlStmt.append("insert into t_category_ability (category_id,ability_id)")
            .append(" values(:categoryId,:abilityId)");
            int abilitySize = adminCategoryRequest.getAbilityIds().size();
            for(int i = 0; i < abilitySize; i++){
                sqlParameters = new HashMap<>();
                sqlParameters.put("categoryId", categoryId);
                sqlParameters.put("abilityId", adminCategoryRequest.getAbilityIds().get(i));
                sqlParametersList.add(sqlParameters);
            }
            try {
                this.jdbcTemplate.batchUpdate(sqlStmt.toString(), (Map<String, Object>[] ) sqlParametersList.toArray(new Map[abilitySize]));
            } catch (Exception e) {
                // TODO: handle exception
                flag = false;
            }
        }
        
        return flag;
    }
    
    public boolean updateCategoryAbility(AdminCategoryRequest adminCategoryRequest){
        boolean flag = false;
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        List<Map<String, Object>> sqlParametersList = new ArrayList<Map<String, Object>>();
        //清除旧的分类技能关联
        sqlStmt.append("update t_category_ability set state = -1 where category_id = :categoryId");
        sqlParameters.put("categoryId", adminCategoryRequest.getCategoryId());
        try {
            this.jdbcTemplate.update(sqlStmt.toString(), sqlParameters);
            flag = true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        //插入,修改新的分类技能关联
        if(flag){
            sqlStmt = new StringBuilder();
            sqlStmt.append(" insert into t_category_ability (category_id,ability_id,state) values(:categoryId,:abilityId,0)")
            .append(" ON DUPLICATE KEY UPDATE state = 0");
            int abilitySize = adminCategoryRequest.getAbilityIds().size();
            for(int i = 0; i < abilitySize; i++){
                sqlParameters = new HashMap<>();
                sqlParameters.put("categoryId", adminCategoryRequest.getCategoryId());
                sqlParameters.put("abilityId", adminCategoryRequest.getAbilityIds().get(i));
                sqlParametersList.add(sqlParameters);
            }
            try {
                this.jdbcTemplate.batchUpdate(sqlStmt.toString(), (Map<String, Object>[] ) sqlParametersList.toArray(new Map[abilitySize]));
            } catch (Exception e) {
                // TODO: handle exception
                flag = false;
            }
        }
        
        return flag;
    }
}
