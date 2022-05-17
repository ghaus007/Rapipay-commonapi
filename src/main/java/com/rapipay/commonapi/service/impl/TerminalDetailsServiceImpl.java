package com.rapipay.commonapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import com.portal.common.models.ResponseHandlingModel;
import com.rapipay.commonapi.dao.MerchantTerminalDetailsDao;
import com.rapipay.commonapi.middleware.RequestHandling;
import com.rapipay.commonapi.middleware.SessionHandling;
import com.rapipay.commonapi.service.TerminalDetailsService;
import com.rapipay.commonapi.utils.StatusCode;

@Service
public class TerminalDetailsServiceImpl implements TerminalDetailsService{
    @Autowired
    SessionHandling sessionHandling;
    @Autowired
    ModelMapper mapper;
    @Autowired
    RequestHandling requestHandling;
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    MerchantTerminalDetailsDao merchantTerminalDetailsDao;
	@Override
	public Response getMerchantTidDetails(RequestDto request, HttpHeaders headers) {
		 ResponseHandlingModel responseModel = new ResponseHandlingModel();
	        Response response = new Response();
	        try {
	            if (!sessionHandling.checkSession(headers, responseModel).getResponseCode().equals("200")) {
	                return mapper.map(responseModel, Response.class);
	            }
	            responseModel = requestHandling.handleRequest(request, responseModel);
	            
	            JSONObject midDetails =objectMapper.readValue(responseModel.getResponseData().toString(),
	            		JSONObject.class);
	            
	            merchantTerminalDetailsDao.getMerchantTerminalDetails((String) midDetails.get("mid"), response);
	          

	        } catch (Exception e) {
	            response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
	            response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
	            response.setResponseData(e.getMessage());
				

	        }
	        return response;


	    }


}
