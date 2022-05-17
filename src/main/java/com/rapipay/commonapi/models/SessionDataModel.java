package com.rapipay.commonapi.models;

public class SessionDataModel {
	private String sessionKey;
	private String sessionRefNo;
	private String timeStamp;
	private String timestamp;
	private String userId;

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getSessionRefNo() {
		return sessionRefNo;
	}

	public void setSessionRefNo(String sessionRefNo) {
		this.sessionRefNo = sessionRefNo;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
