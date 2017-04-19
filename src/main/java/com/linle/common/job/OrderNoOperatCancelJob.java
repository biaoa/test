package com.linle.common.job;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.entity.sys.OrderRefund;
import com.linle.entity.sys.RefundRecord;
import com.linle.entity.sys.SysOrder;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.orderRefund.service.OrderRefundService;
import com.linle.pay.service.PayService;
import com.linle.refundRecord.service.RefundRecordService;
import com.linle.sysOrder.service.SysOrderService;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;

@Component
public class OrderNoOperatCancelJob implements Serializable {

	public ObjectMapper m = new ObjectMapper();

	private static final long serialVersionUID = -4786565172934239371L;

	private Logger _logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysOrderService orderService;

	@Autowired
	private PayService payServce;

	@Autowired
	private RefundRecordService refundService;

	@Autowired
	private OrderRefundService orderRefundService;

    @Autowired
    protected ApplicationContext applicationContext;
	   
	@Value("${ping_apiKey}")
	private String apiKey;

	@Value("${ping_live_apiKey}")
	private String liveApiKey;

	
	/**
	 * 关闭订单任务
	 * 
	 */
	// 每分钟执行一次 0 0/1 * * * ?
//	@Scheduled(cron = "0 0/1 * * * ? ")
	public void cancelOrder() throws AuthenticationException, InvalidRequestException, APIConnectionException,
			APIException, ChannelException, JsonProcessingException {
		// 商家10分钟没接单
		// 先退款(退款完成ping++异步通知)
		Pingpp.apiKey = liveApiKey;
		List<SysOrder> refundList = orderService.getRefundList();
		if (!refundList.isEmpty()) {
			for (SysOrder sysOrder : refundList) {
				if (sysOrder.getChargeId() != null && !"".equals(sysOrder.getChargeId())) {
					Charge charge = Charge.retrieve(sysOrder.getChargeId());
					Refund refund = payServce.refund(charge, "订单号:" + sysOrder.getOrderNo() + "，商家10分钟没有接单。系统自动退款");
					// 插入退款记录
					RefundRecord record = new RefundRecord();
					record.setOrderNo(sysOrder.getOrderNo());
					record.setRefundStatus(refund.getStatus());
					record.setAmount(refund.getAmount());
					record.setChargeId(sysOrder.getChargeId());
					record.setRefundId(refund.getId());
					record.setCreateDate(new Date());
					record.setRefundJson(m.writeValueAsString(refund));
					record.setChannel(charge.getChannel());
					record.setReason("商家10分钟未接单。系统自动退款");
					refundService.insertSelective(record);
//					rongService.sendMessageToOne(Long.valueOf(1), sysOrder.getUserId(), new TxtMessage("订单号:" + sysOrder.getOrderNo() + "，商家10分钟没有接单。系统自动退款"));
					
					//融云发送消息
//					TxtMessage message=new TxtMessage("订单号:" + sysOrder.getOrderNo() + "，商家10分钟没有接单。系统自动退款");//文本信息
//					applicationContext.publishEvent(new RongMessageEvent(Long.valueOf(1), sysOrder.getUserId().toString() , message));
					
					PushBean pushBean = new PushBean();
					pushBean.setRefId(sysOrder.getOrderNo());
					pushBean.setType(PushType.ORDERDETAIL_MSG);//跳转订单详情（orderNo，orderType）
					Map<String ,Object> param=new HashMap<String, Object>();
					param.put("orderType", sysOrder.getType());
					param.put("orderStatus", sysOrder.getOrderStatus());
				  	String jsonStr = m.writeValueAsString(param);
					pushBean.setJsonStr(jsonStr);
					pushBean.setContent("订单号:" + sysOrder.getOrderNo() + "，商家10分钟没有接单。系统自动退款");
					applicationContext.publishEvent(new PushMessageEvent(pushBean, "",sysOrder.getUserId().toString(),PushFrom.LINLE_USER));
				}
			}
		}

		// 关闭订单
		int noOperatResult = orderService.cancelNoOperatOrder();
		_logger.info("关闭订单执行成功:本次共关闭商家未操作订单" + noOperatResult + "条");
		System.out.println("关闭订单执行成功:本次共关闭商家未操作订单" + noOperatResult + "条");

		// 超过30分钟未支付的家园订单
		int payTimeOut = orderService.cancelPayTimeOutOrder();
		_logger.info("关闭订单执行成功:本次共关闭未支付订单" + payTimeOut + "条");
		System.out.println("关闭订单执行成功:本次共关闭未支付订单" + payTimeOut + "条");
		//30分钟未支付的车位订单
		int spaceTimeOut = orderService.cancelPaySpaceTimeOutOrder();
		_logger.info("关闭订单执行成功:本次共关闭未支付车位订单" + spaceTimeOut + "条");
		System.out.println("关闭订单执行成功:本次共关闭未支付车位订单" + spaceTimeOut + "条");
	}

	// 商家24小时未处理退款 自动退款 每小时执行一次
//	@Scheduled(cron = "0 0 * * * ?")
//	@Scheduled(cron = "0 0/1 * * * ? ")//测试，每分钟
	public void shopNoOperateRefundOrder() throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException, ChannelException, JsonProcessingException {
		int count = 0;
		Pingpp.apiKey = liveApiKey;
		List<OrderRefund> refundList = orderRefundService.selectShopNoOperateRefundOrder();
		if (!refundList.isEmpty()) {
			for (OrderRefund order : refundList) {
				if (order.getChargeId() != null && !"".equals(order.getChargeId())) {
					Charge charge = Charge.retrieve(order.getChargeId());
					Refund refund = payServce.refund(charge, "订单号:" + order.getOrderNo() + "，商家24小时没有处理退款订单。系统自动退款");
					// 插入退款记录
					RefundRecord record = new RefundRecord();
					record.setOrderNo(order.getOrderNo());
					record.setRefundStatus(refund.getStatus());
					record.setAmount(refund.getAmount());
					record.setChargeId(charge.getId());
					record.setRefundId(refund.getId());
					record.setCreateDate(new Date());
					record.setRefundJson(m.writeValueAsString(refund));
					record.setChannel(charge.getChannel());
					record.setReason("商家24小时未处理退款订单,系统自动退款");
					refundService.insertSelective(record);
					// 退款状态修改
					orderRefundService.updateRefundStatus(order.getOrderNo(), 3);
					// 订单状态修改
					orderService.updateOrderStatusByOrderNo(order.getOrderNo(), 8);
					
					//推送
					PushBean pushBean = new PushBean();
					pushBean.setRefId(order.getOrderNo());
					pushBean.setType(PushType.ORDERREFUND_MSG);//订单退款
					Map<String ,Object> param=new HashMap<String, Object>();
					param.put("refundStatus",3);
				  	String jsonStr = m.writeValueAsString(param);
					pushBean.setJsonStr(jsonStr);
					pushBean.setContent("订单号:" + order.getOrderNo() + "，商家24小时未处理退款订单,系统自动退款");
					applicationContext.publishEvent(new PushMessageEvent(pushBean, "",order.getUserId().toString(),PushFrom.LINLE_USER));
					
					count++;
				}
			}
		}
		// 自动退款
		_logger.info("商家24小时未处理退款订单,系统自动退款:" + count + "条");
	}

	// 社长24小时未处理 申请客服介入订单 自动退款 每小时执行一次
//	@Scheduled(cron = "0 0 * * * ?")
	public void presidentNoOperateRefundOrder() throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException, ChannelException, JsonProcessingException {
		int count = 0;
		Pingpp.apiKey = liveApiKey;
		List<OrderRefund> refundList = orderRefundService.selectPresidentNoOperateRefundOrder();
		if (!refundList.isEmpty()) {
			for (OrderRefund order : refundList) {
				if (order.getChargeId() != null && !"".equals(order.getChargeId())) {
					Charge charge = Charge.retrieve(order.getChargeId());
					Refund refund = payServce.refund(charge, "订单号:" + order.getOrderNo() + "，社长24小时未处理申请客服介入订单。系统自动退款");
					// 插入退款记录
					RefundRecord record = new RefundRecord();
					record.setOrderNo(order.getOrderNo());
					record.setRefundStatus(refund.getStatus());
					record.setAmount(refund.getAmount());
					record.setChargeId(charge.getId());
					record.setRefundId(refund.getId());
					record.setCreateDate(new Date());
					record.setRefundJson(m.writeValueAsString(refund));
					record.setChannel(charge.getChannel());
					record.setReason("社长24小时未处理申请客服介入订单。系统自动退款");
					refundService.insertSelective(record);
					// 退款状态修改
					orderRefundService.updateRefundStatus(order.getOrderNo(), 8);
					// 订单状态修改
					orderService.updateOrderStatusByOrderNo(order.getOrderNo(), 8);
					count++;
				}
			}
		}
		// 自动退款
		_logger.info("社长24小时未处理申请客服介入订单。系统自动退款:" + count + "条");
	}

	// 用户24小时未处理商家拒绝退款的订单 交易关闭
//	@Scheduled(cron = "0 0 * * * ?")
	public void userNoOperateRefundOrder() throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException, ChannelException, JsonProcessingException {
		int count = 0;
		Pingpp.apiKey = liveApiKey;
		List<OrderRefund> refundList = orderRefundService.selectUserNoOperateRefundOrder();
		if (!refundList.isEmpty()) {
			for (OrderRefund orderRefund : refundList) {
				orderRefund.setStatus(4);
				orderRefund.setFailReason("用户24小时内未申请客服介入，退款失败");
				orderRefund.setUpdateDate(new Date());
				// 退款状态修改
				orderRefundService.updateByPrimaryKeySelective(orderRefund);
				// 订单状态修改
				orderService.updateOrderStatusByOrderNo(orderRefund.getOrderNo(), 4);
				count++;
			}
		}
		// 自动退款
		_logger.info("用户24小时内未申请客服介入，退款失败,订单关闭:" + count + "条");
	}

	// 用户24小时未确认收货。系统自动确认
//	@Scheduled(cron = "0 0 * * * ?")
//	@Scheduled(cron = "0 0/1 * * * ? ")//测试，每分钟
	public void userNoOperateOrder() throws AuthenticationException, InvalidRequestException, APIConnectionException,
			APIException, ChannelException, JsonProcessingException {
		int count = 0;
		Pingpp.apiKey = liveApiKey;
		List<SysOrder> orderList = orderService.selectUserNoOperateOrder();
		if (!orderList.isEmpty()) {
			List<String> toUsers = new ArrayList<>();
			for (SysOrder order : orderList) {
				// 订单状态修改
				orderService.updateOrderStatusByOrderNo(order.getOrderNo(), 4);
				toUsers.add(order.getUserId().toString());
//				rongService.sendMessage(Long.valueOf(1), toUsers,
//						new TxtMessage("订单:" + order.getOrderNo() + "。下单已过24小时，系统自动确认。"));
				
				//融云发送消息
//				TxtMessage txtMessage=new TxtMessage("订单:" + order.getOrderNo() + "。下单已过24小时，系统自动确认。");//文本信息
//				applicationContext.publishEvent(new RongMessageEvent(Long.valueOf(1), toUsers, txtMessage));
				
				//推送
				PushBean pushBean = new PushBean();
				pushBean.setRefId(order.getOrderNo());
				pushBean.setType(PushType.ORDERDETAIL_MSG);//跳转订单详情（orderNo，orderType）
				Map<String ,Object> map=new HashMap<String, Object>();
				map.put("orderType", order.getType());
				map.put("orderStatus",  4);
			  	String jsonStr= m.writeValueAsString(map);
				pushBean.setJsonStr(jsonStr);
				pushBean.setContent("订单号:" + order.getOrderNo() + "，下单已过24小时，系统自动确认。");
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", order.getUserId().toString(),PushFrom.LINLE_USER));
				
				toUsers.clear();
				count++;
			}
		}
		// 自动退款
		_logger.info("用户24小时未确认收货。系统自动确认:" + count + "条");
	}
}
