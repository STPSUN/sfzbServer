package com.idg.bfzb.server.adminfinance.service.impl;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.adminfinance.dao.AdminFinanceWithdrawRepository;
import com.idg.bfzb.server.adminfinance.model.request.FinanceWithdrawRequest;
import com.idg.bfzb.server.adminfinance.model.response.FinanceWithdrawResponse;
import com.idg.bfzb.server.adminfinance.service.AdminFinanceFinanceWithdrawService;
import com.idg.bfzb.server.authentication.dao.AuthenticationDao;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.MsgConstants;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.common.tools.MessageCommon;
import com.idg.bfzb.server.pay.dao.FinanceTradeDetailRepository;
import com.idg.bfzb.server.pay.model.dto.FinanceWithdrawApplyEntity;

@Service
public class AdminFinanceWithdrawServiceImp implements AdminFinanceFinanceWithdrawService {
    private final Logger logger = LoggerFactory.getLogger(AdminFinanceWithdrawServiceImp.class);

    @Autowired
    private AdminFinanceWithdrawRepository adminFinanceWithdrawRepository;
    @Autowired
    private AuthenticationDao authenticationRepository;
    @Resource
    private MessageCommon messageCommon;
    @Autowired
    private FinanceTradeDetailRepository financeTradeDetailRepository;
    
    
	@Override
	public PageInfo<FinanceWithdrawResponse> getWithdrawList(
			FinanceWithdrawRequest financeRechargeRequest, PageRequest pageable) {
		PageInfo<FinanceWithdrawResponse> pageInfo = this.adminFinanceWithdrawRepository.findWithdrawListByCond(
				financeRechargeRequest, pageable);
		return pageInfo;
	}

	@Override
	public APIResponse withrawActionSuccess(String recordId, String reason) {
		APIResponse ret = new APIResponse();
		
		boolean result = adminFinanceWithdrawRepository.withrawActionSuccess(recordId,reason);
		if(result){
			//更新交易状态（转账成功）
	        this.financeTradeDetailRepository.updateTradeStateById(Long.parseLong(recordId),Constants.TRADE_STATE_PAID_SUCCESS,"");
			
			FinanceWithdrawApplyEntity fwaEntity = adminFinanceWithdrawRepository.findWithdrawById(recordId);
			if(fwaEntity!=null){
				String msg = MsgConstants.TIXIAN_COMPLETE.replace("${money}", fwaEntity.getApplyMoney()+"")
						     .replace("${type}", Constants.TRADE_PLAT_ALIPAY.equals(fwaEntity.getApplyType())? "支付宝":"银行")
						     .replace("${account}", fwaEntity.getApplyCode());
				//发送消息
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, msg, fwaEntity.getApplyUserId());
			}
			ret.setSucess(true);
			ret.setMessage("转账成功操作成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("转账成功操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}

	@Override
	public APIResponse withrawActionFailure(String recordId, String reason, String audituserId) {
		APIResponse ret = new APIResponse();
		
		boolean result = adminFinanceWithdrawRepository.withrawActionFailure(recordId,reason,audituserId);
		
		if(result){
			//更新交易状态（转账失败）
	        this.financeTradeDetailRepository.updateTradeStateById(Long.parseLong(recordId),Constants.TRADE_STATE_PAID_FAIL,"");
			FinanceWithdrawApplyEntity fwaEntity = adminFinanceWithdrawRepository.findWithdrawById(recordId);
			//提现失败，金额加回原账户
			this.authenticationRepository.updatePersonBalance(fwaEntity.getApplyUserId(), fwaEntity.getApplyMoney());
			//发送消息
			String msg = MsgConstants.TIXIAN_FAIL.replace("${money}", fwaEntity.getApplyMoney()+"")
					     .replace("${type}", Constants.TRADE_PLAT_ALIPAY.equals(fwaEntity.getApplyType())? "支付宝":"银行")
					     .replace("${account}", fwaEntity.getApplyCode());
			messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, msg, fwaEntity.getApplyUserId());
			ret.setSucess(true);
			ret.setMessage("转账失败操作成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("转账失败操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}

}
