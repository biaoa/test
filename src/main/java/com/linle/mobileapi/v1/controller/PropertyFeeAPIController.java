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

import com.linle.mobileapi.base.BaseMobileapiController;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.PropertyFeeRequest;
import com.linle.mobileapi.v1.response.PropertyFeeResponse;
import com.linle.propertyFee.service.PropertyFeeService;
/**
 * 
* @ClassName: PropertyFeeAPIController 
* @Description: 物业费APP接口
* @author pangd
* @date 2016年3月24日 下午3:47:54 
*
 */
@Controller
@RequestMapping("/api/1")
public class PropertyFeeAPIController extends BaseMobileapiController {
	
	@Autowired
	private PropertyFeeService service;

	// 本月账单
	@ResponseBody
	@RequestMapping(value = "/getPropertyFee", method = RequestMethod.POST)
	public BaseResponse getUtilities(PropertyFeeRequest req, Errors errors,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
		PropertyFeeResponse res = new PropertyFeeResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				res = service.getPropertyFeeForAPI(req);
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.info("获取账单出错" + e.getMessage());
			return BaseResponse.ServerException;
		}
		return res;
	}
}
