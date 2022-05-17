package com.rapipay.commonapi.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnHoldTransactions {

    @JsonProperty("TXN_ID")
    private String txnId;

    @JsonProperty("TERMINAL_SERIAL_NO")
    private String terminalSerialNo;

    @JsonProperty("TID")
    private String tid;

    @JsonProperty("mid")
    private String mid;

    @JsonProperty("USER_ID")
    private String userId;

    @JsonProperty("REQUEST_TYPE")
    private String requestType;

    @JsonProperty("REQUEST_AMOUNT")
    private String requestAmount;

    @JsonProperty("REQUEST_DATE")
    private String requestDate;

    @JsonProperty("SERVICE_TYPE")
    private String serviceType;

    @JsonProperty("STATUS")
    private String status;

    @JsonProperty("CARDNUMBER")
    private String cardNumber;


}
