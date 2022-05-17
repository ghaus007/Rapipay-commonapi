package com.rapipay.commonapi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import com.portal.common.models.ResponseHandlingModel;

import com.rapipay.commonapi.utils.StatusCode;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapipay.commonapi.constants.DatabaseProcedures;
import com.rapipay.commonapi.dao.TransactionsRepository;
import com.rapipay.commonapi.requestdto.DownloadSoaDataDto;
import com.rapipay.commonapi.requestdto.GetOnHoldTransactionsDto;
import com.rapipay.commonapi.requestdto.GetSOADto;
import com.rapipay.commonapi.requestdto.GetSOASettingsDto;
import com.rapipay.commonapi.requestdto.SOASettingsDto;
import com.rapipay.commonapi.requestdto.TransactionHistoryForADateDto;
import com.rapipay.commonapi.middleware.RequestHandling;
import com.rapipay.commonapi.middleware.SessionHandling;
import com.rapipay.commonapi.service.Transactions;

@Service
public class TransactionsImpl implements Transactions {

	@Autowired
	SessionHandling sessionHandling;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	RequestHandling requestHandling;
	@Autowired
	TransactionsRepository transactionsDao;
	@Autowired
	ObjectMapper objectMapper;

	Logger log = LoggerFactory.getLogger(TransactionsImpl.class);

	@Override
	public Response getOnHoldTransactions(RequestDto requestDto, HttpHeaders headers) {
		ResponseHandlingModel responseModel = new ResponseHandlingModel();
		Response response = new Response();
		if (!sessionHandling.checkSession(headers, responseModel).getResponseCode().equals("200")) {
			response = modelMapper.map(responseModel, Response.class);
		} else {
			responseModel = requestHandling.handleRequest(requestDto, responseModel);
			if (!responseModel.getResponseCode().equals("1")) {
				GetOnHoldTransactionsDto getOnHoldTransactionsDto;
				try {
					getOnHoldTransactionsDto = objectMapper.readValue(responseModel.getResponseData().toString(),
							GetOnHoldTransactionsDto.class);
					log.info("GET_ON_HOLD_TRANSACTIONS raw request {}", getOnHoldTransactionsDto.toString());
					transactionsDao.getOnHoldTransactions(getOnHoldTransactionsDto, response);
				} catch (Exception e) {
					response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
					response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
					response.setResponseData(e.getMessage());
				}

			}
		}

		return response;
	}

	@Override
	public Response downloadStatementOfAccount(RequestDto requestDto, HttpHeaders headers) {
		ResponseHandlingModel responseModel = new ResponseHandlingModel();
		try {

			if (!sessionHandling.checkSession(headers, responseModel).getResponseCode().equals("200")) {
				return modelMapper.map(responseModel, Response.class);
			}

			responseModel = requestHandling.handleRequest(requestDto, responseModel);
			log.info("After decrypting request for DOWNLOAD_SOA_DATA {}", responseModel.getResponseData());
			if (responseModel.getResponseCode().equals("1")) {

				return modelMapper.map(responseModel, Response.class);
			}
			DownloadSoaDataDto downloadSoaDataDto = objectMapper.readValue(responseModel.getResponseData().toString(),
					DownloadSoaDataDto.class);
			transactionsDao.downloadSOAData(downloadSoaDataDto, responseModel);

		} catch (Exception e) {

			responseModel.setResponseCode(StatusCode.EXCEPTION_501.getCode());
			responseModel.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
			responseModel.setResponseData(e.getMessage());
		}

		return modelMapper.map(responseModel, Response.class);
	}

	@Override
	public Response statementOfAccount(RequestDto requestDto, HttpHeaders headers) {
		ResponseHandlingModel responseModel = new ResponseHandlingModel();
		Response response = new Response();
		if (!sessionHandling.checkSession(headers, responseModel).getResponseCode().equals("200")) {
			response = modelMapper.map(responseModel, Response.class);
		} else {
			responseModel = requestHandling.handleRequest(requestDto, responseModel);
			if (!responseModel.getResponseCode().equals("1")) {
				GetSOADto getSOARequest;
				try {
					getSOARequest = objectMapper.readValue(responseModel.getResponseData().toString(), GetSOADto.class);
					transactionsDao.getSOA(getSOARequest, response);
				} catch (Exception e) {
					response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
					response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
					response.setResponseData(e.getMessage());
				}

			}

		}
		return response;
	}

	@Override
	public Response statementOfAccountSettings(RequestDto requestDto, HttpHeaders headers) {
		ResponseHandlingModel responseModel = new ResponseHandlingModel();
		Response response = new Response();
		if (!sessionHandling.checkSession(headers, responseModel).getResponseCode().equals("200")) {
			response = modelMapper.map(responseModel, Response.class);
		} else {
			responseModel = requestHandling.handleRequest(requestDto, responseModel);
			if (!responseModel.getResponseCode().equals("1")) {

				try {
					SOASettingsDto updateSoaSettingDto = objectMapper
							.readValue(responseModel.getResponseData().toString(), SOASettingsDto.class);
					transactionsDao.updateSOASettings(updateSoaSettingDto, response);

				} catch (Exception e) {

					response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
					response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
					response.setResponseData(e.getMessage());

				}

			}

		}
		return response;

	}

	@Override
    public Response getStatementOfAccountSettings(RequestDto requestDto, HttpHeaders headers) {
        ResponseHandlingModel responseModel = new ResponseHandlingModel();
        Response response = new Response();

		
		  if (!sessionHandling.checkSession(headers,
		  responseModel).getResponseCode().equals("200")) { response =
		  modelMapper.map(responseModel, Response.class); } else {
		 
            responseModel = requestHandling.handleRequest(requestDto, responseModel);
            if (!responseModel.getResponseCode().equals("1")) {

                try {
                    GetSOASettingsDto getSoaSettingDto = objectMapper
                            .readValue(responseModel.getResponseData().toString(), GetSOASettingsDto.class);
                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                    Validator validator = factory.getValidator();
                    Set<ConstraintViolation<GetSOASettingsDto>> violations = validator.validate(getSoaSettingDto);
                    for (ConstraintViolation<GetSOASettingsDto> violation : violations) {
                        System.out.println(violation.getMessage());

                    }
                    transactionsDao.getSOASettings(getSoaSettingDto, response);
                } catch (Exception e) {
                    response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
                    response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
                    response.setResponseData(e.getMessage());
                }

            }

        }
        return response;

    }

	@Override
	public Response getTransactionHistoryForSpecificDate(TransactionHistoryForADateDto transactionHistoryRequest,
			HttpHeaders headers) {
		// TODO Auto-generated method stub
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		Response response = new Response();
		ResponseHandlingModel responseModel = new ResponseHandlingModel();

		String procName = "";
		String procParameters = "";
		List<String> list = new ArrayList<>();
		if (!sessionHandling.checkSession(headers, responseModel).getResponseCode().equals("200")) {
			response = modelMapper.map(responseModel, Response.class);
		} else {

			try {
				switch (transactionHistoryRequest.getDataType()) {

				case "month_wise":
					procName = DatabaseProcedures.GET_MONTH_WISE_TRANSACTION_DETAILS;
					list.add(transactionHistoryRequest.getMid());
					list.add(transactionHistoryRequest.getTid());
					list.add(transactionHistoryRequest.getUserId());
					procParameters = "(?,?,?)}";

					break;

				case "date_wise":
					procName = DatabaseProcedures.GET_DATE_WISE_TRANSACTION_DETAILS;
					list.add(transactionHistoryRequest.getMid());
					list.add(transactionHistoryRequest.getMonth());

					list.add(transactionHistoryRequest.getYear());

					list.add(transactionHistoryRequest.getTid());
					list.add(transactionHistoryRequest.getUserId());

					procParameters = "(?,?,?,?,?)}";

					break;

				default:
					procName = DatabaseProcedures.Get_Transaction_History;
					procParameters = "(?,?,?,?,?,?,?,?)}";

					list.add(transactionHistoryRequest.getUserId());
					list.add(transactionHistoryRequest.getMid());
					list.add(transactionHistoryRequest.getTid());
					switch (transactionHistoryRequest.getDataType()) {

					case "custom":

						list.add(null);
						list.add(null);
						list.add(transactionHistoryRequest.getFromDate());

						list.add(transactionHistoryRequest.getToDate());
						list.add(null);
						break;
					case "today":

						list.add("1");
						list.add(null);
						list.add(null);

						list.add(null);
						list.add(null);
						break;
					case "recent":
						List<String> list1=new ArrayList<String>();
						list1.add(transactionHistoryRequest.getMid());
						list1.add(transactionHistoryRequest.getTid());
						list1.add(transactionHistoryRequest.getUserId());
						String procNameForYesterdayAndTodayData = DatabaseProcedures.GET_TRANSACTION_HISTORY_TODAY_YESTERDAY;

						String procParametersForYesterdayAndTodayData = "(?,?,?)}";
						transactionsDao.getTransactionHistoryForADate(transactionHistoryRequest, response,
								procNameForYesterdayAndTodayData, list1, procParametersForYesterdayAndTodayData);
						hashMap.put("yesterday_today_total", response.getResponseData());
						list.add(null);
						list.add("1");
						list.add(null);

						list.add(null);
						list.add(null);
						break;

					default:

						list.add("0");
						list.add("0");
						list.add(null);

						list.add(transactionHistoryRequest.getToDate());
						list.add("1");
						break;

					}

				}

				transactionsDao.getTransactionHistoryForADate(transactionHistoryRequest, response, procName, list,
						procParameters);
				if (hashMap.containsKey("yesterday_today_total")) {
					hashMap.put("recent", response.getResponseData());
					response.setResponseData(hashMap);
				}

			} catch (Exception e) {
				response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
				response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
				response.setResponseData(e.getMessage());
			}

		}
		return response;

	}

	public Response getPayOutDetails(TransactionHistoryForADateDto transactionHistoryRequest, HttpHeaders headers) {
		// TODO Auto-generated method stub
		
		Response response = new Response();
		ResponseHandlingModel responseModel = new ResponseHandlingModel();

		String procName = "";
		String procParameters = "";
		List<String> list = new ArrayList<>();
		if (!sessionHandling.checkSession(headers, responseModel).getResponseCode().equals("200")) {
			response = modelMapper.map(responseModel, Response.class);
		} else {

			try {
				procName = DatabaseProcedures.GET_PAYOUT_DETAILS;
				procParameters = "(?,?)}";
						list.add(transactionHistoryRequest.getMid());
						list.add(transactionHistoryRequest.getToDate());
						
					

				

				transactionsDao.getTransactionHistoryForADate(transactionHistoryRequest, response, procName, list,
						procParameters);
				

			} catch (Exception e) {
				response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
				response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
				response.setResponseData(e.getMessage());
			}

		}
		return response;


	}

}
