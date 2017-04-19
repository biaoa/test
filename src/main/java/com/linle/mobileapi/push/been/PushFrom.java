package com.linle.mobileapi.push.been;

import com.linle.common.base.IntEnum;

public enum PushFrom implements IntEnum<PushType>{
	LINLE_USER(0, "用户端"),LINLE_SHOP(1,"商家端"),LINLE_COMMUNITY(2,"物业端");

	private int code;
	private String name;

	private PushFrom(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static PushFrom codeOf(int code) {
		for (PushFrom type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid PushType code: " + code);
	}

	public static PushFrom nameOf(String name) {
		for (PushFrom type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid PushType name: " + name);
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	@Override
	public int getIntValue() {
		return this.code;
	}

}
