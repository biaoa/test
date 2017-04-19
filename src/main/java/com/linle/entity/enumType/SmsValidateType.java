package com.linle.entity.enumType;

import com.linle.common.base.IntEnum;

public enum SmsValidateType implements IntEnum<SmsValidateType> {
	registe(1, "注册"), forgotPassword(2, "忘记密码"), changePhone(3, "修改手机号码");

	private int code;
	private String name;

	private SmsValidateType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static SmsValidateType codeOf(int code) {
		for (SmsValidateType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid SmsValidateType code: "
				+ code);
	}

	public static SmsValidateType nameOf(String name) {
		for (SmsValidateType type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid SmsValidateType name: "
				+ name);
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
