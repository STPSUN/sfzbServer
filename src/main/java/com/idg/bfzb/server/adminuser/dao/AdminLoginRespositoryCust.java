package com.idg.bfzb.server.adminuser.dao;

import java.util.List;
import java.util.Map;

import com.idg.bfzb.server.adminuser.model.vo.LoginUserMsgVo;

public interface AdminLoginRespositoryCust {
    List<LoginUserMsgVo> queryUserInfoByParmas(Map<String, Object> params);
}
