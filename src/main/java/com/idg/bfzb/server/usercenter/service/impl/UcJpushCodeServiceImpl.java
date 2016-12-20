package com.idg.bfzb.server.usercenter.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.usercenter.dao.UcJpushCodeRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcJpushCodeEntity;
import com.idg.bfzb.server.usercenter.model.request.JpushRequest;
import com.idg.bfzb.server.usercenter.service.UcJpushCodeService;

@Service
public class UcJpushCodeServiceImpl implements UcJpushCodeService{
	
	@Autowired
	private UcJpushCodeRepository jpushCodeRepository;

	@Override
	public APIResponse addJpushCode(String userId, JpushRequest jpushRequest) {
		APIResponse apiResponse = new APIResponse();
		
		String jpushCode = jpushRequest.getJpushCode();
		
		if(StringUtils.isEmpty(jpushCode)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		
		try{
			UcJpushCodeEntity entity = new UcJpushCodeEntity();
			entity.setUserUuid(userId);
			entity.setJpushCode(jpushCode);
			
			List<UcJpushCodeEntity> list = jpushCodeRepository.findByCondition(entity);
			if(list==null || list.size()<=0){
				jpushCodeRepository.save(entity);
				apiResponse.setMessage(APIResponse.SUCESS_MSG);
			}else{
				apiResponse.setErrorCode(ErrorCode.UC_JPUSHCODE_IS_EXIST);
			}
		}catch(Exception e){
			apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
			e.printStackTrace();
		}
		
		return apiResponse;
	}

}
