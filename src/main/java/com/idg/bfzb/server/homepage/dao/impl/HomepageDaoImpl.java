package com.idg.bfzb.server.homepage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.idg.bfzb.server.homepage.dao.HomepageDao;
import com.idg.bfzb.server.homepage.model.dto.ContAdvertisementEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：chen
 * 创建日期：2016/10/30
 */
@Repository
public class HomepageDaoImpl implements HomepageDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<ContAdvertisementEntity> getAdvertisementByAdvClientType(String adv_client_type,String adv_location) {
		
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT adv_id,title,adv_content,adv_link,adv_type,adv_sort,"
        		+ " (select attch.attch_url from t_attachment attch where attch.attch_id = t.adv_img and attch.state=0 limit 1) adv_img "
        		+ " FROM t_cont_advertisement t " +
                " WHERE adv_client_type=:adv_client_type AND adv_location =:adv_location AND status = '0' "
                + " order by adv_sort ");

        List<ContAdvertisementEntity> confAds = null;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("adv_client_type",adv_client_type);
        sqlParamters.put("adv_location",adv_location);
        try {
        	confAds = this.jdbcTemplate.query(sqlStmt.toString(), sqlParamters, BeanPropertyRowMapper.newInstance(ContAdvertisementEntity.class));
		} catch (EmptyResultDataAccessException e) {
			System.out.println("企业认证数据为空");
			return null;
		}
        return confAds;
	}
	
}
