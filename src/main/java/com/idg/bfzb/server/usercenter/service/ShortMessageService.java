package com.idg.bfzb.server.usercenter.service;

import com.idg.bfzb.server.common.model.APIResponse;


/**
 * 短信发送Service层
 *
 * @author Administrator
 */
public interface ShortMessageService {
    APIResponse sendVerificationCode(String mobile);
    APIResponse checkVerificationCode(String mobile, String verificationCode);
}