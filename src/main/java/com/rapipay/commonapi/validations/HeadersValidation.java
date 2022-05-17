package com.rapipay.commonapi.validations;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import com.portal.common.models.ResponseHandlingModel;
import com.rapipay.commonapi.utils.StatusCode;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class HeadersValidation {

    public ResponseHandlingModel validateHeaders(HttpHeaders headers, ResponseHandlingModel responseHandle) {

        responseHandle.setResponseCode(StatusCode.SUCCESS_200.getCode());
        if (Objects.isNull(headers.getFirst("requestFrom")) || !Objects.equals(headers.getFirst("requestFrom"), "mobile")) {
            if (Objects.isNull(headers.getFirst("sessionRefNo"))) {
                responseHandle.setResponseCode("301");
                responseHandle.setResponseMessage("Please provide valid headers.");
            }
        } else {
            if (Objects.isNull(headers.getFirst("userId"))) {
                responseHandle.setResponseCode("301");
                responseHandle.setResponseMessage("Please provide valid headers.");
            }
        }
        return responseHandle;

    }

    public ResponseHandlingModel validateHeaders(HttpServletRequest request) {

        ResponseHandlingModel response = new ResponseHandlingModel();
        response.setResponseCode(StatusCode.SUCCESS_200.getCode());
        if (Objects.isNull(request.getHeader("requestFrom")) || !request.getHeader("requestFrom").equals("mobile")) {
            if (Objects.isNull(request.getHeader("sessionRefNo"))) {
                response.setResponseCode("301");
                response.setResponseMessage("Please provide valid headers.");
            }
        } else {
            if (Objects.isNull(request.getHeader("userId"))) {
                response.setResponseCode("301");
                response.setResponseMessage("Please provide valid headers.");
            }
        }
        return response;

    }

}
