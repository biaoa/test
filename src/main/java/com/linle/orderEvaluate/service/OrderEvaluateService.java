package com.linle.orderEvaluate.service;

import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.OrderEvaluate;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.EvaluateHeadRequest;
import com.linle.mobileapi.v1.request.OrderEvaluateRequest;
import com.linle.mobileapi.v1.request.ShopEvaluateRequest;

public interface OrderEvaluateService extends BaseService<OrderEvaluate> {

	BaseResponse submitEvaluate(OrderEvaluateRequest req);

	BaseResponse getShopEvaluate(ShopEvaluateRequest req);

	BaseResponse getEvaluateHead(EvaluateHeadRequest req);
	
	//获得商家的综合评分
	Float getShopComplexStar(Long shopId);
	
	//根据商品ID 订单编号获得订单评价信息
	OrderEvaluate getOrderEvaluate(Map<String, Object> detailMap);

}
