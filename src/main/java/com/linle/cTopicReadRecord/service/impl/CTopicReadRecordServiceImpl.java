package com.linle.cTopicReadRecord.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linle.cTopicReadRecord.mapper.CTopicReadRecordMapper;
import com.linle.cTopicReadRecord.model.CTopicReadRecord;
import com.linle.cTopicReadRecord.service.CTopicReadRecordService;
import com.linle.cTopicType.service.CTopicTypeService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.mobileapi.v1.model.TopicUnreadVO;

@Service
public class CTopicReadRecordServiceImpl extends CommonServiceAdpter<CTopicReadRecord>
		implements CTopicReadRecordService {

	@Autowired
	private CTopicReadRecordMapper mapper;
	
	@Autowired
	private CTopicTypeService typeService;

	@Override
	public boolean recordExist(Long userId, Long typeId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("typeId", typeId);
		CTopicReadRecord record = mapper.selectRecordExist(map);
		return record != null ? true : false;
	}

	@Override
	public boolean updateReadRecord(Long userId, Long typeId) {
		// 如果存在去修改
		if (recordExist(userId, typeId)) {
			Map<String, Object> map = new HashMap<>();
			map.put("userId", userId);
			map.put("typeId", typeId);
			mapper.updateReadRecord(map);
		} else {
			CTopicReadRecord record = new CTopicReadRecord();
			record.setUserId(userId);
			record.setTypeId(typeId);
			record.setLastRequestDate(new Date());
			record.setCreateDate(new Date());
			mapper.insertSelective(record);
		}
		return true;
	}

	@Override
	public List<TopicUnreadVO> getTopicUnreadList(Map<String, Object> map) {
		List<TopicUnreadVO> list = mapper.getTopicUnreadList(map);
		for (TopicUnreadVO topicUnreadVO : list) {
			map.put("typeId", topicUnreadVO.getTypeId());
			map.put("lastReadTime", topicUnreadVO.getLastReadTime());
			topicUnreadVO.setUnreadCount(typeService.getTypeUnreadCount(map));
		}
		return list;
	}

}
