package com.linle.cTopic.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.CTopic;
import com.linle.mobileapi.v1.model.CTopicVO;

import component.BaseMapper;

public interface CTopicMapper extends BaseMapper<CTopic>{
	
	 List<CTopicVO> getTopicListForApi(Map<String, Object> map);
	 
	 CTopicVO getTopicByTopicIdForApi(Map<String, Object> map);
	 
	 List<CTopicVO> getTopicListByUserIdForApi(Map<String, Object> map);
	 
	int getNewTopic(Map<String, Object> map);
	 
}