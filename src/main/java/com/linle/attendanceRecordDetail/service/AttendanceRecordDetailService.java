package com.linle.attendanceRecordDetail.service;

import java.util.List;

import com.linle.attendanceRecordDetail.model.AttendanceRecordDetail;
import com.linle.attendanceRecordDetail.model.WEBAttendanceInfoMode;
import com.linle.attendanceRecordMain.model.AttendanceRecordMain;
import com.linle.common.base.BaseService;
import com.linle.mobileapi.attendance.model.AttendanceDetail;
import com.linle.mobileapi.attendance.model.AttendanceInfoMode;
import com.linle.mobileapi.attendance.request.AttendanceRequest;

public interface AttendanceRecordDetailService extends BaseService<AttendanceRecordDetail> {
	
	/**
	 * 
	 * @Description 根据考勤主记录Id 获得某次考勤的详情
	 * @param mainId 主考勤记录Id
	 * @param type 0 上午 1 下午
	 * @return AttendanceDetail
	 * $
	 */
	public AttendanceDetail selectAttendanceRecordDetail(Long mainId,int type);
	
	/**
	 * 
	 * @Description 插入考勤记录
	 * @param id
	 * @param req
	 * @return boolean
	 * $
	 */
	public boolean insertDetail(AttendanceRecordMain main, AttendanceRequest req);
	
	/**
	 * 
	 * @Description 根据主类型ID 和 考勤类型 去查询考勤详情
	 * @param mainId
	 * @param type
	 * @return AttendanceDetail
	 * $
	 */
	public AttendanceDetail selectDetailByMainIdAndType(Long mainId,Integer type);
	
	//根据主类型ID 和 考勤类型 去查询考勤详情
	public AttendanceInfoMode selectInfoByMainIdAndTypeforMode(Long id, Integer type);
	
	
	public List<WEBAttendanceInfoMode> getSomeDayInfo(Long mainId);
	
}
