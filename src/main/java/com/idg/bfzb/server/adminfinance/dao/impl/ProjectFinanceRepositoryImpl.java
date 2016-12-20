package com.idg.bfzb.server.adminfinance.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.idg.bfzb.server.adminfinance.dao.ProjectFinanceRepository;
import com.idg.bfzb.server.adminfinance.model.request.FinanceTradeDetailRequest;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.pay.model.response.FinanceTradeDetailResponse;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;
import com.idg.bfzb.server.utility.tools.StringUtil;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
@Repository
public class ProjectFinanceRepositoryImpl implements ProjectFinanceRepository{
    private Logger logger = LoggerFactory.getLogger(ProjectFinanceRepositoryImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PageDao pageDao;

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<UserManagerResponse> findUserFinanceListByCond(
			UserManagerRequest userManagerRequest, PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append(" SELECT")
		.append(" uc.user_id,")
		.append(" uc.org_id,")
		.append(" uc.realm,")
		.append(" uc.user_name,")
		.append(" uc.real_name,")
		.append(" uc.nick_name,")
		.append(" uc.mobile,")
		.append(" uc.state,")
		.append(" uc.create_time,")
		.append(" uc.last_modified,")
		.append(" personal.idcard_code,")
		.append(" IFNULL(personal.balance, 0) balance,")
		.append(" IFNULL(personal.incoming, 0) incoming,")
		.append(" IFNULL(personal.expenditure, 0) expenditure,")
		.append(" personal.alipay_code,")
		.append(" personal.bank_card_code,")
		.append(" personal.wechat_code,")
		.append(" personal.last_role,")
		.append(" personal.is_team_user,")
		.append(" personal.is_enterprise_user,")
		.append(" personal.update_time,")
		.append(" personal.review_state,")
		.append(" personal.review_admin_id,")
		.append(" personal.review_time")
		.append(" FROM")
		.append(" uc_user_info uc")
		.append(" LEFT JOIN t_user_personal personal ON uc.user_id = personal.user_id")
		.append(" WHERE 1=1");
		//查询条件
		if(userManagerRequest.getUserId()!=null){
			sqlStmt.append(" and uc.user_id = :userId");
			sqlParameters.put("userId", userManagerRequest.getUserId());
		}
		if(!StringUtil.isNull(userManagerRequest.getUserName())){
			sqlStmt.append(" and uc.user_name LIKE :userName");
			sqlParameters.put("userName", "%"+userManagerRequest.getUserName()+"%");
		}
		if(!StringUtil.isNull(userManagerRequest.getQryStartTime())){
			sqlStmt.append(" and uc.create_time >= :qryStartTime");
			sqlParameters.put("qryStartTime", userManagerRequest.getQryStartTime());
		}
		if(!StringUtil.isNull(userManagerRequest.getQryEndTime())){
			sqlStmt.append(" and uc.create_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
			sqlParameters.put("qryEndTime", userManagerRequest.getQryEndTime());
		}
		if(!StringUtil.isNull(userManagerRequest.getNickName())){
            sqlStmt.append(" and uc.nick_name LIKE :nickName");
            sqlParameters.put("nickName", "%"+userManagerRequest.getNickName()+"%");
        }
		if(!StringUtil.isNull(userManagerRequest.getRealName())){
            sqlStmt.append(" and uc.real_name = :realName");
            sqlParameters.put("realName", userManagerRequest.getRealName());
        }
		if(!StringUtil.isNull(userManagerRequest.getIdcardCode())){
		    sqlStmt.append(" and personal.idcard_code = :idcardCode");
            sqlParameters.put("idcardCode", userManagerRequest.getIdcardCode());
		}
		if(!StringUtil.isNull(userManagerRequest.getMobile())){
			sqlStmt.append(" and uc.user_name LIKE :mobile");
			sqlParameters.put("mobile", "%"+userManagerRequest.getMobile()+"%");
		}

		sqlStmt.append(" order by uc.create_time desc");
		System.out.println(sqlStmt.toString());
		
		PageInfo<UserManagerResponse> pageInfo = new PageInfo<UserManagerResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,UserManagerResponse.class,pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}

	@Override
	public PageInfo findUserProjectDetailList(FinanceTradeDetailEntity entity,
			PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append(" SELECT a.record_id,a.pay_user_id,a.pay_user_name,a.income_user_id,a.income_user_name,a.target_type,")
		.append(" a.target_id,(SELECT b.project_name FROM t_project b where b.project_id = a.target_id limit 1) target_name,")
		.append(" a.money,a.trans_type,a.trade_state,a.trade_platform,a.trade_no,a.trade_extend,a.trade_time")
		.append(" FROM t_finance_trade_detail a ")
		.append(" WHERE a.trans_type in(:income,:payment) ")
		.append(" AND (a.pay_user_id = :payUserId OR a.income_user_id = :incomeUserId) ")
		.append(" ORDER BY a.trade_time desc");
		//收入、支出的交易属于项目类
		sqlParameters.put("income", Constants.TRANS_TYPE_INCOME);
		sqlParameters.put("payment", Constants.TRANS_TYPE_PAYMENT);
		sqlParameters.put("payUserId", entity.getPayUserId());
		sqlParameters.put("incomeUserId", entity.getIncomeUserId());
		
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
	public PageInfo findUserWRDetailList(FinanceTradeDetailEntity entity,
			PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append(" SELECT a.record_id,a.pay_user_id,a.pay_user_name,a.income_user_id,a.income_user_name,a.target_type,")
		.append(" a.target_id,(SELECT b.project_name FROM t_project b where b.project_id = a.target_id limit 1) target_name,")
		.append(" a.money,a.trans_type,a.trade_state,a.trade_platform,a.trade_no,a.trade_extend,a.trade_time")
		.append(" FROM t_finance_trade_detail a ")
		.append(" WHERE a.trans_type in(:recharge,:withdraw) ")
		.append(" AND (a.pay_user_id = :payUserId OR a.income_user_id = :incomeUserId) ")
		.append(" ORDER BY a.trade_time desc");
		//收入、支出的交易属于项目类
		sqlParameters.put("recharge", Constants.TRANS_TYPE_RECHARGE);
		sqlParameters.put("withdraw", Constants.TRANS_TYPE_WITHDRAW);
		sqlParameters.put("payUserId", entity.getPayUserId());
		sqlParameters.put("incomeUserId", entity.getIncomeUserId());
		
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
	public PageInfo findPlatFinanceListByCond(
			FinanceTradeDetailRequest ftdRequest, PageRequest pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append(" select * from(SELECT a.record_id,a.pay_user_id,a.pay_user_name,a.income_user_id,a.income_user_name,a.target_type,")
		.append(" a.target_id,(SELECT b.project_name FROM t_project b where b.project_id = a.target_id limit 1) target_name,")
		.append(" a.money,a.trans_type,a.trade_state,a.trade_platform,a.trade_no,a.trade_extend,a.trade_time,")
		.append(" (case when a.pay_user_id=:platUserId then a.income_user_id else a.pay_user_id end) user_id,")
		.append(" (case when a.pay_user_name=:platUserName then a.income_user_name else a.pay_user_name end) user_name,")
		.append(" (SELECT u.nick_name FROM uc_user_info u WHERE u.user_id=a.pay_user_id or u.user_id=a.income_user_id limit 1) nick_name")
		.append(" FROM t_finance_trade_detail a ")
		.append(" WHERE a.trans_type in(:income,:payment) ");
		sqlParameters.put("platUserId", Constants.PLAT_USER_ID);
		sqlParameters.put("platUserName", Constants.PLAT_USER_NAME);
		sqlParameters.put("income", Constants.TRANS_TYPE_INCOME);
		sqlParameters.put("payment", Constants.TRANS_TYPE_PAYMENT);
		sqlStmt.append(") c where 1=1 ");
		//查询条件
		if(!StringUtil.isNull(ftdRequest.getMobile())){
			sqlStmt.append(" and c.user_name LIKE :userName");
			sqlParameters.put("userName", "%"+ftdRequest.getMobile()+"%");
		}
		if(!StringUtil.isNull(ftdRequest.getNickName())){
            sqlStmt.append(" and c.nick_name LIKE :nickName");
            sqlParameters.put("nickName", "%"+ftdRequest.getNickName()+"%");
        }
		if(!StringUtil.isNull(ftdRequest.getTargetType())){
            sqlStmt.append(" and c.target_type = :targetType");
            sqlParameters.put("targetType", ftdRequest.getTargetType());
        }
		if(!StringUtil.isNull(ftdRequest.getQryStartTime())){
			sqlStmt.append(" and c.trade_time >= :qryStartTime");
			sqlParameters.put("qryStartTime", ftdRequest.getQryStartTime());
		}
		if(!StringUtil.isNull(ftdRequest.getQryEndTime())){
			sqlStmt.append(" and c.trade_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
			sqlParameters.put("qryEndTime", ftdRequest.getQryEndTime());
		}

		sqlStmt.append(" order by c.trade_time desc");
		System.out.println(sqlStmt.toString());
		PageInfo<UserManagerResponse> pageInfo = new PageInfo<UserManagerResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,FinanceTradeDetailResponse.class,pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}
}
