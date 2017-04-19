package com.linle.spaceRecord.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.SpaceRecord;

import component.BaseMapper;

public interface SpaceRecordMapper extends BaseMapper<SpaceRecord>{

	List<SpaceRecord> getSpaceList(Long id);

	SpaceRecord selectByOrderNo(String orderNo);

	int updateEndDate(Map<String, Object> spaceInfoMap);

	SpaceRecord selectSpaceInfo(Map<String, Object> spaceInfoMap);

	int parkingStop(Long spaceRecordId);

	int parkingLease(Long spaceRecordId);

	List<SpaceRecord> getSpaceListforStop(Long id);

  
}