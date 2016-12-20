package com.idg.bfzb.server.usercenter.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.usercenter.dao.EvaluateManagerRepositoryCust;
import com.idg.bfzb.server.usercenter.model.request.EvaluateManagerRequest;
import com.idg.bfzb.server.usercenter.model.response.EvaluateManagerResponse;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class EvaluateManagerRepositoryImpl implements EvaluateManagerRepositoryCust{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private PageDao pageDao;
    
    @Override
    public PageInfo queryEvaluateByCond(EvaluateManagerRequest evaluateManagerRequest,Pageable pageable){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        
        sqlStmt.append("SELECT")
            .append(" ter.evaluate_id,")
            .append(" ter.target_id,")
            .append(" ter.target_type,")
            .append(" ter.target_owner_id,")
            .append(" ter.user_id,")
            .append(" ter.content,")
            .append(" ter.star,")
            .append(" ter.extend_data,")
            .append(" ter.update_time,")
            .append(" uui.user_name,")
            .append(" uui.nick_name,")
            .append(" tuui.user_name target_user_name,")
            .append(" tuui.nick_name target_nick_name,")
            .append(" tp.project_name")
            .append(" FROM (SELECT * FROM t_evaluate_record WHERE state = 0) ter")
            .append(" LEFT JOIN uc_user_info uui ON ter.user_id = uui.user_id")
            .append(" LEFT JOIN uc_user_info tuui ON ter.target_owner_id = tuui.user_id")
            .append(" LEFT JOIN t_project tp ON ter.target_id = tp.project_id")
            .append(" WHERE uui.user_id IS NOT NULL and tuui.user_id IS NOT NULL and tp.project_id IS NOT NULL");
        
        if(!StringUtil.isNull(evaluateManagerRequest.getUserName())){
            sqlStmt.append(" and uui.user_name LIKE :userName");
            sqlParameters.put("userName", "%"+evaluateManagerRequest.getUserName()+"%");
        }
        if(!StringUtil.isNull(evaluateManagerRequest.getTargetUserName())){
            sqlStmt.append(" and tuui.user_name LIKE :targetUserName");
            sqlParameters.put("targetUserName", "%"+evaluateManagerRequest.getTargetUserName()+"%");
        }
        if(!StringUtil.isNull(evaluateManagerRequest.getQryStartTime())){
            sqlStmt.append(" and ter.update_time >= :qryStartTime");
            sqlParameters.put("qryStartTime", evaluateManagerRequest.getQryStartTime());
        }
        if(!StringUtil.isNull(evaluateManagerRequest.getQryEndTime())){
            sqlStmt.append(" and ter.update_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
            sqlParameters.put("qryEndTime", evaluateManagerRequest.getQryEndTime());
        }
        
        PageInfo<EvaluateManagerResponse> pageInfo = new PageInfo<EvaluateManagerResponse>();
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,EvaluateManagerResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }
}
