package com.rapipay.commonapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MERCHANT_MDR_DETAILS", schema = "POS")
@Getter
@ToString
public class MerchantMdr implements Serializable {

    @Id
    @JsonIgnore
    @Column(name = "MMD_ID")
    private String id;

    @Column(name = "MDR_ID")
    private String mdrId;

    @Column(name = "MID")
    private String mid;

    @Column(name = "TID")
    private String tid;

    @Column(name = "MDR_NAME")
    private String mdrName;

    @Column(name = "MDR_PERCENTAGE")
    private String mdrPercent;

    @Column(name = "NAME_TO_DISPLAY")
    private String nameToDisplay;

    @Column(name = "CARD_TYPE")
    private String cardType;

    @Column(name = "SCHEME_TYPE")
    private String schemeType;

    @JsonIgnore
    @Column(name = "AMOUNT")
    private String amount;

    @JsonIgnore
    @Column(name = "MCC")
    private String mcc;

    @Column(name = "IS_DEFAULT")
    private String isDefault;

//    @OneToOne(mappedBy = "mdrData")
//    private TerminalChargesDetails terminalChargesDetails;

}
