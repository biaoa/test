package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class RepairListRequest extends BaseRequest {

	private static final long serialVersionUID = 3567097752434729806L;
	
	@NotNull(message="报修类型不能为空")
	private int parentTypeId;

	public int getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(int parentTypeId) {
		this.parentTypeId = parentTypeId;
	}
	
	

}
