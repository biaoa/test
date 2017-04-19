package com.linle.cTopicType.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.cTopicType.mapper.CTopicTypeMapper;
import com.linle.cTopicType.service.CTopicTypeService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.CTopicType;
@Service
@Transactional
public class CTopicTypeServiceImpl extends CommonServiceAdpter<CTopicType>  implements CTopicTypeService {
	@Autowired
	private CTopicTypeMapper mapper;
	
	@Override
	public List<CTopicType> getAllType() {
		return mapper.getAllType();
	}

	@Override
	public Page<CTopicType> getAllTypeForPage(Page<CTopicType> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public int getTypeUnreadCount(Map<String, Object> map) {
		return mapper.getTypeUnreadCount(map);
	}
	
}
