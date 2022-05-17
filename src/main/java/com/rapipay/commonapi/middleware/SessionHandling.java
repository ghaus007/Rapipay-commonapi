package com.rapipay.commonapi.middleware;

import java.time.Instant;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.common.constants.Constants;
import com.portal.common.models.ResponseHandlingModel;
import com.rapipay.commonapi.models.SessionDataModel;
import com.rapipay.commonapi.models.UserDetailsModel;
import com.rapipay.commonapi.redis.RedisOperations;
import com.rapipay.commonapi.utils.StatusCode;
import com.rapipay.commonapi.utils.Utility;

@Component
public class SessionHandling {

    @Autowired
    RedisOperations redisClient;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    Utility utility;


    public ResponseHandlingModel checkSession(HttpHeaders headers, ResponseHandlingModel response) {
        String redisKey = Constants.sessionKey + headers.getFirst("sessionRefNo");
        Object redisData = redisClient.getValue(redisKey);
        try {
            if ((!(headers.getFirst("requestFrom")==null)) &&  headers.getFirst("requestFrom").equals("mobile")) {
                response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                return response;
            }
            if (redisData == null) {
                response.setResponseCode(StatusCode.ERROR_404_INV_SESSION.getCode());
                response.setResponseMessage(StatusCode.ERROR_404_INV_SESSION.getMessage());
                return response;
            }
            SessionDataModel sessionData = objectMapper.readValue(redisData.toString(), SessionDataModel.class);
            long timestamp = Long.parseLong(sessionData.getTimeStamp());
            long currentTimestamp = Instant.now().getEpochSecond();
            if (Objects.isNull(headers.getFirst("requestFrom")) || !headers.getFirst("requestFrom").equals("mobile")) {


                if (currentTimestamp - timestamp > 12000) {
                    String loginKey = Constants.userInfoKey + sessionData.getUserId();
                    redisClient.delKey(redisKey);
                    Object loginRedisData = redisClient.getValue(loginKey);
                    UserDetailsModel userDetails = objectMapper.readValue(loginRedisData.toString(), UserDetailsModel.class);
                    userDetails.setSession("");
                    redisClient.setValue(loginKey, new Gson().toJson(userDetails));
                    response.setResponseCode(StatusCode.ERROR_403_EXP_SESSION.getCode());
                    response.setResponseMessage(StatusCode.ERROR_403_EXP_SESSION.getMessage());


                } else {
                    String timeStamp = utility.getTimeStamp();
                    sessionData.setTimestamp(timeStamp);
                    sessionData.setTimeStamp(timeStamp);


                    redisClient.setValue(redisKey, new Gson().toJson(sessionData));
                    response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                    
                }
            }
        } catch (Exception e) {
            response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
            response.setResponseData(e.toString());
        }
        //response.setResponseCode("200");

        return response;

    }

    public ResponseHandlingModel getUserIdFromSession(String sessionRefNo, ResponseHandlingModel response) {
        try {
            String redisKey = Constants.sessionKey + sessionRefNo;
            Object redisData = redisClient.getValue(redisKey);
            if (Objects.nonNull(redisData)) {
                SessionDataModel sessionData = objectMapper.readValue(redisData.toString(), SessionDataModel.class);
                response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                response.setResponseData(sessionData.getUserId());
            } else {
                response.setResponseCode(StatusCode.ERROR_401.getCode());
                response.setResponseMessage(StatusCode.ERROR_401_NO_DATA.getMessage());
            }

        } catch (Exception e) {
            response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
            response.setResponseMessage(e.getMessage());
        }
        return response;
    }

    public ResponseHandlingModel checkSessionFromLogout(HttpHeaders headers, ResponseHandlingModel response) {
        String redisKey = Constants.sessionKey + headers.getFirst("sessionRefNo");
        Object redisData = redisClient.getValue(redisKey);
        try {
            if (!Objects.isNull(headers.getFirst("requestFrom")) && headers.getFirst("requestFrom").equals("mobile")) {
                response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                return response;
            }
            if (redisData == null) {
                response.setResponseCode(StatusCode.ERROR_404_INV_SESSION.getCode());
                response.setResponseMessage(StatusCode.ERROR_404_INV_SESSION.getMessage());
                return response;
            }

            if (Objects.isNull(headers.getFirst("requestFrom")) || !headers.getFirst("requestFrom").equals("mobile")) {


                response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());

            }
        } catch (Exception e) {
            response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
            response.setResponseData(e.toString());
        }
        response.setResponseCode("200");

        return response;

    }


}
