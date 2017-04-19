package com.linle.common.base;

import java.util.HashMap;
import java.util.Map;

import com.linle.mobileapi.base.BaseResponse;

/**
 * 异步返回
 *
 * @author pangd
 */
public class CommonResponseMsg extends BaseResponse {
	
	private static final long serialVersionUID = 8063043201391664524L;
	private int code;
	private String msg;
	 private Map<String, Object> paramsMap = new HashMap<String, Object>();

	public final static CommonResponseMsg LoginSucInstance = new CommonResponseMsg(0, "登录成功", null);
    public final static CommonResponseMsg LoginFailInstance = new CommonResponseMsg(1, "登录失败", null);

    public final static CommonResponseMsg UpdatePermissionSucInstance = new CommonResponseMsg(0, "权限更改成功", null);
    public final static CommonResponseMsg UpdatePermissionFailInstance = new CommonResponseMsg(1, "权限更改失败", null);

    public final static CommonResponseMsg UpdateUserSucInstance = new CommonResponseMsg(0, "用户更改成功", null);
    public final static CommonResponseMsg UpdateUserFailInstance = new CommonResponseMsg(1, "用户更改失败", null);

    public final static CommonResponseMsg RemoveUserSucInstance = new CommonResponseMsg(0, "用户删除成功", null);
    public final static CommonResponseMsg RemoveUserFailInstance = new CommonResponseMsg(1, "用户删除失败", null);

    public final static CommonResponseMsg SaveUserSucInstance = new CommonResponseMsg(0, "用户新增成功", null);
    public final static CommonResponseMsg SaveUserFailInstance = new CommonResponseMsg(0, "用户新增成功", null);

    public final static CommonResponseMsg OldPwdIllegalInstance = new CommonResponseMsg(1, "原密码不正确", null);
    public final static CommonResponseMsg ParaIllegalInstance = new CommonResponseMsg(1, "参数错误", null);
    public final static CommonResponseMsg RegSucInstance = new CommonResponseMsg(0, "注册成功", null);

    public final static CommonResponseMsg UpdatePwdSucInstance = new CommonResponseMsg(0, "密码更改成功", null);
    public final static CommonResponseMsg UpdatePwdFailInstance = new CommonResponseMsg(1, "密码更改失败", null);

    public final static CommonResponseMsg PasswordIllegal = new CommonResponseMsg(1, "密码错误", null);
    public final static CommonResponseMsg UnknownAccount = new CommonResponseMsg(1, "账户不存在", null);
    public final static CommonResponseMsg LockedAccount = new CommonResponseMsg(1, "账户锁定", null);
    public final static CommonResponseMsg NotPermit = new CommonResponseMsg(1, "权限不足", null);
    
    public final static CommonResponseMsg RemoveSucInstance = new CommonResponseMsg(0, "删除成功", null);
    public final static CommonResponseMsg RemoveFailInstance = new CommonResponseMsg(1, "删除失败", null);


    public CommonResponseMsg(int code, String msg, Map<String, Object> param) {
        this.code = code;
        this.msg = msg;
        this.paramsMap = param;
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

}
