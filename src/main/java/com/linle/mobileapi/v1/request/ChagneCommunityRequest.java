package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;
/**
 * 
 * @author pangd
 * @Description 修改小区请求
 */
public class ChagneCommunityRequest extends BaseRequest {

	private static final long serialVersionUID = -9213915176272897245L;
	
	@NotNull(message="请选择小区")
	private Long communityId; //小区ID
	
	@NotNull(message="详细地址不能为空")
	private Long addressDetails;//地址补充

	public Long getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(Long addressDetails) {
		this.addressDetails = addressDetails;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

}
