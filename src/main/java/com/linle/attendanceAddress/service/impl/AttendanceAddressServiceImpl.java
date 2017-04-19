package com.linle.attendanceAddress.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.attendanceAddress.mapper.AttendanceAddressMapper;
import com.linle.attendanceAddress.model.AttendanceAddress;
import com.linle.attendanceAddress.service.AttendanceAddressService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.vo.AttendanceAddressVO;

@Service
@Transactional
public class AttendanceAddressServiceImpl extends CommonServiceAdpter<AttendanceAddress>
		implements AttendanceAddressService {
	
	@Autowired
	private AttendanceAddressMapper mapper;
	
	@Override
	public boolean deleteByTemplateId(Long templateId) {
		return mapper.deleteByTemplateId(templateId)>0;
	}

	@Override
	public List<AttendanceAddress> selectByTemplateId(Long templataeId) {
		return mapper.selectByTemplateId(templataeId);
	}

	@Override
	public List<AttendanceAddressVO> getAttendanceDateAddressByTemplateId(Long templataeId) {
		return mapper.getAttendanceDateAddressByTemplateId(templataeId);
	}

}
