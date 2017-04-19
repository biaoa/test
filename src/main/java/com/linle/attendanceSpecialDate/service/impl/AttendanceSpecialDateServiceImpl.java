package com.linle.attendanceSpecialDate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.attendanceSpecialDate.mapper.AttendanceSpecialDateMapper;
import com.linle.attendanceSpecialDate.model.AttendanceSpecialDate;
import com.linle.attendanceSpecialDate.service.AttendanceSpecialDateService;
import com.linle.common.base.CommonServiceAdpter;

@Service
@Transactional
public class AttendanceSpecialDateServiceImpl extends CommonServiceAdpter<AttendanceSpecialDate>
		implements AttendanceSpecialDateService {
	
	@Autowired
	private AttendanceSpecialDateMapper mapper;
	
	@Override
	public List<AttendanceSpecialDate> selectByTemplate(Long templateId) {
		return mapper.selectByTemplate(templateId);
	}

	@Override
	public int deleteAttendanceSpecialDateByTemplate(Long templateId) {
		return mapper.deleteAttendanceSpecialDateByTemplate(templateId);
	}

	@Override
	public AttendanceSpecialDate toDaySpecialDateByTemplateId(Long templateId) {
		return mapper.toDaySpecialDateByTemplateId(templateId);
	}

}
