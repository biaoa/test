package com.linle.mobileapi.property.model;

import java.util.Date;

public class PatrolRoomRecordVo {
    private Long id;

    private String checkAddress;

    private String scanAddress;

    private Integer status;

    private String remark;

    private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}