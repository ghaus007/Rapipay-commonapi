package com.rapipay.commonapi.service;

import org.springframework.http.HttpHeaders;

import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;

public interface TerminalDetailsService {
	
	Response getMerchantTidDetails(RequestDto request, HttpHeaders headers);

}
