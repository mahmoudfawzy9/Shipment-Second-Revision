package com.company.star.utils;

public class ApiResponse {

	Object data;
	Object meta;
	boolean success;
	String message;

	public ApiResponse() {
		this.setSuccess(true);
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setMeta(Object meta) {
		this.meta = meta;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
