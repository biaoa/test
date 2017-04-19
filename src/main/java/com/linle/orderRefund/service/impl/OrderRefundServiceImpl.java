package com.linle.orderRefund.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.OrderRefund;
import com.linle.entity.sys.RefundRecord;
import com.linle.entity.sys.SysOrder;
import com.linle.event.PushMessageEvent;
import com.linle.io.rong.service.RongService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.v1.model.OrderVo;
import com.linle.mobileapi.v1.model.RefundOrderDetailVO;
import com.linle.orderRefund.mapper.OrderRefundMapper;
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

@Service
@Transactional
public class OrderRefundServiceImpl extends CommonServiceAdpter<OrderRefund> implements OrderRefundService {
	
	@Autowired
	private OrderRefundMapper mapper;
	
	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private PayService payServce;
	
	@Autowired
	private RefundRecordService refundService; //这是系统退款记录
	
	@Autowired
	private RongService rongService;
	
	@Override
	public OrderRefund selectOrderRefundByOrderNo(String orderNo) {
		return mapper.selectOrderRefundByOrderNo(orderNo);
	}
	
	@Value("${ping_apiKey}")
	private String apiKey;
	
	@Value("${ping_live_apiKey}")
	private String liveApiKey;
	
	private ObjectMapper objM = new ObjectMapper();

	@Override
	public BaseResponse shopRefundOrder(OrderRefund refund) {
		
		SysOrder order = orderService.getOrderByOrderNo(refund.getOrderNo(),refund.getUserId());
		order.setOrderNo(refund.getOrderNo());
		//发送消息id
		List<String> toUserIds = new ArrayList<>();
		toUserIds.add(order.getUserId().toString());
		String msg ="";
		//同意
		if(refund.getStatus()==1){
			order.setOrderStatus(8);
			//退款
			Pingpp.apiKey = liveApiKey;
			Charge charge = null;
			try {
				charge = Charge.retrieve(order.getChargeId());
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException
					| ChannelException e) {
				e.printStackTrace(); _logger.error("出错了", e);
				_logger.error("退款出现异常");
				 throw new RuntimeException("1");//有异常全部回滚
			}
			Refund refundrecord = payServce.refund(charge, "订单号:"+order.getOrderNo()+",商家同意退款");
			//插入退款结果 ping++
			insertRefundRecord(refundrecord,order);
			msg=",商家已同意退款。请留意退款信息";
		}else if(refund.getStatus()==2){
			//不同意
			msg=",商家拒绝退款,退款原因:"+refund.getFailReason()+"24小时内可申请客服介入，若不申请，24小时后系统自动关闭";
		}
		order.setUpdateDate(new Date());
		//修改退款记录状态
		updateByPrimaryKeySelective(refund);
		//修改订单状态
		orderService.updateByPrimaryKeySelective(order);
		//发送消息 FIXME 这里的发送人是写死的，要改活
//		rongService.sendMessage(Long.valueOf(1),toUserIds, new TxtMessage("订单:"+order.getOrderNo()+ message));
		
		//融云发送消息
//		TxtMessage message=new TxtMessage("订单:"+order.getOrderNo()+ msg);//文本信息
//		applicationContext.publishEvent(new RongMessageEvent(Long.valueOf(1), toUserIds, message));
		try {
			PushBean pushBean = new PushBean();
			pushBean.setRefId(order.getOrderNo());
			pushBean.setType(PushType.ORDERREFUND_MSG);//跳转订单退款
			Map<String ,Object> param=new HashMap<String, Object>();
			param.put("refundStatus", refund.getStatus());
		  	String jsonStr = objM.writeValueAsString(param);
			pushBean.setJsonStr(jsonStr);
			pushBean.setContent("订单号:"+order.getOrderNo()+msg);
			applicationContext.publishEvent(new PushMessageEvent(pushBean, "",order.getUserId().toString() ,PushFrom.LINLE_USER));
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("1");//有异常全部回滚
		}
		
		
		return BaseResponse.OperateSuccess;
	}
	
	public boolean insertRefundRecord(Refund refund,SysOrder order){
		//插入退款记录
		RefundRecord record = new RefundRecord();
		record.setOrderNo(order.getOrderNo());
		record.setRefundStatus(refund.getStatus());
		record.setAmount(refund.getAmount());
		record.setChargeId(order.getChargeId());
		record.setRefundId(refund.getId());
		record.setCreateDate(new Date());
		try {
			Charge charge =Charge.retrieve(refund.getCharge());
			record.setChannel(charge.getChannel());
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException
				| ChannelException e1) {
			e1.printStackTrace();
			 throw new RuntimeException("1");//有异常全部回滚
		}
		try {
			record.setRefundJson(m.writeValueAsString(refund));
		} catch (JsonProcessingException e) {
			e.printStackTrace(); _logger.error("出错了", e);
			 throw new RuntimeException("1");//有异常全部回滚
		}
		return refundService.insertSelective(record)>0;
	}

	@Override
	public List<OrderVo> getRefundListAndDetail(Map<String, Object> map) {
		return mapper.getRefundListAndDetail(map);
	}

	@Override
	public RefundOrderDetailVO selectDetailForAPI(String orderNo) {
		return mapper.selectDetailForAPI(orderNo);
	}

	@Override
	public Page<OrderRefund> getRefundListByPresident(Page<OrderRefund> page) {
		page.setResults(mapper.getRefundListByPresident(page));
		return page;
	}

	@Override
	public boolean applyCustomerServices(Map<String, Object> map) {
		//申请客服介入
		return mapper.applyCustomerServices(map)>0;
	}

	@Override
	public void updateRefundStatus(String orderNo, Integer status) {
		OrderRefund refund = selectOrderRefundByOrderNo(orderNo);
		refund.setStatus(status);
		refund.setUpdateDate(new Date());
		SysOrder order = orderService.getOrderByOrderNo(orderNo,refund.getUserId());
		//发送消息id
		List<String> toUserIds = new ArrayList<>();
		toUserIds.add(order.getUserId().toString());
		String message ="";
		//同意
		if(refund.getStatus()==6){
			order.setOrderStatus(8);
			//退款
			Pingpp.apiKey = liveApiKey;
			Charge charge = null;
			try {
				charge = Charge.retrieve(order.getChargeId());
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException
					| ChannelException e) {
				e.printStackTrace(); _logger.error("出错了", e);
				_logger.error("退款出现异常");
				 throw new RuntimeException("1");//有异常全部回滚
			}
			Refund refundrecord = payServce.refund(charge, "订单号:"+order.getOrderNo()+",社长同意退款");
			//插入退款结果 ping++
			insertRefundRecord(refundrecord,order);
			message=",社长已同意退款。请留意退款信息";
		}else if(refund.getStatus()==7){
			//不同意
			order.setOrderStatus(9);
			refund.setFailReason("社长拒绝退款，退款失败");
			message=",社长拒绝退款,订单关闭";
		}else if(refund.getStatus()==0){
			order.setOrderStatus(8);
			message=",社长24小时未操作,系统自动退款";
		}
		order.setUpdateDate(new Date());
		//修改退款记录状态
		updateByPrimaryKeySelective(refund);
		//修改订单状态
		orderService.updateByPrimaryKeySelective(order);
		//发送消息 FIXME 这里的发送人是写死的，要改活
//		rongService.sendMessage(Long.valueOf(1),toUserIds, new TxtMessage("订单:"+order.getOrderNo()+ message));
		
		//融云发送消息
//		TxtMessage txtMessage= new TxtMessage("订单:"+order.getOrderNo()+ message);//文本信息
//		applicationContext.publishEvent(new RongMessageEvent(Long.valueOf(1), toUserIds, txtMessage));
		
		//推送
		try {
			PushBean pushBean = new PushBean();
			pushBean.setRefId(order.getOrderNo());
			pushBean.setType(PushType.ORDERREFUND_MSG);//订单退款
			Map<String ,Object> param=new HashMap<String, Object>();
			param.put("refundStatus",refund.getStatus());
		  	String jsonStr = objM.writeValueAsString(param);
			pushBean.setJsonStr(jsonStr);
			pushBean.setContent("订单号:"+order.getOrderNo()+ message);
			applicationContext.publishEvent(new PushMessageEvent(pushBean, "",order.getUserId().toString(),PushFrom.LINLE_USER));
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("1");//有异常全部回滚
		}
	}

	@Override
	public List<OrderRefund> selectShopNoOperateRefundOrder() {
		return mapper.selectShopNoOperateRefundOrder();
	}

	@Override
	public List<OrderRefund> selectPresidentNoOperateRefundOrder() {
		return mapper.selectPresidentNoOperateRefundOrder();
	}

	@Override
	public List<OrderRefund> selectUserNoOperateRefundOrder() {
		return mapper.selectUserNoOperateRefundOrder();
	}
	
	


}
