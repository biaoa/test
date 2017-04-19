package com.linle.patrolRoomRecord.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.mobileapi.property.model.PatrolRoomRecordListVo;
import com.linle.patrolRoomRecord.model.PatrolRoomRecord;

public interface PatrolRoomRecordService  extends BaseService<PatrolRoomRecord> {
	public Page<PatrolRoomRecord> getAllDataForPage(Page<PatrolRoomRecord> page);
	
	public List<PatrolRoomRecordListVo> getPatrolRoomRecordListForApi(Map<String, Object> map);
	
	public PatrolRoomRecord getPatrolRoomRecordById(long id);
}
