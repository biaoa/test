package com.linle.mobileapi.v1.controller;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linle.common.base.BaseController;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.EvaluateHeadRequest;
import com.linle.mobileapi.v1.request.OrderEvaluateRequest;
import com.linle.mobileapi.v1.request.ShopEvaluateRequest;
import com.linle.orderEvaluate.service.OrderEvaluateService;

/**
 * 
 * @author pangd
 * @Description 订单评价接口
 */
@Controller
@RequestMapping("/api/1")
public class OrderEvaluateAPIController extends BaseController {
	
	
	@Autowired
	private OrderEvaluateService evaluateService;
	
	@ResponseBody
	@RequestMapping(value="/getEvaluateHead",method=RequestMethod.POST)
	public BaseResponse getEvaluateHead(EvaluateHeadRequest req,Errors errors,HttpServletRequest servletRequest,
            HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				return evaluateService.getEvaluateHead(req);
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.error("获得订单评价头出错"+e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
	
	//提交评价
	@ResponseBody
	@RequestMapping(value="submitOrderEvaluate",method=RequestMethod.POST)
	public BaseResponse submitOrderEvaluate(OrderEvaluateRequest req,Errors errors,HttpServletRequest servletRequest,
            HttpServletResponse servletResponse){
		try {
			System.out.println(m.writeValueAsString(req));
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				return evaluateService.submitEvaluate(req);
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.error("提交订单评价出错"+e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	//获取商铺评价详情ShopEvaluateResponse
	@ResponseBody
	@RequestMapping(value="/getShopEvaluate")
	public BaseResponse getShopEvaluate(ShopEvaluateRequest req,Errors errors,HttpServletRequest servletRequest,
            HttpServletResponse servletResponse){
		try {
			m.writeValueAsString(req);
			return evaluateService.getShopEvaluate(req);
		} catch (Exception e) {
			_logger.error("获取店家评价出错"+e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		
		
		
	}
}
