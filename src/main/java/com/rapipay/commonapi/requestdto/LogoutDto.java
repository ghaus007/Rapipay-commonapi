package com.rapipay.commonapi.requestdto;

public class LogoutDto {
	private String serviceType;
	private String userId;
	private String sessionRefNo;

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSessionRefNo() {
		return sessionRefNo;
	}

	public void setSessionRefNo(String sessionRefNo) {
		this.sessionRefNo = sessionRefNo;
	}

}
