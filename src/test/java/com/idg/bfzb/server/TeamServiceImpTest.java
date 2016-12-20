package com.idg.bfzb.server;

import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.base.UnitTestBase;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
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
 * TeamServiceImp Tester.
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮ�� 24, 2016</pre>
 */
@SuppressWarnings("all")
public class TeamServiceImpTest extends UnitTestBase {

    @Autowired
    private TeamService teamService;

    private final static String USER_ID = "884d551c-0196-454b-bd3a-8bcadce0f260";

    /*@Test
    public void testTeamSet() throws Exception{
    	TeamSetRequest teamSetRequest = new TeamSetRequest();
    	
    	teamSetRequest.setTeamName("团队测试名称");
    	teamSetRequest.setTeamDesc("团队测试昵称");
    	teamSetRequest.setServiceContent("专门用来团队接口测试");
    	teamSetRequest.setServiceExp("测试测试再测试");
    	teamSetRequest.setCity("福州");
    	teamSetRequest.setIdentifyNum("350322XXXX4544");
    	teamSetRequest.setTelephone("110");
    	teamSetRequest.setUserName("测试姓名");
    	
        APIResponse apiResponse = teamService.setTeam(USER_ID, teamSetRequest);
        System.out.println("testTeamSet:"+apiResponse.getMessage());
    }
    
    @Test
    public void testTeamDetail() throws Exception{
        APIResponse apiResponse = teamService.detail(USER_ID, "dca766cb-82be-4698-a027-69370fe1e1c7");
        System.out.println("testTeamDetail:"+apiResponse.getData());
    }*/
    
    @Test
    public void testTeamUpdate() throws Exception{
    	TeamModifyRequest teamModifyRequest = new TeamModifyRequest();
    	
    	teamModifyRequest.setTeamId("dca766cb-82be-4698-a027-69370fe1e1c7");
    	teamModifyRequest.setTeamName("团队测试名称3");
    	teamModifyRequest.setTeamDesc("团队测试昵称");
    	teamModifyRequest.setServiceContent("专门用来团队接口测试");
    	teamModifyRequest.setServiceExp("测试测试再测试");
    	teamModifyRequest.setCity("福州");
    	teamModifyRequest.setIdentifyNum("350322XXXX4544");
    	teamModifyRequest.setTelephone("110");
    	teamModifyRequest.setUserName("测试姓名");
    	
        APIResponse apiResponse = teamService.modifyTeam(USER_ID, teamModifyRequest);
        System.out.println("testTeamUpdate:"+apiResponse.getMessage());
    }
    
    public static void main(String[] args) {
		System.out.println(TokenUtil.createMacToken(USER_ID, System.currentTimeMillis()+10*5*3600));;
	}
} 
