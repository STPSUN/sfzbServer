package com.idg.bfzb.server.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.message.model.request.MessageListRequest;
import com.idg.bfzb.server.message.service.MessageService;

/**
 * 类名称：MessageController
 * 类描述：消息中心controller
 * 创建人：ouzhb
 * 创建时间：2016/11/15
 */
@Controller
@RequestMapping("/api/message")
public class MessageController extends BaseController{
	
	private final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;
	/**
	 * 获取消息列表 [GET] /api/message/actions/list
	 * @param teamSetRequest
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/actions/list" , method = RequestMethod.GET)
    @ResponseBody
    public Object getMessageList(MessageListRequest msgLisRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		msgLisRequest.setUserId(super.getCurrentUser());
		//msgLisRequest.setIsRead(Constants.MESSAGE__STATE_NOT_READ);
		msgLisRequest.setChannel(Constants.MESSAGE_CHANNEL_PUSH);
		
        return messageService.getMessageList(msgLisRequest);
    }
	/**
	 * 消息置为已读 [GET] /api/message/actions/{messageId}/read
	 * @param teamSetRequest
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/actions/{messageId}/read" , method = RequestMethod.GET)
    @ResponseBody
    public Object readMessage(@PathVariable("messageId") Long messageId, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
        return messageService.readMessage(super.getCurrentUser(), messageId);
    }
	
	/**
     * 消息全部置为已读 [GET] /api/message/actions/allread
     * @param teamSetRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/actions/allread" , method = RequestMethod.GET)
    @ResponseBody
    public Object allReadMessage(HttpServletRequest servletRequest){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        return messageService.allReadMessage(super.getCurrentUser());
    }
}
