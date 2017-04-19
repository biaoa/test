package com.linle.orderEvaluate.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.OrderEvaluate;
import com.linle.mobileapi.v1.model.EvaluateInfo;

import component.BaseMapper;

public interface OrderEvaluateMapper extends BaseMapper<OrderEvaluate>{

	OrderEvaluate selectIsEvaluate(Map<String, Object> map);
	//获得店铺商品平均评分
	float getCommodityStarByShopId(Long shopId);
	//获得店铺服务平均评分
	float getServiceStarByShopId(Long shopId);
	//获得店铺配送平均评分
	float getSendStarByShopId(Long shopId);
	//获得商铺评价列表
	List<EvaluateInfo> getEvaluateList(Map<String, Object> map);
	//获得店铺综合评分
	Float getShopComplexStar(Long shopId);
	//根据店铺ID,订单编号获得订单评价信息
	OrderEvaluate getOrderEvaluate(Map<String, Object> detailMap);
}