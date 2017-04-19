package com.linle.orderdetail.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.OrderDetail;
import com.linle.orderdetail.mapper.OrderDetailMapper;
import com.linle.orderdetail.service.OrderDetailService;


@Service("orderDetailService")
@Transactional
public class OrderDetailServiceImpl extends CommonServiceAdpter<OrderDetail> implements OrderDetailService {
	
	@Autowired
	private OrderDetailMapper mapper;
	
	@Override
	public List<OrderDetail> getDetailList(Long orderId) {
		return mapper.getDetailList(orderId);
	}
	
	@Override
	public boolean updateProductPriceByOrderNo(String orderNo, BigDecimal productPrice) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo",orderNo);
		map.put("productPrice", productPrice);
		return mapper.updateProductPriceByOrderNo(map)>0;
	}
}
