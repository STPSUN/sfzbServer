package com.idg.bfzb.server.usercenter.dao;

import java.util.List;

import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.model.vo.UserBaseAndPersonVo;
import com.idg.bfzb.server.usercenter.model.vo.UserBaseVo;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public interface UserPersonalRepositoryCust {
    /**
     *
     * @param userId
     * @return
     */
    UserBaseVo findUserVoByUserId(String userId);
    
    List<UserBaseAndPersonVo> findUserPersonByCond(UserManagerRequest userManagerRequest);
    
    int updateBalanceByUserId(String userId,Double fee);
}
