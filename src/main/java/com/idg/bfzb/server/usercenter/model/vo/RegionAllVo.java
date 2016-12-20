package com.idg.bfzb.server.usercenter.model.vo;

public class RegionAllVo {
    //region_id,parent_code AS parent_region_id,region_code,region_name,full_spell,short_spell,'1' level
    private String regionId;
    private String parentRegionId;
    private String regionCode;
    private String regionName;
    private String fullSpell;
    private String shortSpell;
    private String level;
    
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
    
    public String getParentRegionId() {
        return parentRegionId;
    }

    public void setParentRegionId(String parentRegionId) {
        this.parentRegionId = parentRegionId;
    }

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
    
    public String getFullSpell() {
        return fullSpell;
    }

    public void setFullSpell(String fullSpell) {
        this.fullSpell = fullSpell;
    }
    
    public String getShortSpell() {
        return shortSpell;
    }

    public void setShortSpell(String shortSpell) {
        this.shortSpell = shortSpell;
    }
    
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
