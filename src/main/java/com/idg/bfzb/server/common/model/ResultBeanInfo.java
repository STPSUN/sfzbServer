package com.idg.bfzb.server.common.model;

import java.util.List;

/**
 *service 返回业务层的结果处理对象
 * @param <T>
 */
public class ResultBeanInfo<T> implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private boolean success = false;
	private String msg = "";  // 提示信息
	private T object = null;

	public ResultBeanInfo(){};

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

}
