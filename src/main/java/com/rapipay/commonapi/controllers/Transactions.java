package com.rapipay.commonapi.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portal.common.applicationResponse.ApplicationResponseEntity;
import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import com.rapipay.merchant.constants.ServiceType;
import com.rapipay.merchant.requestdto.TransactionHistoryForADateDto;
import com.rapipay.merchant.service.impl.TransactionsImpl;
import com.rapipay.merchant.utils.StatusCode;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = {"https://merchant.rapipe.tech","https://merchant.rapipay.tech","http://localhost:3000"})

public class Transactions {

    @Autowired
    TransactionsImpl transactions;

    Logger log = LoggerFactory.getLogger(Transactions.class);

    @PostMapping("/getOnHoldTransactions")
    public ApplicationResponseEntity<Object> getOnHoldTransactions(@RequestBody RequestDto requestDto,
                                                                   @RequestHeader HttpHeaders headers) {
        try {
            log.info("in {} api", ServiceType.getOnHoldTransactions);
            Response response = transactions.getOnHoldTransactions(requestDto, headers);
            log.info("response from {} api {}", ServiceType.getOnHoldTransactions, response.toString());
            return ApplicationResponseEntity.builder()
                    .responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData()).build();
        } catch (Exception e) {
            log.error("Exception Occurred in {} {}", ServiceType.getOnHoldTransactions, e.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage()).build();
        }


    }

    @PostMapping("/statement-of-account")
    public ApplicationResponseEntity<Object> statementOfAccount(@RequestBody RequestDto requestDto,
                                                                @RequestHeader HttpHeaders headers) {

        try {
            log.info("in {} api", ServiceType.statementOfAccount);
            Response response = transactions.statementOfAccount(requestDto, headers);
            log.info("response from {} api {}", ServiceType.statementOfAccount, response.toString());
            return ApplicationResponseEntity.builder()
                    .responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData())
                    .build();
        } catch (Exception e) {
            log.error("Exception Occurred in {} api {}", ServiceType.statementOfAccount, e.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage()).build();

        }


    }

    @PostMapping("/downloadSOAData")
    public ApplicationResponseEntity<Object> downloadStatementOfAccount(@RequestBody RequestDto requestDto,
                                                                        @RequestHeader HttpHeaders headers) {

        try {
            log.info("in {} api", ServiceType.downloadStatementOfAccount);
            Response response = transactions.downloadStatementOfAccount(requestDto, headers);
            log.info("response from {} api is {}", ServiceType.downloadStatementOfAccount, response.toString());
            return ApplicationResponseEntity.builder()
                    .responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData())
                    .build();
        } catch (Exception e) {
            log.error("Exception Occurred in {} {}", ServiceType.downloadStatementOfAccount, e.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage()).build();
        }


    }

    @PostMapping("/SOASettings")
    public ApplicationResponseEntity<Object> statementOfAccountSettings(@RequestBody RequestDto requestDto,
                                                                        @RequestHeader HttpHeaders headers) {

        try {
            log.info("in {} api", ServiceType.statementOfAccountSettings);
            Response response = transactions.statementOfAccountSettings(requestDto, headers);
            log.info("Response from {} api {}", ServiceType.statementOfAccountSettings, response);
            return ApplicationResponseEntity.builder()
                    .responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData())
                    .build();
        } catch (Exception e) {
            log.error("Exception Occurred in {} api {}", ServiceType.statementOfAccountSettings, e.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage()).build();
        }


    }

    @PostMapping("/getSOASettings")
    public ApplicationResponseEntity<Object> getStatementOfAccountSettings(@Valid @RequestBody RequestDto requestDto,
                                                                           @RequestHeader HttpHeaders headers) {

        try {
            log.info("in {} api", ServiceType.getStatementOfAccountSettings);
            Response response = transactions.getStatementOfAccountSettings(requestDto, headers);
            log.info("Response from {} api is {}", ServiceType.getStatementOfAccountSettings, response);
            return ApplicationResponseEntity.builder()
                    .responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData())
                    .build();
        } catch (Exception e) {
            log.error("Exception occurred in {} api {}", ServiceType.getStatementOfAccountSettings, e.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage()).build();
        }


    }
    
    //add Transaction History Api Here
    
    @PostMapping("/getTransactionHistory")
    public ApplicationResponseEntity<Object> getTransactionHistoryForSpecificDate( @RequestBody TransactionHistoryForADateDto transactionHistoryRequest,
                                                                           @RequestHeader HttpHeaders headers) {

        try {
            log.info("in {} api", ServiceType.getTransactionHistoryForSpecificDate);
            log.info("in {} api", ServiceType.getTransactionHistoryForSpecificDate);

            Response response = transactions.getTransactionHistoryForSpecificDate(transactionHistoryRequest, headers);
            log.info("Response from {} api is {}", ServiceType.getStatementOfAccountSettings, response);
            return ApplicationResponseEntity.builder()
                    .responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData())
                    .build();
        } catch (Exception e) {
            log.error("Exception occurred in {} api {}", ServiceType.getStatementOfAccountSettings, e.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage()).build();
        }


    }
    
    
    @PostMapping("/getPayoutDetails")
    public ApplicationResponseEntity<Object> getPayoutDetails( @RequestBody TransactionHistoryForADateDto transactionHistoryRequest,
                                                                           @RequestHeader HttpHeaders headers) {

        try {
            log.info("in {} api", ServiceType.getPayOutDetails);

            Response response = transactions.getPayOutDetails(transactionHistoryRequest, headers);
            log.info("Response from {} api is {}", ServiceType.getStatementOfAccountSettings, response);
            return ApplicationResponseEntity.builder()
                    .responseCode(response.getResponseCode())
                    .responseMessage(response.getResponseMessage())
                    .responseData(response.getResponseData())
                    .build();
        } catch (Exception e) {
            log.error("Exception occurred in {} api {}", ServiceType.getStatementOfAccountSettings, e.getMessage());
            return ApplicationResponseEntity.builder()
                    .responseCode(StatusCode.EXCEPTION_501.getCode())
                    .responseMessage(StatusCode.EXCEPTION_501.getMessage())
                    .responseData(e.getMessage()).build();
        }


    }

}
