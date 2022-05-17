package com.rapipay.commonapi.requestdto;

public class GetOnHoldTransactionsDto {
	@Override
	public String toString() {
		return "GetOnHoldTransactionsDto [mid=" + mid + ", tid=" + tid + ", userId=" + userId + ", serviceType="
				+ serviceType + "]";
	}

	private String mid;
	private String tid;
	private String userId;
	private String serviceType;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
}
