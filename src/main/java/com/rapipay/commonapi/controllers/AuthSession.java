package com.rapipay.commonapi.controllers;

import com.portal.common.applicationResponse.ApplicationResponseEntity;

import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;

import com.rapipay.commonapi.constants.ServiceType;
import com.rapipay.commonapi.utils.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import com.rapipay.commonapi.service.impl.AuthSessionImpl;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"https://merchant.rapipe.tech","https://merchant.rapipay.tech","http://localhost:3000"})

public class AuthSession {

    @Autowired
    AuthSessionImpl authSessionService;

    Logger log = LoggerFactory.getLogger(AuthSession.class);

    @PostMapping("/login")
    public ApplicationResponseEntity<Object> merchantLogin(@RequestBody RequestDto request, @RequestHeader HttpHeaders headers) {
        ApplicationResponseEntity<Object> applicationResponseEntity = new ApplicationResponseEntity<>();

        try {
            log.info("In {} API", ServiceType.merchantLogin);
            Response response = authSessionService.loginMerchant(request);
            log.info("Response from {} {} API", ServiceType.merchantLogin, response.toString());
            return ApplicationResponseEntity.builder().responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData()).build();

        } catch (Exception e) {
            log.error("Exception Occurred in {} {}", ServiceType.merchantLogin, e.getMessage());
            applicationResponseEntity.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage())
                    .build();

        }

    }

    @PostMapping("/logout")
    public ApplicationResponseEntity<Object> merchantLogout(@RequestBody RequestDto request,
                                                            @RequestHeader HttpHeaders headers) {
        try {
            log.info("In {} API", ServiceType.merchantLogout);
            Response response = authSessionService.logoutMerchant(request, headers);
            log.info("Response from {} {}", ServiceType.merchantLogout, response.toString());
            return ApplicationResponseEntity.builder().responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData()).build();
        } catch (Exception e) {
            log.error("Exception Occurred in {} {}", ServiceType.merchantLogout, e.toString());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage())
                    .build();
        }

    }

    @PostMapping("/changePassword")
    public ApplicationResponseEntity<Object> changePassword(@RequestBody RequestDto request, @RequestHeader HttpHeaders headers) {
        ApplicationResponseEntity<Object> applicationResponseEntity = new ApplicationResponseEntity<>();

        try {
            log.info("In {} API", ServiceType.changePassword);
            Response response = authSessionService.changePassword(request);
            log.info("Response from {} {} API", ServiceType.changePassword, response.toString());
            return ApplicationResponseEntity.builder().responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData()).build();

        } catch (Exception e) {
            log.error("Exception Occurred in {} {}", ServiceType.changePassword, e.getMessage());
            applicationResponseEntity.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage())
                    .build();

        }

    }
}
