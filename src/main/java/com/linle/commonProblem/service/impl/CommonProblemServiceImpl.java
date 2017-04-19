package com.linle.commonProblem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.commonProblem.mapper.CommonProblemMapper;
import com.linle.commonProblem.model.CommonProblem;
import com.linle.commonProblem.service.CommonProblemService;
@Service
@Transactional
public class CommonProblemServiceImpl extends CommonServiceAdpter<CommonProblem>  implements CommonProblemService {
	@Autowired
	private CommonProblemMapper mapper;
	
	@Override
	public List<CommonProblem> getAllDataForApi() {
		return mapper.getAllDataForApi();
	}

	@Override
	public Page<CommonProblem> getAllDataForPage(Page<CommonProblem> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
}
