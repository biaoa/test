package com.linle.littleKnowledge.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.LittleKnowledge;
import com.linle.mobileapi.v1.model.LittleKnowledgeVO;

public interface LittleKnowledgeService extends BaseService<LittleKnowledge> {

	Page<LittleKnowledge> getALLknowledgeService(Page<LittleKnowledge> page);

	List<LittleKnowledgeVO> getAllForAPI(Map<String, Object> map);

}
