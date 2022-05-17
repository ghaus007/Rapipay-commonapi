package com.rapipay.commonapi.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubUserDto {

    private String mid;
    private String tid;
    private String tofsId;
    private String deviceSerialNo;
    private String userName;
    private String userId;
    private String userAccess;
    private String mobileNo;
    private String password;
    private String createdBy;


}
