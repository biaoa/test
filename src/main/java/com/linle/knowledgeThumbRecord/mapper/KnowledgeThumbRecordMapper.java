package com.linle.knowledgeThumbRecord.mapper;

import java.util.Map;

import com.linle.knowledgeThumbRecord.model.KnowledgeThumbRecord;

import component.BaseMapper;

public interface KnowledgeThumbRecordMapper extends BaseMapper<KnowledgeThumbRecord>{

	KnowledgeThumbRecord selectByUserIdAndKnowledgeId(Map<String, Object> map);
	
}