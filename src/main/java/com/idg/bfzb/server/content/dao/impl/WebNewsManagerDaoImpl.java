package com.idg.bfzb.server.content.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.content.dao.WebNewsManagerDao;
import com.idg.bfzb.server.content.model.WebNewsAdminRequest;
import com.idg.bfzb.server.content.model.WebNewsAdminResponse;
import com.idg.bfzb.server.content.model.dto.TContAdvertisementEntity;

@Repository
public class WebNewsManagerDaoImpl implements WebNewsManagerDao{
	
	@Autowired
	private PageDao pageDao;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<WebNewsAdminResponse> findWebNewsListByCond(
			WebNewsAdminRequest projectRequest, PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select adv_id,"
				+ " t.title,"
				+ " t.adv_keyword,"
				+ " (select attch.attch_url from t_attachment attch where attch.attch_id = t.adv_img and attch.state=0 limit 1) adv_img,"
				+ " t.adv_sort,"
				+ " t.redirect_type,"
				+ " t.create_time,"
				+ " t.update_time,"
				+ " t.adv_client_type,"
				+ " t.start_time,"
				+ " t.end_time,"
				+ " t.play_area,"
				+ " t.adv_user,"
				+ " t.adv_user_mobile,"
				+ " t.adv_location_detail,"
				+ " t.adv_link "
				+ " from t_cont_advertisement t "
				+ " where t.status != '-1' AND t.adv_type = 'news' ");
		Map<String, Object> sqlParameters = new HashMap<>();
		
		if(!StringUtils.isEmpty(projectRequest.getWebNewsNameSea())){
			sqlStmt.append(" AND t.title like '%" + projectRequest.getWebNewsNameSea() +"%' ");
		}
		
		if(!StringUtils.isEmpty(projectRequest.getWebNewsKeyWordSea())){
			sqlStmt.append(" AND t.adv_keyword like '%" + projectRequest.getWebNewsKeyWordSea() +"%' ");
		}
		
		if(projectRequest.getWebNewsKeyWords() != null && projectRequest.getWebNewsKeyWords().length > 0){
			sqlStmt.append(" AND ( 1 = 1 ");
			for (String keyWord : projectRequest.getWebNewsKeyWords()) {
				
				sqlStmt.append(" OR t.adv_keyword like '%" + keyWord +"%' ");
			}
			sqlStmt.append(" ) ");
		}
		
		if(!StringUtils.isEmpty(projectRequest.getWebNewsNotAdvId())){
			sqlStmt.append(" AND t.adv_id != :not_adv_id");
			sqlParameters.put("not_adv_id", projectRequest.getWebNewsNotAdvId());
		}
		
		if(!StringUtils.isEmpty(projectRequest.getWebNewsState())){
			sqlStmt.append(" AND t.status = :status ");
			sqlParameters.put("status", projectRequest.getWebNewsState());
		}
		
		sqlStmt.append(" order by t.create_time desc ");
		pageable.getPageNumber();
		pageable.getPageSize();
		
		PageInfo<WebNewsAdminResponse> pageInfo = new PageInfo<WebNewsAdminResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,WebNewsAdminResponse.class,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}
	@Override
	public boolean addWebNews(TContAdvertisementEntity adver) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("insert into t_cont_advertisement (adv_id,title,adv_content,adv_keyword,adv_img,adv_link,adv_type,status) "
        		+ " values (:adv_id,:title,:adv_content,:adv_keyword,:adv_img,:adv_link,:adv_type,'0')");

        int result = 0;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("adv_id",adver.getAdvId());
        sqlParamters.put("title",adver.getTitle());
        sqlParamters.put("adv_content",adver.getAdvContent());
        sqlParamters.put("adv_img",adver.getAdvImg());
        sqlParamters.put("adv_link",adver.getAdvLink());
        sqlParamters.put("adv_type",adver.getAdvType());
        sqlParamters.put("adv_keyword",adver.getAdvKeyword());
        
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
		} catch (EmptyResultDataAccessException e) {
			
			return false;
		}
        return (result > 0) ? true : false;
	}
	@Override
	public boolean updateWebNews(TContAdvertisementEntity adver) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update t_cont_advertisement set title=:title,adv_content=:adv_content,adv_link=:adv_link,adv_keyword=:adv_keyword ");

        int result = 0;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("adv_id",adver.getAdvId());
        sqlParamters.put("title",adver.getTitle());
        sqlParamters.put("adv_content",adver.getAdvContent());
        sqlParamters.put("adv_keyword",adver.getAdvKeyword());
        
        if(!StringUtils.isEmpty(adver.getAdvImg())){
        	sqlStmt.append(",adv_img=:adv_img");
        	sqlParamters.put("adv_img",adver.getAdvImg());
        }
        sqlParamters.put("adv_link",adver.getAdvLink());
        //拼接SQL
        sqlStmt.append(" where adv_id=:adv_id ");
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
		} catch (EmptyResultDataAccessException e) {
			
			return false;
		}
        return (result > 0) ? true : false;
	}
	@Override
	public boolean deleteWebNewsByAdvId(String advId) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update t_cont_advertisement set status = '-1' ");

        int result = 0;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("adv_id",advId);
        //拼接SQL
        sqlStmt.append(" where adv_id=:adv_id ");
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
		} catch (EmptyResultDataAccessException e) {
			
			return false;
		}
        return (result > 0) ? true : false;
	}
	@Override
	public WebNewsAdminResponse getWebNewsByAdvId(String advId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select adv_id,"
				+ " t.title,"
				+ " t.adv_content,"
				+ " t.adv_keyword,"
				+ " t.adv_read_count,"
				+ " t.adv_sort,"
				+ " t.redirect_type,"
				+ " t.create_time,"
				+ " t.update_time,"
				+ " t.adv_client_type,"
				+ " t.start_time,"
				+ " t.end_time,"
				+ " t.play_area,"
				+ " t.adv_user,"
				+ " t.adv_user_mobile,"
				+ " t.adv_location_detail,"
				+ " t.adv_link "
				+ " from t_cont_advertisement t "
				+ " where t.status != '-1' AND t.adv_type = 'news' "
				+ " AND t.adv_id = :advId ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("advId", advId);
		
		WebNewsAdminResponse wbs = null;
		try {
			wbs = jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParameters, BeanPropertyRowMapper.newInstance(WebNewsAdminResponse.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return wbs;
	}
	@Override
	public boolean updateReadCount(String advId) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update t_cont_advertisement set adv_read_count=(adv_read_count + 1) ");

        int result = 0;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("adv_id",advId);
        
        //拼接SQL
        sqlStmt.append(" where adv_id=:adv_id ");
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
		} catch (EmptyResultDataAccessException e) {
			
			return false;
		}
        return (result > 0) ? true : false;
	}
	@Override
	public WebNewsAdminResponse getWebNewsqueryPre(String webNewsAdvId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select adv_id,"
				+ " t.title,"
				+ " t.adv_keyword "
				+ " from t_cont_advertisement t "
				+ " where t.status != '-1' AND t.adv_type = 'news' "
				+ " AND t.create_time < ( "
				+ " 		SELECT "
				+ " 			t2.create_time "
				+ " 		FROM "
				+ " 			t_cont_advertisement t2 "
				+ " 		WHERE "
				+ " 			t2.adv_id = :adv_id "
				+ " 	)");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("adv_id", webNewsAdvId);
		
		sqlStmt.append(" order by t.create_time DESC limit 1 ");
		WebNewsAdminResponse wb = new WebNewsAdminResponse();
		try {
			wb = jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(WebNewsAdminResponse.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return wb;
	}
	@Override
	public WebNewsAdminResponse getWebNewsqueryNext(String webNewsAdvId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select adv_id,"
				+ " t.title,"
				+ " t.adv_keyword "
				+ " from t_cont_advertisement t "
				+ " where t.status != '-1' AND t.adv_type = 'news' "
				+ " AND t.create_time > ( "
				+ " 		SELECT "
				+ " 			t2.create_time "
				+ " 		FROM "
				+ " 			t_cont_advertisement t2 "
				+ " 		WHERE "
				+ " 			t2.adv_id = :adv_id "
				+ " 	)");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("adv_id", webNewsAdvId);
		
		sqlStmt.append(" order by t.create_time ASC limit 1 ");
		WebNewsAdminResponse wb = new WebNewsAdminResponse();
		try {
			wb = jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(WebNewsAdminResponse.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return wb;
	}

}
