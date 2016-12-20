package com.idg.bfzb.server.project.dao;

import java.util.List;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.model.dto.ProjectTenderEntity;
import com.idg.bfzb.server.project.model.response.ProjectStateTotalResponse;
import com.idg.bfzb.server.project.model.vo.ProjectListVo;
import com.idg.bfzb.server.project.model.vo.ProjectQryVo;
import com.idg.bfzb.server.project.model.vo.TenderUserDetailVo;

/**
 * 类名称：ProjectTenderRepositoryCust
 * 类描述：项目Repository
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface ProjectTenderRepositoryCust {
	/**
	 * 根据条件分页查询项目信息
	 * @param start	起始索引
	 * @param size	记录数
	 * @param projectQryVo	自定义查询条件对象
	 * @return	PageInfo<ProjectListVo>
	 */
	PageInfo<ProjectListVo> findProjectTenderByPageAndCondition(int start, int size, ProjectQryVo projectQryVo);
	/**
	 * 根据条件分页查询项目招标人信息
	 * @param start	起始索引
	 * @param size	记录数
	 * @param projectTenderEntity	查询条件对象
	 * @return	PageInfo<TenderUserDetailVo>
	 */
	PageInfo<TenderUserDetailVo> findTenderUserByCondition(int start, int size, ProjectTenderEntity projectTenderEntity);
	/**
	 * 根据条件统计记录数
	 * @param projectTenderEntity	查询条件对象
	 * @return	int	记录数
	 */
	int countByCondition(ProjectTenderEntity projectTenderEntity);
	/**
	 * 根据项目ID查询项目招标信息
	 * @param projectId
	 * @return
	 */
	List<ProjectTenderEntity> findProjectTenderByProjectId(String projectId);
	/**
	 * 统计接包方各项目状态数量
	 * @param tenderUserId
	 * @return
	 */
	List<ProjectStateTotalResponse> countStateByTenderUserId(String tenderUserId);
}
