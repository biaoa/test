package com.linle.mobileapi.exception;
/**
 * 支付出现异常
 * @author: pangd
 * @time:2015-9-9
 */
public class PayOrderException extends RuntimeException {

	/**
	 * @time:下午1:31:13
	 * @Author:pangd
	 */
	private static final long serialVersionUID = 1L;
	
	public PayOrderException() {}
	
	public PayOrderException(String message) {
		super(message);
	}
}
