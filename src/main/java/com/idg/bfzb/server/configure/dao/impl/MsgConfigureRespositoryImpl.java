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
import com.idg.bfzb.server.configure.model.request.MsgConfigureRequest;
import com.idg.bfzb.server.configure.model.response.MsgConfigureResponse;
import com.idg.bfzb.server.configure.model.response.RegionResponse;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class MsgConfigureRespositoryImpl {
	private Logger logger = LoggerFactory.getLogger(MsgConfigureRespositoryImpl.class);
    @Autowired
    private PageDao pageDao;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public PageInfo findMsgConfigureByCond(MsgConfigureRequest msgConfigureRequest,Pageable pageable){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        PageInfo<MsgConfigureResponse> pageInfo = new PageInfo<MsgConfigureResponse>();
        
        sqlStmt.append("SELECT configure_id,")
            .append(" alert_style,")
            .append(" mobile,")
            .append(" real_name,")
            .append(" create_time,")
            .append(" state")
            .append(" from t_cont_msg_configure where state != -1");
        
        if(!StringUtil.isNull(msgConfigureRequest.getAlertStyle())){
            sqlStmt.append(" and alert_style = :alertStyle");
            sqlParameters.put("alertStyle", msgConfigureRequest.getAlertStyle());
        }
        if(!StringUtil.isNull(msgConfigureRequest.getMobile())){
            sqlStmt.append(" and mobile LIKE :mobile");
            sqlParameters.put("mobile", "%"+msgConfigureRequest.getMobile()+"%");
        }
        
        sqlStmt.append(" order by create_time desc");
        
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,MsgConfigureResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }
}
