package com.idg.bfzb.server.message.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.ArrayResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.message.dao.ContMsgDetailRepository;
import com.idg.bfzb.server.message.model.dto.ContMsgDetailEntity;
import com.idg.bfzb.server.message.model.dto.ContMsgDetailEntityId;
import com.idg.bfzb.server.message.model.request.MessageListRequest;
import com.idg.bfzb.server.message.model.vo.MessageListVo;
import com.idg.bfzb.server.message.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	private ContMsgDetailRepository confMsgDetailRepository;

	@Override
	public APIResponse getMessageList(MessageListRequest msgLisRequest) {
		APIResponse apiResponse = new APIResponse();
		
		try{
			Integer offset = msgLisRequest.getOffset();
			Integer size = msgLisRequest.getSize();
			PageInfo<MessageListVo> pageInfo = confMsgDetailRepository.findMsgDetailByPageAndCondition((offset-1)*size, size, msgLisRequest);
			
			ArrayResponse<MessageListVo> data = new ArrayResponse<MessageListVo>();
			data.setTotal((long) pageInfo.getTotalRows());
			data.setItems(pageInfo.getPageData());
			
			apiResponse.setData(data);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse readMessage(String currentUser, Long messageId) {
		APIResponse apiResponse = new APIResponse();
		
		try{
			ContMsgDetailEntityId entity = new ContMsgDetailEntityId();
			entity.setMessageId(messageId);
			entity.setUserId(currentUser);
			
			ContMsgDetailEntity confMsgDetailEntity = confMsgDetailRepository.findOne(entity);
			if(confMsgDetailEntity==null){
				apiResponse.setErrorCode(ErrorCode.MESSAGE_NOT_EXIST);
				return apiResponse;
			}
			
			confMsgDetailEntity.setIsRead(Constants.MESSAGE__STATE_IS_READ);
			confMsgDetailEntity.setReadTime(new Timestamp(System.currentTimeMillis()));
			confMsgDetailRepository.save(confMsgDetailEntity);
			
			apiResponse.setMessage(APIResponse.SUCESS_MSG);
		}catch(Exception e){
			e.printStackTrace();
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
		}
		
		return apiResponse;
	}

	@Override
    public APIResponse allReadMessage(String currentUser) {
        APIResponse apiResponse = new APIResponse();
        
        try{
            String msg = confMsgDetailRepository.setMsgAllReadByUserId(currentUser);
            
            apiResponse.setData(msg);
        }catch(Exception e){
            e.printStackTrace();
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        
        return apiResponse;
    }
}
