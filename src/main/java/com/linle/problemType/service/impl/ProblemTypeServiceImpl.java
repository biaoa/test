package com.linle.problemType.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.problemType.mapper.ProblemTypeMapper;
import com.linle.problemType.model.ProblemType;
import com.linle.problemType.service.ProblemTypeService;
@Service
@Transactional
public class ProblemTypeServiceImpl extends CommonServiceAdpter<ProblemType>  implements ProblemTypeService {
	@Autowired
	private ProblemTypeMapper mapper;
	
	//获得所有类型,包含该类型所有的问题数据
	@Override
	public List<ProblemType> getAllTypeAndData(HashMap<String, Object> map) {
		return mapper.getAllTypeAndData(map);
	}
	
	@Override
	public List<ProblemType> getAllType() {
		return mapper.getAllType();
	}
	
	@Override
	public Page<ProblemType> getAllDataForPage(Page<ProblemType> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
}
