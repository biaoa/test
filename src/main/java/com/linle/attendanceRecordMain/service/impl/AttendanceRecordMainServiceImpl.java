package com.linle.attendanceRecordMain.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.attendanceRecordDetail.service.AttendanceRecordDetailService;
import com.linle.attendanceRecordMain.mapper.AttendanceRecordMainMapper;
import com.linle.attendanceRecordMain.model.AttendanceRecordMain;
import com.linle.attendanceRecordMain.service.AttendanceRecordMainService;
import com.linle.attendanceStatistics.model.DayStatisticsVo;
import com.linle.attendanceStatistics.model.MonthStatisticsVo;
import com.linle.attendanceTemplate.model.AttendanceTemplate;
import com.linle.attendanceTemplate.service.AttendanceTemplateService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateUtil;
import com.linle.common.util.Page;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.attendance.model.SomeMonthAttenVO;
import com.linle.mobileapi.attendance.request.AttendanceRequest;
import com.linle.mobileapi.base.BaseResponse;

@Service
@Transactional
public class AttendanceRecordMainServiceImpl extends CommonServiceAdpter<AttendanceRecordMain>
		implements AttendanceRecordMainService {
	
	@Autowired
	private AttendanceRecordMainMapper mapper;
	
	@Autowired
	private AttendanceTemplateService templateService;
	
	@Autowired
	private AttendanceRecordDetailService detailService;

	@Override
	public AttendanceRecordMain selectTodayMainRecordByUserId(Long uid) {
		return mapper.selectTodayMainRecordByUserId(uid);
	}

	@Override
	public BaseResponse attendance(Users userinfo, AttendanceRequest req) {
		//获得用户的考勤模板信息
		AttendanceTemplate template = templateService.selectTemplateByUserId(userinfo);
		if (template == null) {
			template = templateService.selectDefaultTemplate(userinfo);
		}
		//获得用户今天的主考勤记录
		AttendanceRecordMain main = mapper.selectTodayMainRecordByUserId(userinfo.getId());
		if (main==null) { 
			//如果没有
			//1插入主记录
			main = new AttendanceRecordMain();
			main.setUid(userinfo.getId());
			main.setTemplateId(template.getId());
			main.setStatus(1);//默认异常
			main.setCreateDate(new Date());
			mapper.insertSelective(main);
			//2插入子记录
			detailService.insertDetail(main,req);
			return BaseResponse.OperateSuccess;
		}else{
			//如果有
			//1判断是否重复考勤
				if(detailService.selectDetailByMainIdAndType(main.getId(), req.getType())!=null){
					//重复 返回提示信息
					return new BaseResponse(1, "已经考勤过了");
				}
				//不重复  插入子记录
				detailService.insertDetail(main,req);
				return BaseResponse.OperateSuccess;
		}
	}

	@Override
	public boolean updateMainRecordStatus(AttendanceRecordMain main) {
		return mapper.updateMainRecordStatus(main)>0;
	}

	@Override
	public AttendanceRecordMain selectSomedayMainRecordByUserId(Date date,Long uid) {
		Map<String, Object> map = new HashMap<>();
		map.put("year", DateUtil.getYear(date));
		map.put("month", DateUtil.getMonth(date));
		map.put("day", DateUtil.getDay(date));
		map.put("uid", uid);
		return mapper.selectSomedayMainRecordByUserId(map);
	}

	@Override
	public List<SomeMonthAttenVO> getSomeMonthAttendanceByUserId(int year,int month, Long uid,AttendanceTemplate template) {
		Map<String, Object> map = new HashMap<>();
		map.put("year", year);
		map.put("month", month);
		map.put("uid", uid);
		List<SomeMonthAttenVO> list = mapper.getSomeMonthAttendanceByUserId(map);
		for (SomeMonthAttenVO someMonthAttenVO : list) {
			if(someMonthAttenVO.getStatus()==null){
				//判断今天是否需要考勤 0 需要 1 不要
				int needAttendance = templateService.needAttendanceByUserId(someMonthAttenVO.getDate(),template, uid);
				if(needAttendance==0){
					//需要打卡
					someMonthAttenVO.setStatus(1);
					someMonthAttenVO.setOnDutyDate(template.getOnDuty());
					someMonthAttenVO.setOffDutyDate(template.getOffDuty());
				}else{
				   //不需要打卡
					someMonthAttenVO.setStatus(2);
				}
			}
		}
		return list;
	}

	@Override
	public Page<DayStatisticsVo> dayStatistics(Page<DayStatisticsVo> page) {
		List<DayStatisticsVo> list = mapper.dayStatistics(page);
		for (DayStatisticsVo dayStatisticsVo : list) {
			if(dayStatisticsVo.getStatus()==null){
				//判断今天是否需要考勤 0 需要 1 不要
				AttendanceTemplate template =templateService.selectByPrimaryKey(dayStatisticsVo.getTemplateId());
				int needAttendance = templateService.needAttendanceByUserId(dayStatisticsVo.getData(),template, dayStatisticsVo.getUid());
				if(needAttendance==0){
					//需要打卡
					dayStatisticsVo.setStatus(1);
				}else{
				   //不需要打卡
					dayStatisticsVo.setStatus(2);
				}
			}
		}
		page.setResults(list);
		return page;
	}

	@Override
	public Page<MonthStatisticsVo> monthStatistics(Page<MonthStatisticsVo> page) {
		List<MonthStatisticsVo> list = mapper.monthStatistics(page);
		page.setResults(list);
		return page;
	}

	@Override
	public List<DayStatisticsVo> exportDayStatistics(Map<String, Object> params) {
		List<DayStatisticsVo> list = mapper.exportDayStatistics(params);
		for (DayStatisticsVo dayStatisticsVo : list) {
			if(dayStatisticsVo.getStatus()==null){
				//判断今天是否需要考勤 0 需要 1 不要
				AttendanceTemplate template =templateService.selectByPrimaryKey(dayStatisticsVo.getTemplateId());
				int needAttendance = templateService.needAttendanceByUserId(dayStatisticsVo.getData(),template, dayStatisticsVo.getUid());
				if(needAttendance==0){
					//需要打卡
					dayStatisticsVo.setStatus(1);
				}else{
				   //不需要打卡
					dayStatisticsVo.setStatus(2);
				}
			}
		}
		return list;
	}

	@Override
	public List<MonthStatisticsVo> exportMonthStatistics(Map<String, Object> params) {
		return mapper.exportMonthStatistics(params);
	}

}
