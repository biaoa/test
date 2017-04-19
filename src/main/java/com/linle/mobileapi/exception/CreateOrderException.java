package com.linle.mobileapi.exception;
/**
 * 我的服务 采纳后创建订单出现异常
 * @author: pangd
 * @time:2015-9-10
 */
public class CreateOrderException extends RuntimeException {

	/**
	 * @time:下午1:31:13
	 * @Author:pangd
	 */
	private static final long serialVersionUID = 1L;
	
	public CreateOrderException() {}
	
	public CreateOrderException(String message) {
		super(message);
	}
}
