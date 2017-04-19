package com.linle.attendanceTemplate.service;

import java.util.Date;
import java.util.List;

import com.linle.attendanceTemplate.model.AttendanceTemplate;
import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.entity.sys.Users;

public interface AttendanceTemplateService extends BaseService<AttendanceTemplate> {

	Page<AttendanceTemplate> getAllData(Page<AttendanceTemplate> page);

	// 操作模板
	boolean operateTemplate(AttendanceTemplate temp);

	// 根据小区ID 获得所有在模板里面的用户
	String getAllTemplateUser(Long communityId);

	// 插入小区默认考勤规则表
	void addDefaultTemplate(Community community);

	// 根据用户ID 获得对应的模板信息
	AttendanceTemplate selectTemplateByUserId(Users user);

	// 获得用户的默认考勤模板
	AttendanceTemplate selectDefaultTemplate(Users userinfo);

	// 某一天某个用户是否需要打卡
	int needAttendanceByUserId(Date date, AttendanceTemplate template, Long uid);

	// 获得所有需要提醒的考勤模板
	List<AttendanceTemplate> selectNeedRemindTemplate();

	// 获得某个模板下的所有考勤用户
	List<String> getAllTemplateUserByTemplateId(Long id);
	// 根据考勤模板ID 判断是否需要打卡
	int needAttendanceByTemplate(Date date, AttendanceTemplate template);

}
