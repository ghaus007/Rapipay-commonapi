/*
 * package com.rapipay.commonapi.entity;
 * 
 * import lombok.Getter; import lombok.Setter; import lombok.ToString; import
 * org.hibernate.annotations.Cascade;
 * 
 * import javax.persistence.*; import java.util.Set;
 * 
 * @Entity
 * 
 * @Table(name = "MERCHANT_MASTER", schema = "MST")
 * 
 * @Getter
 * 
 * @Setter
 * 
 * @ToString public class MerchantMaster {
 * 
 * @Id
 * 
 * @Column(name = "MM_ID") private String mm_id;
 * 
 * @Column(name = "MERCHANT_REF_ID") private String merchantRefId;
 * 
 * @Column(name = "MERCHANT_MOBILE") private String merchantMobile;
 * 
 * @Column(name = "MERCHANT_EMAIL") private String merchantEmail;
 * 
 * @Column(name = "STORE_NAME") private String storeName;
 * 
 * @Column(name = "BRAND_NAME") private String brandName;
 * 
 * @Column(name = "CITY") private String city;
 * 
 * @Column(name = "DISTRICT") private String district;
 * 
 * @Column(name = "STATE") private String state;
 * 
 * @Column(name = "STATE_NAME") private String stateName;
 * 
 * @Column(name = "ADDRESS") private String address;
 * 
 * @Column(name = "ACCOUNT_HOLDER_NAME") private String accountHolderName;
 * 
 * @Column(name = "BANK_IFSC") private String bankIfsc;
 * 
 * @Column(name = "BANK_NAME") private String bankName;
 * 
 * @Column(name = "BANK_ACCOUNT_NUMBER") private String bankAccountNumber;
 * 
 * @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchantMaster") private
 * Set<MerchantonboardingForSwitch> merchantonboardingForSwitchSet;
 * 
 * 
 * @ManyToOne(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "MERCHANT_REF_ID", referencedColumnName = "REF_ID",
 * insertable = false, updatable = false)
 * 
 * private ProfileDetails profileDetails;
 * 
 * @Override public String toString() { return "MerchantMaster [mm_id=" + mm_id
 * + ", merchantRefId=" + merchantRefId + ", merchantMobile=" + merchantMobile +
 * ", merchantEmail=" + merchantEmail + ", storeName=" + storeName +
 * ", brandName=" + brandName + ", city=" + city + ", district=" + district +
 * ", state=" + state + ", stateName=" + stateName + ", address=" + address +
 * ", accountHolderName=" + accountHolderName + ", bankIfsc=" + bankIfsc +
 * ", bankName=" + bankName + ", bankAccountNumber=" + bankAccountNumber +
 * ", merchantonboardingForSwitchSet=" + merchantonboardingForSwitchSet + "]"; }
 * 
 * 
 * }
 */