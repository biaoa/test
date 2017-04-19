package com.linle.entity.enumType;

import com.linle.common.base.IntEnum;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年9月29日
 **/
public enum MessageType implements IntEnum<MessageType> {
	FWDD(1, "服务订单"), ZX(2, "创业咨询"), PLAN(2, "任务");
	private int code;
	private String name;

	private MessageType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static MessageType codeOf(int code) {
		for (MessageType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid MessageType code: "
				+ code);
	}

	public static MessageType nameOf(String name) {
		for (MessageType type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid MessageType name: "
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
