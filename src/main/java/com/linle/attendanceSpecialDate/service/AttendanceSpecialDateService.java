package com.linle.attendanceSpecialDate.service;

import java.util.List;

import com.linle.attendanceSpecialDate.model.AttendanceSpecialDate;
import com.linle.common.base.BaseService;

public interface AttendanceSpecialDateService extends BaseService<AttendanceSpecialDate> {
	
	public List<AttendanceSpecialDate> selectByTemplate(Long templateId);
	
	public int deleteAttendanceSpecialDateByTemplate(Long templateId);
	
	//根据考勤模板Id查询今天的特殊打卡时间
	public AttendanceSpecialDate toDaySpecialDateByTemplateId(Long templateId);
}
