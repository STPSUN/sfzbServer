package com.idg.bfzb.server.usercenter.service;

import com.idg.bfzb.server.usercenter.model.dto.UcAuthCodeEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/29
 */
public interface UcCodeService {
    /**
     * 插入
     *
     * @param ucCodeEntity 验证码实体
     * @return 插入成功与否
     */
    UcAuthCodeEntity insert(UcAuthCodeEntity ucCodeEntity);

    /**
     * 获取用户在指定类型下的最后一条验证码
     * @param userId 用户id
     * @return 最后一条验证码
     */
    String getAuthCodeByUserId(String userId);
}
