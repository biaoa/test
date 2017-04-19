package com.linle.repairRecord.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.RepairRecord;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.RepairVo;

public interface RepairRecordService extends BaseService<RepairRecord> {

	Page<RepairRecord> findAllRepaiRecord(Page<RepairRecord> page);
	//服务报修状态操作
	BaseResponse operate(Map<String, Object> map);
	//获得报修记录
	List<RepairVo> getRepairList(Map<String, Object> map);
	//app用户 操作报修单
	BaseResponse operateForAPI(Map<String, Object> map);
	
	long getCountUnRepair(long community_id);
	
	RepairRecord getNewUnRepairRecord(long community_id);

}
