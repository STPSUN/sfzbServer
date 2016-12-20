package com.idg.bfzb.server.project.dao;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.model.dto.EvaluateRecordEntity;
import com.idg.bfzb.server.project.model.vo.EvaluateListVo;

/**
 * 类名称：EvaluateRecordRepositoryCust
 * 类描述：评价
 * 创建人：ouzhb
 * 创建日期：2016/11/11
 */
public interface EvaluateRecordRepositoryCust {
	/**
	 * 查询用户平均星级
	 * @param targetUserId	用户ID
	 * @return	double	平均星级
	 */
	double findAverageStar(String targetUserId);
	/**
	 * 分页查询评价列表
	 * @param evaluateRecordEntity 查询条件
	 * @param start 起始索引
	 * @param size	记录数
	 * @return	PageInfo<EvaluateListVo>
	 */
	PageInfo<EvaluateListVo> findEvaluateListVoByCondition(Integer start, Integer size,EvaluateRecordEntity evaluateRecordEntity);
	/**
	 * 根据条件统计记录数
	 * @param entity	查询条件
	 * @return	int
	 */
	int countByCondition(EvaluateRecordEntity entity);
}
