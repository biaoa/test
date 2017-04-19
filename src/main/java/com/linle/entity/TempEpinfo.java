package com.linle.entity;

import com.linle.common.util.ExcelVOAttribute;

/**
 * 导入企业信息
 * @author Administrator
 *
 */
public class TempEpinfo {
	@ExcelVOAttribute(column = "B", name = "")
	private String peName;
	
	@ExcelVOAttribute(column = "F", name = "")
	private String address;
	 
	@ExcelVOAttribute(column = "H", name = "")
	private String faren;
	
	@ExcelVOAttribute(column = "J", name = "")
	private String contactPerson;
	
	@ExcelVOAttribute(column = "L", name = "")
	private String email;
	
	@ExcelVOAttribute(column = "M", name = "")
	private String project;
	
	@ExcelVOAttribute(column = "S", name = "")
	private String uname;
	
	@ExcelVOAttribute(column = "K", name = "")
	private String phone;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPeName() {
		return peName;
	}

	public void setPeName(String peName) {
		this.peName = peName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFaren() {
		return faren;
	}

	public void setFaren(String faren) {
		this.faren = faren;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@ExcelVOAttribute(column = "U", name = "")
	private String state;
	
	
}
