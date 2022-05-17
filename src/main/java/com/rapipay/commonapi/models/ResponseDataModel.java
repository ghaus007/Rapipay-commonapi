package com.rapipay.commonapi.models;

public class ResponseDataModel {
	private Object responseData;
	private String responseCode;
	private String responseMessage;
	

	
	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	
}
