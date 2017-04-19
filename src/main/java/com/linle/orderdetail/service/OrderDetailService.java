package com.linle.orderdetail.service;

import java.math.BigDecimal;
import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.OrderDetail;

public interface OrderDetailService extends BaseService<OrderDetail> {

	List<OrderDetail> getDetailList(Long orderId);

	boolean updateProductPriceByOrderNo(String orderNo, BigDecimal productPrice);
	

}
