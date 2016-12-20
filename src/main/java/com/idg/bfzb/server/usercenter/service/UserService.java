package com.idg.bfzb.server.usercenter.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.usercenter.model.request.EvaluateManagerRequest;
import com.idg.bfzb.server.usercenter.model.request.LoginRequest;
import com.idg.bfzb.server.usercenter.model.request.MessageManagerRequest;
import com.idg.bfzb.server.usercenter.model.request.MobileRegisterRequest;
import com.idg.bfzb.server.usercenter.model.request.PlatformBindRequest;
import com.idg.bfzb.server.usercenter.model.request.PlatformLoginRequest;
import com.idg.bfzb.server.usercenter.model.request.UcUsersResumeRequest;
import com.idg.bfzb.server.usercenter.model.request.PwdModifiedRequest;
import com.idg.bfzb.server.usercenter.model.request.RetrieveRequest;
import com.idg.bfzb.server.usercenter.model.request.UserFinancialRequest;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;
import com.idg.bfzb.server.usercenter.model.vo.UserBaseVo;



/**
 * 用户管理Service层
 *
 * @author Administrator
 */
public interface UserService {

    /**
     * 用户通过HTTP登陆，登陆成功后把token保存到缓存中
     * @param loginName 登陆名
     * @param password 首次md5加密后的密码
     * @param imei imei号码
     * @return 登陆结果
     */
    APIResponse doLogin(LoginRequest loginRequest);

    /**
     *
     * @param registerRequest
     * @return
     */
    APIResponse registerByMobile(MobileRegisterRequest registerRequest);

    APIResponse getUserBaseInfo(String userId);

    
    APIResponse queryCityMsg(List<Map<String,Object>> regionAllVoList,String parentRegionId);


    /**
     * 用户修改密码
     * @param pwdModifiedRequest 密码修改请求体
     * @return APIResponse 接口返回内容
     */
    APIResponse modifyUserPassword(String userId,final PwdModifiedRequest pwdModifiedRequest);

    /**
     *
     * @param userId
     * @param newPassword
     * @return
     */
    APIResponse modifyUserPassword(String userId,String newPassword);

    /**
     * 找回密码
     * @param userId 用户ID
     * @param retrieveRequest 重置密码请求
     * @return APIResponse 找回密码操作结果
     */
    APIResponse retrievePassword(final RetrieveRequest retrieveRequest);

    /**
     *
     * @param userId
     * @return
     */
    APIResponse loginOut(String userId);


    APIResponse switchRole(String userId,String roleName);
    
    /**
     * 
     * 修改用户信息
     * 
     * @param userId
     * @param ucUsersResumeRequest
     * @return
     */
    APIResponse modifyUserBaseInfo(String userId, UcUsersResumeRequest ucUsersResumeRequest);

    /**
     * 获取普通用户列表（后台）
     * @param userManagerRequest
     * @return
     */
    PageInfo getUserList(UserManagerRequest userManagerRequest,Pageable pageable);
    
    /**
     * 获取用户实名列表（后台）
     * @param userManagerRequest
     * @return
     */
    PageInfo getUserAuthenticationList(UserManagerRequest userManagerRequest,Pageable pageable);

    /**
     * 获取普通用户的详情信息
     * @param userManagerRequest
     * @return
     */
	APIResponse getOneNormalUser(UserManagerRequest userManagerRequest);
	/**
	 * 个人认证审核
	 * @param userId
	 * @param reviewState
	 * @return
	 */
	APIResponse auditPersonUser(String userId,HttpServletRequest servletRequest);
	
	/**
	 * 
	 * .资金明显流水，
	 * 
	 * @param userFinancialRequest
	 * @param pageable
	 * @return
	 */
	PageInfo financialDetail(UserFinancialRequest userFinancialRequest,Pageable pageable);
	
	/**
	 * 
	 * .账户余额，账户项目预付款
	 * 
	 * @param userFinancialRequest
	 * @return
	 */
	APIResponse balanceAndImprest(UserFinancialRequest userFinancialRequest);
	
	/**
	 * 
	 * .评价信息查询
	 * 
	 * @param evaluateManagerRequest
	 * @param pageable
	 * @return
	 */
	PageInfo queryEvaluate(EvaluateManagerRequest evaluateManagerRequest,Pageable pageable);
	
	/**
	 * 
	 * .消息管理详情查询
	 * 
	 * @param messageManagerRequest
	 * @param pageable
	 * @return
	 */
	PageInfo queryMessageDetail(MessageManagerRequest messageManagerRequest,Pageable pageable);
	
	/**
	 * 
	 * .删除消息记录
	 * 
	 * @param messageId
	 * @return
	 */
	APIResponse deletMessage(String messageId);
	/**
	 * 第三方应用登录
	 * @param platformLoginRequest
	 * @return
	 */
	APIResponse loginPlatform(PlatformLoginRequest platformLoginRequest);
	/**
	 * 登录绑定第三方应用
	 * @param platformLoginRequest
	 * @return
	 */
	APIResponse bindPlatform(PlatformBindRequest platformBindRequest);
}
