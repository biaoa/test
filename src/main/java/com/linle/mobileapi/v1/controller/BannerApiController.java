package com.linle.mobileapi.v1.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.linle.appBanner.service.AppBannerService;
import com.linle.common.base.BaseController;
import com.linle.entity.sys.Users;
import com.linle.housekeeperAD.service.HousekeeperadService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.BannerRequest;
import com.linle.mobileapi.v1.response.BannerResponse;

/**
 * 
 * @author pangd 首页banner图
 * @Description
 */
@Controller
@RequestMapping("/api/1")
public class BannerApiController extends BaseController {

	@Autowired
	private AppBannerService bannerService;
	
	@Autowired
	private HousekeeperadService housekeeperadService;

	@ResponseBody
	@RequestMapping(value = "getBanner", method = RequestMethod.POST)
	public BaseResponse getBanner(BannerRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		BannerResponse res = new BannerResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			Map<String, Object> map = new HashMap<>();
			if (subject.isAuthenticated()) {
				res.setCode(0);
				res.setMsg("获取成功");
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				map.put("communityId", user.getCommunity().getId());
			}
			// FIXME 这里目前获得是系统发出的，将来要改成获取系统发出的和小区发出的广告 1
			res.setData(bannerService.getBannerForAPI(map));
			return res;
		} catch (Exception e) {
			_logger.error("获得banner出错" + e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	@ResponseBody
	@RequestMapping(value = "getHousekeeperAD", method = RequestMethod.POST)
	public BaseResponse getHousekeeperAD(BannerRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		BannerResponse res = new BannerResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				res.setCode(0);
				res.setMsg("获取成功");
				Map<String, Object> map = new HashMap<>();
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				map.put("communityId", user.getCommunity().getId());
				res.setData(housekeeperadService.getHousekeeperadForAPI(map));
				return res;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.error("获得banner出错" + e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

}
