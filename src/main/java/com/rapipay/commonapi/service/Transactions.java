package com.rapipay.commonapi.service;

import org.springframework.http.HttpHeaders;

import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import com.rapipay.commonapi.requestdto.TransactionHistoryForADateDto;

public interface Transactions {
	public Response getOnHoldTransactions(RequestDto requestDto, HttpHeaders headers);

	public Response statementOfAccount(RequestDto requestDto, HttpHeaders headers);
	
	public Response downloadStatementOfAccount(RequestDto requestDto, HttpHeaders headers);

	public Response statementOfAccountSettings(RequestDto requestDto, HttpHeaders headers);

	public Response getStatementOfAccountSettings(RequestDto requestDto, HttpHeaders headers);
	
	public Response getTransactionHistoryForSpecificDate(TransactionHistoryForADateDto requestDto, HttpHeaders headers);
     
	public Response getPayOutDetails(TransactionHistoryForADateDto requestDto, HttpHeaders headers);


}
