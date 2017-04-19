package com.linle.topicUserManager.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.mobileapi.v1.model.TopicUserManagerVo;
import com.linle.topicUserManager.mapper.TopicUserManagerMapper;
import com.linle.topicUserManager.model.TopicUserManager;
import com.linle.topicUserManager.service.TopicUserManagerService;
@Service
@Transactional
public class TopicUserManagerServiceImpl extends CommonServiceAdpter<TopicUserManager>  implements TopicUserManagerService {
	@Autowired
	private TopicUserManagerMapper mapper;

	@Override
	public Page<TopicUserManager> getAllForPage(Page<TopicUserManager> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	
	public TopicUserManager selectById(long userId){
		return mapper.selectById(userId);
	}
	
	public TopicUserManagerVo selectByIdForApi(long userId){
		return mapper.selectByIdForApi(userId);
	}

	@Override
	public TopicUserManager selectByUserIdAndTopicTypeId(Long userId, Long typeId,String type) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("typeId", typeId);
		map.put("type", type);
		return mapper.selectByUserIdAndTopicTypeId(map);
	}
}
