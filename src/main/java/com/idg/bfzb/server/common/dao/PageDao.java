package com.idg.bfzb.server.common.dao;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.PageInfo;

public interface PageDao {
	PageInfo findPageByCond(String sql,Map<String, Object> sqlParameters,Class<?> c,Pageable pageable);
}
