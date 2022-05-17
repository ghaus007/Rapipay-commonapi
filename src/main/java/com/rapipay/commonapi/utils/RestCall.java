package com.rapipay.commonapi.utils;

import com.portal.common.models.Response;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCall {

    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity postCall(String url, MultiValueMap body, HttpMethod type) {

        HttpHeaders headersCall = new HttpHeaders();
        headersCall.setContentType(MediaType.APPLICATION_JSON);
        //headersCall.add("Content-Type", "application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(body, headersCall);
        return restTemplate.exchange(url, type, requestEntity, String.class);
    }


}
