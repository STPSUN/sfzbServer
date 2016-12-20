package com.idg.bfzb.server.adminuser.dao;

import java.util.Map;

public interface AdminUserRespositoryCust {
    int changeErrorNum(Map<String,Object> params);
    
    int queryUserErrorNum(String adminAccount);
    
    boolean updateLastOutTime(String adminUserId);
}
