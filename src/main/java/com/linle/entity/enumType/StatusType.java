package com.linle.entity.enumType;

import com.linle.common.base.IntEnum;

public enum StatusType implements IntEnum<StatusType>{
	normal(0,"正常"),disable(1,"禁用");
	private String name;
	private int code;
	
	private StatusType(int code, String name) {
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
	
	public static StatusType codeOf(int code) {
		for (StatusType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid UserType code: "
				+ code);
	}

	public static StatusType nameOf(String name) {
		for (StatusType type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid UserType name: "
				+ name);
	}

}
