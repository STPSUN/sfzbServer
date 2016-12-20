package com.idg.bfzb.server.usercenter.model.vo;

import java.io.Serializable;

/**
 * 类名称：RegionVo
 * 类描述：行政区域实体
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
public class RegionVo implements Serializable {
    private String regionCode;

    private String regionName;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
