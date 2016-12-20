package com.idg.bfzb.server.ability.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.ability.model.dto.TUserAbilityAssoc;

public interface AbilityRespository extends JpaRepository<TUserAbilityAssoc, String>, AbilityRespositoryCust{
    
}
