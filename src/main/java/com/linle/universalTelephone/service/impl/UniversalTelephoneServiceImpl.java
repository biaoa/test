package com.linle.universalTelephone.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.UniversalTelephone;
import com.linle.mobileapi.v1.model.UniversalTelephoneVO;
import com.linle.universalTelephone.mapper.UniversalTelephoneMapper;
import com.linle.universalTelephone.service.UniversalTelephoneService;

@Service
@Transactional
public class UniversalTelephoneServiceImpl extends CommonServiceAdpter<UniversalTelephone> implements UniversalTelephoneService {
	
	@Autowired
	private  UniversalTelephoneMapper mapper;
	
	@Override
	public List<UniversalTelephoneVO> getAllUniversalTelephoneForAPI() {
		return mapper.getAllUniversalTelephoneForAPI();
	}


}
