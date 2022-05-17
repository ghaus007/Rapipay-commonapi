package com.rapipay.commonapi.service.impl;

import java.util.*;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.portal.common.constants.Constants;

import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import com.portal.common.models.ResponseHandlingModel;

import com.rapipay.commonapi.requestdto.ChangePasswordDto;
import com.rapipay.commonapi.utils.StatusCode;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rapipay.commonapi.requestdto.LoginDto;

import com.rapipay.commonapi.middleware.RequestHandling;
import com.rapipay.commonapi.middleware.SessionHandling;
import com.rapipay.commonapi.models.SessionDataModel;
import com.rapipay.commonapi.models.UserDetailsModel;
import com.rapipay.commonapi.redis.RedisOperations;
import com.rapipay.commonapi.service.AuthSession;
import com.rapipay.commonapi.utils.Utility;

@Service
public class AuthSessionImpl implements AuthSession {

    @Autowired
    RedisOperations redisClient;
    @Autowired
    SessionHandling sessionHandling;
    @Autowired
    RequestHandling requestHandling;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    Utility utility;
    @Autowired
    ModelMapper modelMapper;
    Logger log = LoggerFactory.getLogger(AuthSessionImpl.class);

    @Override
    public Response logoutMerchant(RequestDto requestDto, HttpHeaders headers) {
        ResponseHandlingModel responseHandle = new ResponseHandlingModel();
        Response response = new Response();
        try {

//            if (!sessionHandling.checkSessionFromLogout(headers, responseHandle).getResponseCode()
//                    .equals("200")) {

            responseHandle = requestHandling.handleRequest(requestDto, responseHandle);
            if (!responseHandle.getResponseCode().equals("1")) {

//                LogoutDto logoutDto = objectMapper.readValue(responseHandle.getResponseData().toString(),
//                        LogoutDto.class);
                //logoutDto.setSessionRefNo(headers.getFirst("sessionRefNo"));
                String redisKey = Constants.sessionKey + headers.getFirst("sessionRefNo");
                redisClient.delKey(redisKey);
                response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());


            } else {
                response = modelMapper.map(responseHandle, Response.class);

            }

//            }


        } catch (Exception e) {
            response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
            response.setResponseData(e.getMessage());

        }
        return response;

    }

    @Override
    public Response loginMerchant(RequestDto request) {
        ResponseHandlingModel responseHandleModel = new ResponseHandlingModel();
        try {

            responseHandleModel = requestHandling.handleRequest(request, responseHandleModel);
            if (!responseHandleModel.getResponseCode().equals("1")) {
                LoginDto loginDto = objectMapper.readValue(responseHandleModel.getResponseData().toString(),
                        LoginDto.class);
                responseHandleModel.setResponseData(null);
                String redisKey = Constants.userInfoKey + loginDto.getLoginId();
                System.out.println("respModel"+responseHandleModel.toString());
                Object redisData = redisClient.getValue(redisKey);
                if (redisData != null) {
                    responseHandleModel = checkValidLogin(redisData, loginDto, responseHandleModel);
                } else {

                    responseHandleModel.setResponseCode(StatusCode.ERROR_401.getCode());
                    responseHandleModel.setResponseMessage(StatusCode.ERROR_401_INV_USER.getMessage());

                }
            }


        } catch (Exception e) {
            responseHandleModel.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            responseHandleModel.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
            responseHandleModel.setResponseData(e.getMessage());
        }
        return modelMapper.map(responseHandleModel, Response.class);
    }

    public ResponseHandlingModel checkValidLogin(Object redisData, LoginDto loginRequest,
                                                 ResponseHandlingModel response) {

        try {
            UserDetailsModel loginData = objectMapper.readValue(redisData.toString(), UserDetailsModel.class);
            if (loginData.getAuthPin().equals(loginRequest.getPassword())&&(loginData.getUserStatus().equals("1"))) {

                JSONObject responseJson = new JSONObject();
                responseJson.put("fullName", loginData.getFullName());
                responseJson.put("loginUser", loginData.getInitial());
                responseJson.put("userAccess", loginData.getUserAccess());
                responseJson.put("mid", loginData.getMid());
                createSessionData(responseJson, loginRequest);


                Boolean validLogin = deleteIfExistingSession(loginData, responseJson);
                if (!validLogin) {
                	
                    response.setResponseCode("401");
                    response.setResponseMessage("Error Occurred.");
                    
                    return response;
                }
                loginData.setSession(responseJson.get("sessionRefNo").toString());
                log.info("login Data {}", loginData);
                String redisKey = Constants.userInfoKey + loginRequest.getLoginId();
                redisClient.setValue(redisKey, new Gson().toJson(loginData));
                response.setResponseCode("200");
                response.setResponseData(responseJson);
                response.setResponseMessage("Request Processed Successfully.");

            } else {
                response.setResponseCode("401");
                response.setResponseMessage("Authentication failure.");

            }
        } catch (Exception e) {
            response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
            response.setResponseData(e.getMessage());

        }

        return response;

    }

    public Boolean deleteIfExistingSession(UserDetailsModel loginData, JSONObject responseJson) {

        if (!Objects.isNull(loginData.getSession())) {
            String existingSession = loginData.getSession();

            String redisKey = String.join("", Constants.sessionKey, existingSession);
            log.info("redis Key to delete. {}", redisKey);
            redisClient.delKey(redisKey);
        }
        return true;

    }

    public void createSessionData(JSONObject responseJson, LoginDto loginRequest) {
        String sessionKey = utility.aes256Encryption(utility.generateRandomNumber().substring(0, 5), Constants.initVector,
                Constants.secretKey);
        String sessionRef = utility.aes256Encryption(utility.generateRandomNumber().substring(0, 5), Constants.initVector,
                Constants.secretKey);

        responseJson.put("sessionKey", sessionKey);
        responseJson.put("sessionRefNo", sessionRef);

        SessionDataModel sessionModel = new SessionDataModel();
        sessionModel.setSessionKey(sessionKey);
        sessionModel.setSessionRefNo(sessionRef);
        sessionModel.setUserId(loginRequest.getLoginId());
        String timeStamp = utility.getTimeStamp();
        sessionModel.setTimeStamp(timeStamp);
        sessionModel.setTimestamp(timeStamp);
        String redisKey = Constants.sessionKey + sessionRef;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long howMany = (c.getTimeInMillis() - System.currentTimeMillis());

        redisClient.setValueExpTime(redisKey, new Gson().toJson(sessionModel).toString(), (long) (howMany * 0.001));

    }

    @Override
    public Response changePassword(RequestDto request) {
        ResponseHandlingModel responseHandleModel = new ResponseHandlingModel();
        try {

            responseHandleModel = requestHandling.handleRequest(request, responseHandleModel);
            if (!responseHandleModel.getResponseCode().equals("1")) {
                ChangePasswordDto changePasswordDto = objectMapper.readValue(responseHandleModel.getResponseData().toString(),
                        ChangePasswordDto.class);
                UserDetailsModel userData = objectMapper.readValue(redisClient.getValue(Constants.userInfoKey + changePasswordDto.getLoginId()).toString(), UserDetailsModel.class);
                if (changePasswordDto.getOldPassword().equals(changePasswordDto.getNewPassword())) {
                    return makeValidationResponse(StatusCode.PASS_INV_1.getMessage());
                }
                if (!userData.getAuthPin().equals(changePasswordDto.getOldPassword())) {
                    return makeValidationResponse(StatusCode.PASS_INV_3.getMessage());
                } else {

                    return passwordValidation(changePasswordDto, userData);
                }

            } else {
                return modelMapper.map(responseHandleModel, Response.class);
            }


        } catch (Exception e) {
            responseHandleModel.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            responseHandleModel.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
            responseHandleModel.setResponseData(e.getMessage());
        }
        return modelMapper.map(responseHandleModel, Response.class);
    }

    public Response makeValidationResponse(String s) {
        Response response = new Response();
        response.setResponseCode(StatusCode.ERROR_301_VALIDATION.getCode());
        response.setResponseMessage(s);
        return response;


    }

    public Response passwordValidation(ChangePasswordDto request, UserDetailsModel userData) {
        Response response = new Response();

        response.setResponseCode(StatusCode.ERROR_301_VALIDATION.getCode());
        response.setResponseMessage(StatusCode.ERROR_301_VALIDATION.getMessage());
//        String[] arr = {"rapipay", "pos", "merchant", request.getLoginId()};
        String password = request.getNewPassword();
//        String regex = "^(?=.*[a-z])(?=."
//                + "*[A-Z])(?=.*\\d)"
//                + "(?=.*[-+_!@#$%^&*., ?]).+$";
//
//        // Compile the ReGex
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(password);

//        if (password.length() >= 6) {
//
////            if (Arrays.stream(arr).anyMatch(password::contains)) {
//            if (Arrays.stream(arr).anyMatch(s -> password.toLowerCase().contains(s))) {
//
//                return response;
//            } else if (!m.matches()) {
//
//                return response;
//
//            } else {
        ArrayList<String> oldPasswords = new ArrayList<String>();
        if (!Objects.isNull(userData.getOldPasswords())) {
            oldPasswords = userData.getOldPasswords();
        }
        boolean isNotValid = oldPasswords.stream().anyMatch(s -> Objects.equals(s, password));
        if (isNotValid) {
            response.setResponseCode(StatusCode.PASS_INV_2.getCode());
            response.setResponseMessage(StatusCode.PASS_INV_2.getMessage());
            return response;
        } else {

            if (oldPasswords.size() == 5) {
                oldPasswords.remove(0);
            }
            oldPasswords.add(password);
            userData.setOldPasswords(oldPasswords);
            userData.setAuthPin(request.getNewPassword());
            redisClient.setValue(Constants.userInfoKey + request.getLoginId(), new Gson().toJson(userData));
            response.setResponseCode(StatusCode.SUCCESS_200.getCode());
            response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
        }

        // }
        //  }
        return response;

    }

}
