package com.idg.bfzb.server.adminuser.service;

import java.util.List;
import java.util.Map;

import com.idg.bfzb.server.adminuser.model.vo.LoginUserMsgVo;
import com.idg.bfzb.server.usercenter.model.dto.UcAdminEntity;

public interface AdminService {
    /**
     * 
     * .登录密码填错次数修改（暂时不管）
     * 
     * @param params
     * @return
     */
    boolean changeErrorNum(Map<String,Object> params);
    
    /**
     * 
     * .修改最后登录时间
     * 
     * @param adminMsg
     * @return
     */
    boolean changeLastLoginTime(LoginUserMsgVo adminMsg);
    
    /**
     * 
     * .登录密码填错次数查询（暂时不管）
     * 
     * @param userName
     * @return
     */
    int queryUserErrorNum(String userName);
    
    /**
     * 查询用户
     * @param user
     * @return
     */
    List<LoginUserMsgVo> queryUserMsg(Map<String, Object> params);
    
    /**
     * 
     * .查询首页左边菜单
     * 
     * @param params
     * @return
     */
    List queryMenus(Map<String, Object> params);
    
    /**
     * 
     * .修改管理员用户
     * 
     * @param ucAdminEntity
     * @return
     */
    boolean updateAdminUser(UcAdminEntity ucAdminEntity);
    
    /**
     * 
     * .修改最后退出时间
     * 
     * @param audituserId
     * @return
     */
    boolean updateLastOutTime(String adminUserId);
}
