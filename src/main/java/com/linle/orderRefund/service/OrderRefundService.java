package com.linle.orderRefund.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.OrderRefund;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.OrderVo;
import com.linle.mobileapi.v1.model.RefundOrderDetailVO;

public interface OrderRefundService extends BaseService<OrderRefund> {
	
	//通过订单号查询退款信息
	OrderRefund selectOrderRefundByOrderNo(String orderNo);
	
	//商家处理退款订单
	BaseResponse shopRefundOrder(OrderRefund refund);
	
	//获得退款订单列表
	List<OrderVo> getRefundListAndDetail(Map<String, Object> map);
	//获得退款详情
	RefundOrderDetailVO selectDetailForAPI(String orderNo);
	
	//小区社长获得退款列表
	Page<OrderRefund> getRefundListByPresident(Page<OrderRefund> page);
	//申请客服介入
	boolean applyCustomerServices(Map<String, Object> map);
	//社长操作退款订单
	void updateRefundStatus(String orderNo, Integer status);
	
	//获得商家超过24小时未处理的退款订单
	List<OrderRefund> selectShopNoOperateRefundOrder();
	//获得社长超过24小时未处理的退款订单
	List<OrderRefund> selectPresidentNoOperateRefundOrder();
	//获得用户超过24小时未处理的退款订单
	List<OrderRefund> selectUserNoOperateRefundOrder();
	
}
