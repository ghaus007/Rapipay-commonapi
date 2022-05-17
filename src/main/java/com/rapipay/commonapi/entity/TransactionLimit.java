package com.rapipay.commonapi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "TERMINAL_TRANSACTION_LIMIT", schema = "POS")
public class TransactionLimit {

    @Column(name = "ID")
    @Id
    private String id;

    @Column(name = "MID")
    private String mid;

    @Column(name = "TID")
    private String tid;

    @Column(name = "TRANSACTION_LIMIT_ANNUL_MERCHANT")
    private String txnLimitAnnualMerchant;

    @Column(name = "TRANSACTION_LIMIT_MONTH_MERCHANT")
    private String txnLimitMonth;

    @Column(name = "TRANSACTION_LIMIT_DAY_MERCHANT")
    private String txnLimitDay;

    @Column(name = "CONSUMED_ANNUAL_LIMIT_MERCHANT")
    private String consumedAnnualLimitMerchant;

    @Column(name = "CONSUMED_TODAY_LIMIT_MERCHANT")
    private String consumedTodayLimitMerchant;
    
    @Column(name = "CONSUMED_MONTHLY_LIMIT_MERCHANT")
    private String consumedMonthlyLimitMerchant;

    @Column(name = "TRANSACTION_LIMIT_ANNUL_TERMINAL")
    private String transactionLimitAnnulTerminal;
    
    @Column(name = "TRANSACTION_LIMIT_MONTH_TERMINAL")
    private String transactionLimitMonthTerminal;
    @Column(name = "TRANSACTION_LIMIT_DAY_TERMINAL")
    private String transactionLimitDayTerminal;
    
    @Column(name = "CONSUMED_ANNUAL_LIMIT_TERMINAL")
    private String consumedAnnualLimitTerminal;
    @Column(name = "CONSUMED_MONTHLY_LIMIT_TERMINAL")
    private String consumedMonthlyLimitTerminal;
    @Column(name = "CONSUMED_TODAY_LIMIT_TERMINAL")
    private String consumedTodayLimitTerminal;
    @JsonIgnore
    @Column(name = "TERMINAL_SET_MONTHLY")
    private String terminalSetMonthly;
    
    @JsonIgnore
    @Column(name = "TERMINAL_SET_DAILY")
    private String terminalSetDaily;
    
    @JsonIgnore
    @Column(name = "IS_SET_MANUAL")
    private String isSetManual;
    
    @JsonIgnore
    @Column(name = "UPDATED_ON")
    private String updatedOn;
   
    @JsonIgnore
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    
    @JsonIgnore
    @Column(name = "CREATED_ON")
    private String createdOn;
    
    @JsonIgnore
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    
    @JsonIgnore
    @Column(name = "CONSUMED_MONTHLY_LIMIT")
    private String consumedMonthlyLimit;
    
    @JsonIgnore
    @Column(name = "CONSUMED_TODAY_LIMIT")
    private String consumedTodayLimit;
    
    @JsonIgnore
    @Column(name = "TRANSACTION_LIMIT_DAY")
    private String transactionLimitDay;
    
    @JsonIgnore
    @Column(name = "TRANSACTION_LIMIT_MONTH")
    private String transactionLimitMonth;
    
    
    
   
}
