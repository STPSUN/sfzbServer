package com.idg.bfzb.server;

import com.idg.bfzb.server.authentication.model.EnterpriceAuthRequest;
import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.authentication.service.AuthenticationService;
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

@SuppressWarnings("all")
public class AuthenticationServiceImpTest extends UnitTestBase {

    @Autowired
    private AuthenticationService service;

    private final static String USER_ID = "19707059-13ee-4874-8078-680b9a0a46af";
    
    @Test
    public void enterpriseAuthenticationapply() throws Exception{
    	EnterpriceAuthRequest enterprise = new EnterpriceAuthRequest();
    	enterprise.setBusinessEntity("一二三四五六七八九十一二三四五六七八九十");
    	enterprise.setEnterpriseName("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十");
    	enterprise.setBusinessLicenseImage("6555b3dd-528c-4a11-a65b-3917acc61b8d");
    	enterprise.setPhoneNumber("18705087668");
    	enterprise.setEnterpriseNumber("一二三四五六七八九十一二三四五六七八九十");
    	
    	try{
    		APIResponse apiReponse = (APIResponse) service.enterpriseAuthenticationapply(USER_ID, enterprise);
    		System.out.println(apiReponse);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
} 
