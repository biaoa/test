package com.linle.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.linle.event.KnowledgeAccessRecordEvent;
import com.linle.knowledgeAccessRecord.model.KnowledgeAccessRecord;
import com.linle.knowledgeAccessRecord.service.KnowledgeAccessRecordService;


@Component
public class KnowledgeAccessRecordListener implements ApplicationListener<KnowledgeAccessRecordEvent> {
	protected final Logger _logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private KnowledgeAccessRecordService AccessRecordService;
	
	@Async
	@Override
	public void onApplicationEvent(KnowledgeAccessRecordEvent event) {
		try {
			KnowledgeAccessRecord record = new KnowledgeAccessRecord();
			record.setKnowledgeId(Long.valueOf(event.getSource().toString()));
			record.setUid(event.getUid());
			record.setCommunityId(event.getCommunityId());
			AccessRecordService.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("插入banner访问记录出错");
		}
		
	}

}
