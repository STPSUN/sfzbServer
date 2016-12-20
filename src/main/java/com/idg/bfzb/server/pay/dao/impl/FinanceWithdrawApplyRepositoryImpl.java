package com.idg.bfzb.server.pay.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.idg.bfzb.server.pay.dao.FinanceWithdrawApplyRepositoryCust;
import com.idg.bfzb.server.pay.model.dto.FinanceWithdrawApplyEntity;
import com.idg.bfzb.server.pay.model.request.FinanceWithdrawApplyRequest;
import com.idg.bfzb.server.utility.tools.StringUtil;

/**
 * 类名称：
 * 类描述：
 * 创建人：chen
 * 创建日期：2016/10/30
 */
public class FinanceWithdrawApplyRepositoryImpl implements FinanceWithdrawApplyRepositoryCust {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    private final static String COLUMN = "record_id,apply_user_id,apply_money,apply_code,apply_code_name,apply_type,apply_time,review_state,review_reason,"+
    						"review_admin_id,review_time";
	@Override
	public List<FinanceWithdrawApplyEntity> qryWithdrawInfoByCond(FinanceWithdrawApplyRequest qryRequest) {
		List<FinanceWithdrawApplyEntity> list = null;
        Map<String,Object> sqlParamters = new HashMap<String,Object>();
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT ").append(COLUMN).append(" FROM t_finance_withdraw_apply where 1=1");
        
        if(qryRequest.getRecordId()!=null){
        	sqlStmt.append(" and record_id=:recordId ");
            sqlParamters.put("recordId",qryRequest.getRecordId());
        }
        if(!StringUtil.isNull(qryRequest.getApplyUserId())){
        	sqlStmt.append(" and apply_user_id=:applyUserId ");
            sqlParamters.put("applyUserId",qryRequest.getApplyUserId());
        }
        if(qryRequest.getApplyMoney()!=null){
        	sqlStmt.append(" and apply_money=:applyMoney ");
            sqlParamters.put("applyMoney",qryRequest.getApplyMoney());
        }
        if(!StringUtil.isNull(qryRequest.getApplyCode())){
        	sqlStmt.append(" and apply_code=:applyCode ");
            sqlParamters.put("applyCode",qryRequest.getApplyCode());
        }
        if(!StringUtil.isNull(qryRequest.getApplyTime())){
        	sqlStmt.append(" and apply_time=:applyTime ");
            sqlParamters.put("applyTime",qryRequest.getApplyTime());
        }
        if(!StringUtil.isNull(qryRequest.getReviewState())){
        	sqlStmt.append(" and review_state=:reviewState ");
            sqlParamters.put("reviewState",qryRequest.getReviewState());
        }
        if(!StringUtil.isNull(qryRequest.getReviewAdminId())){
        	sqlStmt.append(" and review_admin_id=:reviewAdminId ");
            sqlParamters.put("reviewAdminId",qryRequest.getReviewAdminId());
        }
        if(!StringUtil.isNull(qryRequest.getReviewTime())){
        	sqlStmt.append(" and review_time=:reviewTime ");
            sqlParamters.put("reviewTime",qryRequest.getReviewTime());
        }
        sqlStmt.append(" order by apply_time desc");

        try {
        	list = this.jdbcTemplate.query(sqlStmt.toString(),sqlParamters, BeanPropertyRowMapper.newInstance(FinanceWithdrawApplyEntity.class));
		} catch (EmptyResultDataAccessException e) {
			System.out.println("当前用户提现数据为空");
			return null;
		}
        return list;
	}
}
