package com.rapipay.commonapi.service;

import org.springframework.http.HttpHeaders;

import com.portal.common.models.Response;
import com.rapipay.commonapi.requestdto.MyPlansRequestDto;


public interface MyPlansService {

	Response getTerminalDetails(MyPlansRequestDto myPlansRequest, HttpHeaders headers);
    
}
