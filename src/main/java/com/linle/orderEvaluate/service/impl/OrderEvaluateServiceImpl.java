package com.linle.orderEvaluate.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateUtil;
import com.linle.common.util.LimitUtil;
import com.linle.entity.sys.OrderEvaluate;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.EvaluateHeadVo;
import com.linle.mobileapi.v1.model.EvaluateInfo;
import com.linle.mobileapi.v1.model.ShopEvaluate;
import com.linle.mobileapi.v1.request.EvaluateHeadRequest;
import com.linle.mobileapi.v1.request.OrderEvaluateRequest;
import com.linle.mobileapi.v1.request.ShopEvaluateRequest;
import com.linle.mobileapi.v1.response.EvaluateHeadResponse;
import com.linle.mobileapi.v1.response.ShopEvaluateResponse;
import com.linle.orderEvaluate.mapper.OrderEvaluateMapper;
import com.linle.orderEvaluate.service.OrderEvaluateService;
import com.linle.shop.service.ShopService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.user.service.UserInfoService;

@Service
@Transactional
public class OrderEvaluateServiceImpl  extends CommonServiceAdpter<OrderEvaluate> implements OrderEvaluateService {
	
	@Autowired
	private OrderEvaluateMapper mapper;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private ShopService shopService;
	
	@Override
	public BaseResponse submitEvaluate(OrderEvaluateRequest req) {
		//星数判断
		if (req.getCommodityStar()>5 || req.getServiceStar()>5 || req.getSendStar()>5 || req.getCommodityStar()<0 || req.getServiceStar()<0 || req.getSendStar()<0) {
			return new BaseResponse(1,"星数错误");
		}
		//判断订单是否存在
		SysOrder order  = orderService.selectByOrderNo(req.getOrderNo());
		if(order==null){
			return new BaseResponse(1,"订单不存在");
		}
		//判断用户是否提交过评价
		Subject subject = SecurityUtils.getSubject();
		Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", req.getOrderNo());
		map.put("uid", user.getId());
		if (mapper.selectIsEvaluate(map) !=null) {
			return new BaseResponse(1,"已评价过该订单");
		}
		OrderEvaluate evaluate = new OrderEvaluate();
		evaluate.setUserId(user.getId());
		evaluate.setShopId(Long.valueOf(order.getDetails()));
		evaluate.setOrderNo(req.getOrderNo());
		evaluate.setCommodityStar(req.getCommodityStar());
		evaluate.setServiceStar(req.getServiceStar());
		evaluate.setSendStar(req.getSendStar());
		evaluate.setContent(req.getContent());
		evaluate.setCreateDate(new Date());
		int sumStar= req.getCommodityStar()+req.getServiceStar()+req.getSendStar();
		BigDecimal sum = new BigDecimal(sumStar);
		
		evaluate.setComplexStar(Float.parseFloat(sum.divide(new BigDecimal(3),2,BigDecimal.ROUND_HALF_DOWN).toString()));
		int result = insertSelective(evaluate);
		System.out.println("提交评价的结果是:"+result);
		//修改订单中的送达时间
		order.setSendDate(req.getDeliverytime());
		order.setOrderStatus(4);
		orderService.updateSendDate(order);
		return BaseResponse.OperateSuccess;
	}

	@Override
	public BaseResponse getShopEvaluate(ShopEvaluateRequest req) {
		ShopEvaluate evaluate = new ShopEvaluate();
		ShopEvaluateResponse res = new ShopEvaluateResponse();
		if(req.getCommodityStar()!=null && req.getCommodityStar()!=0
		 && req.getServiceStar()!=null && req.getServiceStar()!=0
		 && req.getSendStar()!=null && req.getSendStar()!=0 ){
			evaluate.setCommodityStar(req.getCommodityStar());
			evaluate.setServiceStar(req.getServiceStar());
			evaluate.setSendStar(req.getSendStar());
		}else{
			evaluate.setCommodityStar(mapper.getCommodityStarByShopId(req.getShopId()));
			evaluate.setServiceStar(mapper.getServiceStarByShopId(req.getShopId()));
			evaluate.setSendStar(mapper.getSendStarByShopId(req.getShopId()));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", req.getShopId());
		LimitUtil.limit(map, req.getPageSize(), req.getPageNumber());
		List<EvaluateInfo> evaluateList = mapper.getEvaluateList(map);
		evaluate.setEvaluateList(evaluateList);
		res.setCode(0);
		res.setMsg("获取成功");
		res.setShopEvaluate(evaluate);
		return res;
	}

	@Override
	public BaseResponse getEvaluateHead(EvaluateHeadRequest req) {
		EvaluateHeadResponse res = new EvaluateHeadResponse();
		EvaluateHeadVo head = new EvaluateHeadVo();
		Map<String, Object> map = new HashMap<>();
		SysOrder order = orderService.selectByOrderNo(req.getOrderNo());
		if(order==null || !order.getType().equals("commodity")){
			return new BaseResponse(1, "订单不存在或订单不能评价");
		}
		Shop shop =   shopService.selectByPrimaryKey(order.getBusinessId());
		if (shop==null) {
			return new BaseResponse(1,"店铺不存在");
		}
		head.setStoreimage(shop.getShopLogo());
		head.setStorename(shop.getShopName());
		head.setStartmoney(shop.getSendPrice());
		head.setFreight(shop.getDeliveryFee());
		head.setGrade(getShopComplexStar(order.getBusinessId()));
		
		map.put("shopId", order.getBusinessId());
		map.put("month", DateUtil.getMonth());
		map.put("year", DateUtil.getYear());
		head.setSales(shopService.selectShopSales(map));
		res.setHead(head);
		return res;
	}

	@Override
	public Float getShopComplexStar(Long shopId) {
		return mapper.getShopComplexStar(shopId);
	}

	@Override
	public OrderEvaluate getOrderEvaluate(Map<String, Object> detailMap) {
		return mapper.getOrderEvaluate(detailMap);
	}

}
