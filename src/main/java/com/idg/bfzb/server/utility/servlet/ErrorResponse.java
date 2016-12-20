package com.idg.bfzb.server.utility.servlet;

public class ErrorResponse {
	
	private String code; //错误编码
	
	private String message; //错误消息

	private String hostId; //服务器ID
	
	private String serverTime; //服务器时间

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	
}
