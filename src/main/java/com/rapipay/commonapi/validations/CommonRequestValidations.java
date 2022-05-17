package com.rapipay.commonapi.validations;

import com.portal.common.models.RequestDto;
import com.portal.common.models.ResponseHandlingModel;
import com.rapipay.commonapi.utils.StatusCode;
import org.springframework.stereotype.Component;

import org.springframework.web.multipart.MultipartFile;

@Component
public class CommonRequestValidations {

    public ResponseHandlingModel commonRequestValidations(RequestDto request, ResponseHandlingModel responseHandle) {

        responseHandle.setResponseCode(StatusCode.SUCCESS_200.getCode());
        if (request == null || request.getRequest() == null) {
            responseHandle.setResponseCode("301");
            responseHandle.setResponseMessage("Invalid Request.");

        }
        return responseHandle;

    }

    public ResponseHandlingModel uploadProfileImageValidations(MultipartFile file,
                                                               ResponseHandlingModel responseHandle) {
        responseHandle.setResponseCode(StatusCode.SUCCESS_200.getCode());
        if (file.isEmpty()) {
            responseHandle.setResponseCode("301");
            responseHandle.setResponseMessage("Invalid Request.");

        }

        return responseHandle;

    }

}
