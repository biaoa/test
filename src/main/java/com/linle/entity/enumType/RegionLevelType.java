package com.linle.entity.enumType;

import com.linle.common.base.IntEnum;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月20日
 **/
public enum RegionLevelType implements IntEnum<RegionLevelType>{
	province(1, "省"), city(2, "市"), county(3, "县/区");
	
	private int code;
	private String name;

	private RegionLevelType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static RegionLevelType codeOf(int code) {
		for (RegionLevelType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid RegionLevelType code: "
				+ code);
	}

	public static RegionLevelType nameOf(String name) {
		for (RegionLevelType type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid RegionLevelType name: "
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
