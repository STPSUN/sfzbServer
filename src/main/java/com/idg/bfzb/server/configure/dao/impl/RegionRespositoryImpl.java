package com.idg.bfzb.server.configure.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.model.response.RegionResponse;

public class RegionRespositoryImpl {
	private Logger logger = LoggerFactory.getLogger(RegionRespositoryImpl.class);
    @Autowired
    private PageDao pageDao;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
    public PageInfo findRegionAll(Pageable pageable){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        PageInfo<RegionResponse> pageInfo = new PageInfo<RegionResponse>();
        
        sqlStmt.append("SELECT configure_id,")
            .append(" city_id,")
            .append(" city_code,")
            .append(" city_name")
            .append(" from uc_region_configure where state = 0");
        
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,RegionResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }
}
