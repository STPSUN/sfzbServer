package com.idg.bfzb.server;

import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.base.UnitTestBase;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.homepage.service.HomepageService;
import com.idg.bfzb.server.usercenter.dao.UserPersonalRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.usercenter.model.enums.PlatformType;
import com.idg.bfzb.server.usercenter.model.enums.ZBRoleEnum;
import com.idg.bfzb.server.usercenter.model.request.LoginRequest;
import com.idg.bfzb.server.usercenter.model.request.MobileRegisterRequest;
import com.idg.bfzb.server.usercenter.model.request.PlatformLoginRequest;
import com.idg.bfzb.server.usercenter.model.request.PwdModifiedRequest;
import com.idg.bfzb.server.usercenter.model.request.RetrieveRequest;
import com.idg.bfzb.server.usercenter.service.UserService;
import com.idg.bfzb.server.utility.cache.redis.ValueCacheService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SuppressWarnings("all")
public class HomepageServiceImpTest extends UnitTestBase {

    @Autowired
    private HomepageService homepageService;

    @Test
    public void testSwitchRole() throws Exception{
    	APIResponse apiReponse = (APIResponse) homepageService.getHomePageBanners("","1");
    	System.out.println(apiReponse);
    }
} 
