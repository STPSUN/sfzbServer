package com.idg.bfzb.server.common.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.message.dao.ContMsgDetailRepository;
import com.idg.bfzb.server.message.model.dto.ContMessageEntity;
import com.idg.bfzb.server.usercenter.dao.UcJpushCodeRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcJpushCodeEntity;

/**
 * 类名称：MessageCommon
 * 类描述：消息处理类
 * 创建人：ouzhb
 * 创建日期：2016/12/08
 */
@Service
public class MessageCommon {
	@Autowired
	private ContMsgDetailRepository confMsgDetailRepository;
	@Autowired
	private UcJpushCodeRepository ucJpushCodeRepository;
	@Autowired
	private JPushService jpushService;
	/**
	 * 发送消息
	 * @param channel	消息发送渠道： 推送 push | 短信sms
	 * @param msg		消息内容
	 * @param toUserId	接收消息的用户ID
	 * @return
	 */
	public boolean sendMessage(String channel, String msg, String toUserId) {
		ContMessageEntity contMessageEntity = new ContMessageEntity();
		contMessageEntity.setChannel(channel);
		contMessageEntity.setContent(msg);
		
		boolean flag = false;
		try{
			flag = confMsgDetailRepository.insertOneUnReadMessage(contMessageEntity, toUserId);
			if(flag){//极光推送
				sendJpush(toUserId, msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 极光推送
	 * @param toUserId
	 * @param msg
	 * @return
	 */
	public boolean sendJpush(String toUserId, String msg){
		boolean flag = false;
		
		UcJpushCodeEntity entity = new UcJpushCodeEntity();
		entity.setUserUuid(toUserId);
		List<UcJpushCodeEntity> list = ucJpushCodeRepository.findByCondition(entity);
		
		if(list!=null&&list.size()>0){
			String[] jpushCodes = new String[list.size()];
			for(int i=0;i<list.size();i++){
				jpushCodes[i] = list.get(i).getJpushCode();
			}
			
			flag = jpushService.sendTextMsg(msg, jpushCodes);
		}
		
		return flag;
	}
}
