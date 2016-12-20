package com.idg.bfzb.server.usercenter.dao;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.usercenter.model.request.MessageManagerRequest;

public interface MessageManagerRepositoryCust {
    /**
     * 
     * .消息管理详情查询
     * 
     * @param messageManagerRequest
     * @param pageable
     * @return
     */
    PageInfo queryMsgDetailByCond(MessageManagerRequest messageManagerRequest,Pageable pageable);
    
    boolean delMsgByMessageId(String messageId);
}
