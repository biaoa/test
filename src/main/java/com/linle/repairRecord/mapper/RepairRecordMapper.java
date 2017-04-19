package com.linle.repairRecord.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.RepairRecord;
import com.linle.mobileapi.v1.model.RepairVo;

import component.BaseMapper;

public interface RepairRecordMapper extends BaseMapper<RepairRecord>{

	int operate(Map<String, Object> map);

	List<RepairVo> getRepairList(Map<String, Object> map);

	int operateForAPI(Map<String, Object> map);

	long getCountUnRepair(long community_id);

	RepairRecord getNewUnRepairRecord(long community_id);

}