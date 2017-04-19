package com.linle.entity.enumType;

import com.linle.common.base.IntEnum;
public enum MessageStatus implements IntEnum<MessageStatus> {
	unreceive(0,"未接收"),receive(1, "接收");
	private int code;
	private String name;

	private MessageStatus(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static MessageStatus codeOf(int code) {
		for (MessageStatus type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid MessageStatus code: "
				+ code);
	}

	public static MessageStatus nameOf(String name) {
		for (MessageStatus type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid MessageStatus name: "
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
