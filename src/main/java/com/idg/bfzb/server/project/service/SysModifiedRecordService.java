package com.idg.bfzb.server.project.service;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/12/4
 */
public interface SysModifiedRecordService {
    /**
     * 保存修改记录，在执行数据库更新操作后执行
     * @param targetId  被修改的对象id
     * @param entity    修改对象以前的旧对象
     * @param clazz     修改对象的class
     * @param modifierId  修改者的id
     * @param <T> 修改记录类型
     */
    <T> void insertModifiedRecord(String targetId, Object entity, Class<T> clazz, String modifierId);
}
