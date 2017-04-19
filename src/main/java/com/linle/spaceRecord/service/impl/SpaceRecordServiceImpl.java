package com.linle.spaceRecord.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.SpaceRecord;
import com.linle.spaceRecord.mapper.SpaceRecordMapper;
import com.linle.spaceRecord.service.SpaceRecordService;

@Service
@Transactional
public class SpaceRecordServiceImpl extends CommonServiceAdpter<SpaceRecord> implements SpaceRecordService {

	@Autowired
	private SpaceRecordMapper mapper;
	
	@Override
	public List<SpaceRecord> getSpaceList(Long id) {
		return mapper.getSpaceList(id);
	}

	@Override
	public SpaceRecord selectByOrderNo(String orderNo) {
		return mapper.selectByOrderNo(orderNo);
	}

	@Override
	public int updateEndDate(Map<String, Object> spaceInfoMap) {
		return mapper.updateEndDate(spaceInfoMap);
	}

	@Override
	public SpaceRecord selectSpaceInfo(Map<String, Object> spaceInfoMap) {
		return mapper.selectSpaceInfo(spaceInfoMap);
	}

	@Override
	public boolean parkingStop(Long spaceRecordId) {
		return mapper.parkingStop(spaceRecordId)>0;
	}

	@Override
	public boolean parkingLease(Long spaceRecordId) {
		return mapper.parkingLease(spaceRecordId)>0;
	}

	@Override
	public List<SpaceRecord> getSpaceListforStop(Long id) {
		return mapper.getSpaceListforStop(id);
	}


}
