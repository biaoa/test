package com.linle.serviceAgreement.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.BaseServiceAdpter;
import com.linle.common.util.Page;
import com.linle.serviceAgreement.mapper.ServiceAgreementMapper;
import com.linle.serviceAgreement.model.ServiceAgreement;
import com.linle.serviceAgreement.service.ServiceAgreementService;
@Service
@Transactional
public class ServiceAgreementServiceImpl extends BaseServiceAdpter<ServiceAgreement> implements ServiceAgreementService{
	@Autowired
	private ServiceAgreementMapper mapper;
	
	@Override
	public Page<ServiceAgreement> getAllData(Page<ServiceAgreement> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	
	public ServiceAgreement selectByTypeId(HashMap<String, Object> map){
		return mapper.selectByTypeId(map);
	}
}
