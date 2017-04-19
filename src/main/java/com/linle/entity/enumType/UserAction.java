package com.linle.entity.enumType;

import com.linle.common.base.IntEnum;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/
public enum UserAction implements IntEnum<UserAction>{
	login(0,"登入"),logout(1,"登出");
	
	private String name;
	private int code;
	
	private UserAction(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public int getCode() {
		return code;
	}

	@Override
	public int getIntValue() {
		return this.code;
	}
	
	public static UserAction codeOf(int code) {
		for (UserAction type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid UserStatusType code: "
				+ code);
	}

	public static UserAction nameOf(String name) {
		for (UserAction type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid UserStatusType name: "
				+ name);
	}
}
