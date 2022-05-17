package com.rapipay.commonapi.requestdto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TransactionHistoryForADateDto {

	private String serviceType;
	private String userId;
	private String toDate;
	private String fromDate;
	private String mid;
	private String tid;
	private String dataType;
	private String month;
	private String year;
}
