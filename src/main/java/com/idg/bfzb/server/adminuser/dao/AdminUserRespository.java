package com.idg.bfzb.server.adminuser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.usercenter.model.dto.UcAdminEntity;

public interface AdminUserRespository extends JpaRepository<UcAdminEntity, String>,AdminUserRespositoryCust{
    
}
