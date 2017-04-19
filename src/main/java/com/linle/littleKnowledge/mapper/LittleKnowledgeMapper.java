package com.linle.littleKnowledge.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.LittleKnowledge;
import com.linle.mobileapi.v1.model.LittleKnowledgeVO;

import component.BaseMapper;

public interface LittleKnowledgeMapper extends BaseMapper<LittleKnowledge>{

	List<LittleKnowledgeVO> getAllForAPI(Map<String, Object> map);
}