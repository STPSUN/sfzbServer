package com.idg.bfzb.server.adminfinance.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.adminfinance.dao.FinanceRechargeRepositoryCust;
import com.idg.bfzb.server.adminfinance.model.request.FinanceRechargeRequest;
import com.idg.bfzb.server.adminfinance.model.response.FinanceRechargeResponse;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.AvailableEnum;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.usercenter.dao.UserInfoRepositoryCust;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.usercenter.model.request.UserFinancialRequest;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.model.response.UserFinancialResponse;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;
import com.idg.bfzb.server.usercenter.model.vo.RegionAllVo;
import com.idg.bfzb.server.utility.tools.StringUtil;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
public class FinanceRechargeRepositoryImpl implements FinanceRechargeRepositoryCust{
    private Logger logger = LoggerFactory.getLogger(FinanceRechargeRepositoryImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PageDao pageDao;

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FinanceRechargeResponse> findRechargeListByCond(
			FinanceRechargeRequest financeRechargeRequest, Pageable pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append(" select a.record_id,a.pay_user_id,a.pay_user_name,a.income_user_id,a.income_user_name,a.target_type,")
		.append(" a.target_id,a.money,a.trans_type,a.trade_state,a.trade_platform,a.trade_no,a.out_trade_no,a.trade_extend,a.trade_time,")
		.append(" b.mobile,b.nick_name,b.nick_full_spell,b.nick_short_spell,b.real_name, ")
		.append(" (select p.idcard_code from t_user_personal p where p.user_id=a.pay_user_id limit 1) idcard_code, ")
		.append(" c.bank_name,c.bank_account_Name,c.bank_account_number,c.bank_account_time,c.bank_serial_id ")
		.append(" from t_finance_trade_detail a ")
		.append(" left join uc_user_info b on a.pay_user_id=b.user_id ")
		.append(" left join t_finance_offline_trade c on a.record_id = c.record_id ")
		.append(" where a.trans_type=:transType ");
		sqlParameters.put("transType", Constants.TRANS_TYPE_RECHARGE);
		if(!StringUtil.isNull(financeRechargeRequest.getTradePlatform())){
            sqlStmt.append(" and a.trade_platform = :tradePlatform");
            sqlParameters.put("tradePlatform", financeRechargeRequest.getTradePlatform());
        }
		if(financeRechargeRequest.getTradeState()!=null){
            sqlStmt.append(" and a.trade_state = :tradeState");
            sqlParameters.put("tradeState", financeRechargeRequest.getTradeState());
        }
		if(!StringUtil.isNull(financeRechargeRequest.getMobile())){
            sqlStmt.append(" and b.mobile = :mobile");
            sqlParameters.put("mobile", financeRechargeRequest.getMobile());
        }
		if(!StringUtil.isNull(financeRechargeRequest.getNickName())){
            sqlStmt.append(" and b.nick_name LIKE :nickName");
            sqlParameters.put("nickName", "%"+financeRechargeRequest.getNickName()+"%");
        }
		if(!StringUtil.isNull(financeRechargeRequest.getIdcardCode())){
            sqlStmt.append(" and idcard_code LIKE :idcardCode");
            sqlParameters.put("idcardCode", "%"+financeRechargeRequest.getIdcardCode()+"%");
        }
		if(!StringUtil.isNull(financeRechargeRequest.getQryStartTime())){
            sqlStmt.append(" and a.trade_time >= :qryStartTime");
            sqlParameters.put("qryStartTime", financeRechargeRequest.getQryStartTime());
        }
        if(!StringUtil.isNull(financeRechargeRequest.getQryEndTime())){
            sqlStmt.append(" and a.trade_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
            sqlParameters.put("qryEndTime", financeRechargeRequest.getQryEndTime());
        }
        if(financeRechargeRequest.getTradeState()!=null && financeRechargeRequest.getTradeState()==Constants.TRADE_STATE_NOT_PAY){
        	//待审核按正顺序
        	sqlStmt.append(" order by a.trade_time asc");
        }else{
        	sqlStmt.append(" order by a.trade_time desc");
        }
		System.out.println(sqlStmt.toString());
		
		PageInfo<FinanceRechargeResponse> pageInfo = new PageInfo<FinanceRechargeResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,FinanceRechargeResponse.class,pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}
}
