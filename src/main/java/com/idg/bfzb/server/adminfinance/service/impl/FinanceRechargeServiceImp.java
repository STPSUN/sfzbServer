package com.idg.bfzb.server.adminfinance.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.adminfinance.dao.FinanceRechargeRepository;
import com.idg.bfzb.server.adminfinance.model.request.FinanceRechargeRequest;
import com.idg.bfzb.server.adminfinance.service.FinanceRechargeService;
import com.idg.bfzb.server.authentication.dao.AuthenticationDao;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.MsgConstants;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.common.tools.MessageCommon;
import com.idg.bfzb.server.pay.dao.FinanceOfflineTradeRepository;
import com.idg.bfzb.server.pay.dao.FinanceTradeDetailRepository;
import com.idg.bfzb.server.utility.tools.DateUtil;

@Service
public class FinanceRechargeServiceImp implements FinanceRechargeService {
    private final Logger logger = LoggerFactory.getLogger(FinanceRechargeServiceImp.class);

    @Autowired
    private FinanceRechargeRepository financeRechargeRepository;
    @Autowired
    private FinanceTradeDetailRepository financeTradeDetailRepository;
    @Autowired
    private FinanceOfflineTradeRepository financeOfflineTradeRepository;
    @Autowired
    private AuthenticationDao authenticationRepository;
    @Autowired
    private MessageCommon messageCommon;
    
    @Override
	public PageInfo getRechargeList(FinanceRechargeRequest financeRechargeRequest,Pageable pageable) {
		PageInfo pageInfo = this.financeRechargeRepository.findRechargeListByCond(
				financeRechargeRequest, pageable);
		return pageInfo;
	}

	@Override
	public APIResponse auditOffline(FinanceRechargeRequest financeRechargeRequest) {
		APIResponse apiResponse = new APIResponse();
		short reviewState =  financeRechargeRequest.getReviewState();
		//更新线下充值扩展表
		short tradeState = 0;
		String msgModel = "";
		try {
		if(reviewState==Constants.RECHARGE_AUDIT_STATE_PASS){
			tradeState = Constants.TRADE_STATE_PAID_SUCCESS;
			msgModel = MsgConstants.OFFLINE_RECHARGE_COMPLETE;
			//加入余额
            this.authenticationRepository.updatePersonBalance(financeRechargeRequest.getPayUserId(), financeRechargeRequest.getMoney());
		}else if(reviewState==Constants.RECHARGE_AUDIT_STATE_NO_PASS){
			tradeState =  Constants.TRADE_STATE_PAID_FAIL;
			msgModel = MsgConstants.OFFLINE_RECHARGE_FAIL;
		}
			this.financeOfflineTradeRepository.updateOfflineTradeById(
					financeRechargeRequest.getRecordId(), 
					financeRechargeRequest.getBankAccountNumber(), 
					DateUtil.getTimestampByDateString(financeRechargeRequest.getBankAccountTime(), "yyyy-MM-dd HH:mm:ss") , 
					financeRechargeRequest.getBankSerialId(), 
					financeRechargeRequest.getMoney(), 
					reviewState, 
					financeRechargeRequest.getReviewAdminId());
			//更新资金流水
			this.financeTradeDetailRepository.updateTradeStateById(financeRechargeRequest.getRecordId(),tradeState,financeRechargeRequest.getBankSerialId());
			//发送推送消息
			String msg = msgModel.replace("${money}", financeRechargeRequest.getMoney()+"");
			messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, msg, financeRechargeRequest.getPayUserId());
			
			apiResponse.setSucess(true);
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		} catch (Exception e) {
			apiResponse.setSucess(false);
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			return apiResponse;
		}
		return apiResponse;
	}

}
