package com.linle.mobileapi.v1.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.linle.mobileapi.base.BaseRequest;

public class NameCertificationRequest extends BaseRequest {

	private static final long serialVersionUID = -8675184554195572096L;
	
	@NotEmpty(message="姓名不能为空")
	private String name;

	@NotEmpty(message="证件号不能为空")
    private String cradNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCradNo() {
		return cradNo;
	}

	public void setCradNo(String cradNo) {
		this.cradNo = cradNo;
	}
}
