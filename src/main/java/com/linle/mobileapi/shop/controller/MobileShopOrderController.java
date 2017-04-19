package com.linle.mobileapi.shop.controller;

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

import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.entity.sys.OrderRefund;
import com.linle.entity.sys.Shop;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.shop.model.OrderInfoVo;
import com.linle.mobileapi.shop.request.AcceptOrderRequest;
import com.linle.mobileapi.shop.request.OrderListRequest;
import com.linle.mobileapi.shop.request.ShopRefundOrderRequest;
import com.linle.mobileapi.shop.response.OrderListResponse;
import com.linle.orderRefund.service.OrderRefundService;
import com.linle.sysOrder.service.SysOrderService;

/**
 * 
 * @author pangd
 * @Description 商家订单相关接口
 * @date 2016年7月26日下午4:51:24
 */
@Controller
@RequestMapping("/api/1/shop")
public class MobileShopOrderController extends BaseController {

	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private OrderRefundService refundService;

	// 列表
	@RequestMapping(value = "/orderList", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse shopOrderList(@Valid OrderListRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Shop shop = getShopByUserID();
				if (shop == null) {
					return new BaseResponse(1, "该商家不存在");
				}
				OrderListResponse res = new OrderListResponse();
				Map<String, Object> map = new HashMap<>();
				map.put("shopId", shop.getId());
				map.put("status", req.getOrderStatus());
				LimitUtil.limit(map, req.getPageSize(), req.getPageNumber());
				List<OrderInfoVo> list = orderService.getShopOrderListForAPI(map);
				res.setData(list);
				res.setCode(0);
				res.setMsg("获取成功");
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 接单
	@RequestMapping(value = "/acceptOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse acceptOrder(AcceptOrderRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Shop shop = getShopByUserID();
				if (shop == null) {
					return new BaseResponse(1, "该商家不存在");
				}
				return orderService.acceptOrder(req, shop.getId());
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 同意/拒绝退款
	@RequestMapping(value = "/refundOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse refundOrder(@Valid ShopRefundOrderRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Shop shop = getShopByUserID();
				if (shop == null) {
					return new BaseResponse(1, "该商家不存在");
				}
				OrderRefund refund = refundService.selectOrderRefundByOrderNo(req.getOrderNo());
				if (refund==null) {
					return new BaseResponse(1, "退款订单不存在");
				}
				refund.setFailReason(req.getFailReason());
				refund.setStatus(req.getStatus());
				refund.setUpdateDate(new Date());
				refundService.shopRefundOrder(refund);
				return BaseResponse.OperateSuccess;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

}
