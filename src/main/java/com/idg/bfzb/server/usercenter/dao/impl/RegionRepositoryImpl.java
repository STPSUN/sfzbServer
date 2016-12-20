package com.idg.bfzb.server.usercenter.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.model.AvailableEnum;
import com.idg.bfzb.server.team.model.dto.UserTeamEntity;
import com.idg.bfzb.server.usercenter.dao.RegionRepositoryCust;
import com.idg.bfzb.server.usercenter.model.dto.UcRegionEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：weibeifeng
 * 创建日期：2016/10/30
 */
public class RegionRepositoryImpl implements RegionRepositoryCust {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    private final static String COLUMN = "region_id,region_code,region_name,parent_code,region_level,region_order,"+
    							"full_spell,short_spell,state";
	@Override
	public UcRegionEntity findByRegionCode(String regionCode) {
		UcRegionEntity ucRegionEntity = new UcRegionEntity();
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT ").append(COLUMN).append(" FROM uc_region " +
                " WHERE region_code=:regionCode  and state=:state limit 1");

        Map<String,Object> sqlParamters = new HashMap<String,Object>();
        sqlParamters.put("regionCode",regionCode);
        sqlParamters.put("state",AvailableEnum.NORMAL.getValue());
        try {
        	ucRegionEntity = this.jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParamters, BeanPropertyRowMapper.newInstance(UcRegionEntity.class));
		} catch (Exception e) {
			System.out.println("数据库错误,或空数据"+e.getMessage());
			return null;
		}
		return ucRegionEntity;
	}
}
