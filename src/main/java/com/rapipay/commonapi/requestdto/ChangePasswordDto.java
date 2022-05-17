package com.rapipay.commonapi.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {

    private String loginId;
    private String oldPassword;
    private String newPassword;
    private String serviceType;
    
}
