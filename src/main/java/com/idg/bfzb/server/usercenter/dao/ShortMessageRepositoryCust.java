package com.idg.bfzb.server.usercenter.dao;

import java.util.List;

import com.idg.bfzb.server.usercenter.model.dto.UcAuthCodeEntity;


public interface ShortMessageRepositoryCust {
	
	List<UcAuthCodeEntity> selectVerificationCode(String mobile);
	
	int countByMobileAndCurrentDay(String mobile);
}
