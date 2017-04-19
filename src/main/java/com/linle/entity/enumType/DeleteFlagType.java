package com.linle.entity.enumType;

import com.linle.common.base.IntEnum;
/**
 * @Description: normal(0, "正常"), delete(1, "删除")
 * @author: pangd
 * @time:2015-10-10
 */
public enum DeleteFlagType implements IntEnum<DeleteFlagType>{
	normal(0, "正常"), delete(1, "删除");
	private int code;
	private String name;

	private DeleteFlagType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static DeleteFlagType codeOf(int code) {
		for (DeleteFlagType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid DeleteFlagType code: "
				+ code);
	}

	public static DeleteFlagType nameOf(String name) {
		for (DeleteFlagType type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid DeleteFlagType name: "
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
