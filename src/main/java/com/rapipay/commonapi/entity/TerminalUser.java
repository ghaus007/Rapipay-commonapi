package com.rapipay.commonapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TERMINAL_USER_DETAILS", schema = "MST")
@Getter
@ToString
@Setter
public class TerminalUser implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "USER_ID",insertable=false)
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_MOBILE_NO")
    private String userMobileNo;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "USER_LOGIN_ID")
    private String userLoginId;

    @Column(name = "CREATED_ON")
    private String createdOn;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_ON")
    private String updatedOn;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
    


//    @OneToOne(mappedBy = "mdrData")
//    private TerminalChargesDetails terminalChargesDetails;

}
