package com.linle.sentInfo.service.impl;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateUtil;
import com.linle.common.util.OrderCode;
import com.linle.common.util.Page;
import com.linle.communityExpress.service.CommunityExpressService;
import com.linle.entity.sys.CommunityExpress;
import com.linle.entity.sys.OrderDetail;
import com.linle.entity.sys.SentInfo;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.SentRequest;
import com.linle.orderdetail.service.OrderDetailService;
import com.linle.sentInfo.mapper.SentInfoMapper;
import com.linle.sentInfo.service.SentInfoService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.user.service.UserInfoService;

@Service
@Transactional
public class SentInfoServiceImpl extends CommonServiceAdpter<SentInfo> implements SentInfoService {
	
	@Autowired
	private SentInfoMapper mapper;
	
	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private CommunityExpressService expressService;
	
	@Autowired
	private OrderDetailService detailService;
	
	@Override
	public BaseResponse createSentOrder(SentRequest req) {
		
		Subject subject = SecurityUtils.getSubject();
		Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
		//插入寄件信息
		SentInfo sent = new SentInfo();
		sent.setUserId(user.getId());
		sent.setCommunityId(user.getCommunity().getId());
		//FIXME 这里的名字放的是用户的昵称，以后如果要改成真实姓名，可以从实名认证记录表中查询出来
		sent.setSenderName(user.getName());
		sent.setSenderPhone(user.getMobilePhone());
		sent.setSenderAddress(req.getSentAddress());
		sent.setAddresseeName(req.getAddresseeName());
		sent.setAddresseePhone(req.getAddresseePhone());
		sent.setAddresseeAddress(req.getAddresseeAddress());
		sent.setExpressId(req.getExpressId());
		sent.setReservationBeginDate(DateUtil.StringToDate(req.getBeginDate()));
		sent.setReservationEndDate(DateUtil.StringToDate(req.getEndDate()));
		sent.setRemark(req.getRemark());
		sent.setStatus(0);
		sent.setCreateDate(new Date());
		int sentResult = mapper.insertSelective(sent);
		//插入订单信息
		SysOrder order = new SysOrder();
		order.setOrderNo(OrderCode.GenerationOrderCode());
		order.setType("sent");
		order.setDetails(sent.getId().toString());
		order.setOrderStatus(0);
		order.setUserId(user.getId());
		order.setBusinessType("小区物业");
		order.setBusinessId(user.getCommunity().getId());
		order.setBusinessName(user.getCommunity().getName());
		order.setCreateDate(new Date());
		order.setRemark("寄件");
		order.setBuyerAddress(user.getCommunity().getName()+user.getAddressDetails().getOverall());
		order.setBuyerPhone(user.getMobilePhone());
		order.setCommunityId(user.getCommunity().getId());
		int orderResult = orderService.insertSelective(order);
		//得到快递名称
		CommunityExpress express = expressService.selectByPrimaryKey(req.getExpressId());
		//插入明细表
		OrderDetail detail = new OrderDetail();
		detail.setOrderId(order.getId());
		detail.setProductName(express!=null?express.getExpressName():"随机");
		detail.setProductQuantity(1);
		detail.setRemark("代寄快件");
		detail.setCreateDate(new Date());
		detail.setProductId(express.getId());
		detail.setOrderType("sent");
		detail.setPicture("resources/images/order/order-kuaidi.png");
		detail.setContent(order.getRemark()+" "+detail.getProductName());
		int detailisok =  detailService.insertSelective(detail);
		boolean isok = sentResult>0&&orderResult>0&&detailisok>0;
		return new BaseResponse(isok?0:1, isok?"提交成功":"提交失败");
	}

	@Override
	public Page<SentInfo> getAllsentOrder(Page<SentInfo> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public boolean updateStatus(SentInfo sent) {
		return mapper.updateStatus(sent)>0;
	}

	@Override
	public boolean cancelSent(String orderNo) {
		return mapper.cancelSent(orderNo)>0;
	}
	
	
}
