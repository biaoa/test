package com.linle.common.util;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @描述 Plan统计信息
 * @作者 Guitarist
 * @版本 linle
 * @创建时间：2015年8月24日 下午4:28:30
 *青春气贯长虹
 *勇锐盖过怯弱
 *进取压倒苟安
 *年岁有加,并非垂老,理想丢弃,方堕暮年
 */
public class PlanInfo {
	private int planSum;
	private int goldSum;
	private int completedEpSum;

	public int getGoldSum() {
		return goldSum;
	}

	public void setGoldSum(int goldSum) {
		this.goldSum = goldSum;
	}

	public int getCompletedEpSum() {
		return completedEpSum;
	}

	public void setCompletedEpSum(int completedEpSum) {
		this.completedEpSum = completedEpSum;
	}

	public int getPlanSum() {
		return planSum;
	}

	public void setPlanSum(int planSum) {
		this.planSum = planSum;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
