package com.linle.attendanceStatistics.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.attendanceRecordDetail.model.WEBAttendanceInfoMode;
import com.linle.attendanceRecordDetail.service.AttendanceRecordDetailService;
import com.linle.attendanceRecordMain.service.AttendanceRecordMainService;
import com.linle.attendanceStatistics.model.DayStatisticsVo;
import com.linle.attendanceStatistics.model.MonthStatisticsVo;
import com.linle.common.base.BaseController;
import com.linle.common.util.ExcelToolkit;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.communityDepartment.service.CommunityDepartmentService;
import com.linle.entity.sys.CommunityDepartment;


@Controller
@RequestMapping("/attendanceStatistics")
public class AttendanceStatisticsController extends BaseController {

	@Autowired
	private CommunityDepartmentService departmentService;

	@Autowired
	private AttendanceRecordMainService mainService;// 主打卡记录
	
	@Autowired
	private AttendanceRecordDetailService detailService;
	
	// 按日统计
	@RequiresPermissions("attendance_record")
	@RequestMapping("/dayStatistics")
	public String dayStatistics(Model model, Integer pageNo, String name, String beginDate, String endDate,
			Integer status, Long departmentId) {
		Long communityId = getCommunity().getId();
		try {
			if (name != null) {
				name = new String(name.getBytes("iso8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Page<DayStatisticsVo> page = new Page<>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		if (status == null) {
			status = -1;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("status", status);
		params.put("departmentId", departmentId);
		params.put("communityId", communityId);
		page.setParams(params);
		mainService.dayStatistics(page);
		
		// 小区的部门集合
		List<CommunityDepartment> departmentList = departmentService.getAllDepartment(communityId);
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("name", name);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("pagelist", page);
		return "attendanceStatistics/attendanceStatistics_day";
	}
	// 按月统计
	@RequiresPermissions("attendance_record")
	@RequestMapping("/monthStatistics")
	public String monthStatistics(Model model,Integer pageNo, String name, String beginDate, String endDate,
			Integer status, Long departmentId){
		try {
			if (name != null) {
				name = new String(name.getBytes("iso8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if(status==null){
			status=-1;
		}
		Long communityId = getCommunity().getId();
		Page<MonthStatisticsVo> page = new Page<>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("departmentId", departmentId);
		params.put("communityId", communityId);
		params.put("status", status);
		page.setParams(params);
		mainService.monthStatistics(page);
		// 小区的部门集合
		List<CommunityDepartment> departmentList = departmentService.getAllDepartment(communityId);
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("name", name);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("status", status);
		model.addAttribute("pagelist", page);
		return "attendanceStatistics/attendanceStatistics_month";
	}
	
	
	//某天的考勤详情
	@ResponseBody
	@RequestMapping(value="/someDayInfo",method=RequestMethod.POST)
	public List<WEBAttendanceInfoMode> someDayInfo(Long mainId){
		return detailService.getSomeDayInfo(mainId);
	}
	
	//导出excel day
	@RequestMapping(value="exportDayStatistics")
	public void exportDayStatistics(String name, String beginDate, String endDate,Integer status, Long departmentId
			,HttpServletResponse response){
		try {
			if (name != null) {
				name = new String(name.getBytes("iso8859-1"), "utf-8");
			}
			Community community =  getCommunity();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", name);
			params.put("beginDate", beginDate);
			params.put("endDate", endDate);
			params.put("departmentId", departmentId);
			params.put("communityId", community.getId());
			params.put("status", status);
			List<DayStatisticsVo> data = mainService.exportDayStatistics(params);
			for (DayStatisticsVo dayStatisticsVo : data) {
				dayStatisticsVo.setShowDate(sdf.format(dayStatisticsVo.getData()));
				String str = "";
				if(dayStatisticsVo.getStatus()==0){
					str="正常";
				}else if(dayStatisticsVo.getStatus()==1){
					str="异常";
				}else if(dayStatisticsVo.getStatus()==2){
					str="节假日";
				}
				dayStatisticsVo.setShowDate(sdf2.format(dayStatisticsVo.getData()));
				dayStatisticsVo.setShowStatus(str);
				if(dayStatisticsVo.getOnDutyDate()!=null){
					dayStatisticsVo.setShowonDutyDate(sdf.format(dayStatisticsVo.getOnDutyDate()));
				}else{
					dayStatisticsVo.setShowonDutyDate("无记录");
				}
				if(dayStatisticsVo.getOffDutyDate()!=null){
					dayStatisticsVo.setShowoffDutyDate(sdf.format(dayStatisticsVo.getOffDutyDate()));
				}else{
					dayStatisticsVo.setShowoffDutyDate("无记录");
				}
			}
			String msg =sdf2.format(new Date());
			if(StringUtil.isNotNull(beginDate)&&StringUtil.isNotNull(endDate)){
				msg=beginDate+"至"+endDate;
			}else if(StringUtil.isNotNull(beginDate)&&StringUtil.isNull(endDate)){
				msg=beginDate;
			}else if(StringUtil.isNull(beginDate)&&StringUtil.isNotNull(endDate)){
				msg=endDate;
			}
			response.setContentType("application/octet-stream");
		    response.addHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(community.getName()+msg+"考勤统计.xls", "UTF-8"));
			ExcelToolkit<DayStatisticsVo> tool = new ExcelToolkit<>(DayStatisticsVo.class);
			tool.exportExcel(data, "考勤统计", data.size()+1, response.getOutputStream(),community.getName()+msg+"考勤统计");
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
	
	}
	
	//导出excel month
	@RequestMapping(value="exportMonthStatistics")
	public void exportMonthStatistics( String name, String beginDate, String endDate,Integer status, Long departmentId
			,HttpServletResponse response){
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			if (name != null) {
				name = new String(name.getBytes("iso8859-1"), "utf-8");
			}
			Community community =  getCommunity();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", name);
			params.put("beginDate", beginDate);
			params.put("endDate", endDate);
			params.put("departmentId", departmentId);
			params.put("communityId", community.getId());
			params.put("status", status);
			List<MonthStatisticsVo> data = mainService.exportMonthStatistics(params);
			String msg =sdf2.format(new Date());
			if(StringUtil.isNotNull(beginDate)&&StringUtil.isNotNull(endDate)){
				msg=beginDate+"至"+endDate;
			}else if(StringUtil.isNotNull(beginDate)&&StringUtil.isNull(endDate)){
				msg=beginDate;
			}else if(StringUtil.isNull(beginDate)&&StringUtil.isNotNull(endDate)){
				msg=endDate;
			}
			response.setContentType("application/octet-stream");
		    response.addHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(community.getName()+msg+"考勤统计.xls", "UTF-8"));
			ExcelToolkit<MonthStatisticsVo> tool = new ExcelToolkit<>(MonthStatisticsVo.class);
			tool.exportExcel(data, "考勤统计", data.size()+1, response.getOutputStream(),community.getName()+msg+"考勤统计");
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
	
	}
	// 统计图表
}
