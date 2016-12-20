package com.idg.bfzb.server.adminuser.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.adminuser.dao.AdminLoginRespository;
import com.idg.bfzb.server.adminuser.dao.AdminMenusRespository;
import com.idg.bfzb.server.adminuser.dao.AdminUserRespository;
import com.idg.bfzb.server.adminuser.model.vo.LoginUserMsgVo;
import com.idg.bfzb.server.adminuser.service.AdminService;
import com.idg.bfzb.server.usercenter.model.dto.UcAdminEntity;

@Service
public class AdminServiceImpl implements AdminService{
    private final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    
    @Autowired
    private AdminUserRespository adminUserRespository;
    
    @Autowired
    private AdminLoginRespository adminLoginRespository;
    
    @Autowired
    private AdminMenusRespository adminMenusRespository;
    
    /**
     * 
     * .登录密码填错次数修改
     * 
     * @param params
     * @return
     */
    public boolean changeErrorNum(Map<String,Object> params){
        try {
            int reuslt = adminUserRespository.changeErrorNum(params);
            if(reuslt>0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage());
            return false;
        }
    }
    
    /**
     * 
     * .修改最后登录时间
     * 
     * @param params
     * @return
     */
    public boolean changeLastLoginTime(LoginUserMsgVo adminMsg){
        try {
            UcAdminEntity ucAdminEntity= this.adminUserRespository.findOne(adminMsg.getAdminId());
            ucAdminEntity.setLastLoginTime(new Timestamp(new Date().getTime()));
            this.adminUserRespository.save(ucAdminEntity);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("修改最后登录时间失败!");
            return false;
        }
    }
    
    /**
     * 
     * .登录密码填错次数查询
     * 
     * @param userName
     * @return
     */
    public int queryUserErrorNum(String adminAccount){
        return adminUserRespository.queryUserErrorNum(adminAccount);
    }
    
    /**
     * 查询用户
     * @param user
     * @return
     */
    public List<LoginUserMsgVo> queryUserMsg(Map<String, Object> params){
        return adminLoginRespository.queryUserInfoByParmas(params);
    }
    
    /**
     *
     *首页左边菜单
     *
     * @param params
     * @return
     */
    public List queryMenus(Map<String, Object> params){
        List pMenuList=adminMenusRespository.levelOneMenus(params);
        List subMenuList =adminMenusRespository.childrenMenus(params);
        if(pMenuList!=null && !pMenuList.isEmpty()){
            for (int i = 0; i < pMenuList.size(); i++) {
                HashMap p=(HashMap) pMenuList.get(i);
                ArrayList subMenus=new ArrayList();
                if(subMenuList!=null && !subMenuList.isEmpty()){
                    for (int j = 0; j < subMenuList.size(); j++) {
                        HashMap sub=(HashMap) subMenuList.get(j);
                        if(p.get("MENU_ID").toString().equals(sub.get("P_MENU_ID").toString())){
                            subMenus.add(sub);
                        }
                    }
                }//SUB_MENUS
                p.put("SUB_MENUS", subMenus);
            }
        }
        return pMenuList;
    }
    
    /**
     * 
     * .修改管理员用户
     * 
     * @param ucAdminEntity
     * @return
     */
    public boolean updateAdminUser(UcAdminEntity ucAdminEntity){
        try {
            adminUserRespository.save(ucAdminEntity);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
    
    /**
     * 修改最后退出时间
     */
    public boolean updateLastOutTime(String adminUserId){
        try {
            boolean flag = adminUserRespository.updateLastOutTime(adminUserId);
            return flag;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
}
