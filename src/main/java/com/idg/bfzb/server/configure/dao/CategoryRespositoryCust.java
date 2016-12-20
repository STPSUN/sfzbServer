package com.idg.bfzb.server.configure.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.model.request.AdminCategoryRequest;
import com.idg.bfzb.server.configure.model.response.CategoryLinkAbilityResponse;

public interface CategoryRespositoryCust {
    public PageInfo queryCategoryByCond(AdminCategoryRequest adminCategoryRequest,Pageable pageable);
    
    public List<CategoryLinkAbilityResponse> queryCategoryAbilityByCategoryId(AdminCategoryRequest adminCategoryRequest);
    
    public boolean addCategory(AdminCategoryRequest adminCategoryRequest);
    
    public boolean updateCategoryAbility(AdminCategoryRequest adminCategoryRequest);
}
