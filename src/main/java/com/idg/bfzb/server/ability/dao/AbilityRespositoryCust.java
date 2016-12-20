package com.idg.bfzb.server.ability.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.ability.model.vo.AbilityVo;
import com.idg.bfzb.server.ability.model.vo.ProjectAbilityVo;
import com.idg.bfzb.server.common.model.PageInfo;

public interface AbilityRespositoryCust {
    List<AbilityVo> queryAuditAbility(String userId);
    List<AbilityVo> queryNoAuditAbility(String userId);
    List<ProjectAbilityVo> queryProjectAbility(String projectId);
}
