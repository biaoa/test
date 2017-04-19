package com.linle.universalTelephone.mapper;

import java.util.List;

import com.linle.entity.sys.UniversalTelephone;
import com.linle.mobileapi.v1.model.UniversalTelephoneVO;

import component.BaseMapper;

public interface UniversalTelephoneMapper extends BaseMapper<UniversalTelephone> {
	
	List<UniversalTelephoneVO> getAllUniversalTelephoneForAPI();
}