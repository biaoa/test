package com.linle.mobileapi.v1.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.util.JsonUtil;
import com.linle.mobileapi.base.BaseResponse;

public class ApiResponse extends BaseResponse {

	private static final long serialVersionUID = 2601000735196006961L;
	public static ApiResponse success = new ApiResponse(0, "操作成功");
	public static ApiResponse fail = new ApiResponse(1, "操作失败");

	public static ApiResponse SaveSucInstance = new ApiResponse(0, "新增成功");
	public static ApiResponse SaveFailInstance = new ApiResponse(1, "新增失败");

	public static ApiResponse UpdateSucInstance = new ApiResponse(0, "更改成功");
	public static ApiResponse UpdateFailInstance = new ApiResponse(1, "更改失败");

	public static ApiResponse RemoveSucInstance = new ApiResponse(0, "删除成功");
	public static ApiResponse RemoveFailInstance = new ApiResponse(1, "删除失败");

	

	/**
	 * 返回状态
	 **/
	private int code;
	/** 0成功，非0失败 **/
	/**
	 * 返回信息
	 **/
	private String msg;
	/**
	 * 返回参数
	 **/
	private Map<String, Object> paramsMap = new HashMap<String, Object>();

	public ApiResponse() {
	}

	public ApiResponse(int code, String msg, Map<String, Object> paramsMap) {
		this.code = code;
		this.msg = msg;
		this.paramsMap = paramsMap;
	}
	
	public ApiResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public static ApiResponse getERROR(String s) {
		return new ApiResponse(1, s);
	}

	public static ApiResponse getOK(String s) {
		return new ApiResponse(0, s);
	}
	
}
