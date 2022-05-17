package com.rapipay.commonapi.requestdto;

public class GetNotificationsDto {
	private String userId;
	private String serviceType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

}
