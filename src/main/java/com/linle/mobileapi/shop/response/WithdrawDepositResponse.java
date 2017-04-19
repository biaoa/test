package com.linle.mobileapi.shop.response;

import java.math.BigDecimal;

import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @author pangd
 * @Description 提现响应信息
 * @date 2016年7月29日上午9:15:05
 */
public class WithdrawDepositResponse extends BaseResponse {

	private static final long serialVersionUID = -3855040787649324350L;

	private BigDecimal balance; // 余额

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
