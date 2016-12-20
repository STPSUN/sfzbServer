package com.idg.bfzb.server.content.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.content.model.request.ContentRequest;
import com.idg.bfzb.server.content.model.response.ContentResponse;

public interface ContentManagerRespositoryCust {
    public PageInfo queryAdBannersByCond (ContentRequest contentRequest,Pageable pageable);
    
    public List<ContentResponse> queryAdvSort(ContentRequest contentRequest);
}
