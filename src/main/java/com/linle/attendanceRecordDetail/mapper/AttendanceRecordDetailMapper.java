package com.linle.attendanceRecordDetail.mapper;

import java.util.List;
import java.util.Map;

import com.linle.attendanceRecordDetail.model.AttendanceRecordDetail;
import com.linle.attendanceRecordDetail.model.WEBAttendanceInfoMode;
import com.linle.mobileapi.attendance.model.AttendanceDetail;
import com.linle.mobileapi.attendance.model.AttendanceInfoMode;

import component.BaseMapper;

public interface AttendanceRecordDetailMapper extends BaseMapper<AttendanceRecordDetail>{

	AttendanceDetail selectAttendanceRecordDetail(Map<String, Object> map);

	AttendanceDetail selectDetailByMainIdAndType(Map<String, Object> map);
	
	//根据主记录ID 查询子考勤记录
	List<AttendanceRecordDetail> selectAttendanceRecordDetailByMainId(Long mainId);
	
	AttendanceInfoMode selectInfoByMainIdAndType(Map<String, Object> map);

	List<WEBAttendanceInfoMode> getSomeDayInfo(Long mainId);
  
}