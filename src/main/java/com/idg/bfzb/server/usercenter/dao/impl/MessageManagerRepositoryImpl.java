package com.idg.bfzb.server.usercenter.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.usercenter.dao.MessageManagerRepositoryCust;
import com.idg.bfzb.server.usercenter.model.request.MessageManagerRequest;
import com.idg.bfzb.server.usercenter.model.response.EvaluateManagerResponse;
import com.idg.bfzb.server.usercenter.model.response.MessageManagerResponse;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class MessageManagerRepositoryImpl implements MessageManagerRepositoryCust{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private PageDao pageDao;
    
    @Override
    public PageInfo queryMsgDetailByCond(MessageManagerRequest messageManagerRequest,Pageable pageable){
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        
        sqlStmt.append("SELECT")
            .append(" tcm.channel,")
            .append(" tcm.content,")
            .append(" tcm.create_time,")
            .append(" tcm.messge_id,")
            .append(" tcmd.is_read,")
            .append(" tcmd.read_time,")
            .append(" uui.user_id,")
            .append(" uui.user_name,")
            .append(" uui.nick_name")
            .append(" FROM (select * from t_cont_message where state = 0) tcm")
            .append(" LEFT JOIN t_cont_msg_detail tcmd ON tcm.messge_id = tcmd.message_id")
            .append(" LEFT JOIN uc_user_info uui ON tcmd.user_id = uui.user_id")
            .append(" WHERE uui.user_id IS NOT NULL");
        if(!StringUtil.isNull(messageManagerRequest.getUserName())){
            sqlStmt.append(" and uui.user_name LIKE :userName");
            sqlParameters.put("userName", "%"+messageManagerRequest.getUserName()+"%");
        }
        if(!StringUtil.isNull(messageManagerRequest.getNickName())){
            sqlStmt.append(" and uui.nick_name LIKE :nickName");
            sqlParameters.put("nickName", "%"+messageManagerRequest.getNickName()+"%");
        }
        if(!StringUtil.isNull(messageManagerRequest.getQryStartTime())){
            sqlStmt.append(" and tcm.create_time >= :qryStartTime");
            sqlParameters.put("qryStartTime", messageManagerRequest.getQryStartTime());
        }
        if(!StringUtil.isNull(messageManagerRequest.getQryEndTime())){
            sqlStmt.append(" and tcm.create_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
            sqlParameters.put("qryEndTime", messageManagerRequest.getQryEndTime());
        }
        
        PageInfo<MessageManagerResponse> pageInfo = new PageInfo<MessageManagerResponse>();
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,MessageManagerResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }
    
    @Override
    public boolean delMsgByMessageId(String messageId){
        boolean res = false;
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        try {
            sqlStmt.append("update t_cont_message set state = -1 where messge_id = :messageId");
            sqlParameters.put("messageId", messageId);
            this.jdbcTemplate.update(sqlStmt.toString(), sqlParameters);
            sqlStmt = new StringBuilder();
            sqlParameters = new HashMap<>();
            sqlStmt.append("update t_cont_msg_detail set state = -1 where message_id = :messageId");
            sqlParameters.put("messageId", messageId);
            this.jdbcTemplate.update(sqlStmt.toString(), sqlParameters);
            res = true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        return res;
    }
}
