package com.linle.knowledgeAccessRecord.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.knowledgeAccessRecord.mapper.KnowledgeAccessRecordMapper;
import com.linle.knowledgeAccessRecord.model.KnowledgeAccessRecord;
import com.linle.knowledgeAccessRecord.service.KnowledgeAccessRecordService;

@Service
public class KnowledgeAccessRecordServiceImpl extends CommonServiceAdpter<KnowledgeAccessRecord> implements KnowledgeAccessRecordService {

	@Autowired
	private KnowledgeAccessRecordMapper mapper;
}
