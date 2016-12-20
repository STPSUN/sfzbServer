package com.idg.bfzb.server.configure.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.configure.model.dto.UcRegionConfigureEntity;

public interface RegionRespository extends JpaRepository<UcRegionConfigureEntity,Long>,RegionRespositoryCust{

}
