package com.linle.preferentialActivityRecord.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.linle.preferentialActivityRecord.model.PreferentialActivityRecord;

import component.BaseMapper;

public interface PreferentialActivityRecordMapper extends BaseMapper<PreferentialActivityRecord>{

	List<PreferentialActivityRecord> getUserJoinPreferentialActivity(Map<String, Object> map);

	BigDecimal getActivityCostMoney(Long activityId);

	List<PreferentialActivityRecord> getPreferentialActivityRecord(Long activityId);

	PreferentialActivityRecord selectByOrderNo(String orderNo);
  
}