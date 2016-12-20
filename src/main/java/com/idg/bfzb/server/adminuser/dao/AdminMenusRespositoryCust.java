package com.idg.bfzb.server.adminuser.dao;

import java.util.List;
import java.util.Map;

public interface AdminMenusRespositoryCust {
    List levelOneMenus(Map<String, Object> params);
    
    List childrenMenus(Map<String, Object> params);
}
