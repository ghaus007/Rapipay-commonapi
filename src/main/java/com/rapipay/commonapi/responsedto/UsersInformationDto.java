package com.rapipay.commonapi.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersInformationDto {

    private String lat;

    private String lon;
    
    @JsonProperty("fullName")
    private String userName;

    private String userAccess;

    private String userStatus;

    private String mid;

    private String tid;

    private String mobileNo;

}
