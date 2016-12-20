package com.idg.bfzb.server.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.model.response.AdminCategoryResponse;
import com.idg.bfzb.server.content.model.request.ContentRequest;
import com.idg.bfzb.server.content.model.response.ContentResponse;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class ContentManagerRespositoryImpl {
    private Logger logger = LoggerFactory.getLogger(ContentManagerRespositoryImpl.class);
    @Autowired
    private PageDao pageDao;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public PageInfo queryAdBannersByCond (ContentRequest contentRequest,Pageable pageable){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        PageInfo<AdminCategoryResponse> pageInfo = new PageInfo<AdminCategoryResponse>();
        
        sqlStmt.append("SELECT adv_id,")
        .append(" title,")
        .append(" adv_content,")
        .append(" (select attch.attch_url from t_attachment attch where attch.attch_id = adv_img and attch.state=0 limit 1) adv_img,")
        .append(" adv_sort,")
        .append(" redirect_type,")
        .append(" adv_link,")
        .append(" adv_type,")
        .append(" adv_location,")
        .append(" status,")
        .append(" create_time,")
        .append(" update_time,")
        .append(" update_admin_id,")
        .append(" adv_client_type,")
        .append(" start_time,")
        .append(" end_time,")
        .append(" play_area,")
        .append(" adv_user,")
        .append(" adv_user_mobile,")
        .append(" adv_location_detail")
        .append(" FROM t_cont_advertisement where status != -1");
        
        if(!StringUtil.isNull(contentRequest.getAdvType())){
            sqlStmt.append(" and adv_type like :advType");
            sqlParameters.put("advType", "%"+contentRequest.getAdvType()+"%");
        }
        
        if(!StringUtil.isNull(contentRequest.getAdvId())){
            sqlStmt.append(" and adv_id like :advId");
            sqlParameters.put("advId", "%"+contentRequest.getAdvId()+"%");
        }
        
        if(!StringUtil.isNull(contentRequest.getAdvClientType())){
            sqlStmt.append(" and adv_client_type like :advClientType");
            sqlParameters.put("advClientType", "%"+contentRequest.getAdvClientType()+"%");
        }
        
        if(!StringUtil.isNull(contentRequest.getTitle())){
            sqlStmt.append(" and title like :title");
            sqlParameters.put("title", "%"+contentRequest.getTitle()+"%");
        }
        
        if(!StringUtil.isNull(contentRequest.getAdvLocation())){
            sqlStmt.append(" and adv_location like :advLocation");
            sqlParameters.put("advLocation", "%"+contentRequest.getAdvLocation()+"%");
        }
        
        if(contentRequest.getStatus() !=null){
            sqlStmt.append(" and status = :status");
            sqlParameters.put("status", contentRequest.getStatus());
        }
        
        if(!StringUtil.isNull(contentRequest.getQryStartTime())){
            sqlStmt.append(" and create_time >= :qryStartTime");
            sqlParameters.put("qryStartTime", contentRequest.getQryStartTime());
        }
        if(!StringUtil.isNull(contentRequest.getQryEndTime())){
            sqlStmt.append(" and create_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
            sqlParameters.put("qryEndTime", contentRequest.getQryEndTime());
        }
        
        sqlStmt.append(" order by create_time desc");
        
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,ContentResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }
    
    public List<ContentResponse> queryAdvSort(ContentRequest contentRequest){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        
        sqlStmt.append("SELECT distinct adv_sort FROM t_cont_advertisement where status != -1 and ")
        .append(" adv_type = :advType and adv_sort is not null ORDER BY adv_sort ASC");

        sqlParameters.put("advType", contentRequest.getAdvType());
        
        List<ContentResponse> contentResponseList = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters,
            BeanPropertyRowMapper.newInstance(ContentResponse.class));
        
        return contentResponseList;
    }
}
