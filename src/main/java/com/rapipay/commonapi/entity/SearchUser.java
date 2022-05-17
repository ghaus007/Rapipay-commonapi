package com.rapipay.commonapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@Table(name = "SEARCH", schema = "POS")
public class SearchUser {

    @Id
    @Column(name = "ID")
    @JsonProperty("id")
    private long id;

    @Column(name = "RAPIMID")
    @JsonIgnore
    private String rapiMid;

    @Column(name = "MID")
    @JsonProperty("mid")
    private String mid;

    @Column(name = "RAPITID")
    @JsonIgnore
    private String rapiTid;

    @Column(name = "TID")
    @JsonProperty("tid")
    private String tid;

    @Column(name = "USER_ID")
    @JsonProperty("userId")
    private String userId;

    @Column(name = "MOBILE_NO")
    @JsonProperty("mobileNo")
    private String mobileNo;

    @Column(name = "DESCRIPTION")
    @JsonProperty("description")
    private String description;

    @Column(name = "CREATED_ON")
    @JsonProperty("createdOn")
    private String createdOn;

}
