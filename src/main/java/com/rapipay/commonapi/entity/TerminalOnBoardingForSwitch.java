package com.rapipay.commonapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TERMINAL_ONBOARDING_FOR_SWITCH", schema = "POS")
@Getter
//@Setter
@ToString
public class TerminalOnBoardingForSwitch {

    @Id
    @Column(name = "TOFS_ID")
    @JsonIgnore
    private String tofsId;

    @JsonIgnore
    @Column(name = "MID")
    private String mid;

    @Column(name = "TID", insertable = false, updatable = false)
    private String tid;

    @Column(name = "INTL_CARD_ACCEPTANCE")
    private boolean internationalCardAcceptance;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MOFS_ID")
//    private MerchantonboardingForSwitch merchantonboardingForSwitch;

    @Column(name = "TID_STATUS")
    private String tidStatus;

    @JsonIgnore
    @Column(name = "SWITCH_ID")
    private String switchId;


    @Column(name = "TRANSACTION_LIMIT")
    private String transactionLimit;

   @JsonIgnore
   @Column(name = "TM_ID")
   private String tmId;

    @Column(name = "PREAUTH")
    private boolean preAuth;

//    @Column(name = "PREAUTH_COMPL")
//    private String preAuthCompl;

    @Column(name = "REFUND")
    private boolean refund;

    @Column(name = "SALE")
    private boolean sale;

    @Column(name = "SALE_WITH_CASH")
    private boolean saleWithCash;

    @Column(name = "BANK_EMI")
    private boolean bankEmi;

    @Column(name = "BRAND_EMI")
    private boolean brandEmi;

    @Column(name = "AMEX")
    private boolean amex;

    @Column(name = "VOID")
    private boolean voidTid;
    
    
    @Column(name = "IS_RKI")
    private boolean isRki;
    @Column(name = "IS_REV")
    private boolean isRev;
    @Column(name = "REV_STATUS")
    private String revStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TM_ID", referencedColumnName = "TM_ID",insertable=false, updatable=false)
    private TerminalMaster terminalMaster;


//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "TID", referencedColumnName = "TID")
//    private TerminalChargesDetails terminalChargesDetails;

//    @Transient
//    private List<MerchantMdr> mdrData;


}
