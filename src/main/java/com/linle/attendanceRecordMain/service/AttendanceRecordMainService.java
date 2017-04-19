package com.linle.attendanceRecordMain.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.linle.attendanceRecordMain.model.AttendanceRecordMain;
import com.linle.attendanceStatistics.model.DayStatisticsVo;
import com.linle.attendanceStatistics.model.MonthStatisticsVo;
import com.linle.attendanceTemplate.model.AttendanceTemplate;
import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.attendance.model.SomeMonthAttenVO;
import com.linle.mobileapi.attendance.request.AttendanceRequest;
import com.linle.mobileapi.base.BaseResponse;

public interface AttendanceRecordMainService extends BaseService<AttendanceRecordMain> {

	/**
	 * 
	 * @Description 根据用户Id 获得今天的主考勤记录
	 * @param uid
	 * @return AttendanceRecordMain $
	 */
	AttendanceRecordMain selectTodayMainRecordByUserId(Long uid);

	/**
	 * 
	 * @Description 移动端考勤
	 * @param userinfo
	 * @param req
	 * @return boolean $
	 */
	BaseResponse attendance(Users userinfo, AttendanceRequest req);

	/**
	 * 
	 * @Description 修改主考勤记录的状态
	 * @param main
	 * @return boolean $
	 */
	boolean updateMainRecordStatus(AttendanceRecordMain main);

	AttendanceRecordMain selectSomedayMainRecordByUserId(Date date,Long uid);
	
	/**
	 * 
	 * @Description 获得某个员工 某个月的考勤记录
	 * @param req
	 * @param uid
	 * @return List<SomeMonthAttenVO>
	 * $
	 */
	List<SomeMonthAttenVO> getSomeMonthAttendanceByUserId(int year,int month, Long uid,AttendanceTemplate template);

	Page<DayStatisticsVo> dayStatistics(Page<DayStatisticsVo> page);

	Page<MonthStatisticsVo> monthStatistics(Page<MonthStatisticsVo> page);
	//导出日报表excel用到的查询
	List<DayStatisticsVo> exportDayStatistics(Map<String, Object> params);
	
	//导出月报表excel
	List<MonthStatisticsVo> exportMonthStatistics(Map<String, Object> params);

}
