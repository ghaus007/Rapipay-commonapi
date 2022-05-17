package com.rapipay.commonapi.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDataDto {


    private String userName;

    private String userAccess;

    private String userStatus;

    private String mobileNo;

    private String authPin;

    private String serviceType;
    
    private String userId;

}
