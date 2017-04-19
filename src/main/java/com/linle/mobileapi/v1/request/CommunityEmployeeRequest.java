package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class CommunityEmployeeRequest extends BaseRequest {

	private static final long serialVersionUID = 7607017135362780677L;
	
	@NotNull(message="部门ID不能为空")
	private Long departmentId;

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}
