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
import com.linle.mobileapi.v1.request.UtilitiesRequest;
import com.linle.mobileapi.v1.response.UtilitiesResponse;
import com.linle.utilities.service.UtilitiesService;

/**
 * @Description:水电费相关接口
 **/
@Controller
@RequestMapping("/api/1")
public class UtilitiesAPIController extends BaseMobileapiController {
	
	@Autowired
	private UtilitiesService service;

	// 本月账单
	@ResponseBody
	@RequestMapping(value = "/getUtilities", method = RequestMethod.POST)
	public BaseResponse getUtilities(UtilitiesRequest req, Errors errors,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
		UtilitiesResponse res = new UtilitiesResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				res = service.getUtilitiesForAPI(req);
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
