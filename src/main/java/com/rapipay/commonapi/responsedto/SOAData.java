package com.rapipay.commonapi.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SOAData {

    @JsonProperty("MID")
    private String mid;

    @JsonProperty("TXN_COUNT")
    private String txnCount;

    @JsonProperty("TXN_AMOUNT")
    private String txnAmount;

    @JsonProperty("TXN_DATE")
    private String txnDate;

    @JsonProperty("MDR")
    private String mdr;

    @JsonProperty("CGST")
    private String cgst;

    @JsonProperty("SGST")
    private String sgst;

    @JsonProperty("IGST")
    private String igst;

    @JsonProperty("TDS")
    private String tds;

    @JsonProperty("RENT")
    private String rent;

    @JsonProperty("DEDUCTION")
    private String deduction;

    @JsonProperty("ADDITION")
    private String addition;

    @JsonProperty("AMOUNT_TO_BE_CREDITED")
    private String amountToBeCredited;

    @JsonProperty("SUCCESS_TRANSACTION")
    private String successTransaction;

    @JsonProperty("FAILED_TRANSACTION")
    private String failedTransaction;

    @JsonProperty("HOLD_TRANSACTION")
    private String holdTransaction;

    @JsonProperty("TOTAL_TRANSACTION")
    private String totalTransaction;


}
