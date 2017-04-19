package com.linle.repairType.sercie.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.RepairType;
import com.linle.repairType.mapper.RepairTypeMapper;
import com.linle.repairType.sercie.RepairTypeService;

@Service("RepairTypeService")
@Transactional
public class RepairTypeServiceImpl extends CommonServiceAdpter<RepairType> implements RepairTypeService {
	
	@Autowired
	private RepairTypeMapper mapper;
	
	@Override
	public Page<RepairType> findAllRepairType(Page<RepairType> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<RepairType> getAllType() {
		return mapper.getAllType();
	}

}
