package com.rapipay.commonapi.requestdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UpdateProfileDetailsDto {


    private String email;
    private String serviceType;
    private String addressLine;
    private String merchantMobile;

    @Getter
    @Setter
    @ToString
    public static class GetKycImageDto {


        private String documentId;

        private String documentPath;

        private String encodedDocumentContent;
    }
}
