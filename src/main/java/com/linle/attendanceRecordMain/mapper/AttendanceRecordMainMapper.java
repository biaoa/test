package com.linle.attendanceRecordMain.mapper;

import java.util.List;
import java.util.Map;

import com.linle.attendanceRecordMain.model.AttendanceRecordMain;
import com.linle.attendanceStatistics.model.DayStatisticsVo;
import com.linle.attendanceStatistics.model.MonthStatisticsVo;
import com.linle.common.util.Page;
import com.linle.mobileapi.attendance.model.SomeMonthAttenVO;

import component.BaseMapper;

public interface AttendanceRecordMainMapper extends BaseMapper<AttendanceRecordMain> {

	AttendanceRecordMain selectTodayMainRecordByUserId(Long uid);

	// 修改主记录状态
	int updateMainRecordStatus(AttendanceRecordMain main);

	// 根据年月日 打卡类型 用户ID 获得某一天的打卡主记录详情
	AttendanceRecordMain selectSomedayMainRecordByUserId(Map<String, Object> map);
	
	//根据年 月 用户ID 获得某个月的考勤记录
	List<SomeMonthAttenVO> getSomeMonthAttendanceByUserId(Map<String, Object> map);
	//后台考勤按日分析
	List<DayStatisticsVo> dayStatistics(Page<DayStatisticsVo> page);
	//后台考勤按月分析
	List<MonthStatisticsVo> monthStatistics(Page<MonthStatisticsVo> page);
	
	List<DayStatisticsVo> exportDayStatistics(Map<String, Object> params);
	
	List<MonthStatisticsVo> exportMonthStatistics(Map<String, Object> params);

}