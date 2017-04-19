package com.linle.repairRecord.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.RepairRecord;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.RepairVo;
import com.linle.repairRecord.mapper.RepairRecordMapper;
import com.linle.repairRecord.service.RepairRecordService;

@Transactional
@Service("RepairRecordService")
public class RepairRecordServiceImpl extends CommonServiceAdpter<RepairRecord> implements RepairRecordService {
	
	@Autowired
	private RepairRecordMapper mapper;

	@Override
	public Page<RepairRecord> findAllRepaiRecord(Page<RepairRecord> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public BaseResponse operate(Map<String, Object> map) {
		boolean isok = mapper.operate(map)>0;
		return isok?BaseResponse.OperateSuccess:BaseResponse.OperateFail;
	}

	@Override
	public List<RepairVo> getRepairList(Map<String, Object> map) {
		return mapper.getRepairList(map);
	}

	@Override 
	public long getCountUnRepair(long community_id){
		return mapper.getCountUnRepair(community_id);
	}
	
	@Override 
	public RepairRecord getNewUnRepairRecord(long community_id){
		return mapper.getNewUnRepairRecord(community_id);
	}
	
	@Override
	public BaseResponse operateForAPI(Map<String, Object> map) {
		boolean isok = mapper.operateForAPI(map)>0;
		return isok?BaseResponse.OperateSuccess:BaseResponse.OperateFail;
	}

}
