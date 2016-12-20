package com.idg.bfzb.server.utility.servlet;

public class ResponseState implements java.io.Serializable {
	protected static final long serialVersionUID = 1L;
	protected boolean success;
	protected String msg;
	protected Object obj = null;
	private String rest_code;  // 1为成功  -1为失败
	public static final ResponseState SUCCESS = new ResponseState(true);
	public static final ResponseState FAILURE = new ResponseState(false);

	public ResponseState() {
	}

	public ResponseState(boolean susscess) {
		this.success = susscess;
	}

	public ResponseState(boolean susscess, String msg) {
		this.success = susscess;
		this.msg = msg;
	}

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

	@Override
	public String toString() {
		return "[{success=" + success + ", msg=" + msg + "}]";
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getRest_code() {
		return rest_code;
	}

	public void setRest_code(String rest_code) {
		this.rest_code = rest_code;
	}
}
