package com.linle.mobileapi.v1.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linle.BroadbandFee.service.BroadbandFeeService;
import com.linle.commodity.service.CommodityService;
import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.common.util.LimitUtil;
import com.linle.common.util.SysConfig;
import com.linle.entity.sys.Commodity;
import com.linle.entity.sys.OrderDetail;
import com.linle.entity.sys.OrderRefund;
import com.linle.entity.sys.SentInfo;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.SpaceRecord;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.event.PushMessageEvent;
import com.linle.event.SystemMsgEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.v1.model.DealAddressVo;
import com.linle.mobileapi.v1.request.ApplyCustomerServicesRequest;
import com.linle.mobileapi.v1.request.DelOrderRequest;
import com.linle.mobileapi.v1.request.OperateOrderRequest;
import com.linle.mobileapi.v1.request.OrderCountRequest;
import com.linle.mobileapi.v1.request.OrderDetailRequest;
import com.linle.mobileapi.v1.request.OrderDetailVo2;
import com.linle.mobileapi.v1.request.OrderRequest;
import com.linle.mobileapi.v1.request.OrderSuccessRequest;
import com.linle.mobileapi.v1.request.RefundOrderListAndDetailRequest;
import com.linle.mobileapi.v1.request.RefundOrderRequest;
import com.linle.mobileapi.v1.response.OrderCountResponse;
import com.linle.mobileapi.v1.response.OrderDetailResponse;
import com.linle.mobileapi.v1.response.OrderResponse;
import com.linle.mobileapi.v1.response.RefundOrderDetailResponse;
import com.linle.mobileapi.v1.response.RefundOrderListAndDetailRes;
import com.linle.orderRefund.service.OrderRefundService;
import com.linle.orderdetail.service.OrderDetailService;
import com.linle.parkingspace.service.ParkingSpaceService;
import com.linle.preferentialActivityRecord.service.PreferentialActivityRecordService;
import com.linle.propertyFee.service.PropertyFeeService;
import com.linle.sentInfo.service.SentInfoService;
import com.linle.shop.service.ShopService;
import com.linle.socket.msg.BaseWebSocketMsgDataVo;
import com.linle.socket.msg.model.WebSocketMsg;
import com.linle.spaceRecord.service.SpaceRecordService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.utilities.service.UtilitiesService;

/**
 * 
 * @ClassName: ParkingSpaceAPIController
 * @Description: 订单
 * @author pangd
 * @date 2016年4月11日20:46:42
 *
 */
@Controller
@RequestMapping("/api/1")
public class SysOrderAPIController extends BaseController {

	@Autowired
	private SysOrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private SpaceRecordService spaceRecordService;

	@Autowired
	private SentInfoService sentInfoService;
	
	@Autowired
	private CommodityService commodityService;
	
	@Autowired
	private ParkingSpaceService parkService;
	
	@Autowired
	private BroadbandFeeService broadbandFeeService;
	
	@Autowired
	private UtilitiesService utilitiesService;
	
	@Autowired
	private PropertyFeeService propertyFeeService;
	
	@Autowired
	private OrderRefundService refundService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private PreferentialActivityRecordService RecordService;

	@ResponseBody
	@RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
	public BaseResponse spaceList(OrderRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());

				Map<String, Object> map = new HashMap<>();
				map.put("uid", userInfo.getId());
				map.put("type", req.getType());
				map.put("status", req.getStatus());
				LimitUtil.limit(map, req.getPageSize(), req.getPageNumber());
				OrderResponse res = new OrderResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				System.out.println(m.writeValueAsString(map));
				res.setData(orderService.getOrderList(map));
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getOrderListAndDetail", method = RequestMethod.POST)
	public BaseResponse getOrderListAndDetail(OrderRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws JsonProcessingException {
		System.out.println(m.writeValueAsString(req));
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Map<String, Object> map = new HashMap<>();
				map.put("uid", userInfo.getId());
				map.put("type", req.getType());
				map.put("status", req.getStatus());
				OrderResponse res = new OrderResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				LimitUtil.limit(map, req.getPageSize(), req.getPageNumber());
				res.setData(orderService.getOrderListAndDetail(map));
				System.out.println(m.writeValueAsString(res));
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	@ResponseBody
	@RequestMapping(value = "orderSuccess", method = RequestMethod.POST)
	public BaseResponse orderSuccess(OrderSuccessRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {/*
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			Date d = new Date();
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());

				Map<String, Object> map = new HashMap<>();
				map.put("uid", userInfo.getId());
				map.put("orderNo", req.getOrderNo());
				SysOrder order = orderService.findOrder(map);
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
						order.setPayDate(new Date());
						order.setOrderStatus(4);// 将订单状态改为已完成
						orderService.orderSuccess(order);
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
						order.setPayDate(new Date());
						order.setOrderStatus(4);// 将订单状态改为已完成
						orderService.orderSuccess(order);
						return BaseResponse.OperateSuccess;
					} else if ("commodity".equals(order.getType())) {
						//订单30分钟支付时间
						if (d.getTime()-order.getCreateDate().getTime()>1800000) {
							return new BaseResponse(1, "订单已过期");
						}
						order.setPayDate(new Date());
						//判断订单是否是做活动的，判断订单包含商品是否有活动商品，若有一个商品，则该订单就是活动订单
						boolean isJoinActivity=orderService.checkOrderIsJoinActivity(order.getId());
						if(isJoinActivity){
							order.setOrderStatus(2);//待收货
						}else{
							order.setOrderStatus(0);
						}
						orderService.updateOrderStatus(order);
						String msg="您有新的订单,请及时接单";
						PushBean pushBean = new PushBean();
						pushBean.setRefId(order.getOrderNo());
						pushBean.setType(PushType.ORDER_MSG);
						pushBean.setContent(msg);
						Shop shop = shopService.selectByPrimaryKey(order.getBusinessId());
						applicationContext.publishEvent(new PushMessageEvent(pushBean, "", shop.getUser().getId()+"",PushFrom.LINLE_SHOP));
						return BaseResponse.OperateSuccess;
					}else if("broadband".equals(order.getType()) || "cableTelevision".equals(order.getType())){
						order.setPayDate(new Date());
						order.setOrderStatus(4); //已完成
						orderService.orderSuccess(order);
						broadbandFeeService.updateStatus(order.getOrderNo());
						feeMsg(order.getType(),userInfo,order.getRemark());
						return BaseResponse.OperateSuccess;
					}else if("water".equals(order.getType()) || "electricity".equals(order.getType()) || "gas".equals(order.getType()) ){
						order.setPayDate(new Date());
						order.setOrderStatus(4); //已完成
						orderService.orderSuccess(order);
						utilitiesService.updateStatus(order.getOrderNo());
						feeMsg(order.getType(),userInfo,order.getRemark());
						return BaseResponse.OperateSuccess;
					}else if("propertyFee".equals(order.getType())){
						order.setPayDate(new Date());
						order.setOrderStatus(4); //已完成
						orderService.orderSuccess(order);
						propertyFeeService.updateStatus(order.getOrderNo());
						feeMsg(order.getType(),userInfo,order.getRemark());
						return BaseResponse.OperateSuccess;
					}
				}
				return new BaseResponse(1, "订单不存在");
			}
			return BaseResponse.PleaseSignIn;*/
			return BaseResponse.OperateSuccess;	
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 根据订单编号和类型。获得订单性情
	@ResponseBody
	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.POST)
	public BaseResponse getOrderDetail(@Valid OrderDetailRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws JsonProcessingException {
		try {
			Subject subject = SecurityUtils.getSubject();
			 Users userInfo = null;
			if (subject.isAuthenticated()) {
				OrderDetailResponse res = new OrderDetailResponse();
				 userInfo =
				 userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				 SysOrder order = orderService.getOrderByOrderNo(req.getOrderNo(),userInfo.getId());
					if (order == null) {
						return new BaseResponse(1, "订单不存在");
					}
				OrderDetailVo2 detail = orderService.getOrderDetail(req.getOrderNo(), order.getType(),userInfo.getId());
				detail.setCommunityAmount(detail.getTotalMoney().add(detail.getPreferentialAmount())
						.subtract(detail.getDeliveryAmount()));
				DealAddressVo buyer = new DealAddressVo();
				switch (order.getType()) {
				case "sent": // 寄件
					SentInfo info = sentInfoService.selectByPrimaryKey(Long.valueOf(order.getDetails()));
					detail.setRemark(info.getRemark());
					// 买家信息
					buyer.setName(info.getSenderName());
					buyer.setPhone(info.getSenderPhone());
					buyer.setAddress(info.getSenderAddress());
					detail.setBuyersAddress(buyer);
					// 卖家信息
					DealAddressVo seller = new DealAddressVo();
					seller.setName(info.getAddresseeName());
					seller.setPhone(info.getAddresseePhone());
					seller.setAddress(info.getAddresseeAddress());
					detail.setSellerAddress(seller);
					break;
				case "space": // 预定
					buyer.setName(detail.getUser().getName());
					buyer.setPhone(detail.getBuyerPhone());
					buyer.setAddress(detail.getBuyerAddress());
					detail.setBuyersAddress(buyer);
					break;
				case "spaceLease": // 转租
					buyer.setName(detail.getUser().getName());
					buyer.setPhone(detail.getBuyerPhone());
					buyer.setAddress(detail.getBuyerAddress());
					detail.setBuyersAddress(buyer);
					break;
				case "spaceRenew": // 续费
					buyer.setName(detail.getUser().getName());
					buyer.setPhone(detail.getBuyerPhone());
					buyer.setAddress(detail.getBuyerAddress());
					detail.setBuyersAddress(buyer);
					break;
				case "commodity": // 家园购物
					buyer.setName(detail.getUser().getName());
					buyer.setPhone(detail.getBuyerPhone());
					buyer.setAddress(detail.getBuyerAddress());
					Shop shop = shopService.selectByPrimaryKey(order.getBusinessId()) ;
					detail.setRefundTime(shop.getRefundTime()+"");
					detail.setBuyersAddress(buyer);
				default:
					break;
				}
				res.setCode(0);
				res.setMsg("获取成功");
				res.setData(detail);
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	//获得订单数量
	@ResponseBody
	@RequestMapping(value = "/getOrderCount", method = RequestMethod.POST)
	public BaseResponse getOrderCount(@Valid OrderCountRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Users userInfo = null;
			Long uid =(long) 0;
			Subject subject = SecurityUtils.getSubject();
			OrderCountResponse res = new OrderCountResponse();
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				uid = userInfo.getId();
			}
			res.setWaitHandleCount(orderService.getStatusCount(uid, 0));// 待处理
			res.setWaitPaymentCount(orderService.getStatusCount(uid, 1));// 待付款
			res.setWaitReceiptCount(orderService.getStatusCount(uid, 2));// 待收货
			res.setWatiEstimationCount(orderService.getStatusCount(uid, 3));// 待评价
			res.setRefundsCount(orderService.getStatusCount(uid, 7));// 退款
			res.setCode(0);
			res.setMsg("获取成功");
			return res;
		} catch (Exception e) {
			_logger.error("getOrderCount方法出错");
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	//操作订单
	@RequestMapping(value = "operateOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse operateOrder(@Valid OperateOrderRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = getCurrentUser();
			if (subject.isAuthenticated()) {
				SysOrder order = orderService.selectByOrderNo(req.getOrderNo());
				if(order==null){
					return new BaseResponse(1, "订单不存在");
				}
				if(!userInfo.getId().equals(order.getUserId())){
					return new BaseResponse(1, "非本人订单");
				}
				return orderService.operateOrder(req,order);
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("处理订单出现异常。订单号为："+req.getOrderNo()+"。异常信息："+e);
			return BaseResponse.ServerException;
		}
	}
	//删除订单
	@RequestMapping(value = "delOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse delOrder(DelOrderRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = getCurrentUser();
			if (subject.isAuthenticated()) {
				SysOrder order = orderService.selectByOrderNo(req.getOrderNo());
				if(order==null){
					return new BaseResponse(1, "订单不存在");
				}
				if(!userInfo.getId().equals(order.getUserId())){
					return new BaseResponse(1, "非本人订单");
				}
				order.setDelFlag(1);
				orderService.delOrder(order);
				return BaseResponse.OperateSuccess;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("删除订单出现异常。订单号为："+req.getOrderNo()+"。异常信息："+e);
			return BaseResponse.ServerException;
		}
	}
	
	//订单提醒
	@RequestMapping(value = "remindOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse remindOrder(DelOrderRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = getCurrentUser();
			if (subject.isAuthenticated()) {
				SysOrder order = orderService.selectByOrderNo(req.getOrderNo());
				if(order==null){
					return new BaseResponse(1, "订单不存在");
				}
				if(!userInfo.getId().equals(order.getUserId())){
					return new BaseResponse(1, "非本人订单");
				}
				//根据状态来确定提醒内容，您有一个订单已付款，需要您及时接单。订单编号XXXXXXX.
				String msg="";
				if(order.getOrderStatus()==0){//待处理
					 msg="您有一个订单已付款，需要您及时接单。订单编号："+order.getOrderNo();
				}else if(order.getOrderStatus()==2){//待收货
					 msg="您有一个订单已付款，需要您及时发货。订单编号："+order.getOrderNo();
				}
				PushBean pushBean = new PushBean();
				pushBean.setRefId(order.getOrderNo());
				pushBean.setType(PushType.ORDER_MSG);
				pushBean.setContent(msg);
				Shop shop = shopService.selectByPrimaryKey(order.getBusinessId());
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", shop.getUser().getId()+"",PushFrom.LINLE_SHOP));
				return BaseResponse.OperateSuccess;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("订单提醒出现异常。订单号为："+req.getOrderNo()+"。异常信息："+e);
			_logger.info("订单提醒出现异常。订单号为："+req.getOrderNo()+"。异常信息："+e);
			return BaseResponse.ServerException;
		}
	}
	
	//用户取消订单
	@RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse cancelOrder(DelOrderRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = getCurrentUser();
			if (subject.isAuthenticated()) {
				SysOrder order = orderService.selectByOrderNo(req.getOrderNo());
				if(order==null){
					return new BaseResponse(1, "订单不存在");
				}
				if(!userInfo.getId().equals(order.getUserId())){
					return new BaseResponse(1, "非本人订单");
				}
				if(order.getOrderStatus()==0 && order.getType().equals("commodity")){
					return orderService.noOperateCanceOrder(order);
				}
				if(order.getPayDate()!=null){
					return new BaseResponse(1, "已支付订单不能取消");
				}
				order.setOrderStatus(5);
				orderService.updateByPrimaryKeySelective(order);
				switch (order.getType()) {
				case "commodity":
					//家园购物 要去吧商品数量修改回来
					List<OrderDetail> detailList = orderDetailService.getDetailList(order.getId());
					Commodity commodity = new Commodity();
					for (OrderDetail orderDetail : detailList) {
						commodity.setId(orderDetail.getProductId());
						commodity.setQuantity(-orderDetail.getProductQuantity());
						commodityService.updateQuantity(commodity);
					}
					break;
				case "space":
					//释放车位
					parkService.freedSpace(order.getOrderNo());
					break;
				case "sent":
					sentInfoService.cancelSent(order.getDetails());
					break;
				default:
					break;
				}
				return BaseResponse.OperateSuccess;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("删除订单出现异常。订单号为："+req.getOrderNo()+"。异常信息："+e);
			return BaseResponse.ServerException;
		}
	}
	
	//申请退款
	@RequestMapping(value="/refundOrder",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse refundOrder(RefundOrderRequest req,Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users userInfo = getCurrentUser();
				SysOrder order = orderService.getOrderByOrderNo(req.getOrderNo(),userInfo.getId());
				if(order==null){
					return new BaseResponse(1, "订单不存在");
				}
				//只能退自己的订单
				if(!order.getUserId().equals(userInfo.getId())){
					return new BaseResponse(1,"不能操作非本人订单");
				}
				//只有家园的订单 才能退款
				if (!"commodity".equals(order.getType())) {
					return new BaseResponse(1,"该类型订单不能退款");
				}
				//待处理，待收货，待评价，已完成订单才能退款
				if(order.getOrderStatus()!=0 && order.getOrderStatus()!=2 && order.getOrderStatus()!=3 && order.getOrderStatus()!=4){
					return new BaseResponse(1, "该状态下的订单不能退款");
				}
				//判断订单是否是做活动的，判断订单包含商品是否有活动商品，若有一个商品，则该订单就是活动订单
				boolean isJoinActivity=orderService.checkOrderIsJoinActivity(order.getId());
				if(isJoinActivity){
					return new BaseResponse(1, "做活动商品订单在待收货状态下不能退款");
				}
				Long folderId = doFile(req.getList(), servletRequest,SysConfig.ORDER_REFUND);
				return orderService.refundOrder(order,req,folderId);
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("退款出现异常。订单号为："+req.getOrderNo()+"。异常信息："+e);
			return BaseResponse.ServerException;
		}
	}
	//退款列表
	@RequestMapping(value="/getRefundOrderListAndDetail",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getRefundOrderListAndDetail(RefundOrderListAndDetailRequest req,Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		RefundOrderListAndDetailRes res = new RefundOrderListAndDetailRes();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users userInfo = getCurrentUser();
				Map<String, Object> map = new HashMap<>();
				map.put("uid", userInfo.getId());
				LimitUtil.limit(map, req.getPageSize(), req.getPageNumber());
				res.setCode(0);
				res.setMsg("success");
				res.setData(refundService.getRefundListAndDetail(map));
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("获得退款订单列表出错");
			return BaseResponse.ServerException;
		}
	}
	//退款详情
	@RequestMapping(value="/getRefundOrderDetail",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getRefundDetail(@Valid OrderDetailRequest req,Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				RefundOrderDetailResponse res = new RefundOrderDetailResponse();
				res.setCode(0);
				res.setMsg("success");
				res.setData(refundService.selectDetailForAPI(req.getOrderNo()));
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.info("获得退款详情出错");
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	//申请客服介入
	@RequestMapping(value="applyCustomerServices",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse applyCustomerServices(@Valid ApplyCustomerServicesRequest req,Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users userInfo = getCurrentUser();
				//判断订单是否能退款
				OrderRefund refund = refundService.selectOrderRefundByOrderNo(req.getOrderNo());
				if (refund==null) {
					return new BaseResponse(1,"退款订单不存在");
				}
				if (DateUtil.DateDiffReturnHour(new Date(),refund.getUpdateDate())>24) {
					return new BaseResponse(1,"已过24小时，不能退款");
				}
				
				Map<String, Object> map = new HashMap<>();
				map.put("uid", userInfo.getId());
				map.put("orderNo", req.getOrderNo());
				refundService.applyCustomerServices(map);
				return BaseResponse.OperateSuccess;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("申请客服介入出错");
			return BaseResponse.ServerException;
		}
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
		applicationContext.publishEvent(new SystemMsgEvent(msg.getTo().toString(),msg));
	}
}
