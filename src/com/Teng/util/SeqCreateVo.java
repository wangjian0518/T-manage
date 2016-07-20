package com.Teng.util;

public class SeqCreateVo {

	private long  num = 1;
	private String curDate;
	private String serviceType;
	
	
	public SeqCreateVo(long num,String curDate,String serviceType)
	{
		this.num =num;
		this.curDate = curDate;
		this.serviceType = serviceType;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getCurDate() {
		return curDate;
	}
	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
}
