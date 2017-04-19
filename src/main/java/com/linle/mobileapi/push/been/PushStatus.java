package com.linle.mobileapi.push.been;

import com.linle.common.base.IntEnum;

/**
 * @描述:推送been
 * @作者:杨立忠
 * @创建时间：2015年10月9日
 **/
public enum PushStatus implements IntEnum<PushStatus>{
	cancel(-1,"取消"),unpush(0,"未推送"),push(1, "已推送");
	private int code;
	private String name;

	private PushStatus(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static PushStatus codeOf(int code) {
		for (PushStatus type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid PushStatus code: "
				+ code);
	}

	public static PushStatus nameOf(String name) {
		for (PushStatus type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid PushStatus name: "
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
