package com.linle.mobileapi.base;

import java.io.Serializable;
import java.util.Map;

public  class BaseResponse implements Serializable {
	
	public static BaseResponse PhoneNumberWrongInstance = new BaseResponse(1, "手机号码格式错误");
	
	public static BaseResponse SMSTypeISNotEmpty = new BaseResponse(1, "短信类型不能为空");
	
	public static BaseResponse PhoneExist = new BaseResponse(1, "该号码已注册");
	
	public static BaseResponse RegisterSuccess = new BaseResponse(0,"注册成功");
	
	public static BaseResponse RegisterFail = new BaseResponse(1, "注册失败");
	
	public static BaseResponse UserNotExist = new BaseResponse(1, "用户不存在");
	
	public static BaseResponse UserExist = new BaseResponse(0, "用户名存在");
	
	public static BaseResponse ForgotPasswordSuccess = new BaseResponse(0, "找回密码成功");
	
	public static BaseResponse ModifyPasswordSuccess = new BaseResponse(0, "修改密码成功");
	
	public static BaseResponse AddSuccess = new BaseResponse(0, "添加成功");
	
	public static BaseResponse AddFail = new BaseResponse(1, "添加失败");
	
	public static BaseResponse ServerException = new BaseResponse(9, "服务异常");
	
	public static BaseResponse OperateSuccess = new BaseResponse(0, "操作成功");
	
	public static BaseResponse OperateFail = new BaseResponse(1, "操作失败");
	
	public static BaseResponse PleaseSignIn = new BaseResponse(2, "请登录");
	
	public static BaseResponse CardExist = new BaseResponse(1, "卡片已经绑定");
	
	public static BaseResponse CardError = new BaseResponse(1, "卡号信息错误");
	
	public static BaseResponse NameExist = new BaseResponse(1, "实名认证存在");
	
	public static BaseResponse NameNotExist = new BaseResponse(1, "实名认证不存在");
	
	public static BaseResponse NotNeedLogin = new BaseResponse(0, "不需要登陆");
	
	public static BaseResponse CommentSuccess = new BaseResponse(0, "评论成功");
	
	public static BaseResponse JSONPARSEEXCEPTION = new BaseResponse(1, "JSON解析异常");
	
	private static final long serialVersionUID = 5866147335641254679L;
	private int code;
	private String msg="";
	
	private Map<String, Object> result;

	public BaseResponse() {
		super();
	}

	public BaseResponse(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public BaseResponse(int code, String msg,Map<String, Object> result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result=result;
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

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	
	
}
