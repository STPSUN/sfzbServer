package com.idg.bfzb.server.adminuser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.adminuser.model.dto.UcAdminMenusEntity;


public interface AdminMenusRespository extends JpaRepository<UcAdminMenusEntity, String>,AdminMenusRespositoryCust{

}
