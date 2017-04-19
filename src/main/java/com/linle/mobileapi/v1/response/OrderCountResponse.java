package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @author pangd
 * @Description 个人中心各种状态的订单数量
 */
public class OrderCountResponse extends BaseResponse {

	private static final long serialVersionUID = 5139216805757935871L;
	
	private int waitHandleCount; //待处理
	
	private int waitPaymentCount;//待付款
	
	private int waitReceiptCount;//待收货 / 已处理
	
	private int watiEstimationCount;//待评价
	
	private int refundsCount;//退款


	public int getWaitReceiptCount() {
		return waitReceiptCount;
	}

	public void setWaitReceiptCount(int waitReceiptCount) {
		this.waitReceiptCount = waitReceiptCount;
	}

	public int getWatiEstimationCount() {
		return watiEstimationCount;
	}

	public void setWatiEstimationCount(int watiEstimationCount) {
		this.watiEstimationCount = watiEstimationCount;
	}

	public int getRefundsCount() {
		return refundsCount;
	}

	public void setRefundsCount(int refundsCount) {
		this.refundsCount = refundsCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getWaitHandleCount() {
		return waitHandleCount;
	}

	public void setWaitHandleCount(int waitHandleCount) {
		this.waitHandleCount = waitHandleCount;
	}

	public int getWaitPaymentCount() {
		return waitPaymentCount;
	}

	public void setWaitPaymentCount(int waitPaymentCount) {
		this.waitPaymentCount = waitPaymentCount;
	}
}
