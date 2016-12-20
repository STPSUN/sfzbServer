package com.idg.bfzb.server.common.dao.impl;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
@Repository
public class PageDaoImpl implements PageDao {
	
	@Autowired
	private NamedParameterJdbcTemplate  jdbcTemplate;

	@Override
	public PageInfo findPageByCond(String sql,
			Map<String, Object> sqlParameters, Class<?> c, Pageable pageable) {
		PageInfo pageInfo = new PageInfo();
		int pageNum=pageable.getPageNumber();
		int pageSize=pageable.getPageSize();
		//设置每页显示记录数
		pageInfo.setPageSize(pageSize);
		//设置要显示的页数
		pageInfo.setPageNum(pageNum);
		//总记录数
		try {
			pageInfo.setTotalRows(jdbcTemplate.query(sql,sqlParameters,
					BeanPropertyRowMapper.newInstance(c)).size());
		} catch (Exception e) {
			pageInfo.setTotalRows(0);
		}
		//计算总页数
		pageInfo.setTotalPages();
		//计算起始行数
		pageInfo.setStartIndex();
		//计算结束行数
		pageInfo.setLastIndex();
		//使用mysql时直接使用limits
		StringBuffer paginationSQL = new StringBuffer();
		paginationSQL.append(sql);
		paginationSQL.append(" limit " + pageInfo.getStartIndex() + "," + pageInfo.getPageSize());
		//装入结果集
		try {
			pageInfo.setPageData(jdbcTemplate.query(paginationSQL.toString(),sqlParameters,
					BeanPropertyRowMapper.newInstance(c)));
		} catch (Exception e) {
			pageInfo.setPageData(new ArrayList<Object>());
		}
		return pageInfo;
	}

}
