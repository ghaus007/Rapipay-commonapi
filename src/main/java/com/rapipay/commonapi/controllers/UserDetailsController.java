package com.rapipay.commonapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.portal.common.applicationResponse.ApplicationResponseEntity;

import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;

import com.rapipay.commonapi.constants.ServiceType;
import com.rapipay.commonapi.enums.ServiceTypes;
import com.rapipay.commonapi.requestdto.CreateSubUserDto;
import com.rapipay.commonapi.requestdto.UpdateUserDataDto;
import com.rapipay.commonapi.service.impl.StorageServiceImpl;
import com.rapipay.commonapi.service.impl.UserDetailsImpl;
import com.rapipay.commonapi.utils.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user-details")
@CrossOrigin(origins = {"https://merchant.rapipe.tech", "https://merchant.rapipay.tech", "http://localhost:3000"})
public class UserDetailsController {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsController.class);

    @Autowired
    StorageServiceImpl storageService;
    @Autowired
    UserDetailsImpl userDetailsService;


    @PostMapping("/uploadProfileImage")
    public ApplicationResponseEntity<Object> uploadProfileImage(@RequestParam MultipartFile image,
                                                                @RequestHeader HttpHeaders headers) {

        try {
            log.info("inside {} api", ServiceType.uploadProfileImage);
            Response response = storageService.uploadFile(image, headers);
            log.info("response from {} {}", ServiceType.uploadProfileImage, response.toString());
            return ApplicationResponseEntity.builder().
                    responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage()).
                    responseData(response.getResponseData()).build();

        } catch (Exception ex) {
            log.error("Exception in {} {}", ServiceType.uploadProfileImage, ex.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(ex.getMessage()).build();
        }
    }

    @GetMapping("/getProfileImage")
    public ApplicationResponseEntity<Object> getProfileImage(@RequestHeader HttpHeaders headers) {

        try {
            log.info("inside {} api", ServiceType.getProfileImage);
            Response response = storageService.getFile(headers);
            log.info("response from {} {}", ServiceType.getProfileImage, response.toString());
            return ApplicationResponseEntity.builder().
                    responseCode(response.getResponseCode()).
                    responseMessage(response.getResponseMessage()).
                    responseData(response.getResponseData()).build();

        } catch (Exception ex) {
            log.error("Exception in {} {}", ServiceType.getProfileImage, ex.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(ex.getMessage()).build();
        }
    }

    @PostMapping("/updateProfileDetails")
    public ApplicationResponseEntity<Object> updateProfileDetails(@RequestBody RequestDto request, @RequestHeader HttpHeaders headers) {

        try {
            log.info("inside {} api", ServiceType.updateProfileDetails);
            Response response = userDetailsService.updateProfileDetails(request, headers);
            log.info("response from {} api is {}", ServiceType.updateProfileDetails, response.toString());
            return ApplicationResponseEntity.builder().
                    responseCode(response.getResponseCode()).
                    responseMessage(response.getResponseMessage()).
                    responseData(response.getResponseData()).build();
        } catch (Exception ex) {

            log.error("Exception in {} {}", ServiceType.updateProfileDetails, ex.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(ex.getMessage()).build();

        }

    }

    @PostMapping("/getProfileDetails")
    public ApplicationResponseEntity<Object> getProfileDetails(@RequestBody RequestDto request, @RequestHeader HttpHeaders headers) {

        try {
            log.info("inside {} api", ServiceType.getMerchantDetails);
            Response response = userDetailsService.getProfileDetails(request, headers);
            log.info("response from {} api is {}", ServiceType.getMerchantDetails, response.toString());
            return ApplicationResponseEntity.builder().
                    responseCode(response.getResponseCode()).
                    responseMessage(response.getResponseMessage()).
                    responseData(response.getResponseData()).build();
        } catch (Exception ex) {

            log.error("Exception in {} {}", ServiceType.getMerchantDetails, ex.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(ex.getMessage()).build();

        }

    }

    @GetMapping("/getUserDetails")
    public ApplicationResponseEntity<Object> getUserDetails(@RequestParam(required = false) String mid, @RequestParam(required = false) String
            tid, @RequestParam(required = false) String mobileNo, @RequestHeader HttpHeaders headers) {

        log.info("inside {} api", ServiceTypes.GET_USERS_LIST);
        Response response = userDetailsService.getUserList(mid, tid, mobileNo, headers);
        log.info("response from {} api is {}", ServiceTypes.GET_USERS_LIST, response.toString());
        return ApplicationResponseEntity.builder().
                responseCode(response.getResponseCode()).
                responseMessage(response.getResponseMessage()).
                responseData(response.getResponseData()).build();

    }

    @GetMapping("/getUserInformation")
    public ApplicationResponseEntity<Object> getUserInformation(@RequestParam String userId,
                                                                @RequestHeader HttpHeaders headers) throws JsonProcessingException {

        log.info("inside {} api", ServiceTypes.GET_USER_INFORMATION);
        Response response = userDetailsService.getUserInformation(userId, headers);
        log.info("response from {} api is {}", ServiceTypes.GET_USER_INFORMATION, response.toString());
        return ApplicationResponseEntity.builder().
                responseCode(response.getResponseCode()).
                responseMessage(response.getResponseMessage()).
                responseData(response.getResponseData()).build();

    }

    @PostMapping("/updateUserData")
    public ApplicationResponseEntity<Object> updateUserData(@RequestBody UpdateUserDataDto updateUserDataDto,
                                                            @RequestHeader HttpHeaders headers) throws JsonProcessingException {

        log.info("inside {} api", ServiceTypes.UPDATE_USER_INFORMATION);
        Response response = userDetailsService.updateUserInformation(updateUserDataDto, headers);
        log.info("response from {} api is {}", ServiceTypes.UPDATE_USER_INFORMATION, response.toString());
        return ApplicationResponseEntity.builder().
                responseCode(response.getResponseCode()).
                responseMessage(response.getResponseMessage()).
                responseData(response.getResponseData()).build();

    }

    @PostMapping("/createSubUser")
    public ApplicationResponseEntity<Object> createSubUser(@RequestBody CreateSubUserDto createSubUserDto,
                                                           @RequestHeader HttpHeaders headers) throws JsonProcessingException {

        log.info("inside {} api", ServiceTypes.CREATE_SUB_USER);
        Response response = userDetailsService.createSubUserViaProc(createSubUserDto, headers);
        log.info("response from {} api is {}", ServiceTypes.CREATE_SUB_USER, response.toString());
        return ApplicationResponseEntity.builder().
                responseCode(response.getResponseCode()).
                responseMessage(response.getResponseMessage()).
                responseData(response.getResponseData()).build();

    }


}
