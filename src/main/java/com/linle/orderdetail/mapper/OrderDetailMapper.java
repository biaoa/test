package com.linle.orderdetail.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.OrderDetail;

import component.BaseMapper;

public interface OrderDetailMapper extends BaseMapper<OrderDetail>{

	List<OrderDetail> getDetailList(Long orderId);

	int updateProductPriceByOrderNo(Map<String, Object> map);
}