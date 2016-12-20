package com.idg.bfzb.server;

import javax.annotation.Resource;

import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.base.UnitTestBase;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.tools.JPushService;
import com.idg.bfzb.server.usercenter.dao.UserPersonalRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.usercenter.model.enums.ZBRoleEnum;
import com.idg.bfzb.server.usercenter.model.request.LoginRequest;
import com.idg.bfzb.server.usercenter.model.request.MobileRegisterRequest;
import com.idg.bfzb.server.usercenter.model.request.PwdModifiedRequest;
import com.idg.bfzb.server.usercenter.model.request.RetrieveRequest;
import com.idg.bfzb.server.usercenter.service.UcJpushCodeService;
import com.idg.bfzb.server.usercenter.service.UserService;
import com.idg.bfzb.server.utility.cache.redis.ValueCacheService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
/**
 * UserServiceImp Tester.
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮ�� 24, 2016</pre>
 */
@SuppressWarnings("all")
public class UserServiceImpTest extends UnitTestBase {

    @Autowired
    private UserPersonalRepository userPersonalRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UcJpushCodeService jpushCodeService;
    @Resource
    private JPushService jpushService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    private final static String USER_ID = "884d551c-0196-454b-bd3a-8bcadce0f260";

    /*@Test
    @Transactional
    public void testRegisterByMobile() throws Exception{
        APIResponse apiResponse;
        MobileRegisterRequest registerRequest = new MobileRegisterRequest();

        registerRequest.setAuthCode("809694");
        registerRequest.setMobile("18650363881");
        registerRequest.setPassword("c46d0964b95f60b1c231bae2bdf36858");
        apiResponse = this.userService.registerByMobile(registerRequest);
        assertEquals(apiResponse.getCode(), ErrorCode.UC_MOBILE_ALREADY_REGISTER.getCode());

         已存在这个账号 
        registerRequest.setAuthCode("8888");
        registerRequest.setMobile("18120920855");
        registerRequest.setPassword("123456");
        apiResponse = this.userService.registerByMobile(registerRequest);
        assertEquals(apiResponse.getCode(), ErrorCode.UC_MOBILE_ALREADY_REGISTER.getCode());

        registerRequest.setAuthCode("8888");
        registerRequest.setMobile("18905911680");
        registerRequest.setPassword("123456");
        apiResponse = this.userService.registerByMobile(registerRequest);
    }
    *//**
     *//*
    @Test
    public void testDoLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLoginName("18120920855");
        loginRequest.setPassword("123456");

        this.userService.doLogin(loginRequest);
    }

    @Transactional
    @Test
    public void testModifyUserPassword() throws Exception{
        PwdModifiedRequest pwdModifiedRequest = new PwdModifiedRequest();
        pwdModifiedRequest.setOldPassword("123456");
        pwdModifiedRequest.setNewPassword("1234567");

        this.userService.modifyUserPassword(USER_ID,pwdModifiedRequest);
    }

    @Test
    public void testRetrievePassword() throws Exception{
        RetrieveRequest retrieveRequest = new RetrieveRequest();
        retrieveRequest.setNewPassword("123456");
        retrieveRequest.setAuthCode("8888");
        retrieveRequest.setMobile("18120920855");
        this.userService.retrievePassword(retrieveRequest);
    }

    @Autowired
    private ValueCacheService<String, String> valueCacheService;

    @Test
    public void testLoginOut() throws Exception{
        this.userService.loginOut(USER_ID);
        String json = valueCacheService.get(USER_ID);
        assertNull(json);
    }

    @Test
    public void testSwitchRole() throws Exception{
        APIResponse apiResponse;
        apiResponse = this.userService.switchRole(USER_ID, ZBRoleEnum.EMPLOYER.getValue());

        UserPersonalEntity personalEntity =  userPersonalRepository.findOne(USER_ID);
        assertEquals(personalEntity.getLastRole(),ZBRoleEnum.EMPLOYER.getValue());
         参数异常 
        apiResponse = this.userService.switchRole(USER_ID, "adadaf");
        assertEquals(apiResponse.getCode(),ErrorCode.INVALID_ARGUMENT.getCode());

         用户不存在 
        apiResponse = this.userService.switchRole("3432", ZBRoleEnum.EMPLOYER.getValue());
        assertEquals(apiResponse.getCode(),ErrorCode.UC_USER_NOT_EXIST.getCode());
    }*/
    
    /*@Test
    public void addJpushCode(){
    	try{
    		APIResponse apiResponse = jpushCodeService.addJpushCode(USER_ID, "XXOO");
    		System.out.println(apiResponse);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
    @Test
    public void sendJpush(){
    	try{
    		String[] userIds = {"190e35f7e04b4aeb178"};
        	boolean flag = jpushService.sendTextMsg("极光测试", userIds);
        	System.out.println(flag);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
} 
