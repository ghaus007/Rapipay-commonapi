package com.rapipay.commonapi.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.common.models.Response;
import com.portal.common.models.ResponseHandlingModel;
import com.rapipay.commonapi.dao.MyPlansRepository;
import com.rapipay.commonapi.middleware.RequestHandling;
import com.rapipay.commonapi.middleware.SessionHandling;
import com.rapipay.commonapi.requestdto.MyPlansRequestDto;
import com.rapipay.commonapi.service.MyPlansService;
import com.rapipay.commonapi.utils.StatusCode;

@Service
public class MyPlansServiceImpl implements MyPlansService {
	
	@Autowired
	MyPlansRepository myPlansRepository;
	
	 @Autowired
	    SessionHandling sessionHandling;
	    @Autowired
	    ModelMapper mapper;
	   
	    @Autowired
	    ObjectMapper objectMapper;

	@Override
	public Response getTerminalDetails(MyPlansRequestDto myPlansRequest, HttpHeaders headers) {
		// TODO Auto-generated method stub
		Response response = new Response();		
		ResponseHandlingModel responseModel = new ResponseHandlingModel();
		
		try {
			
			
			  if (!sessionHandling.checkSession(headers,
			  responseModel).getResponseCode().equals("200")) { return
			  mapper.map(responseModel, Response.class); }
			 
			 
			Map<String, Object> row = new HashMap<String, Object>();
			 myPlansRepository.getTerminalDetails(myPlansRequest,response);
			 if(response.getResponseCode().equalsIgnoreCase(StatusCode.SUCCESS_200.getCode()))
			 {
				row.put("terminalData", response.getResponseData());
				 myPlansRepository.getMdrDetails(myPlansRequest,response);
				 if(response.getResponseCode().equalsIgnoreCase(StatusCode.SUCCESS_200.getCode()))
				 {
					row.put("mdrData", response.getResponseData());
					 myPlansRepository.getMdrDetails(myPlansRequest,response);
			        response.setResponseData(row);
				 }
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
			response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
			response.setResponseData(e.getMessage());
		}
		 
		 
		return response;
	}

}
