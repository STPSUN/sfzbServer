package com.idg.bfzb.server.common.dao;

import java.util.List;

import com.idg.bfzb.server.common.model.vo.SysCodeVo;

/**
 * 类名称：SysCodeRepositoryCust
 * 类描述：字典表持久化
 * 创建人：ouzhb
 * 创建日期：2016/11/5
 */
public interface SysCodeRepositoryCust{
	
    List<SysCodeVo> findByClassify(String classify);

}
