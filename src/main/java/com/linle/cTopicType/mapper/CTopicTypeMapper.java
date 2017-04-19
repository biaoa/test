package com.linle.cTopicType.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.CTopicType;

import component.BaseMapper;

public interface CTopicTypeMapper extends BaseMapper<CTopicType>{
	
	List<CTopicType> getAllType();

	int getTypeUnreadCount(Map<String, Object> map);
	
}