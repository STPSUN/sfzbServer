package com.idg.bfzb.server.message.service;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.message.model.request.MessageListRequest;

/**
 * 类名称：MessageService
 * 类描述：消息中心serivce
 * 创建人：ouzhb
 * 创建时间：2016/11/15
 */
public interface MessageService {
	/**
	 * 获取消息列表
	 * @param msgLisRequest
	 * @return	APIResponse
	 */
	APIResponse getMessageList(MessageListRequest msgLisRequest);
	/**
	 * 消息置为已读
	 * @param currentUser
	 * @param messageId
	 * @return APIResponse
	 */
	APIResponse readMessage(String currentUser, Long messageId);
	
	/**
	 * 
	 * .消息全部置为已读
	 * 
	 * @param currentUser
	 * @return
	 */
	APIResponse allReadMessage(String currentUser);
}
