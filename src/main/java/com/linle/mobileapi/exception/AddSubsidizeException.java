package com.linle.mobileapi.exception;

/**
 * 
 * @author pangd
 * @date 2015年9月23日
 * @Description 申请专利资助异常
 */
public class AddSubsidizeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public AddSubsidizeException() {}
	
	public AddSubsidizeException(String message) {
		super(message);
	}

}
