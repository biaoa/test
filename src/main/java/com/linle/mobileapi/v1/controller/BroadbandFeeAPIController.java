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
import com.linle.BroadbandFee.service.BroadbandFeeService;
import com.linle.mobileapi.base.BaseMobileapiController;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.BroadbandFeeRequest;
import com.linle.mobileapi.v1.response.BroadbandFeeResponse;

/**
 * 
 * @ClassName: BroadbandFeeAPIController
 * @Description: 宽带费。有线电视费
 * @author pangd
 * @date 2016年3月24日 下午3:47:54
 *
 */
@Controller
@RequestMapping("/api/1")
public class BroadbandFeeAPIController extends BaseMobileapiController {

	@Autowired
	private BroadbandFeeService service;

	// 本月账单
	@ResponseBody
	@RequestMapping(value = "/getBroadbandFee", method = RequestMethod.POST)
	public BaseResponse getBroadbandFee(BroadbandFeeRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		BroadbandFeeResponse res = new BroadbandFeeResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				res = service.getBroadbandFeeForAPI(req);
				return res;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.error("宽带费/有线电视费用账单获取出错" + e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
}
