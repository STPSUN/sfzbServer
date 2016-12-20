package com.idg.bfzb.server.project.service.impl;

import com.idg.bfzb.server.project.dao.SysModifiedRecordRepository;
import com.idg.bfzb.server.project.model.dto.SysModifiedRecordEntity;
import com.idg.bfzb.server.project.service.SysModifiedRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/12/4
 */
@Service
public class SysModifiedRecordImpl  implements SysModifiedRecordService {
    @Autowired
    private SysModifiedRecordRepository modifiedRecordRepository;

    @Override
    public <T> void insertModifiedRecord(String targetId, Object entityOld, Class<T> entityClass, String modifierId) {

        Method[] methods = entityClass.getMethods();
        String tableName = ((Table) entityClass.getAnnotation(Table.class)).name();
        String keyName = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Id.class)) {
                Column idColumn = method.getAnnotation(Column.class);
                keyName = idColumn.name();
            }
        }
        if (keyName == null) {
            return;
        }

        List<SysModifiedRecordEntity> modifiedRecordEntityList = new ArrayList<>();
        /* 找到目前数据库的值 */
        T databaseEntity = this.modifiedRecordRepository.findEntityByKey(tableName, keyName, targetId, entityClass);
        /* 新的entity和数据库旧值做比较,记录下有变化的字段 */
        Field[] fields = entityClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                String fieldName = fields[i].getName();
                
                //加上可能两者不同时为null，必然修改过的情况
                if((fields[i].get(entityOld) == null && fields[i].get(databaseEntity) == null)){
                	continue;
                }
                if ((fields[i].get(entityOld) == null && fields[i].get(databaseEntity) != null) || (fields[i].get(entityOld) != null && fields[i].get(databaseEntity) == null) 
                		|| !fields[i].get(databaseEntity).equals(fields[i].get(entityOld))) {
                    SysModifiedRecordEntity modifiedRecordEntity = new SysModifiedRecordEntity();
                    modifiedRecordEntity.setTargetObject(tableName);
                    modifiedRecordEntity.setTargetObjectId(targetId);
                    modifiedRecordEntity.setProperty(fieldName);
                    modifiedRecordEntity.setModifierId(modifierId);
                    //避免空指针异常
                    String content = fields[i].get(databaseEntity) == null ? null : fields[i].get(databaseEntity).toString();
                    // todo: 要区分类型
                    modifiedRecordEntity.setContent(content);
                    modifiedRecordEntityList.add(modifiedRecordEntity);
                }

            } catch (IllegalAccessException accessExcep) {
                // todo:不同的异常要做处理
                continue;
            }

        }
        
        for (SysModifiedRecordEntity md : modifiedRecordEntityList) {
			
        	/* 有变化的字段存储到实体中,最后批量更新 */
        	this.modifiedRecordRepository.insertModifiedRecord(md);
		}
    }
}
