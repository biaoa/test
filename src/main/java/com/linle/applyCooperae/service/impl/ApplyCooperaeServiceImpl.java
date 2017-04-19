package com.linle.applyCooperae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.applyCooperae.mapper.ApplyCooperaeMapper;
import com.linle.applyCooperae.model.ApplyCooperae;
import com.linle.applyCooperae.service.ApplyCooperaeService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;

@Service
@Transactional
public class ApplyCooperaeServiceImpl extends CommonServiceAdpter<ApplyCooperae> implements ApplyCooperaeService {
	
	@Autowired
	private ApplyCooperaeMapper mapper;
	
	@Override
	public Page<ApplyCooperae> getAllApplyCooperaeService(Page<ApplyCooperae> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}


}
