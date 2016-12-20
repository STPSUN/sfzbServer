package com.idg.bfzb.server.ability.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.ability.model.dto.TAbilityEntity;

public interface AdminAbilityRespository extends JpaRepository<TAbilityEntity, String>,AdminAbilityRespositoryCust{

}
