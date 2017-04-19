package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class UserInfoRequest extends BaseRequest {

	private static final long serialVersionUID = 2507502372391322865L;
	
	private Long uid;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}
