package com.linle.preferentialActivity.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.SysOrder;
import com.linle.preferentialActivity.model.PreferentialActivity;

public interface PreferentialActivityService extends BaseService<PreferentialActivity> {
	
	//判断是否有优惠
	boolean hasPreferential(SysOrder order);
	
	//参加活动
	void joinPreferentialActivity(SysOrder order);
	
	//生成优惠金额
	int generatePreferentialMoney(Long activityId);
	
	public Page<PreferentialActivity> getAllForPage(Page<PreferentialActivity> page) ;
}
