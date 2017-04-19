package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年9月7日
 **/
public class EpBaseInfoResponse  extends BaseResponse{
	private String epName;
	private String address;
	private String legalPerson ;
	private String fund;
	private String contactPerson;
	private String contactPhone;
	private String projectBrief;
	public String getEpName() {
		return epName;
	}
	public void setEpName(String epName) {
		this.epName = epName;
	}
	public String getAddress() {
		return address ==null?"":address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLegalPerson() {
		return legalPerson==null?"":legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getFund() {
		return fund ==null?"":fund;
	}
	public void setFund(String fund) {
		this.fund = fund;
	}
	public String getContactPerson() {
		return contactPerson ==null?"":contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone ==null?"":contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getProjectBrief() {
		return projectBrief ==null?"":projectBrief;
	}
	public void setProjectBrief(String projectBrief) {
		this.projectBrief = projectBrief;
	}
	
}
