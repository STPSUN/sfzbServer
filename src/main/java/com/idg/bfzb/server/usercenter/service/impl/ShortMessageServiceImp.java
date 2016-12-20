package com.idg.bfzb.server.usercenter.service.impl;


import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.usercenter.dao.ShortMessageRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcAuthCodeEntity;
import com.idg.bfzb.server.usercenter.service.ShortMessageService;
import com.idg.bfzb.server.utility.tools.SMSUtils;

@Service
public class ShortMessageServiceImp implements ShortMessageService {
    //private final Logger logger = LoggerFactory.getLogger(ShortMessageServiceImp.class);

    @Autowired
    private ShortMessageRepository shortMessageRepository;
    
    @Override
    public APIResponse sendVerificationCode(String mobile){
        SMSUtils smsUtils = new SMSUtils();
        APIResponse apiResponse = new APIResponse();
        String sendMsg = "失败";
        //验证码当天次数限制
        int sendTime = shortMessageRepository.countByMobileAndCurrentDay(mobile);
        if(sendTime > Constants.UC_AUTHCODE_SEND_TIME){
        	apiResponse.setErrorCode(ErrorCode.UC_PIN_OVER_FREQUENCY);
        	return apiResponse;
        }
        //1:生成6位验证码
        String verificationCode = createVerificationCode();
        //2:生成成功发送短信
        if(!verificationCode.isEmpty()){
            try {
                sendMsg = smsUtils.sendCheckCodeSMS(verificationCode,mobile);
            } catch (Exception e) {
                // TODO: handle exception
                apiResponse.setErrorCode(ErrorCode.UC_PIN_SEND_FAIL);
            }
        }else{
            //验证码生成失败
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        //3:发送成功后插入记录
        if(sendMsg != null && sendMsg.isEmpty()){
            try {
                UcAuthCodeEntity ucAuthCodeEntity= new UcAuthCodeEntity();
                ucAuthCodeEntity.setCode(verificationCode);
                ucAuthCodeEntity.setUserId(mobile);
                ucAuthCodeEntity.setMobile(mobile);
                shortMessageRepository.save(ucAuthCodeEntity);
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
            } catch (Exception e) {
                // TODO: handle exception
                apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
            }
        }else{
            apiResponse.setErrorCode(ErrorCode.UC_PIN_SEND_FAIL);
        }
        
        return apiResponse;
    }
    
    @Override
    public APIResponse checkVerificationCode(String mobile, String verificationCode){
        APIResponse apiResponse = new APIResponse();
    	UcAuthCodeEntity ucAuthCodeEntity = new UcAuthCodeEntity();
    	String lastVerificationCode = "";
    	/*//获取最新发送验证码
    	Pageable pageable = new PageRequest(0, 1, Direction.DESC, "recordId");*/
    	try {
    	    //Page<UcAuthCodeEntity> ucAuthCodeEntityPage = shortMessageRepository.selectVerificationCode(mobile, pageable);
    		List<UcAuthCodeEntity> authCodeList = shortMessageRepository.selectVerificationCode(mobile);
            if(!(authCodeList == null || authCodeList.size()<=0)){
            	ucAuthCodeEntity = authCodeList.get(0);
                lastVerificationCode = ucAuthCodeEntity.getCode();
            }
            //验证验证码
            if(verificationCode.equals(lastVerificationCode)){
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
            }else{
                apiResponse.setErrorCode(ErrorCode.UC_PIN_NOT_CORRECT);
            }
        } catch (Exception e) {
            // TODO: handle exception
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
    	
        return apiResponse;
    }
    
    private String createVerificationCode() {
        Random random = new Random();
        String result="";
        for(int i=0;i<6;i++){
            result+=random.nextInt(10);
        }
        return result;
    }
    
}
