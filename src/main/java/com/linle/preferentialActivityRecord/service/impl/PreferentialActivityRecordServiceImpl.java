package com.linle.preferentialActivityRecord.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.preferentialActivity.model.PreferentialActivity;
import com.linle.preferentialActivityRecord.mapper.PreferentialActivityRecordMapper;
import com.linle.preferentialActivityRecord.model.PreferentialActivityRecord;
import com.linle.preferentialActivityRecord.service.PreferentialActivityRecordService;

@Service
@Transactional
public class PreferentialActivityRecordServiceImpl extends CommonServiceAdpter<PreferentialActivityRecord>
		implements PreferentialActivityRecordService {

	@Autowired
	private PreferentialActivityRecordMapper mapper;

	@Override
	public boolean isJoinPreferentialActivity(Long uid, Long activityId) {
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("activityId", activityId);
		List<PreferentialActivityRecord> record = mapper.getUserJoinPreferentialActivity(map);
		return !record.isEmpty();
	}
	@Override
	public Page<PreferentialActivityRecord> getAllForPage(Page<PreferentialActivityRecord> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	@Override
	public BigDecimal getActivityCostMoney(Long activityId) {
		return mapper.getActivityCostMoney(activityId);
	}

	@Override
	public List<PreferentialActivityRecord> getPreferentialActivityRecord(Long activityId) {
		return mapper.getPreferentialActivityRecord(activityId);
	}

	@Override
	public void updatePreferentialActivityRecord(String orderNo) {
		PreferentialActivityRecord record = mapper.selectByOrderNo(orderNo);
		if (record != null) {
			record.setPayFlag(1);
			record.setUpdateDate(new Date());
			mapper.updateByPrimaryKeySelective(record);
		}
	}

	@Override
	public PreferentialActivityRecord getActivityRecordByOrderNo(String orderNo) {
		return mapper.selectByOrderNo(orderNo);
	}

}
