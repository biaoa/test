package com.linle.mobileapi.attendance.model;

import java.util.Date;
import java.util.List;

import com.linle.entity.vo.AttendanceAddressVO;

public class GoAttendance {
	
	private Date onDuty; // 上班时间

	private Date offDuty;// 下班时间

	private List<AttendanceAddressVO> address;// 打卡地址

	private int status; // 0需要打卡 1 不需要打卡
	
	private Long distance; //多少米内考勤有效

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(Date onDuty) {
		this.onDuty = onDuty;
	}

	public Date getOffDuty() {
		return offDuty;
	}

	public void setOffDuty(Date offDuty) {
		this.offDuty = offDuty;
	}

	public List<AttendanceAddressVO> getAddress() {
		return address;
	}

	public void setAddress(List<AttendanceAddressVO> address) {
		this.address = address;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

}
