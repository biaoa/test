package com.linle.attendanceAddress.service;

import java.util.List;

import com.linle.attendanceAddress.model.AttendanceAddress;
import com.linle.common.base.BaseService;
import com.linle.entity.vo.AttendanceAddressVO;

public interface AttendanceAddressService extends BaseService<AttendanceAddress> {

	boolean deleteByTemplateId(Long id);
	/**
	 * 
	 * @Description 根据模板ID 获得对应的考勤地址列表
	 * @param templataeId
	 * @return List<AttendanceAddress>
	 * $
	 */
	List<AttendanceAddress> selectByTemplateId(Long templataeId);
	/**
	 * 
	 * @Description 根据考勤模板ID 获得对应的考勤地址集合
	 * @param id
	 * @return List<AttendanceAddressVO>
	 * $
	 */
	List<AttendanceAddressVO> getAttendanceDateAddressByTemplateId(Long templataeId);

}
