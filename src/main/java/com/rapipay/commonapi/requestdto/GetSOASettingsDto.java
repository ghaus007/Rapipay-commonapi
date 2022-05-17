package com.rapipay.commonapi.requestdto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSOASettingsDto {
	@NotNull(message = "mid cannot be null.")
	@Pattern(regexp = "^[0-9]+$", message = "Invalid mid.")
	@JsonProperty("mid")
	private String mid;

	@NotNull(message = "serviceType cannot be null.")
	@Pattern(regexp = "^[a-zA-Z_]{5,}$")
	@JsonProperty("serviceType")
	private String serviceType;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
}
