package com.rapipay.commonapi.middleware;

import com.portal.common.models.RequestDto;
import com.portal.common.models.ResponseHandlingModel;
import com.portal.common.utils.RequestDecryption;
import org.springframework.stereotype.Component;


@Component
public class RequestHandling {


    RequestDecryption requestDecryption = new RequestDecryption();


    public ResponseHandlingModel handleRequest(RequestDto request, ResponseHandlingModel responseHandle) {
        return requestDecryption.decryptRequest(request, responseHandle);

    }
}
