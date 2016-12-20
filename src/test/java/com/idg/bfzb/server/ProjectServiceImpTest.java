package com.idg.bfzb.server;

import java.util.UUID;

import javax.annotation.Resource;

import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.base.UnitTestBase;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.service.SysCodeService;
import com.idg.bfzb.server.project.model.request.EmployeeProjectListRequest;
import com.idg.bfzb.server.project.model.request.EmployeeSelectTenderRequest;
import com.idg.bfzb.server.project.model.request.ProjectPublishRequest;
import com.idg.bfzb.server.project.model.request.ProjectSignUpRequest;
import com.idg.bfzb.server.project.model.request.ReceiverReportRequest;
import com.idg.bfzb.server.project.model.request.ReceiverSearchRequest;
import com.idg.bfzb.server.project.model.request.RecommendRequest;
import com.idg.bfzb.server.project.model.request.TendersSearchRequest;
import com.idg.bfzb.server.project.quartz.ProjectQuartz;
import com.idg.bfzb.server.project.service.ProjectService;
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
 * 类名称：ProjectServiceImpTest
 * 类描述：项目管理ProjectServiceImpTest
 * 创建人：ouzhb
 * 创建时间：2016/11/5
 */
@SuppressWarnings("all")
public class ProjectServiceImpTest extends UnitTestBase {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private SysCodeService sysCodeService;
    @Resource
    private ProjectQuartz pojectQuartz;

    private final static String USER_ID = "00bb3b54-a4e2-4a7e-8647-e51f3caa220e";
    
    private final static String PROJECT_ID = "22eb22e2-859a-4fc6-bd71-daac958ee147";
    
    /*@Test
    public void getAllProjectCategory() throws Exception{
        APIResponse apiResponse = projectService.getAllProjectCategory();
        System.out.println("getAllProjectCategory:"+apiResponse.getData());
    }*/
    
    /*@Test
    public void recommendProject() throws Exception{
    	try{
    		RecommendRequest recommendRequest = new RecommendRequest();
            APIResponse apiResponse = projectService.recommendProject(recommendRequest);
            System.out.println("recommendProject:"+apiResponse.getData());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
    
    /*@Test
    public void qrySysCode() throws Exception{
    	try{
    		APIResponse response = sysCodeService.getSysCodeByClassify("DEADLINE");
        	System.out.println(response);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
   /* @Test
    public void searchReceiverProject() throws Exception{
    	try{
    		ReceiverSearchRequest receiverSearchRequest = new ReceiverSearchRequest();
            APIResponse apiResponse = projectService.searchReceiverProject(receiverSearchRequest);
            System.out.println("searchReceiverProject:"+apiResponse.getData());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
    @Test
    public void employeeProjectList(){
    	EmployeeProjectListRequest projectListRequest = new EmployeeProjectListRequest();
		projectListRequest.setUserId("9814a9e4-1c6b-46c8-84de-e9cbf61841c7");
		//projectListRequest.setState(Constants.PROJECT_STATE_APPROVE_PASS);
		APIResponse apiReponse = projectService.employeeProjectList(projectListRequest);
		System.out.println(apiReponse);
    }
    /*@Test
    public void employeePublishProject(){
    	try{
    		ProjectPublishRequest proPublishRequest = new ProjectPublishRequest();
        	proPublishRequest.setOwnerId("9814a9e4-1c6b-46c8-84de-e9cbf61841c7");
        	proPublishRequest.setCategoryIds("e8ec9731-50ec-488c-8d40-499f747b7414、dd72a7a2-2589-429c-883a-46caaae4295c");
        	proPublishRequest.setProjectName("默认值测试");
        	proPublishRequest.setAddress("福州市晋安区");
        	proPublishRequest.setHouseNumber("斗门邮政XX号");
        	proPublishRequest.setDescription("总包平台测试测试测试");
        	proPublishRequest.setApplyDeadline("20161116");
        	proPublishRequest.setSubmitDeadline("20161230");
        	proPublishRequest.setTelephone("18359134952");
        	proPublishRequest.setBudget(Double.valueOf(7800));
        	proPublishRequest.setTenderType(Constants.PROJECT_TENDER_COMMON);
        	APIResponse apiResponse = projectService.publishProject(proPublishRequest);
        	System.out.println(apiResponse);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
    /*@Test
    public void receiverProjectList(){
    	EmployeeProjectListRequest projectListRequest = new EmployeeProjectListRequest();
		projectListRequest.setUserId("09a64517-7ae6-4108-a054-65acc6859f45");
		projectListRequest.setState(Constants.PROJECT_STATE_SELECTING);
		APIResponse apiReponse = projectService.receiverProjectList(projectListRequest);
		System.out.println(apiReponse);
    }*/
   /* @Test
    public void employeeProjectDetail(){
    	APIResponse apiResponse = projectService.employeeProjectDetail(USER_ID, PROJECT_ID);
    	System.out.println(apiResponse);
    }*/
    /*@Test
    public void confirmProject(){
    	APIResponse apiResponse = projectService.confirmProject(USER_ID, "6ab5a2b8-dd01-4288-ae45-674ec8f0c32f", "ok");
    	System.out.println(apiResponse);
    }*/
   /* @Test
    public void receiverProjectDetail(){
    	APIResponse apiResponse = projectService.receiverProjectDetail(USER_ID, PROJECT_ID);
    	System.out.println(apiResponse);
    }*/
    /*@Test
    public void receiverProjectDetail(){
    	APIResponse apiResponse = sysCodeService.getSysCodeOneByClassify(Constants.PROJECT_CLASSIFY_AGREEMENT);
    	System.out.println(apiResponse);
    }*/
    /*@Test
    public void signUpProject(){
    	try{
    		ProjectSignUpRequest projectSignUpRequest = new ProjectSignUpRequest();
        	projectSignUpRequest.setUserId(USER_ID);
        	projectSignUpRequest.setOffer(3500.00);
        	projectSignUpRequest.setProjectId("6ab5a2b8-dd01-4288-ae45-674ec8f0c32f");
        	
        	APIResponse apiResponse = projectService.signUpProject(projectSignUpRequest);
        	System.out.println(apiResponse);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
    /*@Test
    public void searchTenders(){
    	try{
    		TendersSearchRequest tendersSearchRequest = new TendersSearchRequest();
    		tendersSearchRequest.setProjectId("6ab5a2b8-dd01-4288-ae45-674ec8f0c32f");
        	
        	APIResponse apiResponse = projectService.searchTenders(tendersSearchRequest);
        	System.out.println(apiResponse);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
    /*@Test
    public void receiverReport(){
    	try{
    		//{"project_id":"64ef4af9-4734-4fbf-bed4-2dbd838ddc9e",
    		//"location":"泰禾广场","work_desc":"啦啦啦","work_pic":"c421e5fb-f0ed-4e60-b817-60e19d94584b"}
    		ReceiverReportRequest receiverReportRequest = new ReceiverReportRequest();
    		receiverReportRequest.setProjectId("64ef4af9-4734-4fbf-bed4-2dbd838ddc9e");
    		receiverReportRequest.setLocation("泰禾广场");
    		receiverReportRequest.setWorkDesc("啦啦啦");
    		receiverReportRequest.setWorkPic("c421e5fb-f0ed-4e60-b817-60e19d94584b");
    		receiverReportRequest.setUserId("09a64517-7ae6-4108-a054-65acc6859f45");
    		APIResponse apiResponse = projectService.receiverReport(receiverReportRequest);
    		System.out.println(apiResponse);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
    /*@Test
    public void selectTender(){
    	EmployeeSelectTenderRequest employeeSelectTenderRequest = new EmployeeSelectTenderRequest();
    	employeeSelectTenderRequest.setProjectId("81ecb756-3e22-47c0-9500-c2c6c89dd194");
    	employeeSelectTenderRequest.setEmployerId("09a64517-7ae6-4108-a054-65acc6859f45");
    	employeeSelectTenderRequest.setIsReveal((short) 0);
    	employeeSelectTenderRequest.setTenderUserId("2932233f-61b6-4e7c-a8f3-a0d50423690b");
    	
    	try{
    		APIResponse apiResponse = projectService.selectTender(employeeSelectTenderRequest);
    		System.out.println(apiResponse);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
    /*@Test
    public void receiverBaseProjectDetail(){
    	APIResponse apiResponse = projectService.receiverBaseProjectDetail("ed542396-7dba-470e-9bcd-166b52350594");
    	System.out.println(apiResponse);
    }*/
    /*@Test
    public void doDelayProject(){
    	try{
    		pojectQuartz.autoPayRejection();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }*/
} 
