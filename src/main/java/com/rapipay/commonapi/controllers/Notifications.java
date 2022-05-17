package com.rapipay.commonapi.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portal.common.applicationResponse.ApplicationResponseEntity;
import com.rapipay.commonapi.constants.ServiceType;
import com.rapipay.commonapi.entity.NotificationEntity;
import com.rapipay.commonapi.repository.NotificationsRepository;
import com.rapipay.commonapi.utils.StatusCode;

@RequestMapping("/notifications")
@RestController
@CrossOrigin(origins = {"https://merchant.rapipe.tech","https://merchant.rapipay.tech","http://localhost:3000"})

public class Notifications {

    @Autowired
    NotificationsRepository notifications;

    Logger log = LoggerFactory.getLogger(Notifications.class);

    @GetMapping("/getNotifications")
    public ApplicationResponseEntity<Object> getNotifications(@RequestParam(name="userId") String userId
                                                             ) {

        try {
            log.info("In {} Api", ServiceType.getNotifications);
            List<NotificationEntity> list = notifications.getMerchantNotification(userId);
          //  log.info("Response from {} API {}", ServiceType.getNotifications, response.toString());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.SUCCESS_200.getCode())
                    .responseMessage(StatusCode.SUCCESS_200.getMessage())
                    .responseData(list).build();

        } catch (Exception e) {
            log.error("Exception Occurred in {} {}", ServiceType.getNotifications, e.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage()).build();
        }

    }

	/*
	 * @PostMapping("/updateNotifications") public ApplicationResponseEntity<Object>
	 * updateNotifications(@RequestBody RequestDto request,
	 * 
	 * @RequestHeader HttpHeaders headers) {
	 * 
	 * try { log.info("In {} API", ServiceType.updateNotifications); Response
	 * response = notifications.updateNotificationView(request, headers);
	 * log.info("Response from {} Api {}", ServiceType.updateNotifications,
	 * response.toString()); return ApplicationResponseEntity.builder()
	 * .responseCode(response.getResponseCode())
	 * .responseMessage(response.getResponseMessage())
	 * .responseData(response.getResponseData()) .build(); } catch (Exception e) {
	 * log.error("Exception Occurred in {} Api", ServiceType.updateNotifications);
	 * return ApplicationResponseEntity.builder()
	 * .responseCode(StatusCode.EXCEPTION_501.getCode())
	 * .responseMessage(StatusCode.EXCEPTION_501.getMessage())
	 * .responseData(e.getMessage()).build(); } }
	 * 
	 * @PostMapping("/getCountNotifications") public
	 * ApplicationResponseEntity<Object> countNotifications(@RequestBody RequestDto
	 * request,
	 * 
	 * @RequestHeader HttpHeaders headers) {
	 * 
	 * try { log.info("In {} API", ServiceType.countNotifications); Response
	 * response = notifications.getCountNotification(request, headers);
	 * log.info("Response from {} Api {}", ServiceType.countNotifications,
	 * response.toString()); return ApplicationResponseEntity.builder()
	 * .responseCode(response.getResponseCode())
	 * .responseMessage(response.getResponseMessage())
	 * .responseData(response.getResponseData()) .build(); } catch (Exception e) {
	 * log.error("Exception Occurred in {} {}", ServiceType.countNotifications,
	 * e.getMessage()); return ApplicationResponseEntity.builder()
	 * .responseCode(StatusCode.EXCEPTION_501.getCode())
	 * .responseMessage(StatusCode.EXCEPTION_501.getMessage())
	 * .responseData(e.getMessage()).build();
	 * 
	 * } }
	 */}
