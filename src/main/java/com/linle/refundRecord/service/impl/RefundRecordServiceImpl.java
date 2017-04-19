package com.linle.refundRecord.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.RefundRecord;
import com.linle.entity.sys.SysOrder;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.refundRecord.mapper.RefundRecordMapper;
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
public class RefundRecordServiceImpl extends CommonServiceAdpter<RefundRecord> implements RefundRecordService {
	
	@Autowired
	private RefundRecordMapper mapper;
	
	@Autowired
	private SysOrderService orderService;
	
	@Value("${ping_live_apiKey}")
	private String liveApiKey;
	
	@Override
	public Page<RefundRecord> selectRefundOrderList(Page<RefundRecord> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public BaseResponse resetRefund(String orderNo) {
		//第一步 查到退款信息
		RefundRecord recode = mapper.selectByOrderNo(orderNo);
		if(recode!=null){
			Pingpp.apiKey = liveApiKey;
			Charge charge = null;
			Refund refund = null;
			Map<String, Object> params = new HashMap<String, Object>();
	        params.put("description", recode.getReason());
			try {
				charge = Charge.retrieve(recode.getChargeId());
				 refund = charge.getRefunds().create(params);
			} catch (AuthenticationException  | APIConnectionException | APIException
					| ChannelException e) {
				e.printStackTrace(); _logger.error("出错了", e);
				return new BaseResponse(1, "退款服务异常,请稍后重试");
			}catch (InvalidRequestException e) {
				return new BaseResponse(1, "该订单已经退款");
			}
			try {
				recode.setRefundJson(m.writeValueAsString(refund));
				mapper.updateByPrimaryKeySelective(recode);
				Map<String, Object> map = new HashMap<>();
				map.put("url", recode.getUrl());
				return new BaseResponse(0, "获取成功", map);
			} catch (JsonProcessingException e) {
				e.printStackTrace(); _logger.error("出错了", e);
			}
		}
		return new BaseResponse(1, "没有查询到该退款订单");
	}

	@Override
	public BaseResponse confirmRefund(String orderNo) {
		//修改订单状态
		SysOrder order = orderService.selectByOrderNo(orderNo);
		order.setOrderStatus(8);
		orderService.updateOrderStatus(order);
		//修改退款状态
		RefundRecord recode = mapper.selectByOrderNo(orderNo);
		recode.setRefundStatus("succeeded");
		recode.setSuccessDate(new Date());
		mapper.updateByPrimaryKeySelective(recode);
		return BaseResponse.OperateSuccess;
	}


}
