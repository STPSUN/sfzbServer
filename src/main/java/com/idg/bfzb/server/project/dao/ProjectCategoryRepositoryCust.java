package com.idg.bfzb.server.project.dao;

import java.util.List;

import com.idg.bfzb.server.project.model.vo.ProjectCategoryAbilityVo;

/**
 * 类名称：ProjectCategoryRepositoryCust
 * 类描述：项目分类Repository
 * 创建人：ouzhb
 * 创建日期：2016/11/15
 */
public interface ProjectCategoryRepositoryCust {
	/**
	 * 根据项目ID获取项目分类技能信息
	 * @param projectId
	 * @return
	 */
	List<ProjectCategoryAbilityVo> findProjectCategoryAbilityByProjectId(String projectId);
}
