package com.rapipay.commonapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.portal.common.applicationResponse.ApplicationResponseEntity;
import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import com.rapipay.commonapi.constants.ServiceType;
import com.rapipay.commonapi.requestdto.MyPlansRequestDto;
import com.rapipay.commonapi.service.MyPlansService;
import com.rapipay.commonapi.service.TerminalDetailsService;
import com.rapipay.commonapi.utils.StatusCode;

@RestController
@RequestMapping(value="/terminal-details")
@CrossOrigin(origins = {"https://merchant.rapipe.tech","https://merchant.rapipay.tech","http://localhost:3000"})
public class TerminalDetails {
	//public void ResonseEntity<Response> getTidDetails
    
	@Autowired TerminalDetailsService terminalDetailsService;
	
	@Autowired
	MyPlansService myPlansService;
    Logger log = LoggerFactory.getLogger(TerminalDetails.class);
	@PostMapping("/getTidDetails")
    public ApplicationResponseEntity<Object> getProfileDetails(@RequestBody RequestDto request, @RequestHeader HttpHeaders headers) {

        try {
            log.info("inside {} api", ServiceType.getTerminalDetails);
            Response response = terminalDetailsService.getMerchantTidDetails(request, headers);
            log.info("response from {} api is {}", ServiceType.getMerchantDetails, response.toString());
            return ApplicationResponseEntity.builder().
                    responseCode(response.getResponseCode()).
                    responseMessage(response.getResponseMessage()).
                    responseData(response.getResponseData()).build();
        } catch (Exception ex) {

            log.error("Exception in {} {}", ServiceType.getMerchantDetails, ex.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(ex.getMessage()).build();

        }

    }
	//myPlans
	@PostMapping("getMyPlans")
	public ApplicationResponseEntity<Object> getTerminalDetails(
                    @RequestBody MyPlansRequestDto myPlansRequest,@RequestHeader HttpHeaders headers) {

		try {
			log.info("inside {} api", ServiceType.myPlans);
			Response response = myPlansService.getTerminalDetails(myPlansRequest, headers);
			log.info("response from {} api {}", ServiceType.myPlans, response.toString());
			return ApplicationResponseEntity.builder().responseCode(response.getResponseCode())
					.responseMessage(response.getResponseMessage()).responseData(response.getResponseData()).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 log.error("Exception occurred in {} api {}", ServiceType.getStatementOfAccountSettings, e.getMessage());
	            return ApplicationResponseEntity.builder()
	                    .responseCode(StatusCode.EXCEPTION_501.getCode())
	                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
	                    .responseData(e.getMessage()).build();
	        }
		

	
}
}
