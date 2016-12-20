package com.idg.bfzb.server.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.dao.SysCodeRepositoryCust;
import com.idg.bfzb.server.common.model.vo.SysCodeVo;

public class SysCodeRepositoryImpl implements SysCodeRepositoryCust{
	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<SysCodeVo> findByClassify(String classify) {
		String sqlStmt = "SELECT s.conf_name,s.conf_data FROM t_sys_code s where s.classify=:classify";

        Map<String, Object> sqlParameters = new HashMap<String, Object>();
        sqlParameters.put("classify", classify);

        List<SysCodeVo> results = this.jdbcTemplate.query(sqlStmt, sqlParameters, BeanPropertyRowMapper.newInstance(SysCodeVo.class));
        return results;
	}

}
