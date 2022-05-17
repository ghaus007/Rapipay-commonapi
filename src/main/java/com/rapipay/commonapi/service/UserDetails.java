package com.rapipay.commonapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import com.rapipay.commonapi.requestdto.CreateSubUserDto;
import com.rapipay.commonapi.requestdto.UpdateUserDataDto;
import org.springframework.http.HttpHeaders;

public interface UserDetails {

    Response updateProfileDetails(RequestDto request, HttpHeaders headers);

    Response getProfileDetails(RequestDto request, HttpHeaders headers);

    Response getUserInformation(String userId, HttpHeaders headers) throws JsonProcessingException;

    Response updateUserInformation(UpdateUserDataDto updateUserDataDto, HttpHeaders headers) throws JsonProcessingException;

   // Response createSubUser(CreateSubUserDto createSubUserDto, HttpHeaders headers) throws JsonProcessingException;

    Response getUserList(String mid, String tid, String mobileNo, HttpHeaders headers);

	Response createSubUserViaProc(CreateSubUserDto createSubUserDto, HttpHeaders headers)
			throws JsonProcessingException;


}
