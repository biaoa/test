package com.linle.patrolRoomRecord.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.mobileapi.property.model.PatrolRoomRecordListVo;
import com.linle.patrolRoomRecord.mapper.PatrolRoomRecordMapper;
import com.linle.patrolRoomRecord.model.PatrolRoomRecord;
import com.linle.patrolRoomRecord.service.PatrolRoomRecordService;
@Service
@Transactional
public class PatrolRoomRecordServiceImpl extends CommonServiceAdpter<PatrolRoomRecord>  implements PatrolRoomRecordService {
	@Autowired
	private PatrolRoomRecordMapper mapper;
	
	
	@Override
	public Page<PatrolRoomRecord> getAllDataForPage(Page<PatrolRoomRecord> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	
	@Override
	public List<PatrolRoomRecordListVo> getPatrolRoomRecordListForApi(Map<String, Object> map) {
		return mapper.getPatrolRoomRecordListForApi(map);
	}
	
	public PatrolRoomRecord getPatrolRoomRecordById(long id){
		return mapper.selectByPrimaryKey(id);
	}
	
	
}
