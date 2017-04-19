package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

public class MailInfoResponse extends BaseResponse {
	private static final long serialVersionUID = 1L;
	
	private String mail; 

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
