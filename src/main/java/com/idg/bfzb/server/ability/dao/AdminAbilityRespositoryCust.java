package com.idg.bfzb.server.ability.dao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.ability.model.request.AdminAbilityRequest;
import com.idg.bfzb.server.common.model.PageInfo;

public interface AdminAbilityRespositoryCust {
    PageInfo findUserAbilityList(HttpServletRequest servletRequest,Pageable pageable);
    boolean userAbilityModify(String userId,String state,String reason);
    PageInfo findAbilityByCond(AdminAbilityRequest adminAbilityRequest,Pageable pageable);
}
