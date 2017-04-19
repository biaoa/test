package com.linle.cTopicReadRecord.mapper;

import java.util.List;
import java.util.Map;

import com.linle.cTopicReadRecord.model.CTopicReadRecord;
import com.linle.mobileapi.v1.model.TopicUnreadVO;

import component.BaseMapper;

public interface CTopicReadRecordMapper extends BaseMapper<CTopicReadRecord>{

	CTopicReadRecord selectRecordExist(Map<String, Object> map);

	int updateReadRecord(Map<String, Object> map);

	List<TopicUnreadVO> getTopicUnreadList(Map<String, Object> map);
	
}