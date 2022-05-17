package com.rapipay.commonapi.service;

import org.springframework.http.HttpHeaders;

import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;

public interface AuthSession {
    public Response logoutMerchant(RequestDto requestDto, HttpHeaders headers);

    public Response loginMerchant(RequestDto request);

    public Response changePassword(RequestDto request);

}
