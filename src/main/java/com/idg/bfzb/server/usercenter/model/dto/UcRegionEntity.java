package com.idg.bfzb.server.usercenter.model.dto;

import javax.persistence.*;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
@Entity
@Table(name = "uc_region")
public class UcRegionEntity {
    private long regionId;
    private String regionCode;
    private String regionName;
    private Long parentCode;
    private Integer regionLevel;
    private Integer regionOrder;
    private String fullSpell;
    private String shortSpell;
    private Byte state;

    @Id
    @Column(name = "region_id")
    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "region_code")
    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Basic
    @Column(name = "region_name")
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Basic
    @Column(name = "parent_code")
    public Long getParentCode() {
        return parentCode;
    }

    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

    @Basic
    @Column(name = "region_level")
    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    @Basic
    @Column(name = "region_order")
    public Integer getRegionOrder() {
        return regionOrder;
    }

    public void setRegionOrder(Integer regionOrder) {
        this.regionOrder = regionOrder;
    }

    @Basic
    @Column(name = "full_spell")
    public String getFullSpell() {
        return fullSpell;
    }

    public void setFullSpell(String fullSpell) {
        this.fullSpell = fullSpell;
    }

    @Basic
    @Column(name = "short_spell")
    public String getShortSpell() {
        return shortSpell;
    }

    public void setShortSpell(String shortSpell) {
        this.shortSpell = shortSpell;
    }

    @Basic
    @Column(name = "state")
    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UcRegionEntity that = (UcRegionEntity) o;

        if (regionId != that.regionId) return false;
        if (regionCode != null ? !regionCode.equals(that.regionCode) : that.regionCode != null) return false;
        if (regionName != null ? !regionName.equals(that.regionName) : that.regionName != null) return false;
        if (parentCode != null ? !parentCode.equals(that.parentCode) : that.parentCode != null) return false;
        if (regionLevel != null ? !regionLevel.equals(that.regionLevel) : that.regionLevel != null) return false;
        if (regionOrder != null ? !regionOrder.equals(that.regionOrder) : that.regionOrder != null) return false;
        if (fullSpell != null ? !fullSpell.equals(that.fullSpell) : that.fullSpell != null) return false;
        if (shortSpell != null ? !shortSpell.equals(that.shortSpell) : that.shortSpell != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (regionId ^ (regionId >>> 32));
        result = 31 * result + (regionCode != null ? regionCode.hashCode() : 0);
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
        result = 31 * result + (parentCode != null ? parentCode.hashCode() : 0);
        result = 31 * result + (regionLevel != null ? regionLevel.hashCode() : 0);
        result = 31 * result + (regionOrder != null ? regionOrder.hashCode() : 0);
        result = 31 * result + (fullSpell != null ? fullSpell.hashCode() : 0);
        result = 31 * result + (shortSpell != null ? shortSpell.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
