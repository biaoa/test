package com.linle.mobileapi.v1.model;

import java.util.List;

/**
 * 
 * @author pangd
 * @Description 店铺评价
 */
public class ShopEvaluate {
	private float commodityStar;//商品评分
	
	private float serviceStar;//服务评分
	
	private float sendStar;//配送评分
	
	private List<EvaluateInfo> evaluateList;//评价信息

	public float getCommodityStar() {
		return commodityStar;
	}

	public void setCommodityStar(float commodityStar) {
		this.commodityStar = commodityStar;
	}

	public float getServiceStar() {
		return serviceStar;
	}

	public void setServiceStar(float serviceStar) {
		this.serviceStar = serviceStar;
	}

	public float getSendStar() {
		return sendStar;
	}

	public void setSendStar(float sendStar) {
		this.sendStar = sendStar;
	}

	public List<EvaluateInfo> getEvaluateList() {
		return evaluateList;
	}

	public void setEvaluateList(List<EvaluateInfo> evaluateList) {
		this.evaluateList = evaluateList;
	}
}
