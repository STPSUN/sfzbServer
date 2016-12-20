package com.idg.bfzb.server.project.dao;

import com.idg.bfzb.server.project.model.dto.SysModifiedRecordEntity;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/12/4
 */
public interface SysModifiedRecordRepositoryCust {
    /**
     * 查询不同的表中实体记录
     * @param tableName 表名
     * @param keyField 字段名
     * @param keyValue 字段值
     * @param entityClass 实体类型
     * @return 数据库中的实体记录
     */
    <T> T findEntityByKey(String tableName,String keyField,Object keyValue,Class<T> entityClass);
    /**
     * 插入一条修改记录
     * @param mdEntry
     */
    void insertModifiedRecord(SysModifiedRecordEntity mdEntry);
}
