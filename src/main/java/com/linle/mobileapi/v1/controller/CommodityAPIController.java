package com.linle.mobileapi.v1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.commodity.service.CommodityService;
import com.linle.common.base.BaseController;
import com.linle.entity.sys.Commodity;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.CommodityRequest;
import com.linle.sysOrder.service.SysOrderService;

@Controller
@RequestMapping("/api/1")
public class CommodityAPIController extends BaseController {
	
	@Autowired
	private SysOrderService orderService; //订单服务类
	
	@Autowired
	private CommodityService commodityService;//商品
	//家园购买接口
	@ResponseBody
	@RequestMapping(value="/buyCommodity",method=RequestMethod.POST)
	public BaseResponse buyCommodity(CommodityRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		try {
			_logger.info("购买参数："+m.writeValueAsString(req));
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				return orderService.createCommodityOrder(req);
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.info("购买出错了");
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//商品详情(H5)
	@RequestMapping(value="/getCommodityDetail")
	public String commodityDetail(Long commodityId,Model model){
		Commodity commodity =commodityService.selectByPrimaryKey(commodityId);
		model.addAttribute("commodity", commodity);
		return "/commodity/commodityDetail_api";
	}
	//服务范围(H5)
	@RequestMapping(value="/getServerScope")
	public String getServerScope(Long commodityId,Model model){
		Commodity commodity =commodityService.selectByPrimaryKey(commodityId);
		model.addAttribute("commodity", commodity);
		return "/commodity/serverScope_api";
	}
	//服务详情(H5)
	@RequestMapping(value="/getServerDetail")
	public String getServerDetail(Long commodityId,Model model){
		Commodity commodity =commodityService.selectByPrimaryKey(commodityId);
		model.addAttribute("commodity", commodity);
		return "/commodity/serverDetail_api";
	}
}
