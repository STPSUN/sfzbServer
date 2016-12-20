package com.idg.bfzb.server.common.service;

import com.idg.bfzb.server.common.model.APIResponse;

/**
 * 类名称：SysCodeService
 * 类描述：字典表业务类
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface SysCodeService {
	/**
	 * 根据类型获取字典表配置数据
	 * @param classify
	 * @return	APIResponse
	 */
	APIResponse getSysCodeListByClassify(String classify);
	/**
	 * 根据类型获取字典表配置数据
	 * @param classify
	 * @return	APIResponse
	 */
	APIResponse getSysCodeOneByClassify(String classify);
}
