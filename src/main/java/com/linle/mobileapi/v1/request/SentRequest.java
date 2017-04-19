package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class SentRequest extends BaseRequest {
	
	private static final long serialVersionUID = 5348042943889134015L;
	
	@NotNull(message="寄件人地址不能为空")
	private String sentAddress;

	@NotNull(message="收件人名称不能为空")
	private String addresseeName;
	
	@NotNull(message="收件人电话不能为空")
	private String addresseePhone;
	
	@NotNull(message="收件人地址不能为空")
	private String addresseeAddress;
	
	@NotNull(message="快递类型不能为空")
	private Long expressId;

	private String beginDate;
	
	private String endDate;
	
	private String remark;

	public String getAddresseeName() {
		return addresseeName;
	}

	public void setAddresseeName(String addresseeName) {
		this.addresseeName = addresseeName;
	}

	public String getAddresseePhone() {
		return addresseePhone;
	}

	public void setAddresseePhone(String addresseePhone) {
		this.addresseePhone = addresseePhone;
	}

	public String getAddresseeAddress() {
		return addresseeAddress;
	}

	public void setAddresseeAddress(String addresseeAddress) {
		this.addresseeAddress = addresseeAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSentAddress() {
		return sentAddress;
	}

	public void setSentAddress(String sentAddress) {
		this.sentAddress = sentAddress;
	}

	public Long getExpressId() {
		return expressId;
	}

	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
	
}
