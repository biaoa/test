package com.linle.entity.enumType;

import com.linle.common.base.IntEnum;

public enum UserStatusType implements IntEnum<UserStatusType>{
	normal(0,"正常"),locked(1,"被锁"),deleted(2,"被删除"),timeOut(3,"超时"),refuse(4,"申请被拒绝");
	
	private String name;
	private int code;
	
	private UserStatusType(int code, String name) {
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
	
	public static UserStatusType codeOf(int code) {
		for (UserStatusType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid UserStatusType code: "
				+ code);
	}

	public static UserStatusType nameOf(String name) {
		for (UserStatusType type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid UserStatusType name: "
				+ name);
	}

}
