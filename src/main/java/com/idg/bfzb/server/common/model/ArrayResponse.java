package com.idg.bfzb.server.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：ArrayResponse
 * 类描述：数组返回实体
 * 创建人：jiangdong
 * 创建日期：2016/11/5
 */
public class ArrayResponse<T> implements Serializable {
    private Long total;

    private List<T> items;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void addItem(T item){
        if (items == null){
            items = new ArrayList<>();
        }
        this.items.add(item);
    }
}
