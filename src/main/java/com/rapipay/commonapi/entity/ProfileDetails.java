package com.rapipay.commonapi.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MASTER_LOOKUP", schema = "MST")

@Getter
@Setter
public class ProfileDetails  implements Serializable{


	    /**
	 * 
	 */
	private static final long serialVersionUID = 947289641205505248L;

		@Id
		 @Column(name = "REF_ID")
	    private String refId;

		
	    @Column(name = "NAME")
	    private String name;

	    @Column(name = "MobileNo")
	    private String mobileNo;

	    @Column(name = "Email", updatable = false, insertable = false)
	    private String email;


	    @Column(name = "DOBonDoc")
	    private String dobOnDoc;

	    @Column(name = "DocAddress")
	    private String docAddress;

	    @Column(name = "StateName")
	    private String stateName;

	    @Column(name = "BusinessCity")
	    private String businessCity;

	    @Column(name = "PinCode")
	    private String pinCode;

	    @Column(name = "TID")
	    private String tid;

	    @Column(name = "KYC_Doc_ContenT_1")
	    private String kycDocContent1;

	    @Column(name = "KYC_Doc_ContenT_2")
	    private String kycDocContent2;
	    
	    
	    @Column(name = "KYC_Doc_Name")
	    private String kycDocName;

	    @Column(name = "KYC_Doc_Format")
	    private String kycDocFormat;
	    
	    @Column(name = "KYC_Doc_Type")
	    private String kycDocType;

	    @Column(name = "KYC_Doc_ContenT_1_PAN")
	    private String kycDocContent1Pan;
	    @Column(name = "KYC_Doc_Name_PAN")
	    private String kycDocNamePan;

	    @Column(name = "KYC_Doc_Format_PAN")
	    private String kycDocFormatPan;
	    @Column(name = "KYC_Doc_Type_PAN")
	    private String kycDocTypePAN;

	    @Column(name = "panNumber")
	    private String panNo;
	    
	    @Column(name = "KYC_BUSINESS_NAME")
	    private String kycBusinessName;

	    @Column(name = "CITY")
	    private String city;
	    @Column(name = "STATE")
	    private String state;

	    @Column(name = "BANK_NAME")
	    private String bankName;
	    
	    @Column(name = "BANK_ACCOUNT_NUMBER")
	    private String bankAccountNumber;

	    @Column(name = "BANK_IFSC")
	    private String bankIFSC;
	    @Column(name = "ACCOUNT_HOLDER_NAME")
	    private String accountHolderName;

	    @Column(name = "IS_POI_AUTO_VERIFIED")
	    private String isPoiAutoVerified;
	    
	    @Column(name = "IS_POI")
	    private String isPoi;

	    @Column(name = "IS_POA_AUTO_VERIFIED")
	    private String isPoaAutoVerified;
	    @Column(name = "IS_POA")
	    private String isPoa;

	    @Column(name = "IS_BANK_AUTO_VERIFIED")
	    private String isBankAutoVerified;
	    
	    @Column(name = "IS_BANK")
	    private String isBank;

	    @Column(name = "IS_BUSINESS")
	    private String isBusiness;
	    @Column(name = "IS_GSTN")
	    private String isGstn;

	    @Column(name = "IS_FACE_MATCH")
	    private String isFaceMatch;
	    
	    @Column(name = "KYC_FINAL_STATUS")
	    private String kycFinalStatus;
	    
	    @Column(name = "NAME_ON_PAN")
	    private String nameOnPan;

	    @Column(name = "PAN_NO")
	    private String panNum;
	    @Column(name = "DOC_NO")
	    private String docNo;

	   
	    

	    @Column(name = "MCC")
	    private String mcc;
	    
	    @Column(name = "MERCHANT_TYPE")
	    private String merchantType;
	    
	    @Column(name = "IS_NODAL")
	    private String isNodal;

		/*
		 * @Column(name = "CREATED_ON") private String createdOn;
		 * 
		 * @Column(name = "DOC_NO") private String docNo;
		 * 
		 * @Column(name = "REF_ID") private String refId;
		 */
	


	 

}
