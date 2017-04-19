package com.linle.mobileapi.shop.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 体现申请请求
 * @date 2016年7月25日下午3:25:39
 */
public class WithdrawDepositRequest extends BaseRequest {

	private static final long serialVersionUID = 423134498226278041L;
	
	@NotNull(message="提现金额错误")
	private BigDecimal amount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
