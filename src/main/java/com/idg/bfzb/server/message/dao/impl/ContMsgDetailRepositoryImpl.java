package com.idg.bfzb.server.message.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.message.dao.ContMsgDetailRepositoryCust;
import com.idg.bfzb.server.message.model.dto.ContMessageEntity;
import com.idg.bfzb.server.message.model.request.MessageListRequest;
import com.idg.bfzb.server.message.model.vo.MessageListVo;
import com.idg.bfzb.server.project.model.vo.ProjectListVo;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class ContMsgDetailRepositoryImpl implements ContMsgDetailRepositoryCust{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public PageInfo<MessageListVo> findMsgDetailByPageAndCondition(int start,
			int size, MessageListRequest msgLisRequest) {
		PageInfo<MessageListVo> pageInfo = new PageInfo<MessageListVo>();
		
        StringBuilder sqlStmt = new StringBuilder();
        
        sqlStmt.append(" select d.message_id,m.content,m.create_time,d.is_read ")
        	   .append(" from t_cont_msg_detail d left join t_cont_message m on d.message_id=m.messge_id where 1=1 ");
        
        if(msgLisRequest.getIsRead()!=null){
        	sqlStmt.append(" and d.is_read=").append(msgLisRequest.getIsRead()).append("");
        }
        if(!StringUtil.isNull(msgLisRequest.getUserId())){
        	sqlStmt.append(" and d.user_id='").append(msgLisRequest.getUserId()).append("'");
        }
        if(!StringUtil.isNull(msgLisRequest.getChannel())){
        	sqlStmt.append(" and m.channel='").append(msgLisRequest.getChannel()).append("'");
        }

        try {
        	pageInfo.setTotalRows(jdbcTemplate.query(sqlStmt.toString(), BeanPropertyRowMapper.newInstance(ProjectListVo.class)).size());
        	
        	sqlStmt.append(" order by m.create_time desc limit ").append(start).append(",").append(size);
        	
        	pageInfo.setPageData(this.jdbcTemplate.query(sqlStmt.toString(), BeanPropertyRowMapper.newInstance(MessageListVo.class)));
        } catch (Exception e) {
        	pageInfo.setTotalRows(0);
			e.printStackTrace();
		}
        
        return pageInfo;
	}

	@Override
	public boolean insertOneUnReadMessage(ContMessageEntity msg,String userId) {
		StringBuilder sqlStmt = new StringBuilder();
		//
		sqlStmt.append("insert into t_cont_message "
				+ " (content,channel,state) "
				+ " values ("
				+ " :content,"
				+ " :channel,"
				+ " 0"
				+ ")");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("content", msg.getContent());
		sqlParameters.put("channel", msg.getChannel());
		
		StringBuilder sqlStmt2 = new StringBuilder();
		//select @@IDENTITY
		sqlStmt2.append("insert into t_cont_msg_detail "
				+ " (message_id,user_id,is_read,state) "
				+ " values ("
				+ " (select LAST_INSERT_ID()),"
				+ " :user_id,"
				+ " 0,"
				+ " 0"
				+ ")");
		Map<String, Object> sqlParameters2 = new HashMap<>();
		sqlParameters2.put("user_id", userId);
		
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
			res = jdbcTemplate.update(sqlStmt2.toString(),sqlParameters2);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;
	}

	@Override
	public String setMsgAllReadByUserId(String userId){
	    String msg = "fail";
	    
	    StringBuilder sqlStmt = new StringBuilder();
	    Map<String, Object> sqlParameters = new HashMap<>();
        
        sqlStmt.append(" update t_cont_msg_detail set is_read = 1,read_time = current_timestamp()")
               .append(" where user_id = :userId ");
        sqlParameters.put("userId", userId);
        
        int res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
        if(res > 0){
            msg = "success";
        }
	    
	    return msg;
	}
}
