package com.rapipay.commonapi.requestdto;

public class UpdateNotificationsViewDto {
	private String userId;
	private String notificationId;
	private String viewStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}
}
