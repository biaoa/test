package com.linle.refundRecord.controller;

import java.util.HashMap;
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
import com.linle.entity.sys.RefundRecord;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.refundRecord.service.RefundRecordService;

/**
 * 
 * @author pangd 
 * @Description 退款相关的操作
 */
@Controller
@RequestMapping("/refundOrder")
public class RefundRecordController extends BaseController {

	@Autowired
	private RefundRecordService refundService;

	//列表
	@RequiresPermissions(value="refund_manage")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String commodityOrderList(Model model,Integer pageNo,String orderNo,String status){
		
		Page<RefundRecord> page = new Page<RefundRecord>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		params.put("orderNo", orderNo);
		params.put("status", status);
		page.setParams(params);
		try {
			refundService.selectRefundOrderList(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("退款订单列表出错");
		}
		
		model.addAttribute("pagelist", page);
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("status", status);
		return "sysorder/order_refund_record";
	}

	//退款过期，重新发起退款
	@RequiresPermissions(value="refund_manage")
	@RequestMapping(value="/resetRefund/{orderNo}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse resetRefund(@PathVariable String orderNo){
		try {
			return refundService.resetRefund(orderNo);
		} catch (Exception e) {
			return BaseResponse.ServerException;
		}
	}
	
	//确认退款
	@RequiresPermissions(value="refund_manage")
	@RequestMapping(value="/confirmRefund/{orderNo}")
	@ResponseBody
	public BaseResponse confirm(@PathVariable String orderNo){
		try {
			return refundService.confirmRefund(orderNo);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
}
