package com.linle.patrolRoomRecord.mapper;

import java.util.List;
import java.util.Map;

import com.linle.mobileapi.property.model.PatrolRoomRecordListVo;
import com.linle.patrolRoomRecord.model.PatrolRoomRecord;

import component.BaseMapper;

public interface PatrolRoomRecordMapper extends BaseMapper<PatrolRoomRecord>{

	List<PatrolRoomRecordListVo> getPatrolRoomRecordListForApi(Map<String, Object> map);

}