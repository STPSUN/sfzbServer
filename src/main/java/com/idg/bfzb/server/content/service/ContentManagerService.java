package com.idg.bfzb.server.content.service;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.content.model.request.ContentRequest;

public interface ContentManagerService {
    /**
     * 
     * .查询广告配置内容
     * 
     * @param contentRequest
     * @param pageable
     * @return
     */
    PageInfo queryAdBanners(ContentRequest contentRequest,Pageable pageable);
    
    /**
     * 
     * .查询已使用的序列
     * 
     * @param contentRequest
     * @return
     */
    APIResponse queryAdvSort(ContentRequest contentRequest);
    
    /**
     * 
     * .保存广告，友情链接
     * 
     * @param contentRequest
     * @return
     */
    APIResponse saveAdvertisement(ContentRequest contentRequest);
    
    /**
     * 
     * .删除广告，友情链接
     * 
     * @param advId
     * @return
     */
    APIResponse delAdvertisement(String advId,String updateUserId);
    
    /**
     * 
     * .修改广告状态
     * 
     * @param advId
     * @return
     */
    APIResponse changeAdvertisementType(String advId,String updateUserId,short status);
}
