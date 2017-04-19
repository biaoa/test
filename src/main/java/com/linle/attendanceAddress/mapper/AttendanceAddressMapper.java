package com.linle.attendanceAddress.mapper;

import java.util.List;

import com.linle.attendanceAddress.model.AttendanceAddress;
import com.linle.entity.vo.AttendanceAddressVO;

import component.BaseMapper;

public interface AttendanceAddressMapper extends BaseMapper<AttendanceAddress>{

	int deleteByTemplateId(Long templateId);

	List<AttendanceAddress> selectByTemplateId(Long templataeId);

	List<AttendanceAddressVO> getAttendanceDateAddressByTemplateId(Long templataeId);
}