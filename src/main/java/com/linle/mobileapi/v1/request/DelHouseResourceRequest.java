package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class DelHouseResourceRequest extends BaseRequest {

	private static final long serialVersionUID = -1792342164257033324L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
