package com.idg.bfzb.server.usercenter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.usercenter.model.dto.UcAuthCodeEntity;

public interface ShortMessageRepository extends JpaRepository<UcAuthCodeEntity, String>,ShortMessageRepositoryCust {
	/*@Query("select u from UcAuthCodeEntity u where u.mobile = ?1 ")
	Page<UcAuthCodeEntity> selectVerificationCode(String mobile,Pageable pageable);*/
}
