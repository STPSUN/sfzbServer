package com.idg.bfzb.server.usercenter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.message.model.dto.ContMessageEntity;

public interface MessageManagerRepository extends JpaRepository<ContMessageEntity,Long>,MessageManagerRepositoryCust {
    
}
