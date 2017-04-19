package com.linle.attendanceSpecialDate.mapper;

import java.util.List;

import com.linle.attendanceSpecialDate.model.AttendanceSpecialDate;

import component.BaseMapper;

public interface AttendanceSpecialDateMapper extends  BaseMapper<AttendanceSpecialDate>{

	List<AttendanceSpecialDate> selectByTemplate(Long templateId);

	int deleteAttendanceSpecialDateByTemplate(Long templateId);

	AttendanceSpecialDate toDaySpecialDateByTemplateId(Long templateId);
}