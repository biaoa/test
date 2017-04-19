package com.linle.entity.enumType;

import com.linle.common.base.IntEnum;

public enum UserType implements IntEnum<UserType>{  
	SYS(0,"系统管理员"),YJDL(1,"一级代理"),WY(2,"物业"),XQ(3,"小区"),
	JM(4,"小区居民"),SZ(5,"社长"),SJ(6,"商家"),EJDL(7,"二级代理"),JMS(8,"加盟商"),YYB(9,"运营部"),
	XQPTYG(10,"小区普通员工"),XQBMFZR(11,"小区部门负责人"),CWB(12,"财务部"),XQHHR(13,"小区合伙人");
	private String name;
	private int code;
	
	private UserType(int code, String name) {
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
	
	public static UserType codeOf(int code) {
		for (UserType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid UserType code: "
				+ code);
	}

	public static UserType nameOf(String name) {
		for (UserType type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid UserType name: "
				+ name);
	}

}
