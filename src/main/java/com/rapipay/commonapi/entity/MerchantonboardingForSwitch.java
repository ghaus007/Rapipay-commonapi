/*
 * package com.rapipay.commonapi.entity;
 * 
 * import lombok.Getter; import lombok.Setter; import lombok.ToString;
 * 
 * import javax.persistence.*; import java.util.Set;
 * 
 * @Entity
 * 
 * @Table(name = "MERCHANT_ONBORDING_FOR_SWITCH", schema = "POS")
 * 
 * @Getter
 * 
 * @Setter
 * 
 * @ToString public class MerchantonboardingForSwitch {
 * 
 * @Id
 * 
 * @Column(name = "MOFS_ID") private String mofsId;
 * 
 * // @Column(name = "MM_ID") // private String mmId;
 * 
 * @Column(name = "SWITCH_ID") private String switchId;
 * 
 * @Column(name = "MERCHANT_REF_ID") private String merchantRefId;
 * 
 * @Column(name = "MID") private String mid;
 * 
 * @Column(name = "MID_STATUS") private String midStatus;
 * 
 * 
 * @OneToOne(cascade = CascadeType.ALL) // @JoinColumn(name = "MM_ID",
 * referencedColumnName="MM_ID") private MerchantMaster merchantMaster;
 * 
 * @ManyToOne(fetch = FetchType.LAZY)
 * 
 * @JoinColumn(name = "MM_ID") private MerchantMaster merchantMaster;
 * 
 * @OneToMany(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "MOFS_ID", referencedColumnName = "MOFS_ID")
 * Set<TerminalOnBoardingForSwitch> terminalOnBoardingOfSwitchSet;
 * 
 * 
 * @OneToOne(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "MERCHANT_REF_ID", referencedColumnName = "REF_ID",
 * insertable = false, updatable = false) private ProfileDetails profileDetails;
 * 
 * 
 * }
 */