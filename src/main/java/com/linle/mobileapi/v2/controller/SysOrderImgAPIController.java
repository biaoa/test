package com.linle.mobileapi.v2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.RefundOrderRequest;
import com.linle.sysOrder.service.SysOrderService;

/**
 * 
 * @ClassName: ParkingSpaceAPIController
 * @Description: 订单
 * @author pangd
 * @date 2016年4月11日20:46:42
 *
 */
@Controller
@RequestMapping("/api/2")
public class SysOrderImgAPIController extends BaseController {

	@Autowired
	private SysOrderService orderService;

	//退单申请
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
//				Long folderId = doFile(req.getList(), servletRequest,SysConfig.ORDER_REFUND);
				return orderService.refundOrder(order,req,req.getFolderId());
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("退款出现异常。订单号为："+req.getOrderNo()+"。异常信息："+e);
			return BaseResponse.ServerException;
		}
	}
}
