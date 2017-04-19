package com.linle.orderRefund.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.sys.OrderRefund;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.orderRefund.service.OrderRefundService;
import com.linle.sysOrder.service.SysOrderService;

@Controller
@RequestMapping("/orderRefund")
public class OrderRefundController extends BaseController {
	
	@Autowired
	private OrderRefundService refundService;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private SysOrderService orderService;
	
	//获得所有的退款列表
	@RequiresPermissions("refund_order_manage")
	@RequestMapping(value="/refundOrderList")
	public String list(Integer pageNo,Model model){
		Page<OrderRefund> page = new Page<OrderRefund>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		
		Long presidentId = getCommunityPresident().getId();
		List<Community> communityList = communityService.getCommunityByPresident(presidentId);
		params.put("communityList", communityList);
		page.setParams(params);
		try {
			refundService.getRefundListByPresident(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("退款列表出错了");
		}
		model.addAttribute("pagelist", page);
		return "/refundorder/list";
	}
	
	//获得退款详情
	@RequiresPermissions("refund_order_manage")
	@RequestMapping("/detail/{orderNo}")
	public String detail(@PathVariable String orderNo,Model model){
		OrderRefund refund = refundService.selectOrderRefundByOrderNo(orderNo);
		//订单明细
		Map<String, Object> detailMap = new HashMap<>();
		detailMap.put("shopId",refund.getShopId());
		detailMap.put("orderNo", orderNo);
		model.addAttribute("orderDetail", orderService.getCommodityOrderDetail(detailMap));
		//退款信息
		model.addAttribute("refund", refundService.selectOrderRefundByOrderNo(orderNo));
		return "/refundorder/detail";
	}
	
	//社长同意/拒绝退款
	@RequestMapping(value="/operateRefundOrder",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse operateRefundOrder(String orderNo,Integer status){
		try {
			refundService.updateRefundStatus(orderNo,status);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("社长处理退款订单出现异常");
			return BaseResponse.ServerException;
		}
	}
}
