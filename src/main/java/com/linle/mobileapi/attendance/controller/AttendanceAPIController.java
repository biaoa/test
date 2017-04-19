package com.linle.mobileapi.attendance.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.attendanceAddress.service.AttendanceAddressService;
import com.linle.attendanceRecordDetail.service.AttendanceRecordDetailService;
import com.linle.attendanceRecordMain.model.AttendanceRecordMain;
import com.linle.attendanceRecordMain.service.AttendanceRecordMainService;
import com.linle.attendanceTemplate.model.AttendanceTemplate;
import com.linle.attendanceTemplate.service.AttendanceTemplateService;
import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.common.util.SysConfig;
import com.linle.entity.sys.Users;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.attendance.model.AttendanceInfoMode;
import com.linle.mobileapi.attendance.model.GoAttendance;
import com.linle.mobileapi.attendance.request.AttendanceInfoRequest;
import com.linle.mobileapi.attendance.request.AttendanceRequest;
import com.linle.mobileapi.attendance.request.GoAttendanceRequest;
import com.linle.mobileapi.attendance.request.SomeMonthAttendanceRequest;
import com.linle.mobileapi.attendance.response.AttendanceInfoResponse;
import com.linle.mobileapi.attendance.response.GoAttendanceResponse;
import com.linle.mobileapi.attendance.response.SomeMonthAttendanceResponse;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;

@Controller
@RequestMapping("/api/1/property")
public class AttendanceAPIController extends BaseController {

	@Autowired
	private AttendanceTemplateService templateService; // 模板

	@Autowired
	private AttendanceRecordMainService mainService;// 主打卡记录

	@Autowired
	private AttendanceRecordDetailService detailService;// 打卡详情

	@Autowired
	private AttendanceAddressService addressService;

	// 进入打卡界面
	@RequestMapping(value = "/enterAttendance", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse goAttendance(GoAttendanceRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				GoAttendanceResponse res = new GoAttendanceResponse();
				GoAttendance basicInfo = new GoAttendance(); // 考勤基本信息
				Users userinfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				// 1 获得用户对应的考勤模板
				AttendanceTemplate template = templateService.selectTemplateByUserId(userinfo);
				if (template == null) {
					template = templateService.selectDefaultTemplate(userinfo);
				}
				// 是否需要打卡
				Date date = new Date();
				int needAttendance = templateService.needAttendanceByUserId(date,template, userinfo.getId());
				basicInfo.setDistance((long) template.getDistance());
				basicInfo.setOnDuty(DateUtil.attendanceDateConvert(template.getOnDuty()));
				basicInfo.setOffDuty(DateUtil.attendanceDateConvert(template.getOffDuty()));
				basicInfo.setAddress(addressService.getAttendanceDateAddressByTemplateId(template.getId()));
				basicInfo.setStatus(needAttendance);
				res.setBasicInfo(basicInfo);
				// 获得用户今天的主考勤记录
				AttendanceRecordMain mainRecord = mainService.selectTodayMainRecordByUserId(userinfo.getId());
				if (mainRecord != null) {
					res.setOnDutyInfo(detailService.selectAttendanceRecordDetail(mainRecord.getId(), 0)); // 上班打卡信息
					res.setOffDutyInfo(detailService.selectAttendanceRecordDetail(mainRecord.getId(), 1));// 下班打卡信息
				}
				res.setPropertyLogo(userinfo.getCommunity().getPropertyCompany().getLogo());
				res.setCode(0);
				res.setMsg("获取成功");
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 打卡
	@RequestMapping(value = "/attendance", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse attendance(@Valid AttendanceRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users userinfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				if (req.getImgs() !=null && !req.getImgs().isEmpty()) {
					Long folderId = doFile(req.getImgs(), servletRequest, SysConfig.ATTENDANCE_FOLDER);
					req.setFolderId(folderId);
				}
				return mainService.attendance(userinfo, req);
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 打卡详情
	@RequestMapping(value = "/attendanceInfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse attendanceInfo(@Valid AttendanceInfoRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users userinfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				AttendanceInfoResponse res = new AttendanceInfoResponse();
				// 先获得今天的主记录
				Date date = new Date(req.getDate());
				AttendanceRecordMain main = mainService.selectSomedayMainRecordByUserId(date, userinfo.getId());
				if (main != null) {
					AttendanceInfoMode mode = detailService.selectInfoByMainIdAndTypeforMode(main.getId(), req.getType());
					res.setInfo(mode);
				}
				res.setCode(0);
				res.setMsg("获取成功");
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 某一个月的打卡记录
	@RequestMapping(value = "/someMonthAttendance", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse someMonthAttendance(@Valid SomeMonthAttendanceRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users userinfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				//获得用户对应的考勤模板
				//FIXME 这里存在一个问题  就是用户的模板发生了变化
				AttendanceTemplate template = templateService.selectTemplateByUserId(userinfo);
				if (template == null) {
					template = templateService.selectDefaultTemplate(userinfo);
				}
				SomeMonthAttendanceResponse res = new SomeMonthAttendanceResponse();
				res.setData(mainService.getSomeMonthAttendanceByUserId(req.getYear(),req.getMonth(),userinfo.getId(),template));
				res.setCode(0);
				res.setMsg("获取成功");
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequestMapping("/testSend")
	@ResponseBody
	public BaseResponse testSend(){
		// 发送通知
		PushBean pushBean = new PushBean();
		pushBean.setRefId("");
		pushBean.setType(PushType.ATTENDANCE_MSG);
		pushBean.setContent("马上就要考勤了");
		List<String> toUserIds=new ArrayList<>();
		toUserIds.add("4414");
		toUserIds.add("4415");
	    String[] array = new String[toUserIds.size()];
	    array =  toUserIds.toArray(array);
		applicationContext.publishEvent(new PushMessageEvent(pushBean, "", array, PushFrom.LINLE_COMMUNITY));
		return BaseResponse.OperateSuccess;
	}
}
