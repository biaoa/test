package com.linle.mobileapi.attendance.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.mobileapi.base.BaseRequest;

public class AttendanceRequest extends BaseRequest {

	private static final long serialVersionUID = 5994532798176358724L;

	@NotEmpty(message = "实际打卡位置不能为空")
	private String address; // 实际打卡位置

	private String description;// 补充说明

	private List<CommonsMultipartFile> imgs; // 图片

	@NotEmpty(message = "考勤位置不能为空")
	private String attendanceAddress; // 考勤位置

	@NotNull(message = "考勤类型不能为空")
	private Integer type; // 考勤类型 0 上午 1 下午

	@NotNull(message = "地址状态不能为空")
	private Integer addressStatus; // 0正常 1 异常
	
	private Long folderId; //文件ID

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CommonsMultipartFile> getImgs() {
		return imgs;
	}

	public void setImgs(List<CommonsMultipartFile> imgs) {
		this.imgs = imgs;
	}

	public String getAttendanceAddress() {
		return attendanceAddress;
	}

	public void setAttendanceAddress(String attendanceAddress) {
		this.attendanceAddress = attendanceAddress;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAddressStatus() {
		return addressStatus;
	}

	public void setAddressStatus(Integer addressStatus) {
		this.addressStatus = addressStatus;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
}
