package com.idg.bfzb.server.usercenter.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.usercenter.model.request.UserFinancialRequest;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.model.response.UserFinancialResponse;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;
import com.idg.bfzb.server.usercenter.model.vo.RegionAllVo;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
public interface UserInfoRepositoryCust {
    UcUserInfoEntity findByNameOrMobile(String userName, String mobile);
    List<RegionAllVo> findAllRepository(String parentRegionId);
    /**
     * 根据微信或者QQ的openId查询用户信息
     * @param weixinId	微信openId
     * @param qqId		QQopenId
     * @return
     */
    UcUserInfoEntity findByWeixinIdOrQQId(String weixinId, String qqId);
    /**
     * 判断昵称是否已使用
     * @param userId
     * @param nickName
     * @return
     */
    boolean isExistNickName(String userId,String nickName);
    /**
     * 获取用户列表（普通、企业、团队）
     * @param userManagerRequest
     * @param pageable
     * @return
     */
    PageInfo<UserManagerResponse> findUserListByCond(
			UserManagerRequest userManagerRequest, Pageable pageable);
    
    /**
     * 获取用户实名列表（普通、企业、团队）
     * @param userManagerRequest
     * @param pageable
     * @return
     */
    PageInfo<UserManagerResponse> findUserAuthenticationListByCond(
            UserManagerRequest userManagerRequest, Pageable pageable);
    
    /**
     * 获取普通用户详情
     * @param userManagerRequest
     * @return
     */
    UserManagerResponse findOneNormalUser(UserManagerRequest userManagerRequest);
    
    /**
     * 
     * .获取用户，项目资金信息
     * 
     * @param userFinancialRequest
     * @param pageable
     * @return
     */
    PageInfo<UserFinancialResponse> getFinancialDetail(UserFinancialRequest userFinancialRequest,Pageable pageable);
    
    /**
     * 获取账户余额，
     * @param userFinancialRequest
     * @return
     */
    UserFinancialResponse queryBalance(UserFinancialRequest userManagerRequest);
    
    /**
     * 账户项目预付款
     * @param userFinancialRequest
     * @return
     */
    List<UserFinancialResponse> queryImprest(UserFinancialRequest userManagerRequest);
}
