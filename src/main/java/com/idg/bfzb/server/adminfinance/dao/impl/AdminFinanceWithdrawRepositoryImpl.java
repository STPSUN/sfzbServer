package com.idg.bfzb.server.adminfinance.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.idg.bfzb.server.adminfinance.dao.AdminFinanceWithdrawRepository;
import com.idg.bfzb.server.adminfinance.model.request.FinanceWithdrawRequest;
import com.idg.bfzb.server.adminfinance.model.response.FinanceWithdrawResponse;
import com.idg.bfzb.server.authentication.model.UserPersonalVo;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.pay.model.dto.FinanceWithdrawApplyEntity;
import com.idg.bfzb.server.utility.tools.StringUtil;

/**
 * 类名称：
 * 类描述：
 */
@Repository
public class AdminFinanceWithdrawRepositoryImpl implements AdminFinanceWithdrawRepository{
    private Logger logger = LoggerFactory.getLogger(AdminFinanceWithdrawRepositoryImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private PageDao pageDao;

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FinanceWithdrawResponse> findWithdrawListByCond(
			FinanceWithdrawRequest financeRechargeRequest, PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append("select * from (")
		.append(" select a.record_id,a.apply_user_id,a.apply_money,a.apply_code,a.apply_type,a.apply_code_name,a.apply_time,a.review_state,")
		.append(" a.review_admin_id,a.review_time,")
		.append(" (select p.idcard_code from t_user_personal p where p.user_id=a.apply_user_id limit 1) idcard_code ,")
		.append(" (select p.nick_name from uc_user_info p where p.user_id=a.apply_user_id limit 1) nick_name ,")
		.append(" (select p.real_name from uc_user_info p where p.user_id=a.apply_user_id limit 1) real_name ,")
		.append(" (select p.mobile from uc_user_info p where p.user_id=a.apply_user_id limit 1) mobile, ")
		.append(" (select p.admin_account from uc_admin p where p.admin_id=a.review_admin_id limit 1) review_admin_name ")
		.append(" from t_finance_withdraw_apply a) b ")
		.append(" where 1 = 1");
		if(!StringUtil.isNull(financeRechargeRequest.getMobile())){
            sqlStmt.append(" and b.mobile = :mobile");
            sqlParameters.put("mobile", financeRechargeRequest.getMobile());
        }
		if(!StringUtil.isNull(financeRechargeRequest.getReviewState())){
            sqlStmt.append(" and b.review_state = :reviewState");
            sqlParameters.put("reviewState", financeRechargeRequest.getReviewState());
        }
		if(!StringUtil.isNull(financeRechargeRequest.getApplyType())){
            sqlStmt.append(" and b.apply_type = :applyType");
            sqlParameters.put("applyType", financeRechargeRequest.getApplyType());
        }
		if(!StringUtil.isNull(financeRechargeRequest.getQryStartTime())){
            sqlStmt.append(" and b.apply_time >= :qryStartTime");
            sqlParameters.put("qryStartTime", financeRechargeRequest.getQryStartTime());
        }
        if(!StringUtil.isNull(financeRechargeRequest.getQryEndTime())){
            sqlStmt.append(" and b.apply_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
            sqlParameters.put("qryEndTime", financeRechargeRequest.getQryEndTime());
        }
		sqlStmt.append(" order by b.apply_time asc");
		System.out.println(sqlStmt.toString());
		
		PageInfo<FinanceWithdrawResponse> pageInfo = new PageInfo<FinanceWithdrawResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,FinanceWithdrawResponse.class,pageable);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}

	@Override
	public boolean withrawActionSuccess(String recordId, String reason) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_finance_withdraw_apply set "
				+ " review_state = 'withdraw_succeed',"
				+ " review_reason = :reason "
				+ " where record_id = :recordId ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("recordId", recordId);
		sqlParameters.put("reason", reason);
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;
	}

	@Override
	public boolean withrawActionFailure(String recordId, String reason,String audituserId) {
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("update t_finance_withdraw_apply set "
				+ " review_state = 'withdraw_failse',"
				+ " review_reason = :reason,"
				+ " review_admin_id = :audituserId,"
				+ " review_time = current_timestamp() "
				+ " where record_id = :recordId ");
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlParameters.put("recordId", recordId);
		sqlParameters.put("reason", reason);
		sqlParameters.put("audituserId", audituserId);
		int res = 0;
		try {
			res = jdbcTemplate.update(sqlStmt.toString(),sqlParameters);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(res > 0) return true;
		return false;
	}

	@Override
	public FinanceWithdrawApplyEntity findWithdrawById(String recordId) {
		
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append(" select a.record_id,a.apply_user_id,a.apply_money,a.apply_code,a.apply_type,a.apply_code_name,a.apply_time,a.review_state,")
		.append(" a.review_admin_id,a.review_time,")
		.append(" (select p.idcard_code from t_user_personal p where p.user_id=a.apply_user_id limit 1) idcard_code ,")
		.append(" (select p.user_name from uc_user_info p where p.user_id=a.apply_user_id limit 1) nickName ,")
		.append(" (select p.mobile from uc_user_info p where p.user_id=a.apply_user_id limit 1) mobile, ")
		.append(" (select p.admin_account from uc_admin p where p.admin_id=a.review_admin_id limit 1) reviewAdminName ")
		.append(" from t_finance_withdraw_apply a ")
		.append(" left join uc_user_info b ")
		.append(" on a.apply_user_id = b.user_id ")
		.append(" where 1 = 1" 
				+ " AND a.record_id = :recordId ");
		
		sqlParameters.put("recordId", recordId);
		try {
			FinanceWithdrawApplyEntity fw = this.jdbcTemplate.queryForObject(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(FinanceWithdrawApplyEntity.class));
			return fw;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
