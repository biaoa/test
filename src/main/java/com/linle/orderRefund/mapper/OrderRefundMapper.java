package com.linle.orderRefund.mapper;

import java.util.List;
import java.util.Map;

import com.linle.common.util.Page;
import com.linle.entity.sys.OrderRefund;
import com.linle.mobileapi.v1.model.OrderVo;
import com.linle.mobileapi.v1.model.RefundOrderDetailVO;

import component.BaseMapper;
/**
 * 
 * @author pangd
 * @Description 订单退款申请
 */
public interface OrderRefundMapper extends BaseMapper<OrderRefund>{

	OrderRefund selectOrderRefundByOrderNo(String orderNo);
	
	List<OrderVo> getRefundListAndDetail(Map<String, Object> map);

	RefundOrderDetailVO selectDetailForAPI(String orderNo);
	//退款列表
	List<OrderRefund> getRefundListByPresident(Page<OrderRefund> page);

	int applyCustomerServices(Map<String, Object> map);
	
	//获得商家24小时未处理的退款订单
	List<OrderRefund> selectShopNoOperateRefundOrder();
	//获得社长24小时未处理的退款订单
	List<OrderRefund> selectPresidentNoOperateRefundOrder();
	//获得用户24小时未操作的商家拒绝退款订单
	List<OrderRefund> selectUserNoOperateRefundOrder();
}