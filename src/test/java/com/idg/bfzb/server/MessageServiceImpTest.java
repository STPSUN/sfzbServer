package com.idg.bfzb.server;

import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.base.UnitTestBase;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.message.model.request.MessageListRequest;
import com.idg.bfzb.server.message.service.MessageService;
import com.idg.bfzb.server.team.model.request.TeamModifyRequest;
import com.idg.bfzb.server.team.model.request.TeamSetRequest;
import com.idg.bfzb.server.team.service.TeamService;
import com.idg.bfzb.server.usercenter.dao.UserPersonalRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.usercenter.model.enums.ZBRoleEnum;
import com.idg.bfzb.server.usercenter.model.request.LoginRequest;
import com.idg.bfzb.server.usercenter.model.request.MobileRegisterRequest;
import com.idg.bfzb.server.usercenter.model.request.PwdModifiedRequest;
import com.idg.bfzb.server.usercenter.model.request.RetrieveRequest;
import com.idg.bfzb.server.usercenter.service.TokenUtil;
import com.idg.bfzb.server.usercenter.service.UserService;
import com.idg.bfzb.server.utility.cache.redis.ValueCacheService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
/**
 * MessageServiceImpTest Tester.
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮ�� 24, 2016</pre>
 */
@SuppressWarnings("all")
public class MessageServiceImpTest extends UnitTestBase {

    @Autowired
    private MessageService messageService;

    private final static String USER_ID = "884d551c-0196-454b-bd3a-8bcadce0f260";

    /*@Test
    public void testTeamUpdate() throws Exception{
    	MessageListRequest msgLisRequest = new MessageListRequest();
    	
    	msgLisRequest.setUserId(USER_ID);
    	msgLisRequest.setIsRead(Constants.MESSAGE__STATE_NOT_READ);
    	msgLisRequest.setChannel(Constants.MESSAGE_CHANNEL_PUSH);
		
    	APIResponse apiResponse = messageService.getMessageList(msgLisRequest);
    	System.out.println(apiResponse);
    }*/
    /*@Test
    public void readMessage() throws Exception{
    	APIResponse apiResponse = messageService.readMessage(USER_ID, 1L);
    	System.out.println(apiResponse);
    }*/
    /*@Test
    public void jpush(){
    	JPushService service = new JPushService();
    	boolean msg = service.sendTextMsg("测试");
    	System.out.println(msg);
    }*/
} 
