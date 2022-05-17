package com.rapipay.commonapi.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum StatusCode {

    INV_E("400", "Please provide valid Email Id."),
    NO_REC("401", "No record Found."),
    ERROR_204("204", "No record Found for this mid."),
    ERROR_501("501", "Exception Occured"),
    SUCCESS_200("200", "Request Processed Successfully."),
    ERROR_400_EMPTYINPUT("400", "Input fields can not be empty"),
    ERROR_401_INV_SESSION("404", "Invalid Session."),
    ERROR_401("401", "Something went wrong."),
    ERROR_401_AUTHENTICATION_FAILURE("401", "Authentication failure."),
    ERROR_401_IMAGE("401", "Invalid destination for image."),
    ERROR_401_IMAGE_NAME("401", "Error finding image name for user."),
    ERROR_401_INV_USER("401", "Invalid User."),
    ERROR_401_SESSION_EXPIRED("403", "Session Expired."),
    ERROR_401_NO_DATA("401", "No Data found for this session id."),
    ERROR_301_VALIDATION("401", "Invalid Request."),
    ERROR_204_EMPTYLISTFORMID("401", "No Record Found for given Mid"),
    EXCEPTION_501("501", "Exception Occurred."),
    ERROR_403_EXP_SESSION("403", "Session Expired.Please login again."),
    ERROR_404_INV_SESSION("404", "Invalid Session."),
    PASS_INV_1("301", "Old Password cannot be the same as New Password"),
    PASS_INV_2("301", "Password cannot be same as Last five Passwords."),
    PASS_INV_3("301", "Invalid Old Password."),
    RECORD_NOT_INSERTED("201", "User_id already Exists."),
    LIMIT_CONSUMED("201", "Limit Consumed:-Only three users are allow to create for POS.");


    private final String code;
    private final String message;

    StatusCode(String s, String s1) {
        this.code = s;
        this.message = s1;

    }
}
