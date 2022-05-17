package com.rapipay.commonapi.requestdto;

public class DownloadSoaDataDto {
	private String serviceType;
	private String mid;
	private String fromDate;
	private String toDate;
	private String tid;
	private String selectionType;

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(String selectionType) {
		this.selectionType = selectionType;
	}

}
