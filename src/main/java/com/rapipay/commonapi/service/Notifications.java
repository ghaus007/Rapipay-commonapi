package com.rapipay.commonapi.service;

import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import org.springframework.http.HttpHeaders;

public interface Notifications {
	public Response getNotifications(RequestDto request, HttpHeaders headers);

	public Response updateNotificationView(RequestDto request, HttpHeaders headers);

	public Response getCountNotification(RequestDto request, HttpHeaders headers);
}
