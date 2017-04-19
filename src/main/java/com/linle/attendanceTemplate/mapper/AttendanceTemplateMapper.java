package com.linle.attendanceTemplate.mapper;

import java.util.List;

import com.linle.attendanceTemplate.model.AttendanceTemplate;
import com.linle.entity.sys.Users;

import component.BaseMapper;

public interface AttendanceTemplateMapper extends BaseMapper<AttendanceTemplate>{

	String getAllTemplateUser(Long communityId);

	AttendanceTemplate selectTemplateByUserId(Users user);

	AttendanceTemplate selectDefaultTemplate(Users userinfo);

	List<AttendanceTemplate> selectNeedRemindTemplate();

  
}