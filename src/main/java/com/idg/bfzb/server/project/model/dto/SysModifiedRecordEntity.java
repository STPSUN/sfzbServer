package com.idg.bfzb.server.project.model.dto;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/12/4
 */
@Entity
@Table(name = "t_sys_modified_record")
public class SysModifiedRecordEntity {
    private Long recordId;
    private String targetObject;
    private String targetObjectId;
    private String property;
    private String propertyAlias;
    private String content;
    private String modifierId;
    private String modifierRealName;
    private Timestamp createDate;

    @Id
    @Column(name = "record_id", nullable = false)
    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "target_object", nullable = true, length = 64)
    public String getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    @Basic
    @Column(name = "target_object_id", nullable = true, length = 64)
    public String getTargetObjectId() {
        return targetObjectId;
    }

    public void setTargetObjectId(String targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    @Basic
    @Column(name = "property", nullable = true, length = 128)
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Basic
    @Column(name = "property_alias", nullable = true, length = 128)
    public String getPropertyAlias() {
        return propertyAlias;
    }

    public void setPropertyAlias(String propertyAlias) {
        this.propertyAlias = propertyAlias;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "modifier_id", nullable = true, length = 64)
    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    @Basic
    @Column(name = "modifier_real_name", nullable = true, length = 128)
    public String getModifierRealName() {
        return modifierRealName;
    }

    public void setModifierRealName(String modifierRealName) {
        this.modifierRealName = modifierRealName;
    }

    @Basic
    @Column(name = "create_date", nullable = false)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysModifiedRecordEntity that = (SysModifiedRecordEntity) o;

        if (recordId != that.recordId) return false;
        if (targetObject != null ? !targetObject.equals(that.targetObject) : that.targetObject != null) return false;
        if (targetObjectId != null ? !targetObjectId.equals(that.targetObjectId) : that.targetObjectId != null)
            return false;
        if (property != null ? !property.equals(that.property) : that.property != null) return false;
        if (propertyAlias != null ? !propertyAlias.equals(that.propertyAlias) : that.propertyAlias != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (modifierId != null ? !modifierId.equals(that.modifierId) : that.modifierId != null) return false;
        if (modifierRealName != null ? !modifierRealName.equals(that.modifierRealName) : that.modifierRealName != null)
            return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recordId ^ (recordId >>> 32));
        result = 31 * result + (targetObject != null ? targetObject.hashCode() : 0);
        result = 31 * result + (targetObjectId != null ? targetObjectId.hashCode() : 0);
        result = 31 * result + (property != null ? property.hashCode() : 0);
        result = 31 * result + (propertyAlias != null ? propertyAlias.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (modifierId != null ? modifierId.hashCode() : 0);
        result = 31 * result + (modifierRealName != null ? modifierRealName.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}
