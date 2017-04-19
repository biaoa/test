package com.linle.topicUserManager.mapper;

import java.util.Map;

import com.linle.mobileapi.v1.model.TopicUserManagerVo;
import com.linle.topicUserManager.model.TopicUserManager;

import component.BaseMapper;

public interface TopicUserManagerMapper extends BaseMapper<TopicUserManager> {

	TopicUserManager selectById(long id);

	TopicUserManagerVo selectByIdForApi(long userId);

	TopicUserManager selectByUserIdAndTopicTypeId(Map<String, Object> map);
	
}