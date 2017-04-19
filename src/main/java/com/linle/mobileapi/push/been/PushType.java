package com.linle.mobileapi.push.been;

import com.linle.common.base.IntEnum;

/**
 * @描述:
 * @版权:
 * @公司:
 * @作者:王熙斌
 * @创建时间：2014-6-5 下午3:23:00
 **/
public enum PushType implements IntEnum<PushType> {
	TOPIC_MSG(0, "topic"),//话题
	ORDER_MSG(1,"order"),//订单
	BROADBAND_MSG(2,"broadband"),//宽带
	CABLETELEVISION_MSG(3,"cableTelevision"),//有限电视
	PROPERTYFEE_MSG(4,"propertyFee"),//物业费
	WATER_MSG(5,"water"),//水费
	ELECTRICITY_MSG(6,"electricity"),//电费
	GAS_MSG(7,"gas"),//燃气费
	VOTE_MSG(8,"vote"),//投票
	ORDERREFUND_MSG(9,"orderRefund"),//订单退款
	ORDERDETAIL_MSG(10,"orderDetail"),//订单详情
	ATTENDANCE_MSG(11,"attendance"),//考勤
	COMMUNITYNOTICE_MSG(12,"communityNotice"),//物业通知
	COMMUNITYSUGGESTIONS_MSG(13,"communitySuggestions"),//小区意见反馈
	REPAIR_MSG(14,"repair"); //物业报修的
	
	private int code;
	private String name;

	private PushType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static PushType codeOf(int code) {
		for (PushType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid PushType code: " + code);
	}

	public static PushType nameOf(String name) {
		for (PushType type : values()) {
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
