package com.idg.bfzb.server.tools;


public enum APIReturnCode {
	SUCESSFUL(200,"SUCESSFUL","请求成功");

	// 成员变量
	private int httpStatus;
    private String code;
    private String message;

    // 构造方法
    APIReturnCode(int httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
        this.code = code;
		this.message = message;
    }

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

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



}
