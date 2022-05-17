package com.rapipay.commonapi.service.impl;

import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import com.portal.common.models.ResponseHandlingModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapipay.commonapi.dao.NotificationsDao;
import com.rapipay.commonapi.requestdto.CountNotificationsDto;
import com.rapipay.commonapi.requestdto.GetNotificationsDto;
import com.rapipay.commonapi.requestdto.UpdateNotificationsViewDto;
import com.rapipay.commonapi.middleware.RequestHandling;
import com.rapipay.commonapi.middleware.SessionHandling;
import com.rapipay.commonapi.service.Notifications;

@Service
public class NotificationsImpl implements Notifications {

	@Autowired
	SessionHandling sessionHandling;
	@Autowired
	RequestHandling requestHandling;
	@Autowired
	NotificationsDao notificationsDao;
	@Autowired
	ObjectMapper mapper;
	@Autowired
	ModelMapper modelMapper;
	Logger log = LoggerFactory.getLogger(NotificationsImpl.class);

	@Override
	public Response getNotifications(RequestDto request, HttpHeaders headers) {
		Response response = new Response();
		ResponseHandlingModel responseHandle = new ResponseHandlingModel();
		try {
			if (!sessionHandling.checkSession(headers, responseHandle).getResponseCode()
							.equals("200")) {
				return modelMapper.map(responseHandle, Response.class);

			}
			responseHandle = requestHandling.handleRequest(request, responseHandle);
			if (!responseHandle.getResponseCode().equals("1")) {
				log.info("GET_NOTIFICATION request after decrypting {}", responseHandle.getResponseData().toString());
				GetNotificationsDto getNotificationsDto = mapper.readValue(responseHandle.getResponseData().toString(),
						GetNotificationsDto.class);
				notificationsDao.getNotificationsList(getNotificationsDto, response);

			} else {
				response = modelMapper.map(responseHandle, Response.class);

			}

		} catch (Exception e) {
			response.setResponseCode("501");
			response.setResponseMessage("Exception Occurred.");
			response.setResponseData(e.getMessage());
			log.error("Exception Occurred  in GET_NOTIFICATIONS {}", response.toString());
		}
		return response;
	}

	@Override
	public Response updateNotificationView(RequestDto request, HttpHeaders headers) {
		Response response = new Response();
		ResponseHandlingModel responseHandle = new ResponseHandlingModel();
		try {
			if (sessionHandling.checkSession(headers, responseHandle).getResponseCode()
							.equals("200")) {

				responseHandle = requestHandling.handleRequest(request, responseHandle);
				log.info("Request for update notification view after decrypting {}", response.getResponseData());

				if (!responseHandle.getResponseCode().equals("1")) {
					UpdateNotificationsViewDto updateNotificationsDto = mapper
							.readValue(responseHandle.getResponseData().toString(), UpdateNotificationsViewDto.class);
					notificationsDao.updateNotificationViewStatus(updateNotificationsDto, response);
				} else {
					response = modelMapper.map(responseHandle, Response.class);

				}

			} else {
				response = modelMapper.map(responseHandle, Response.class);
			}
		} catch (Exception e) {
			response.setResponseCode("501");
			response.setResponseMessage("Exception Occurred.");
			response.setResponseData(e.getMessage());
			log.error("Exception Occurred in update notification view service {}", response.toString());
		}

		return response;
	}

	@Override
	public Response getCountNotification(RequestDto request, HttpHeaders headers) {
		ResponseHandlingModel responseHandle = new ResponseHandlingModel();
		Response response = new Response();
		try {
			if (sessionHandling.checkSession(headers, responseHandle).getResponseCode()
							.equals("200")) {
				responseHandle = requestHandling.handleRequest(request, responseHandle);
				log.info("decrypted request for get count notification {}", responseHandle.getResponseData());
				if (!responseHandle.getResponseCode().equals("1")) {
					CountNotificationsDto countNotificationsDto;
					countNotificationsDto = mapper.readValue(responseHandle.getResponseData().toString(),
							CountNotificationsDto.class);
					notificationsDao.getNotificationsCount(countNotificationsDto, response);

				} else {
					response = modelMapper.map(responseHandle, Response.class);

				}

			} else {
				response = modelMapper.map(responseHandle, Response.class);
			}
		} catch (Exception e) {
			response.setResponseCode("501");
			response.setResponseMessage("Exception Occurred.");
			response.setResponseData(e.getMessage());
			log.error("Exception occurred in get notification count {}", response);
		}

		return response;
	}

}
