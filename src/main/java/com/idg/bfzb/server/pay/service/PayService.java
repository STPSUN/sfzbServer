package com.idg.bfzb.server.pay.service;

import java.util.Map;
import java.util.SortedMap;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.pay.model.dto.FinanceOfflineTradeEntity;
import com.idg.bfzb.server.pay.model.dto.FinanceTradeDetailEntity;
import com.idg.bfzb.server.pay.model.dto.FinanceWithdrawApplyEntity;
import com.idg.bfzb.server.pay.model.request.FinanceWithdrawApplyRequest;
import com.idg.bfzb.server.pay.model.request.RechargeRequest;
import com.idg.bfzb.server.pay.wechatpay.dto.UnifiedorderResult;


public interface PayService {

    /**
     * 支付回调
     * @param map
     * @return
     */
    boolean callBackAliPay(Map<String, String> map);
    
	/**
	 * 支付宝支付
	 * @param orderId 订单编号
	 * @param actualPay 实际支付金额
	 * @param termType
	 * @return
	 */
    APIResponse getOrderInfoByAliPay(String orderId,Double actualPay, String termType);
	
	/**
	 * 提现申请
	 * @param financeWithdrawApplyRequest
	 * @return
	 */
	APIResponse withdraw(FinanceWithdrawApplyRequest financeWithdrawApplyRequest);

	/**
	 * 生成订单号
	 * @param orderType
	 * @return
	 */
	String createOrderId(String orderType);
	
	/**
	 * 资金明细
	 * @param financeTradeDetailEntity
	 * @param pageable
	 * @return
	 */
	APIResponse financialDetail(FinanceTradeDetailEntity financeTradeDetailEntity,Pageable pageable);

	/**
	 * 查询余额
	 * @param userId
	 * @return
	 */
	APIResponse qryBalance(String userId);

	/**
	 * 提现申请列表
	 * @param qryRequest
	 * @return
	 */
	APIResponse qryWithdrawInfoByCond(
			FinanceWithdrawApplyRequest qryRequest);

	/**
	 * 充值，插入流水表
	 * @param ftdEntity
	 * @return
	 */
	APIResponse recharge(FinanceTradeDetailEntity ftdEntity);

	/**
	 * 银联支付
	 * @param orderId
	 * @param dfee
	 * @return
	 */
	APIResponse getTNByUnionPay(String orderId, Double dfee);

	/**
	 * 生成微信支付凭证
	 * @param orderId
	 * @param dfee
	 * @param termType
	 * @return
	 */
	APIResponse getPreInfoByWechartPay(String orderId, Double dfee, String termType);

	/**
	 * 微信支付回调
	 * @param parameters
	 * @return
	 */
	boolean callBackWechat(SortedMap<Object, Object> parameters);

	/**
	 * 插入线下汇款信息
	 * @param fotEntity
	 * @return
	 */
	APIResponse addOfflineInfo(FinanceOfflineTradeEntity fotEntity);

	/**
	 * 获取订单信息
	 * @param outTradeNo
	 * @return
	 */
	APIResponse qryTradeInfo(String outTradeNo);
}
