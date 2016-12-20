package com.idg.bfzb.server.content.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.content.model.dto.TContAdvertisementEntity;

public interface ContentManagerRespository extends JpaRepository<TContAdvertisementEntity,String>,ContentManagerRespositoryCust {
    
}
