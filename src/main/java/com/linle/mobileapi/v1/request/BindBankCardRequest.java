package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class BindBankCardRequest extends BaseRequest {

	private static final long serialVersionUID = 5587304483893075874L;
	
	@NotNull(message="银行卡号不能为空")
	private String cardNo;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

}
