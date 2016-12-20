package com.idg.bfzb.server.common.tools;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.authentication.dao.AuthenticationDao;
import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.MsgConstants;
import com.idg.bfzb.server.pay.dao.FinanceTradeDetailRepository;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.project.dao.ProjectRepository;
import com.idg.bfzb.server.usercenter.dao.UserInfoRepository;
import com.idg.bfzb.server.usercenter.dao.UserPersonalRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;

/**
 * 类名称：ProjectCommon
 * 类描述：项目管理基础通用方法
 * 创建人：ouzhb
 * 创建时间：2016/12/6
 */
@Service
public class ProjectCommon {
	@Autowired
	private UserPersonalRepository userPersonalRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private FinanceTradeDetailRepository financeTradeDetailRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private AuthenticationDao authenticationDao;
	@Resource
	private MessageCommon messageCommon;
	/**
	 * 用户付款给平台（实际扣除用户余额）
	 * @param payUserId	付款用户
	 * @param payMoney	付款金额
	 * @param projectId	项目ID
	 * @param targetType 交易对象类型(业务类型) imprestmoney预付款 | revealmoney兜底服务费  | remainMoney尾款 | marginmoney质保金
	 * @return	false余额不足 (或付款金额小于等于0,或账号余额更新失败)| true付款成功
	 */
	public boolean userPayToPlatform(String payUserId, double payMoney, String projectId, String targetType){
		if(payMoney<=0)	return false;
		UserPersonalEntity userPersonalEntity = userPersonalRepository.findOne(payUserId);
		double balance = userPersonalEntity.getBalance().doubleValue();
		if(userPersonalEntity==null || balance<payMoney){
			return false;
		}
		//扣款
		int payResult = authenticationDao.updatePersonBalance(payUserId, -payMoney);
		if(payResult==0)	return false;
		//插入资金流水明细
		UcUserInfoEntity userInfoEntity = userInfoRepository.findOne(payUserId);
		if(userInfoEntity!=null){
			//扣除雇主账号金额（支付项目尾款）
			FinanceTradeDetailEntity remainFtdEntity = new FinanceTradeDetailEntity();
			remainFtdEntity.setPayUserId(payUserId);
			remainFtdEntity.setPayUserName(userInfoEntity.getUserName());
			remainFtdEntity.setIncomeUserId(Constants.PLAT_USER_ID);
			remainFtdEntity.setIncomeUserName(Constants.PLAT_USER_NAME);
			remainFtdEntity.setTargetType(targetType);
			remainFtdEntity.setTargetId(projectId);
			remainFtdEntity.setMoney(payMoney);
			remainFtdEntity.setTransType(Constants.TRANS_TYPE_PAYMENT);
			remainFtdEntity.setTradeState(Constants.TRADE_STATE_PAID_SUCCESS);
			financeTradeDetailRepository.save(remainFtdEntity);
		}
		
		return true;
	}
	/**
	 * 平台付款给用户（实际增加用户余额）
	 * @param payUserId	收款用户
	 * @param payMoney	收款金额
	 * @param projectId	项目ID
	 * @param targetType 交易对象类型(业务类型) imprestmoney预付款 | revealmoney兜底服务费  | remainmoney尾款 | marginmoney质保金
	 * return false收款金额小于等于0（或账号余额更新失败） | true付款成功
	 */
	public boolean platformPayToUser(String toUserId, double toMoney, String projectId, String targetType){
		if(toMoney<=0)	return false;
		//打款
		int toResult = authenticationDao.updatePersonBalance(toUserId, toMoney);
		if(toResult==0)	return false;
		//插入资金流水明细
		UcUserInfoEntity userInfoEntity = userInfoRepository.findOne(toUserId);
		if(userInfoEntity!=null){
			FinanceTradeDetailEntity remainFtdEntity = new FinanceTradeDetailEntity();
			remainFtdEntity.setPayUserId(Constants.PLAT_USER_ID);
			remainFtdEntity.setPayUserName(Constants.PLAT_USER_NAME);
			remainFtdEntity.setIncomeUserId(toUserId);
			remainFtdEntity.setIncomeUserName(userInfoEntity.getUserName());
			remainFtdEntity.setTargetType(targetType);
			remainFtdEntity.setTargetId(projectId);
			remainFtdEntity.setMoney(toMoney);
			remainFtdEntity.setTransType(Constants.TRANS_TYPE_INCOME);
			remainFtdEntity.setTradeState(Constants.TRADE_STATE_PAID_SUCCESS);
			financeTradeDetailRepository.save(remainFtdEntity);
		}
		
		return true;
	}
	/**
	 * 同意拒收平台退款给用户
	 * @param tradeMoney	交易金额
	 * @param imprestScale	预付款比例
	 * @param projectId		项目ID
	 * @param projectName	项目名称
	 * @param employerId	雇主ID
	 * @return	false退款失败（或退款金额小于等于0）
	 */
	public boolean rejectionReturnMoney(Double tradeMoney, Float imprestScale, String projectId, 
			String projectName, String employerId){
		Double imprestMoney = tradeMoney*imprestScale;//预付款
		if(imprestMoney==null || imprestMoney<=0)	return false;
		boolean platformPayToUser = platformPayToUser(employerId, imprestMoney, projectId, Constants.TARGET_TYPE_RETURNMONEY);
		
		if(platformPayToUser){
			String msgStr = MsgConstants.EMPLOYER_GETMONEY_RETURN.replace("${projectName}", projectName)
							.replace("${imprestMoney}", String.valueOf(imprestMoney));
			//发送消息
			messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, msgStr, employerId);
			projectRepository.updateProjectRejectionStateByProjectId(projectId, Constants.PROJECT_REFUSE_ADMIN_AGREE);
			projectRepository.updateProjectStateByProjectId(projectId, Constants.PROJECT_STATE_INVALID);
		}
		
		return platformPayToUser;
	}
}
