package com.idg.bfzb.server.usercenter.service.impl;

import com.idg.bfzb.server.usercenter.dao.UcAuthCodeRepository;
import com.idg.bfzb.server.usercenter.model.dto.UcAuthCodeEntity;
import com.idg.bfzb.server.usercenter.service.UcCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
@Service
public class UcCodeServiceImp implements UcCodeService {

    @Autowired
    private UcAuthCodeRepository authCodeRepository;

    @Override
    public UcAuthCodeEntity insert(UcAuthCodeEntity ucCodeEntity) {
        return authCodeRepository.save(ucCodeEntity);
    }

    @Override
    public String getAuthCodeByUserId(String userId) {
        return authCodeRepository.findAuthCodeByUserId(userId);
    }
}
