package com.rapipay.commonapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@Table(schema = "POS", name = "USER_LOGIN_INFO")
public class UsersList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LN_ID", insertable = false)
    private long id;

    @Column(name = "MID")
    private String mid;

    @Column(name = "TID")
    private String tid;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DEVICE_SERIAL_NO")
    private String deviceSerialNo;
    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @JsonIgnore
    @Column(name = "Access_Type")
    private String accessType;

    @JsonIgnore
    @Column(name = "UPDATED_ON")
    private String updatedOn;
    @JsonIgnore
    @Column(name = "UPDATED_BY")
    private String updatedBy;

}
