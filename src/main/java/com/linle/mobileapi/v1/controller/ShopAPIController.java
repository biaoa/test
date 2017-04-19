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

import com.linle.commodity.service.CommodityService;
import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.common.util.LimitUtil;
import com.linle.common.util.ResponseMsg;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.GetAllGoods;
import com.linle.mobileapi.v1.request.CommodityListRequest;
import com.linle.mobileapi.v1.request.SearchShopRequest;
import com.linle.mobileapi.v1.request.ShopInfoRequest;
import com.linle.mobileapi.v1.response.CommodityListResponse;
import com.linle.mobileapi.v1.response.SearchShopResponse;
import com.linle.mobileapi.v1.response.ShopInfoResponse;
import com.linle.shop.service.ShopService;

/**
 * 
 * @author pangd
 * @Description 商家相关接口
 */
@Controller
@RequestMapping("/api/1")
public class ShopAPIController extends BaseController {
	
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private CommodityService commodityService;
	
	@RequestMapping(value="searchShop",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse searchShop(SearchShopRequest req,Errors errors,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse){
		try {
			_logger.info(m.writeValueAsString(req));
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				SearchShopResponse res = new SearchShopResponse();
				Map<String, Object> map = new HashMap<>();
				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				map.put("communityId", user.getCommunity().getId());
				map.put("sortId", req.getSortId());
				map.put("childSortId", req.getChildSortId());
				map.put("type", req.getType());
				map.put("search", req.getSearch());
				map.put("year", DateUtil.getYear());
				map.put("month", DateUtil.getMonth());
				map.put("sortRules", req.getSortRules());
				res.setCode(0);
				res.setShopList(shopService.getShopListForAPI(map));
				res.setMsg("获取成功");
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("搜素店铺信息出错："+e);
			return BaseResponse.ServerException;
		}
	}
	
	//商品列表
	@RequestMapping(value="getCommodityList",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getCommodityList(CommodityListRequest req,Errors errors,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse){
		try {
			_logger.info(m.writeValueAsString(req));
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				CommodityListResponse res = new CommodityListResponse();
				Users user=getCurrentUser();
				Map<String, Object> map = new HashMap<>();
				map.put("shopId", req.getShopId());
				map.put("userId", user.getId());
				res.setCode(0);
				res.setMsg("获取成功");
				GetAllGoods goods =  commodityService.getAllCommodityForAPI(map);
				res.setAllGoods(goods);
				System.out.println(m.writeValueAsString(res));
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("搜素店铺信息出错："+e);
			return BaseResponse.ServerException;
		}
	}
	
	
	@RequestMapping(value="getShopInfo",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getShopInfo(ShopInfoRequest req,Errors errors,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse){
		try {
			_logger.info(m.writeValueAsString(req));
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				ShopInfoResponse res = new ShopInfoResponse();
				Map<String, Object> map = new HashMap<>();
				map.put("shopId", req.getShopId());
				res.setCode(0);
				res.setShopInfo(shopService.selectShopInfoAPI(map));
				res.setMsg("获取成功");
				return res;
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("搜素店铺信息出错："+e);
			return BaseResponse.ServerException;
		}
	}
	
	//获得某小区所有活动商家
	@RequestMapping(value="getAllActivityShop",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMsg getAllActivityShop(HttpServletRequest servletRequest,
            HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Map<String, Object> paramsMap=new HashMap<String, Object>();
				paramsMap.put("shoplist", shopService.getAllActivityShop(user.getCommunity().getId()));
				return new ResponseMsg(0,"获取成功",paramsMap);
			}else{
				return new ResponseMsg(1,"获取成功",null);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("getAllActivityShop："+e);
			return new ResponseMsg(1,"服务器异常",null);
		}
	}
	
}
