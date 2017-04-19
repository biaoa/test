package com.linle.sysOrder.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.communityPresident.service.CommunityPresidentService;
import com.linle.entity.sys.CommunityPresident;
import com.linle.entity.sys.OrderRefund;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.SysOrder;
import com.linle.event.RongMessageEvent;
import com.linle.io.rong.models.TxtMessage;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.orderEvaluate.service.OrderEvaluateService;
import com.linle.orderRefund.service.OrderRefundService;
import com.linle.shop.service.ShopService;
import com.linle.sysOrder.service.SysOrderService;


@RequestMapping("/sysorder")
@Controller
public class SysOrderController extends BaseController{
	
	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private OrderEvaluateService EvaluateService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private OrderRefundService refundService;
	
	@Autowired
	private CommunityPresidentService CommunityPresidentService;
	
	@RequiresPermissions("commodity_order_manage")
	@RequestMapping(value="/commodityOrderList",method=RequestMethod.GET)
	public String commodityOrderList(Integer pageNo,String orderNo,String buyer,String beginDate ,String endDate,Integer status,Model model){
		
		try {
			if(buyer!=null){
				buyer=new String(buyer.getBytes("iso8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		Page<SysOrder> page = new Page<SysOrder>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		params.put("shopId", getShopByUserID().getId());
		params.put("orderNo", orderNo);
		params.put("buyer", buyer);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("status", status);
		page.setParams(params);
		try {
			orderService.selectCommodityOrderList(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("商家订单出现错误："+e);
		}
		
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("buyer", buyer);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		model.addAttribute("pagelist", page);
		
		model.addAttribute("status_", (status == null || -1 == status) ? "current" : "");
		model.addAttribute("status0", (status != null && status == 0) ? "current" : "");
		model.addAttribute("status2", (status != null && status == 2) ? "current" : "");
		model.addAttribute("status3", (status != null && status == 3) ? "current" : "");
		model.addAttribute("status4", (status != null && status == 4) ? "current" : "");
		model.addAttribute("status5", (status != null && status == 5) ? "current" : "");
		model.addAttribute("status7", (status != null && status == 7) ? "current" : "");
		return "/sysorder/commodity_order_list";
	}
	
	@RequiresPermissions(value={"commodity_order_manage","refund_manage"},logical=Logical.OR)
	@RequestMapping(value="/commodityOrderDetail/{orderNo}",method=RequestMethod.GET)
	public String commodityOrderDetail(@PathVariable String orderNo,Model model){
		Map<String, Object> detailMap = new HashMap<>();
		SysOrder order = orderService.selectByOrderNo(orderNo);
		Long shopId = order.getBusinessId();
		//订单明细
		detailMap.put("shopId",shopId);
		detailMap.put("orderNo", orderNo);
		model.addAttribute("orderDetail", orderService.getCommodityOrderDetail(detailMap));
		//配送信息
		model.addAttribute("sendInfo", orderService.getSendInfo(orderNo));
		//评价信息
		model.addAttribute("evaluate", EvaluateService.getOrderEvaluate(detailMap));
		//退款信息
		model.addAttribute("refund", refundService.selectOrderRefundByOrderNo(orderNo));
		return "/sysorder/commodity_order_detail";
	}
	
	//商家操作订单
	@ResponseBody
	@RequestMapping(value="/shoperOperateOrder",method=RequestMethod.POST)
	public BaseResponse shoperOperateOrder(String orderNo,Integer status){
		try {
			SysOrder order = orderService.selectByOrderNo(orderNo);
			if(order.getOrderStatus()!=0){
				return new BaseResponse(1, "订单状态已变化不能处理。请刷新页面");
			}
			
			Shop shop = shopService.selectByUserID(getCurrentUser().getId());
			Map<String, Object> map = new HashMap<>();
			map.put("orderNo", orderNo);
			map.put("status", status);
			map.put("shopId", shop.getId());
			boolean isok  = orderService.shoperOperateOrder(map);
			return isok?BaseResponse.OperateSuccess:BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequestMapping(value="/operateRefundOrder",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse operateRefundOrder(String orderNo,int status,String failReason){
		try {
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				OrderRefund refund = refundService.selectOrderRefundByOrderNo(orderNo);
				if(refund.getStatus()!=0){
					return new BaseResponse(1, "不能处理该状态的订单");
				}
				if(!refund.getShopId().equals(getShopByUserID().getId())){
					return new BaseResponse(1,"非本商家订单");
				}
				refund.setFailReason(failReason);
				refund.setStatus(status);
				refund.setUpdateDate(new Date());
				return refundService.shopRefundOrder(refund);
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("commodity_order_manage")
	@RequestMapping(value="/needPresident",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse needPresident(String orderNo){
		try {
			SysOrder order = orderService.selectByOrderNo(orderNo);
			if(order.getOrderStatus()!=2){
				return new BaseResponse(1, "该状态下订单不能呼叫社长配送");
			}
			Shop shop = shopService.selectByUserID(getCurrentUser().getId());
			CommunityPresident  President =	CommunityPresidentService.selectByCommunityUserId(order.getUserId());
			List<String> ids = new ArrayList<>();
			ids.add(President.getUserId().toString());
//			rongService.sendMessage(Long.valueOf(1),ids , new TxtMessage("商家:"+shop.getShopName()+"。订单号:"+order.getOrderNo()+"需要您配送，请及时处理"));
			
			//融云发送消息
			TxtMessage message= new TxtMessage("商家:"+shop.getShopName()+"。订单号:"+order.getOrderNo()+"需要您配送，请及时处理");//文本信息
			applicationContext.publishEvent(new RongMessageEvent(Long.valueOf(1), ids, message));
			
			
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
	
	
	
}
