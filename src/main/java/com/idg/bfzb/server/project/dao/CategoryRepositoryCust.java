package com.idg.bfzb.server.project.dao;

import java.util.List;

import com.idg.bfzb.server.project.model.dto.CategoryEntity;

/**
 * 类名称：CategoryRepositoryCust
 * 类描述：项目分类
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface CategoryRepositoryCust {
	
	List<CategoryEntity> findAllCategory();
}
