package com.rapipay.commonapi.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
public class UserDetailsModel {

    private String userId;
    private String mid;
    private String tid[];
    private String userAccess;
    private String authPin;
    private String initial;
    private String oneTime;
    private String revStatus[];
    private String isRev[];
    private String deviceName;
    private String deviceType;
    private String isRki[];
    private String fullName;
    private String deviceSerialNo[];
    private String appVersion;
    private String lat;
    private String lon;
    private String clientIP;
    private String userImageName;
    private String session;
    private String mobileNo;
    private String userStatus;
    private ArrayList<String> oldPasswords;
//    private String session2;


}
