package com.rapipay.commonapi.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rapipay.commonapi.requestdto.UpdateProfileDetailsDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class KycEncodingDto {

    @JsonProperty(value = "kycDocEncodingDto")
    private ArrayList<UpdateProfileDetailsDto.GetKycImageDto> kycDocEncodingDto;
}
