package com.idg.bfzb.server.project.dao;

import java.util.List;

import com.idg.bfzb.server.project.model.dto.ProjectProgressEntity;
import com.idg.bfzb.server.project.model.vo.ProjectProgressVo;

/**
 * 类名称：ProjectProgressRepositoryCust
 * 类描述：项目进度Repository
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectProgressRepositoryCust {
	/**
	 * 根据项目ID以及进度类型查询进度
	 * @param projectId
	 * @return
	 */
	List<ProjectProgressEntity> findProjectProgressByProjectIdAndEventType(String projectId, String eventType);
	/**
	 * 根据项目ID以及进度类型查询进度
	 * @param projectId
	 * @return
	 */
	List<ProjectProgressVo> findProjectProgressVoByProjectIdAndEventType(String projectId, String eventType);
}
