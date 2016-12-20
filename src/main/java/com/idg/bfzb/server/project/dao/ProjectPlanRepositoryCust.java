package com.idg.bfzb.server.project.dao;

import java.util.List;

import com.idg.bfzb.server.project.model.vo.ProjectPlanVo;

/**
 * 类名称：ProjectPlanRepositoryCust
 * 类描述：项目计划
 * 创建人：ouzhb
 * 创建日期：2016/11/17
 */
public interface ProjectPlanRepositoryCust {
	/**
	 * 获取项目计划列表
	 * @param projectId
	 * @return
	 */
	List<ProjectPlanVo> getProjectPlanByProjectId(String projectId);
}
