package com.idg.bfzb.server.adminuser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.usercenter.model.dto.UcAdminEntity;

public interface AdminLoginRespository extends JpaRepository<UcAdminEntity, String>,AdminLoginRespositoryCust{
    
}
