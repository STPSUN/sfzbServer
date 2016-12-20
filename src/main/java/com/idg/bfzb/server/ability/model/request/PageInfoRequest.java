package com.idg.bfzb.server.ability.model.request;

import java.io.Serializable;

/**
 * 
 * .分页请求信息
 * 
 * @author Administrator
 * @version Revision 1.0.0
 *
 */
public class PageInfoRequest implements Serializable{
    private int pageNum;
    private int pageSize;
    
    public int getPageNum(){
        return pageNum;
    }
    
    public void setPageNum(int pageNum){
        this.pageNum = pageNum;
    }
    
    public int getPageSize(){
        return pageSize;
    }
    
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }
}
