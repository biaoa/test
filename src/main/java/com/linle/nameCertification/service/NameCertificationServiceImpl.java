package com.linle.nameCertification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.NameCertification;
import com.linle.nameCertification.mapper.NameCertificationMapper;


@Service
@Transactional
public class NameCertificationServiceImpl extends CommonServiceAdpter<NameCertification> implements NameCertificationService {

	@Autowired
	private NameCertificationMapper mapper;
		
	@Override
	public NameCertification selectByUserId(Long uid) {
		return mapper.selectByUserId(uid);
	}
	
}
