package com.rapipay.commonapi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "TERMINAL_CHARGES_DETAILS", schema = "MST")
@ToString
@Getter
@Setter
public class TerminalCharges implements Serializable {

    @Id
    @Column(name = "CHARGES_ID")
    private String chargesId;

    @Column(name = "MID")
    private String mid;

    @Column(name = "TID", updatable = false, insertable = false)
    private String tid;


    @Column(name = "TERMINAL_RENT_CHARGES")
    private String termialRentCharges;

    @Column(name = "INSTALLATION_CHARGES")
    private String installationCharges;

    @Column(name = "IS_MONTHLY")
    private String isMonthly;

    @Column(name = "IS_DAILY")
    private String isDaily;

    @Column(name = "SIM_RENT")
    private String simRent;

    @Column(name = "IS_YEARLY")
    private String isYearly;

    @Column(name = "IS_COMMON_FOR_MERCHANT")
    private String isCommonForMerchant;

    @Column(name = "START_DATE")
    private String startDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "TID", referencedColumnName = "TID", insertable = false, updatable = false, foreignKey = @javax.persistence
            .ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<MerchantMdr> mdrData;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "TID", referencedColumnName = "TID")
//    private TerminalOnBoardingForSwitch terminalOnBoardingForSwitch;


}
