package com.idg.bfzb.server.message.dao;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.message.model.dto.ContMessageEntity;
import com.idg.bfzb.server.message.model.request.MessageListRequest;
import com.idg.bfzb.server.message.model.vo.MessageListVo;

/**
 * 类名称：ConfMsgDetailRepositoryCust
 * 类描述：消息发送记录repository
 * 创建人：ouzhb
 * 创建日期：2016/11/15
 */
public interface ContMsgDetailRepositoryCust {
	/**
	 * 根据条件分页查询发送的消息记录
	 * @param start	起始索引
	 * @param size	记录数
	 * @param msgListQryVo	查询条件
	 * @return	PageInfo<MessageListVo>
	 */
	PageInfo<MessageListVo> findMsgDetailByPageAndCondition(int start, int size, MessageListRequest msgLisRequest);
	/**
	 * 为用户插入一条未读消息
	 * @param msg
	 * @param userId
	 * @return
	 */
	boolean insertOneUnReadMessage(ContMessageEntity msg,String userId);
	
	/**
	 * 
	 * .设置所有信息为已读
	 * 
	 * @param userId
	 * @return
	 */
	String setMsgAllReadByUserId(String userId);
}
