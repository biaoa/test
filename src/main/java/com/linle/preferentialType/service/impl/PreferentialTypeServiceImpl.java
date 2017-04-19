package com.linle.preferentialType.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.PreferentialType;
import com.linle.mobileapi.v1.model.Privilege;
import com.linle.preferentialType.mapper.PreferentialTypeMapper;
import com.linle.preferentialType.service.PreferentialTypeService;

@Service
@Transactional
public class PreferentialTypeServiceImpl extends CommonServiceAdpter<PreferentialType>
		implements PreferentialTypeService {
	
	@Autowired
	private PreferentialTypeMapper mapper;

	@Override
	public Page<PreferentialType> findAllPreferentialType(Page<PreferentialType> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<PreferentialType> getAllType() {
		return mapper.getallType();
	}

	@Override
	public List<Privilege> getAllTypeForAPI() {
		return mapper.getAllTypeForAPI();
	}

}
