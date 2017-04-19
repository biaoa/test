package com.linle.mobileapi.v1.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.HomeADShopVO;
import com.linle.mobileapi.v1.model.HomeBannerVO;
import com.linle.mobileapi.v1.model.ShopItem;
import com.linle.mobileapi.v1.request.HomeADRequest;
import com.linle.mobileapi.v1.request.HomeADShopRequest;
import com.linle.mobileapi.v1.request.HomeBannerRequest;
import com.linle.mobileapi.v1.response.HomeADResponse;
import com.linle.mobileapi.v1.response.HomeADShopResponse;
import com.linle.mobileapi.v1.response.HomeBannerResponse;
import com.linle.shop.service.ShopService;

@Controller
@RequestMapping("/api/1")
public class HomeADAPIController extends BaseController {

	@Autowired
	private ShopService shopService;

	// 获得热门商家
	@RequestMapping(value = "getHomeAD", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getHomeAD(HomeADRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			HomeADResponse res = new HomeADResponse();
			Subject subject = SecurityUtils.getSubject();
			Map<String, Object> map = new HashMap<>();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				map.put("communityId", user.getCommunity().getId());
			}
			map.put("year", DateUtil.getYear());
			map.put("month", DateUtil.getMonth());
			List<ShopItem> list = shopService.getHomeAD(map);
			res.setCode(0);
			res.setData(list);
			res.setMsg("获取成功");
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			_logger.error("获得家园广告出现错误:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 获得家园轮播
	@RequestMapping(value = "/getHomeBanner", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getHomeBanner(HomeBannerRequest request, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				List<HomeBannerVO> bannerList = new ArrayList<>();
				HomeBannerVO vo = new HomeBannerVO();
				vo.setImg("/upload/appBanner/20161018/20161018100229486882.png");
				vo.setUrl("/appBanner/detail/11");
				HomeBannerVO vo1 = new HomeBannerVO();
				vo1.setImg("/upload/appBanner/20161018/20161018100229486882.png");
				vo1.setUrl("/appBanner/detail/23");
				bannerList.add(vo);
				bannerList.add(vo1);
				HomeBannerResponse res = new HomeBannerResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				res.setBannerList(bannerList);
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("获得家园轮播出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 获得家园广告商家

	@RequestMapping(value = "/getHomeADShop", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getHomeADShop(HomeADShopRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				List<HomeADShopVO> shopList = new ArrayList<>();
				HomeADShopVO v0 = new HomeADShopVO();
				v0.setImg("upload/shop_logo/20161206/20161206100648800188.jpg");
				v0.setShopId((long) 3);
				HomeADShopVO v1 = new HomeADShopVO();
				v1.setImg("upload/shop_logo/20161206/20161206100953571560.jpg");
				v1.setShopId((long)5);
				HomeADShopVO v2 = new HomeADShopVO();
				v2.setImg("upload/shop_logo/20161206/20161206101143221492.jpg");
				v2.setShopId((long)6);
				shopList.add(v0);
				shopList.add(v1);
				shopList.add(v2);
				HomeADShopResponse res = new HomeADShopResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				res.setHomeADShopList(shopList);
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("获得家园广告商家出错:" + e);
			return BaseResponse.ServerException;
		}
	}
}
