package com.idg.bfzb.server.pay.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.pay.dao.FinanceTradeDetailRepositoryCust;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.pay.model.response.FinanceTradeDetailResponse;

/**
 * 类名称：
 * 类描述：
 * 创建人：chen
 * 创建日期：2016/10/30
 */
public class FinanceTradeDetailRepositoryImpl implements FinanceTradeDetailRepositoryCust {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private PageDao pageDao;
	@Override
	public PageInfo<FinanceTradeDetailResponse> findAllFinanceDetailByCond(FinanceTradeDetailEntity entity,Pageable pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append(" SELECT a.record_id,a.pay_user_id,a.pay_user_name,a.income_user_id,a.income_user_name,a.target_type,");
		sqlStmt.append(" a.target_id,(SELECT b.project_name FROM t_project b where b.project_id = a.target_id limit 1) target_name,");
		sqlStmt.append(" a.money,a.trans_type,a.trade_state,a.trade_platform,a.trade_no,a.trade_extend,a.trade_time,a.out_trade_no,");
		sqlStmt.append(" c.apply_type,c.apply_bank,c.apply_code,c.apply_code_name ");
		sqlStmt.append(" FROM t_finance_trade_detail a ");
		sqlStmt.append(" LEFT JOIN t_finance_withdraw_apply c ");
		sqlStmt.append(" ON c.record_id=a.record_id ");
		sqlStmt.append(" WHERE a.trans_type = :transType ");
		sqlParameters.put("transType", entity.getTransType());
		if(entity.getTradeState()!=null){//手机端充值明细，只显示成功
			sqlStmt.append(" AND a.trade_state = :tradeState");
			sqlParameters.put("tradeState", entity.getTradeState());
		}
		sqlStmt.append(" AND (a.pay_user_id = :payUserId OR a.income_user_id = :incomeUserId) ");
		sqlStmt.append(" ORDER BY a.trade_time desc");
		sqlParameters.put("payUserId", entity.getPayUserId());
		sqlParameters.put("incomeUserId", entity.getIncomeUserId());
		System.out.println(sqlStmt.toString());
		PageInfo<FinanceTradeDetailResponse> pageInfo = new PageInfo<FinanceTradeDetailResponse>();
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,FinanceTradeDetailResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
	}
	@Override
	public List<FinanceTradeDetailEntity> findFinanceDetailByCond(
			FinanceTradeDetailEntity entity) {
		List<FinanceTradeDetailEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		
		sqlStmt.append(" SELECT a.record_id,a.pay_user_id,a.pay_user_name,a.income_user_id,a.income_user_name,a.target_type,")
			   .append(" a.target_id,a.money,a.trans_type,a.trade_state,a.trade_platform,a.trade_no,a.trade_extend,a.trade_time")
			   .append(" FROM t_finance_trade_detail a WHERE 1=1 ");
		
		if(StringUtils.isNotEmpty(entity.getTargetId())){
			sqlStmt.append(" AND a.target_id=:targetId ");
			sqlParameters.put("targetId", entity.getTargetId());
		}
		if(StringUtils.isNotEmpty(entity.getTargetType())){
			sqlStmt.append(" AND a.target_type=:targetType ");
			sqlParameters.put("targetType", entity.getTargetType());
		}
		if(StringUtils.isNotEmpty(entity.getPayUserId())){
			sqlStmt.append(" AND a.pay_user_id=:payUserId ");
			sqlParameters.put("payUserId", entity.getPayUserId());
		}
		
        try {
        	list = jdbcTemplate.query(sqlStmt.toString(),sqlParameters, BeanPropertyRowMapper.newInstance(FinanceTradeDetailEntity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}
	@Override
	public int countByCond(FinanceTradeDetailEntity entity) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		
		sqlStmt.append(" SELECT count(a.record_id) cnt ")
			   .append(" FROM t_finance_trade_detail a WHERE 1=1 ");
		
		if(StringUtils.isNotEmpty(entity.getTargetId())){
			sqlStmt.append(" AND a.target_id=:targetId ");
			sqlParameters.put("targetId", entity.getTargetId());
		}
		if(StringUtils.isNotEmpty(entity.getTargetType())){
			sqlStmt.append(" AND a.target_type=:targetType ");
			sqlParameters.put("targetType", entity.getTargetType());
		}
		if(StringUtils.isNotEmpty(entity.getPayUserId())){
			sqlStmt.append(" AND a.pay_user_id=:payUserId ");
			sqlParameters.put("payUserId", entity.getPayUserId());
		}
		
		Map<String, Object> map = this.jdbcTemplate.queryForMap(sqlStmt.toString(), sqlParameters);
		
    	Object cnt = map.get("cnt");
    	if(cnt!=null){
    		return Integer.valueOf(String.valueOf(cnt));
    	}
    	
        return 0;
	}
}
