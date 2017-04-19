package com.linle.mobileapi.property.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.linle.mobileapi.base.BaseRequest;

public class PatrolRoomRecordOperateRequest extends BaseRequest {
	
	private long id;
	
	@NotEmpty(message="检查地点不能为空")
	private String checkAddress;
	
	@NotEmpty(message="定位地点不能为空")
    private String scanAddress;
	
	@NotNull(message="异常状态不能为空")
    private int status;
	
    private String remark;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCheckAddress() {
		return checkAddress;
	}

	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}

	public String getScanAddress() {
		return scanAddress;
	}

	public void setScanAddress(String scanAddress) {
		this.scanAddress = scanAddress;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
