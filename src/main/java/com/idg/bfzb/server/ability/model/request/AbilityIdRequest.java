package com.idg.bfzb.server.ability.model.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 类名称：AbilityIdRequest
 * 类描述：我的技能新增请求实体
 * 创建人：
 * 创建日期：2016/10/31
 */
public class AbilityIdRequest implements Serializable {
    //Map<String,List<Map<String,String>>>
    private List<Map<String,String>> ability;
    
    public List<Map<String,String>> getAbility() {
        return ability;
    }

    public void setAbility(List<Map<String,String>> ability) {
        this.ability = ability;
    }
}
