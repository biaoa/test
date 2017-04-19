package com.linle.preferentialActivityRecord.service;

import java.math.BigDecimal;
import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.preferentialActivity.model.PreferentialActivity;
import com.linle.preferentialActivityRecord.model.PreferentialActivityRecord;

public interface PreferentialActivityRecordService extends BaseService<PreferentialActivityRecord> {
	
	/**
	 * 
	 * @Description  根据用户ID,活动ID 判断用户是否参加活动
	 * @param uid
	 * @param activityId
	 * @return boolean
	 * $
	 */
    boolean isJoinPreferentialActivity(Long uid,Long activityId);
	
	/**
	 * 
	 * @Description 获得活动已花费金额
	 * @param activityId
	 * @return int
	 * $
	 */
	 BigDecimal getActivityCostMoney(Long activityId);
	 
	 /**
	  * 
	  * @Description  根据活动ID 获得活动记录列表
	  * @param activityId
	  * @return List<PreferentialActivityRecord>
	  * $
	  */
	List<PreferentialActivityRecord> getPreferentialActivityRecord(Long activityId);
	
	/**
	 * 
	 * @Description 修改优惠活动记录的状态
	 * @param orderNo void
	 * $
	 */
	void updatePreferentialActivityRecord(String orderNo);
	
	PreferentialActivityRecord getActivityRecordByOrderNo(String orderNo);

	Page<PreferentialActivityRecord> getAllForPage(Page<PreferentialActivityRecord> page);
	
	
}
