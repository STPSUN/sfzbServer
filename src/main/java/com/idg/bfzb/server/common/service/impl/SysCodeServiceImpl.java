package com.idg.bfzb.server.common.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.dao.SysCodeRepository;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.vo.SysCodeVo;
import com.idg.bfzb.server.common.service.SysCodeService;
import com.idg.bfzb.server.utility.tools.StringUtil;

@Service
public class SysCodeServiceImpl implements SysCodeService{
	
	private final Logger logger = LoggerFactory.getLogger(SysCodeServiceImpl.class);
	
	@Autowired
	private SysCodeRepository sysCodeRepository;

	@Override
	public APIResponse getSysCodeListByClassify(String classify) {
		APIResponse apiResponse = new APIResponse();
		
		if(StringUtil.isNull(classify)){
			logger.error(String.format("%1$s:%2$s", ErrorCode.PARAM_INPUT_WRONG.getMsg(),"teamId"));
			apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
			return apiResponse;
		}
		
		List<SysCodeVo> list = sysCodeRepository.findByClassify(classify);
		apiResponse.setData(list);
		
		return apiResponse;
	}

	@Override
	public APIResponse getSysCodeOneByClassify(String classify) {
		APIResponse apiResponse = new APIResponse();
		
		if(StringUtil.isNull(classify)){
			logger.error(String.format("%1$s:%2$s", ErrorCode.PARAM_INPUT_WRONG.getMsg(),"teamId"));
			apiResponse.setErrorCode(ErrorCode.PARAM_INPUT_WRONG);
			return apiResponse;
		}
		
		List<SysCodeVo> list = sysCodeRepository.findByClassify(classify);
		if(list!=null && list.size()>0){
			apiResponse.setData(list.get(0));
		}
		
		return apiResponse;
	}

}
