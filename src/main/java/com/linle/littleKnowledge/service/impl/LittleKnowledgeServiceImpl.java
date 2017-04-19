package com.linle.littleKnowledge.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.LittleKnowledge;
import com.linle.littleKnowledge.mapper.LittleKnowledgeMapper;
import com.linle.littleKnowledge.service.LittleKnowledgeService;
import com.linle.mobileapi.v1.model.LittleKnowledgeVO;

@Service
@Transactional
public class LittleKnowledgeServiceImpl extends CommonServiceAdpter<LittleKnowledge> implements LittleKnowledgeService {

	@Autowired
	private LittleKnowledgeMapper mapper;

	@Override
	public Page<LittleKnowledge> getALLknowledgeService(Page<LittleKnowledge> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<LittleKnowledgeVO> getAllForAPI(Map<String, Object> map) {
		return mapper.getAllForAPI(map);
	}
}
