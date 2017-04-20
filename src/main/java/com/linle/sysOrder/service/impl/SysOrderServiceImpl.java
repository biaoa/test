package com.linle.sysOrder.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.BroadbandFee.service.BroadbandFeeService;
import com.linle.capitalManage.vo.TransactionflowVo;
import com.linle.commodity.service.CommodityService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateUtil;
import com.linle.common.util.JsonUtil;
import com.linle.common.util.OrderCode;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.entity.statistics.BaseStatistics;
import com.linle.entity.statistics.OrderStatistics;
import com.linle.entity.sys.BroadbandFee;
import com.linle.entity.sys.Commodity;
import com.linle.entity.sys.OrderDetail;
import com.linle.entity.sys.OrderRefund;
import com.linle.entity.sys.PropertyFee;
import com.linle.entity.sys.RefundRecord;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.ShopPreferential;
import com.linle.entity.sys.SpaceRecord;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.entity.sys.Utilities;
import com.linle.entity.vo.SalesVO;
import com.linle.event.PushMessageEvent;
import com.linle.event.SystemMsgEvent;
import com.linle.funds.vo.Incomedetail;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.shop.model.OrderInfoVo;
import com.linle.mobileapi.shop.request.AcceptOrderRequest;
import com.linle.mobileapi.v1.model.OrderVo;
import com.linle.mobileapi.v1.model.ShopGoods;
import com.linle.mobileapi.v1.request.CommodityRequest;
import com.linle.mobileapi.v1.request.OperateOrderRequest;
import com.linle.mobileapi.v1.request.OrderDetailVo2;
import com.linle.mobileapi.v1.request.RefundOrderRequest;
import com.linle.mobileapi.v1.response.CommodityResponse;
import com.linle.orderRefund.service.OrderRefundService;
import com.linle.orderdetail.service.OrderDetailService;
import com.linle.parkingspace.service.ParkingSpaceService;
import com.linle.pay.service.PayService;
import com.linle.preferentialActivityRecord.service.PreferentialActivityRecordService;
import com.linle.propertyFee.service.PropertyFeeService;
import com.linle.refundRecord.service.RefundRecordService;
import com.linle.shop.service.ShopService;
import com.linle.shopPreferential.service.ShopPreferentialService;
import com.linle.socket.msg.BaseWebSocketMsgDataVo;
import com.linle.socket.msg.model.WebSocketMsg;
import com.linle.spaceRecord.service.SpaceRecordService;
import com.linle.sysOrder.mapper.SysOrderMapper;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.sysOrder.vo.CommodityOrderSendInfo;
import com.linle.user.service.UserInfoService;
import com.linle.utilities.service.UtilitiesService;
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
public class SysOrderServiceImpl extends CommonServiceAdpter<SysOrder> implements SysOrderService {

	@Autowired
	private SysOrderMapper mapper;
	
	@Autowired
	private CommodityService commodityService;//商品
	
	@Autowired
	private ShopPreferentialService preferentialService; //商家优惠
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private OrderDetailService detailService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private OrderRefundService refundService;
	
	@Autowired
	private PayService payServce;
	
	@Autowired
	private RefundRecordService refundRecordService;
	
	@Autowired
	private ParkingSpaceService parkService;
	
	@Value("${ping_live_apiKey}")
	private String liveApiKey;

	@Autowired
	private UtilitiesService utilitiesService;
	
	@Autowired
	private PropertyFeeService propertyFeeService;
	
	@Autowired
	private BroadbandFeeService broadbandFeeService;
	
	
	ObjectMapper objM=new ObjectMapper();
	
	@Override
	public SysOrder getOrderByOrderNo(String orderNo,Long uid) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderNo);
		map.put("uid", uid);
		return mapper.getOrderByOrderNo(map);
	}

	@Override
	public List<OrderVo> getOrderList(Map<String, Object> map) {
		return mapper.getOrderList(map);
	}

	@Override
	public SysOrder findOrder(Map<String, Object> map) {
		return mapper.findOrder(map);
	}

	@Override
	public SysOrder selectByOrderTypeAndBusinessType(String type, Long sid) {
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("businessId", sid);
		return mapper.selectByOrderTypeAndBusinessType(map);
	}

	@Override
	public boolean updateOrderPrice(SysOrder order) {
		return mapper.updateOrderPrice(order) > 0;
	}

	@Override
	public boolean closeOrder(SysOrder order) {
		return mapper.closeOrder(order) > 0;
	}

	@Override
	public List<OrderVo> getOrderListAndDetail(Map<String, Object> map) {
		return mapper.getOrderListAndDetail(map);
	}

	@Override
	public boolean accepted(SysOrder order) {
		return mapper.accepted(order) > 0;
	}

	@Override
	public boolean waitieforEeceiving(SysOrder order) {
		return mapper.waitieforEeceiving(order) > 0;
	}

	@Override
	public boolean orderSuccess(SysOrder order) {
		//这里要改成两步操作
		//第一步把user_id+order_no的支付时间改了
		mapper.orderSuccess(order);
		Map<String, Object> map = new HashMap<>();
		map.put("status", order.getOrderStatus());
		map.put("orderNo", order.getOrderNo());
		//第二步把order_no的状态改了
		mapper.updateOrderStatusByOrderNo(map);
		return true;
	}

	@Override
	public OrderDetailVo2 getOrderDetail(String orderNo, String orderType,Long uid) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderNo);
		map.put("orderType", orderType);
		map.put("uid",uid );
		return mapper.getOrderDetail(map);
	}

	@Override
	public int getStatusCount(Long uid, int status) {
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("status", status);
		return mapper.getStatusCount(map);
	}

	@Override
	public BaseResponse createCommodityOrder(CommodityRequest req) {
		Subject subject = SecurityUtils.getSubject();
		Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
		CommodityResponse res = new CommodityResponse();
		Shop shop =  shopService.selectByPrimaryKey(req.getShopId());
		if(shop.getOperateStatus()==1){
			return new BaseResponse(1,"该商家已歇业");
		}
		List<ShopGoods> goodsList = new ArrayList<>();
		goodsList = JsonUtil.StringToList(req.getGoodsList(),JsonUtil.getCollectionType(ArrayList.class, ShopGoods.class));
		if (goodsList.isEmpty()) {
			return BaseResponse.JSONPARSEEXCEPTION;
		}
		// 判断商品数量和库存
		for (ShopGoods shopGoods : goodsList) {
			if(commodityService.verifyQuantity(shopGoods)==0){
				return new BaseResponse(1, shopGoods.getGoodName()+"库存不足");
			}
		}
		// 第二步 判断总价
		BigDecimal total = new BigDecimal("0");//
//		BigDecimal actualAmount = req.getActualAmount(); //实际支付金额
		int activityEnableNums=0;//活动商品可购买总数
		boolean isCanBuyActivityProduct=true;
		for (ShopGoods shopGoods : goodsList) {
			Commodity commodity = commodityService.selectByPrimaryKey(shopGoods.getId());
			total = total.add((commodity.getPrice().multiply(new BigDecimal(shopGoods.getBuyNums()))));
			HashMap<String, Object> map=new HashMap<>();
			map.put("userId", user.getId());
			map.put("shopId", commodity.getShopId());
			map.put("productId", commodity.getId());
			activityEnableNums=this.getBuyActivityProductNumByUserId(map);
			if(activityEnableNums==0){//判断订单包含活动商品购买次数，某用户是否达到最大购买次数，暂时每人每种商品最多体验三次
				isCanBuyActivityProduct=false;
			}
		}
		if(!isCanBuyActivityProduct){
			return new BaseResponse(1, "该订单包含活动商品,活动商品每人每种商品最多只能购买三次");
		}
		System.out.println("客户端传来的总价："+req.getTotalAmount());
		System.out.println("服务端计算出的总价："+total);
		if(total.compareTo(req.getTotalAmount())!=0){
			return new BaseResponse(1, "总价不符");
		}
		//判断优惠价格
		BigDecimal temp = new BigDecimal("0");
		if(StringUtil.isNotNull(req.getPrivilegeId())){
			String ids[] = req.getPrivilegeId().split(",");
			List<ShopPreferential> preferential = preferentialService.selectPreferentials(ids);
			if(preferential.size()!=ids.length){
				return new BaseResponse(1, "优惠列表有误");
			}
			for (ShopPreferential shopPreferential : preferential) {
				if(req.getTotalAmount().compareTo(shopPreferential.getMeetPrice())!=-1){
					temp = temp.add(shopPreferential.getPreferentialPrice());
				}
			}
			System.out.println("客户端传来的优惠价格："+req.getTotalAmount().subtract(req.getActualAmount()));
			System.out.println("服务端计算出的优惠价格："+temp);
			if (temp.compareTo(req.getTotalAmount().subtract(req.getActualAmount()))!=0) {
				return new BaseResponse(1,"优惠价格不符");
			}
		}
		// 第三步 生成订单

		//插入订单信息
		String orderNo = OrderCode.GenerationOrderCode();
		SysOrder order = new SysOrder();
		order.setOrderNo(orderNo);
		order.setType("commodity");
		order.setDetails(req.getShopId().toString()); //商铺ID
		order.setOrderStatus(1); //家园订单 默认待支付
		order.setUserId(user.getId());
		order.setBusinessType("家园购物");
		order.setBusinessId(shop.getId());
		order.setBusinessName(shop.getShopName());
		order.setCreateDate(new Date());
		order.setRemark("家园购物");
		order.setBuyerAddress(user.getCommunity().getName()+user.getAddressDetails().getOverall());
		order.setBuyerPhone(user.getMobilePhone());
		order.setTotalMoney(req.getActualAmount());
		order.setBuyerMessage(req.getBuyerMessage());
		order.setPreferentialAmount(temp);
		order.setDeliveryFee(shop.getDeliveryFee());//插入配送费
		order.setCommunityId(user.getCommunity().getId());//小区ID
		order.setBeginDate(DateUtil.StringToDate(req.getBeginDate()));
		order.setEndDate(DateUtil.StringToDate(req.getEndDate()));
		order.setLastRefundDate(DateUtil.addHour(new Date(), shop.getRefundTime())); //下单的时候增加商家最晚退款时间。
		int count = mapper.getShopTodayOrderCount(shop.getId());
		order.setSingle(++count);
		int orderResult = this.insertSelective(order);
		if(orderResult<0){
			return new BaseResponse(1,"插入订单出错");
		}
		// 第四步 插入订单明细
		Commodity commodity = new Commodity();
		for (ShopGoods shopGoods : goodsList) {
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(order.getId());
			detail.setProductName(shopGoods.getGoodName());
			detail.setProductQuantity(shopGoods.getBuyNums());
			detail.setRemark("家园购物");
			detail.setCreateDate(new Date());
			detail.setProductId(shopGoods.getId());
			detail.setOrderType("commodity");
			detail.setPicture(shopGoods.getUrl());
			detail.setContent(order.getRemark()+" "+detail.getProductName());
			detail.setProductPrice(new BigDecimal(shopGoods.getGoodPrice()));
			int result =  detailService.insertSelective(detail);
			commodity.setId(shopGoods.getId());
			commodity.setQuantity(shopGoods.getBuyNums());
			int modifyResult = commodityService.updateQuantity(commodity);//修改商品数量
			if(result<0||modifyResult<0){
				return new BaseResponse(1, "插入详情出错");
			}
			
		}
		res.setCode(0);
		res.setMsg("提交成功");
		res.setOrderNo(orderNo);
		return res;
	}
	
	@Override
	public int getBuyActivityProductNumByUserId(HashMap<String, Object>  map) {
		return mapper.getBuyActivityProductNumByUserId(map);
	}
	
	@Override
	public SysOrder selectByOrderNo(String orderNo) {
		return mapper.selectByOrderNo(orderNo);
	}
	@Override
	public int selectCountByOrderNo(String orderNo) {
		return mapper.selectCountByOrderNo(orderNo);
	}
	
	@Override
	public int updateSendDate(SysOrder order) {
		return mapper.updateSendDate(order);
	}

	@Override
	public int cancelNoOperatOrder() {
		return mapper.cancelNoOperatOrder();
	}

	@Override
	public int cancelPayTimeOutOrder() {
		return mapper.cancelPayTimeOutOrder();
	}

	@Override
	public boolean updateChargeId(Map<String, Object> params) {
		return mapper.updateChargeId(params)>0;
	}

	@Override
	public List<SysOrder> getRefundList() {
		return mapper.getRefundList();
	}

	@Override
	public BaseResponse operateOrder(OperateOrderRequest req, SysOrder order) {
		switch (order.getType()) {
		case "commodity": //家园订单
			order.setOrderStatus(req.getStatus());
			updateOrderStatus(order);
			break;
//		case "space": //车位预定
//			
//			break;
//		case "sent": //寄件
//			
//			break;
//		case "spaceRenew": //车位续费
//			
//			break;
//		case "spaceLease": //车位转租
//			
//			break;
		default:
			break;
		}
		
		return BaseResponse.OperateSuccess;
	}

	@Override
	public boolean delOrder(SysOrder order) {
		return mapper.delOrder(order)>0;
	}
	
	public boolean updateOrderStatus(SysOrder order){
		return mapper.updateOrderStatus(order)>0;
	}
	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private SpaceRecordService spaceRecordService;
	
	@Autowired
	private PreferentialActivityRecordService RecordService;
	
	//交易成功业务操作
	public BaseResponse orderSuccess(long userId,String orderNo) {
		try {
				Users userInfo = null;
				Date d = new Date();
				Charge charge = new Charge();
				userInfo = userInfoService.getById(userId);
				Map<String, Object> map = new HashMap<>();
				map.put("uid", userInfo.getId());
				map.put("orderNo", orderNo);
				SysOrder order = this.findOrder(map);
				if (order != null) {
					//支付成功 修改优惠活动记录表中的记录
					RecordService.updatePreferentialActivityRecord(order.getOrderNo());
					if ("space".equals(order.getType())) {
						// 插入购买记录
						List<OrderDetail> details = orderDetailService.getDetailList(order.getId());
						for (OrderDetail orderDetail : details) {
							SpaceRecord record = new SpaceRecord();
							record.setUserId(userInfo.getId());
							record.setOrderNo(order.getOrderNo());
							record.setCommunityId(userInfo.getCommunity().getId());
							record.setType(order.getDetails());
							record.setBeginDate(order.getCreateDate());
							record.setEndDate(DateUtil.DateAddByType(order.getCreateDate(), order.getDetails()));
							record.setStatus(0);
							record.setCreateDate(new Date());
							record.setGrageId(orderDetail.getProductId());
							record.setSpaceNo(orderDetail.getOther());
							record.setSpaceInfo(orderDetail.getProductName());
							spaceRecordService.insertSelective(record);
						}
						charge = payServce.retrieve(order.getChargeId());
						if(charge!=null){
							order.setPayDate(new Date(charge.getTimePaid()));
						}
						order.setOrderStatus(4);// 将订单状态改为已完成
						this.orderSuccess(order);
						return BaseResponse.OperateSuccess;
					} else if ("spaceRenew".equals(order.getType())) {
						List<OrderDetail> detailList = orderDetailService.getDetailList(order.getId());
						OrderDetail detail = detailList.get(0);
						Map<String, Object> spaceInfoMap = new HashMap<>();
						spaceInfoMap.put("grageid", detail.getProductId()); // 车位
						spaceInfoMap.put("space", detail.getOther()); // 车位
						// 修改车位记录
						SpaceRecord record = spaceRecordService.selectSpaceInfo(spaceInfoMap);
						spaceInfoMap.put("type", order.getDetails());
						spaceInfoMap.put("id", record.getId());
						spaceInfoMap.put("endDate", record.getEndDate());
						spaceRecordService.updateEndDate(spaceInfoMap);
						charge = payServce.retrieve(order.getChargeId());
						if(charge!=null){
							order.setPayDate(new Date(charge.getTimePaid()));
						}
						order.setOrderStatus(4);// 将订单状态改为已完成
						this.orderSuccess(order);
						return BaseResponse.OperateSuccess;
					} else if ("commodity".equals(order.getType())) {
						//订单30分钟支付时间
						if (d.getTime()-order.getCreateDate().getTime()>1800000) {
							return new BaseResponse(1, "订单已过期");
						}
						charge = payServce.retrieve(order.getChargeId());
						if(charge!=null){
							order.setPayDate(new Date(charge.getTimePaid()));
						}
						//判断订单是否是做活动的，判断订单包含商品是否有活动商品，若有一个商品，则该订单就是活动订单
						boolean isJoinActivity=this.checkOrderIsJoinActivity(order.getId());
						if(isJoinActivity){
							order.setOrderStatus(2);//待收货
						}else{
							order.setOrderStatus(0);
						}
						this.updateOrderStatus(order);
						String msg="您有新的订单,请及时接单";
						PushBean pushBean = new PushBean();
						pushBean.setRefId(order.getOrderNo());
						pushBean.setType(PushType.ORDER_MSG);
						pushBean.setContent(msg);
						Shop shop = shopService.selectByPrimaryKey(order.getBusinessId());
						applicationContext.publishEvent(new PushMessageEvent(pushBean, "", shop.getUser().getId()+"",PushFrom.LINLE_SHOP));
						return BaseResponse.OperateSuccess;
					}else if("broadband".equals(order.getType()) || "cableTelevision".equals(order.getType())){
						charge = payServce.retrieve(order.getChargeId());
						if(charge!=null){
							order.setPayDate(new Date(charge.getTimePaid()));
						}
						order.setOrderStatus(4); //已完成
						this.orderSuccess(order);
						broadbandFeeService.paySuccessupdateStatus(order.getOrderNo());
						feeMsg(order.getType(),userInfo,order.getRemark());
						return BaseResponse.OperateSuccess;
					}else if("water".equals(order.getType()) || "electricity".equals(order.getType()) || "gas".equals(order.getType()) ){
						charge = payServce.retrieve(order.getChargeId());
						if(charge!=null){
							order.setPayDate(new Date(charge.getTimePaid()));
						}
						order.setOrderStatus(4); //已完成
						this.orderSuccess(order);
						//修改缴费记录中的订单支付时间
						utilitiesService.paySuccessupdateStatus(order.getOrderNo());
						feeMsg(order.getType(),userInfo,order.getRemark());
						return BaseResponse.OperateSuccess;
					}else if("propertyFee".equals(order.getType())){
						charge = payServce.retrieve(order.getChargeId());
						if(charge!=null){
							order.setPayDate(new Date(charge.getTimePaid()));
						}
						order.setOrderStatus(4); //已完成
						this.orderSuccess(order);
						propertyFeeService.paySuccessupdateStatus(order.getOrderNo());
						feeMsg(order.getType(),userInfo,order.getRemark());
						return BaseResponse.OperateSuccess;
					}
				}
				return new BaseResponse(1, "订单不存在");
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	/***
	 * 判断订单是否是做活动的，判断订单包含商品是否有活动商品，若有一个商品，则该订单就是活动订单，目前活动商家录入的商品都是活动商品
	 */
	@Override
	public boolean checkOrderIsJoinActivity(long orderId){
		return mapper.checkOrderIsJoinActivity(orderId)==1?true:false;
	}
	public void feeMsg(String orderType,Users user,String body) throws JsonProcessingException{
		String url ="";
		switch (orderType) {
		case "broadband":
			url = "/broadbandFee/list?type=1";
			break;
		case "cableTelevision":
			url = "/broadbandFee/list?type=2";
			break;
		case "water":
			url = "/utilities/list?type=1";
			break;
		case "electricity":
			url = "/utilities/list?type=2";
			break;
		case "gas":
			url = "/utilities/list?type=3";
			break;
		case "propertyFee":
			url = "/propertyFee/list";
			break;
		default:
			break;
		}
		BaseWebSocketMsgDataVo msgDate = new BaseWebSocketMsgDataVo();
		msgDate.setTilte("缴费提醒");
		msgDate.setBody(body+"，请及时查看");
		msgDate.setUrl(url);
		WebSocketMsg msg = new WebSocketMsg();
		msg.setFrom((long)1);
		msg.setTo(user.getCommunity().getUser().getId());
		msg.setObj(m.writeValueAsString(msgDate));
		msg.setSendDate(new Date());
		applicationContext.publishEvent(new SystemMsgEvent("communityMsg",msg));
	}
		
	@Override
	public Page<SysOrder> selectCommodityOrderList(Page<SysOrder> page) {
		page.setResults(mapper.getCommodityOrderList(page));
		return page;
	}

	/**
	 * 获得交易订单
	 */
	@Override
	public SysOrder getCommodityOrderDetail(Map<String, Object> detailMap) {
		return mapper.getCommodityOrderDetail(detailMap);
	}

	/**
	 * 商品交易订单发货信息
	 */
	@Override
	public CommodityOrderSendInfo getSendInfo(String orderNo) {
		return mapper.getSendInfo(orderNo);
	}

	@Override
	public boolean shoperOperateOrder(Map<String, Object> map) {
		Integer status = (Integer) map.get("status");
		String orderNo = (String) map.get("orderNo");
		//发送消息
		SysOrder sysOrder = selectByOrderNo(orderNo);
		List<String> toUserIds = new ArrayList<>();
		toUserIds.add(sysOrder.getUserId().toString());
		String msg=",商家接单";
		if(status==5){
			try {
				msg="，商家拒绝接单。系统退款";
				Pingpp.apiKey = liveApiKey;
				Charge charge = Charge.retrieve(sysOrder.getChargeId());
				Refund refund = payServce.refund(charge, "订单号:"+sysOrder.getOrderNo()+"，商家拒绝接单。系统退款");
				//插入退款记录
				RefundRecord record = new RefundRecord();
				record.setOrderNo(sysOrder.getOrderNo());
				record.setRefundStatus(refund.getStatus());
				record.setAmount(refund.getAmount());
				record.setChargeId(sysOrder.getChargeId());
				record.setRefundId(refund.getId());
				record.setCreateDate(new Date());
				record.setRefundJson(m.writeValueAsString(refund));
				record.setChannel(charge.getId());
				refundRecordService.insertSelective(record);
				
			} catch (Exception e) {
				e.printStackTrace(); _logger.error("出错了", e);
				throw new RuntimeException("1");//有异常全部回滚
			}
		}
			
			try {
				//推送
				PushBean pushBean = new PushBean();
				pushBean.setRefId(sysOrder.getOrderNo());
				pushBean.setType(PushType.ORDERDETAIL_MSG);//跳转订单详情
				Map<String ,Object> param=new HashMap<String, Object>();
				param.put("orderType", sysOrder.getType());
				param.put("orderStatus", status);
			  	String jsonStr = objM.writeValueAsString(param);
				pushBean.setJsonStr(jsonStr);
				pushBean.setContent("订单号:"+sysOrder.getOrderNo()+msg);
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", sysOrder.getUserId().toString(),PushFrom.LINLE_USER));
			} catch (JsonProcessingException e) {
				e.printStackTrace(); _logger.error("出错了", e);
			}
			
//		rongService.sendMessage(Long.valueOf(1),toUserIds, new TxtMessage("订单号:"+sysOrder.getOrderNo()+message));
		
		//融云发送消息
//		TxtMessage message=new TxtMessage("订单号:"+sysOrder.getOrderNo()+msg);//文本信息
//		applicationContext.publishEvent(new RongMessageEvent(Long.valueOf(1), toUserIds, message));
		
		
		return mapper.shoperOperateOrder(map)>0;
	}

	/**
	 * 退货订单
	 */
	@Override
	public BaseResponse refundOrder(SysOrder order,RefundOrderRequest req,Long folderId) {
		List<String> toUserIds = new ArrayList<>();

		//插入退款记录
		OrderRefund refund = new OrderRefund();
		refund.setOrderNo(order.getOrderNo());
		refund.setUserId(order.getUserId());
		refund.setShopId(order.getBusinessId());
		refund.setCommunityId(order.getCommunityId());
		refund.setDescription(req.getDescription());
		refund.setFolderId(folderId);
		refund.setStatus(0);//待处理
		refund.setCreateDate(new Date());
		refund.setRefundType(req.getRefundType());
		int refundResult = refundService.insertSelective(refund);
		//修改订单状态
		order.setOrderStatus(7);
		boolean resut = updateOrderStatus(order);
		//给商家通知 webIM FIXME 这里的发送人是新建一个系统账户呢 还是直接用户的id
		Shop shop = shopService.selectByPrimaryKey(order.getBusinessId());
		
		toUserIds.add(shop.getUser().getId().toString());
//		rongService.sendMessage(order.getUserId(), toUserIds, new TxtMessage("用户申请退款。订单号为:"+order.getOrderNo()+"，请及时处理"));
		
		//融云发送消息
//		TxtMessage message=new TxtMessage("用户申请退款。订单号为:"+order.getOrderNo()+"，请及时处理");//文本信息
//		applicationContext.publishEvent(new RongMessageEvent(order.getUserId(), toUserIds, message));
	
			//推送
			PushBean pushBean = new PushBean();
			pushBean.setRefId(order.getOrderNo());
			pushBean.setType(PushType.ORDERREFUND_MSG);
//			Map<String ,Object> param=new HashMap<String, Object>();
//			param.put("refundStatus", 0);
//		  	String jsonStr = objM.writeValueAsString(param);
//			pushBean.setJsonStr(jsonStr);
			pushBean.setContent("用户申请退款。订单号为:"+order.getOrderNo()+"，请及时处理");
			String[] array = new String[toUserIds.size()];
			String[] toUserIdsArr=toUserIds.toArray(array);
			applicationContext.publishEvent(new PushMessageEvent(pushBean, "", toUserIdsArr,PushFrom.LINLE_SHOP));
		
		System.out.println("退款记录结果:"+refundResult+",订单状态结果:"+resut);
		return new BaseResponse(0, "申请成功，请等待商家处理");
	}

	@Override
	public boolean updateOrderStatusByOrderNo(String orderNo, Integer status) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo",orderNo);
		map.put("status", status);
		return mapper.updateOrderStatusByOrderNo(map)>0;
	}
	
	@Override
	public boolean updateOrderStatusByHouseNumber(Map<String, Object> map) {
		return mapper.updateOrderStatusByHouseNumber(map)>0;
	}
	
	
	@Override
	public boolean updateTotalMoneyByOrderNo(String orderNo,  BigDecimal totalMoney) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo",orderNo);
		map.put("total_money", totalMoney);
		return mapper.updateTotalMoneyByOrderNo(map)>0;
	}
	
	@Override
	public int selectDayValidOrderCount(Long id) {
		return mapper.selectDayValidOrderCount(id);
	}

	@Override
	public BigDecimal selectDayTurnover(Long id) {
		return mapper.selectDayTurnover(id);
	}
	@Override
	public BigDecimal selectAllShopTurnover(Map<String, Object> map) {
		return mapper.selectAllShopTurnover(map);
	}
	@Override
	public List<BaseStatistics> getTurnoverByCommunity(Map<String, Object> map){
		return mapper.getTurnoverByCommunity(map);
	}
	
	@Override
	public OrderStatistics getOrderCountStatistics(Map<String, Object> map){
		return mapper.getOrderCountStatistics(map);
	}
	@Override
	public OrderStatistics getOrderAmountStatistics(Map<String, Object> map){
		return mapper.getOrderAmountStatistics(map);
	}
	
	@Override
	public List<BaseStatistics> getValidOrderByCommunity(Map<String, Object> map){
		return mapper.getValidOrderByCommunity(map);
	}
	
	@Override
	public int selectAllShopValidOrderCount(Map<String, Object> map) {
		return mapper.selectAllShopValidOrderCount(map);
	}
	
	
	@Override
	public BigDecimal getAllTurnover(Long id) {
		return mapper.getAllTurnover(id);
	}

	@Override
	public BigDecimal getMonthTurnover(Long id) {
		return mapper.getMonthTurnover(id);
	}

	@Override
	public BigDecimal getWeekTurnover(Long id) {
		return mapper.getWeekTurnover(id);
	}

	@Override
	public List<SysOrder> selectUserNoOperateOrder() {
		return mapper.selectUserNoOperateOrder();
	}

	@Override
	public int cancelPaySpaceTimeOutOrder() {
		int count = 0;
		List<SysOrder> list = mapper.PaySpaceTimeOutOrder();
		for (SysOrder sysOrder : list) {
			if("space".equals(sysOrder.getType())){
				parkService.freedSpace(sysOrder.getOrderNo());
			}else if("spaceRenew".equals(sysOrder.getType())){
				
			}
			sysOrder.setOrderStatus(5);
			sysOrder.setUpdateDate(new Date());
			mapper.updateByPrimaryKeySelective(sysOrder);
			count++;
		}
		return count;
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
			record.setRefundJson(m.writeValueAsString(refund));
		} catch (JsonProcessingException e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("1");//有异常全部回滚
		}
		return refundRecordService.insertSelective(record)>0;
	}

	@Override
	public BaseResponse noOperateCanceOrder(SysOrder order) {
		List<String> toUserIds = new ArrayList<>();
		order.setOrderStatus(5);
		toUserIds.add(order.getUserId().toString());
//		rongService.sendMessage(Long.valueOf(1), toUserIds, new TxtMessage("订单:"+order.getOrderNo()+""+"退款成功。请留意"));
		
		//融云发送消息
//		TxtMessage message=new TxtMessage("订单:"+order.getOrderNo()+""+"退款成功。请留意");//文本信息
//		applicationContext.publishEvent(new RongMessageEvent(Long.valueOf(1), toUserIds, message));
		
		updateOrderStatus(order);
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
		Refund refundrecord = payServce.refund(charge, "订单号:"+order.getOrderNo()+",用户在商家接单前取消订单");
		//插入退款结果 ping++
		insertRefundRecord(refundrecord,order);
		
		try {
			//推送
			PushBean pushBean = new PushBean();
			pushBean.setRefId(order.getOrderNo());
			pushBean.setType(PushType.ORDERDETAIL_MSG);//跳转订单详情（orderNo，orderType）
			Map<String ,Object> param=new HashMap<String, Object>();
			param.put("orderType", order.getType());
			param.put("orderStatus", order.getOrderStatus());
		  	String jsonStr = m.writeValueAsString(param);
			pushBean.setJsonStr(jsonStr);
			
			pushBean.setContent("订单:"+order.getOrderNo()+""+"退款成功。请留意");
			applicationContext.publishEvent(new PushMessageEvent(pushBean, "", order.getUserId().toString(),PushFrom.LINLE_USER));
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return BaseResponse.OperateSuccess;
	}

	@Override
	public SalesVO getShopSales(Long id, int day) {
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", id);
		map.put("day", day);
		return mapper.getShopSales(map);
	}

	@Override
	public Page<Incomedetail> getShopIncomedetailList(Page<Incomedetail> page) {
		page.setResults(mapper.getShopIncomedetailList(page));
		return page;
	}

	@Override
	public BigDecimal getPaymetIncome(Long communityId, String type) {
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", communityId);
		map.put("type", type);
		return mapper.getPaymetIncome(map);
	}

	@Override
	public BigDecimal getCommunityShopShare(Long communityId) {
		return mapper.getCommunityShopShare(communityId);
	}

	@Override
	public BigDecimal getCommunitySpaceIncome(Long communityId) {
		return mapper.getCommunitySpaceIncome(communityId);
	}

	@Override
	public Page<Incomedetail> getCommunitydetailList(Page<Incomedetail> page) {
		page.setResults(mapper.getCommunitydetailList(page));
		return page;
	}

	@Override
	public List<OrderInfoVo> getShopOrderListForAPI(Map<String, Object> map) {
		return mapper.getShopOrderListForAPI(map);
	}

	@Override
	public BaseResponse acceptOrder(AcceptOrderRequest req, Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("status",2);
		map.put("shopId", id);
		List<String> toUserIds = new ArrayList<>();
		if ("0".equals(req.getType()) || "1".equals(req.getType())) {
			Map<String, Object> params = new HashMap<>();
			params.put("orderNo", req.getOrderNo());
			params.put("id", id);
			List<SysOrder> list = mapper.selectWaitAcceptOrder(params);
			for (SysOrder sysOrder : list) {
				map.put("orderNo", sysOrder.getOrderNo());
				toUserIds.add(sysOrder.getUserId().toString());
				mapper.shoperOperateOrder(map);
				
				//rongService.sendMessage(Long.valueOf(1),toUserIds, new TxtMessage("订单号:"+sysOrder.getOrderNo()+"商家已接单"));
				//融云发送消息
//				TxtMessage message=new TxtMessage("订单号:"+sysOrder.getOrderNo()+"商家已接单");//文本信息
//				applicationContext.publishEvent(new RongMessageEvent(Long.valueOf(1), toUserIds, message));
				
				try {
					//推送
					PushBean pushBean = new PushBean();
					pushBean.setRefId(sysOrder.getOrderNo());
					pushBean.setType(PushType.ORDERDETAIL_MSG);//跳转订单详情
					Map<String ,Object> param=new HashMap<String, Object>();
					param.put("orderType", sysOrder.getType());
					param.put("orderStatus", sysOrder.getOrderStatus());
				  	String jsonStr = objM.writeValueAsString(param);
					pushBean.setJsonStr(jsonStr);
					pushBean.setContent("订单号:"+sysOrder.getOrderNo()+"商家已接单");
					applicationContext.publishEvent(new PushMessageEvent(pushBean, "", sysOrder.getUserId().toString(),PushFrom.LINLE_USER));
				} catch (JsonProcessingException e) {
					e.printStackTrace(); _logger.error("出错了", e);
				}
				
			}
			return BaseResponse.OperateSuccess;
		}else{
			return new BaseResponse(1, "类型异常");
		}
	}

	@Override
	public List<String> alonesendMessageUser(String orderNo) {
		return mapper.alonesendMessageUser(orderNo);
	}

	
	/**
	 * 用户注册时，创建相应缴费订单
	 */
	@Override
	public boolean createFeeOrderAndDetailByRoomNo(Users users) {
		long roomId=users.getAddressDetails().getId();
		long communityId=users.getCommunity().getId();
		//1.查询水电费燃气费,创建相应订单
		List<Utilities> utilitiesList=utilitiesService.selectUtilitiesByHousenumber(roomId,communityId);
		if(utilitiesList.size()>0){
			for (Utilities utilities : utilitiesList) {
				if(utilities.getPayable().compareTo(BigDecimal.ZERO)==1){//大于0
					//如果已经创建了，则不再生成，如果未创建，则生成一条新的
					boolean isCanCreate=true;
					
					//SysOrder order = mapper.getOrderByOrderNo(utilities.getOrderNo(), users.getId());
					SysOrder order = this.getOrderByOrderNo(utilities.getOrderNo(), users.getId());
					if(order != null){//如果订单已生成，则不再生成
						isCanCreate = false;
					}
					
					if(isCanCreate){
						utilitiesService.createFeeOrderAndDetail(utilities, users);
					}
					
				}
			}
		}
		//2.查询物业费,创建相应订单
		List<PropertyFee> propertyFeeList=propertyFeeService.selectPropertyFeeByHousenumber(roomId,communityId);
		if(propertyFeeList.size()>0){
			for (PropertyFee propertyFee : propertyFeeList) {
				if(propertyFee.getPayable().compareTo(BigDecimal.ZERO)==1){//大于0
					//如果已经创建了，则不再生成，如果未创建，则生成一条新的
					boolean isCanCreate=true;
					
					SysOrder order = this.getOrderByOrderNo(propertyFee.getOrderNo(), users.getId());
					if(order != null){//如果订单已生成，则不再生成
						isCanCreate = false;
					}
					
					if(isCanCreate){
						propertyFeeService.createFeeOrderAndDetail(propertyFee, users);
					}
				}
			}
		}
		//3.查询宽带费，有限电视费,创建相应订单
		List<BroadbandFee> broadbandFeeList=broadbandFeeService.selectBroadbandFeeByHousenumber(roomId,communityId);
		if(broadbandFeeList.size()>0){
			for (BroadbandFee broadbandFee : broadbandFeeList) {
				if(broadbandFee.getPayable().compareTo(BigDecimal.ZERO)==1){//大于0
					//如果已经创建了，则不再生成，如果未创建，则生成一条新的
					boolean isCanCreate=true;
					
					SysOrder order = this.getOrderByOrderNo(broadbandFee.getOrderNo(), users.getId());
					if(order != null){//如果订单已生成，则不再生成
						isCanCreate = false;
					}
					
					if(isCanCreate){
						broadbandFeeService.createFeeOrderAndDetail(broadbandFee, users);
					}
				}
			}
		}
		
		return true;
	}
	
	@Override
	public SysOrder getOneOrderByOrderNo(String orderNo) {
		return mapper.getOneOrderByOrderNo(orderNo);
	}

	@Override
	public Page<TransactionflowVo> getTransactionflow(Page<TransactionflowVo> page) {
		page.setResults(mapper.getTransactionflow(page));
		return page;
	}
	
	
	@Override
	public BaseStatistics getSalesStatisticss(Map<String, Object> map) {
		return mapper.getSalesStatisticss(map);
	}
	
	@Override
	public BaseStatistics getSalesStatisticssByYear(Map<String, Object> map) {
		return mapper.getSalesStatisticssByYear(map);
	}
	
	@Override
	public BaseStatistics getOrdersStatisticss(Map<String, Object> map) {
		return mapper.getOrdersStatisticss(map);
	}
	
	@Override
	public BaseStatistics getOrdersStatisticssByYear(Map<String, Object> map) {
		return mapper.getOrdersStatisticssByYear(map);
	}

	@Override
	public void sayHello() {
		System.err.println("hello");
	}
	
	
}
