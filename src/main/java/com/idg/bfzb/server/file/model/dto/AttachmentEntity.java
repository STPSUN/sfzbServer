package com.idg.bfzb.server.file.model.dto;
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * AttachmentEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_attachment")

public class AttachmentEntity  implements java.io.Serializable {


    // Fields    

     private String attchId;
     private String attchName;
     private String attchRename;
     private String targetType;
     private String targetId;
     private String attchUrl;
     private Long attchSize;
     private String fileType;
     private String fileMd5;
     private Short state;
     private Timestamp createTime;
     private Timestamp updateTime;


    // Constructors

    /** default constructor */
    public AttachmentEntity() {
    }

	/** minimal constructor */
    public AttachmentEntity(String attchId, String targetType, String targetId, String fileType, Timestamp createTime, Timestamp updateTime) {
        this.attchId = attchId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.fileType = fileType;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    
    /** full constructor */
    public AttachmentEntity(String attchId, String attchName, String attchRename, String targetType, String targetId, String attchUrl, Long attchSize, String fileType, String fileMd5, Short state, Timestamp createTime, Timestamp updateTime) {
        this.attchId = attchId;
        this.attchName = attchName;
        this.attchRename = attchRename;
        this.targetType = targetType;
        this.targetId = targetId;
        this.attchUrl = attchUrl;
        this.attchSize = attchSize;
        this.fileType = fileType;
        this.fileMd5 = fileMd5;
        this.state = state;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="attch_id", unique=true, nullable=false, length=64)

    public String getAttchId() {
        return this.attchId;
    }
    
    public void setAttchId(String attchId) {
        this.attchId = attchId;
    }
    
    @Column(name="attch_name", length=256)

    public String getAttchName() {
        return this.attchName;
    }
    
    public void setAttchName(String attchName) {
        this.attchName = attchName;
    }
    
    @Column(name="attch_rename", length=256)

    public String getAttchRename() {
        return this.attchRename;
    }
    
    public void setAttchRename(String attchRename) {
        this.attchRename = attchRename;
    }
    
    @Column(name="target_type", nullable=false, length=64)

    public String getTargetType() {
        return this.targetType;
    }
    
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
    
    @Column(name="target_id", nullable=false, length=64)

    public String getTargetId() {
        return this.targetId;
    }
    
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
    
    @Column(name="attch_url", length=256)

    public String getAttchUrl() {
        return this.attchUrl;
    }
    
    public void setAttchUrl(String attchUrl) {
        this.attchUrl = attchUrl;
    }
    
    @Column(name="attch_size")

    public Long getAttchSize() {
        return this.attchSize;
    }
    
    public void setAttchSize(Long attchSize) {
        this.attchSize = attchSize;
    }
    
    @Column(name="file_type", nullable=false, length=64)

    public String getFileType() {
        return this.fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    @Column(name="file_md5", length=128)

    public String getFileMd5() {
        return this.fileMd5;
    }
    
    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
    
    @Column(name="state")

    public Short getState() {
        return this.state;
    }
    
    public void setState(Short state) {
        this.state = state;
    }
    
    @Column(name="create_time", nullable=false, length=19)

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="update_time", nullable=false, length=19)

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
   








}