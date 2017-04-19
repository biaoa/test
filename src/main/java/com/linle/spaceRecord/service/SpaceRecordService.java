package com.linle.spaceRecord.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.SpaceRecord;

public interface SpaceRecordService extends BaseService<SpaceRecord> {
	
	//获得用户还未到期的 车位购买记录
	List<SpaceRecord> getSpaceList(Long id);
	//根据订单号获得车位购买记录
	SpaceRecord selectByOrderNo(String orderNo);
	//车位续费后 更改车位到期时间
	int updateEndDate(Map<String, Object> spaceInfoMap);
	//根据车库和车位获得车位购买记录
	SpaceRecord selectSpaceInfo(Map<String, Object> spaceInfoMap);
	//设置状态为车位申停
	boolean parkingStop(Long spaceRecordId);
	//设置状态为车位转租
	boolean parkingLease(Long valueOf);
	//用户所有的车位记录
	List<SpaceRecord> getSpaceListforStop(Long id);
	

}
