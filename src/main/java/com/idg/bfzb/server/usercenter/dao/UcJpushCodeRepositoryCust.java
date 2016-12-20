package com.idg.bfzb.server.usercenter.dao;

import java.util.List;

import com.idg.bfzb.server.usercenter.model.dto.UcJpushCodeEntity;


/**
 * 类名称：UcJpushCodeRepositoryCust
 * 类描述：用户极光ID关联表
 * 创建人：ouzhb
 * 创建日期：2016/12/8
 */
public interface UcJpushCodeRepositoryCust {
	/**
	 * 根据条件查询用户极光关联信息
	 * @param entity	查询条件
	 * @return
	 */
	List<UcJpushCodeEntity> findByCondition(UcJpushCodeEntity entity);
}
