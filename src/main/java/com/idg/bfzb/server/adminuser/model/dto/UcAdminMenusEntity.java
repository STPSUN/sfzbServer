package com.idg.bfzb.server.adminuser.model.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UcAdminMenus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "uc_admin_menus")
public class UcAdminMenusEntity implements java.io.Serializable {
    
    // Fields
    
    private Short  menuId;
    private String menuName;
    private Date   createDate;
    private String url;
    private Short  PMenuId;
    private String createUserId;
    private Short  state;
    private Short  level;
    private String iconClass;
    private Short  orderId;
    
    // Constructors
    
    /** default constructor */
    public UcAdminMenusEntity() {
    }
    
    /** minimal constructor */
    public UcAdminMenusEntity(Short menuId) {
        this.menuId = menuId;
    }
    
    /** full constructor */
    public UcAdminMenusEntity(Short menuId, String menuName, Date createDate,
        String url, Short PMenuId, String createUserId, Short state,
        Short level, String iconClass, Short orderId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.createDate = createDate;
        this.url = url;
        this.PMenuId = PMenuId;
        this.createUserId = createUserId;
        this.state = state;
        this.level = level;
        this.iconClass = iconClass;
        this.orderId = orderId;
    }
    
    // Property accessors
    @Id
    @Column(name = "menu_id", unique = true, nullable = false)
    public Short getMenuId() {
        return this.menuId;
    }
    
    public void setMenuId(Short menuId) {
        this.menuId = menuId;
    }
    
    @Column(name = "menu_name", length = 100)
    public String getMenuName() {
        return this.menuName;
    }
    
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "create_date", length = 10)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Column(name = "url", length = 100)
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name = "p_menu_id")
    public Short getPMenuId() {
        return this.PMenuId;
    }
    
    public void setPMenuId(Short PMenuId) {
        this.PMenuId = PMenuId;
    }
    
    @Column(name = "create_user_id", length = 100)
    public String getCreateUserId() {
        return this.createUserId;
    }
    
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    
    @Column(name = "state")
    public Short getState() {
        return this.state;
    }
    
    public void setState(Short state) {
        this.state = state;
    }
    
    @Column(name = "level")
    public Short getLevel() {
        return this.level;
    }
    
    public void setLevel(Short level) {
        this.level = level;
    }
    
    @Column(name = "icon_class", length = 20)
    public String getIconClass() {
        return this.iconClass;
    }
    
    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }
    
    @Column(name = "order_id")
    public Short getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(Short orderId) {
        this.orderId = orderId;
    }
    
}
