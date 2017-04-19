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

import com.linle.common.base.BaseController;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.ShopTypeRequest;
import com.linle.mobileapi.v1.request.TypeSalesRequest;
import com.linle.mobileapi.v1.response.ShopTypeResponse;
import com.linle.mobileapi.v1.response.TypeSalesResponse;
import com.linle.preferentialType.service.PreferentialTypeService;
import com.linle.shopMainType.service.ShopMainTypeService;

/**
 * 
 * @author pangd
 * @Description 商铺类型相关接口
 */
@Controller
@RequestMapping("/api/1")
public class ShopTypeAPIController extends BaseController {

	@Autowired
	private ShopMainTypeService mainTypeService;

	@Autowired
	private PreferentialTypeService preferentialTypeService;

	@RequestMapping(value = "getShopType", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getShopTypeList(ShopTypeRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {

			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				ShopTypeResponse res = new ShopTypeResponse();
				Map<String, Object> map = new HashMap<>();
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());

				map.put("communityId", user.getCommunity().getId());
				res.setCode(0);
				res.setMsg("获取成功");
				res.setSortList(mainTypeService.getSortList(map));
				res.setPrivilegesList(preferentialTypeService.getAllTypeForAPI());
				return res;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("获取店铺分类信息出错：" + e);
			return BaseResponse.ServerException;
		}
	}

	// 获得更大分类下的销量
	// FIXME 现在是统计所有订单的。有时间要改成 可以按照年，月，小区来查询的（后台配置）
	@RequestMapping(value = "getTypeSales", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getTypeSales(TypeSalesRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			TypeSalesResponse res = new TypeSalesResponse();
			Subject subject = SecurityUtils.getSubject();
			Map<String, Object> map = new HashMap<>();
			if(subject.isAuthenticated()){
				//FIXME 这里暂时不加小区筛选条件了。等以后用户多了，改成区分小区的
//				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
//				map.put("communityId",user.getCommunity().getId());
			}
			res.setSortSalesList(mainTypeService.selectTypeSales(map));
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("获得各类目下的销量出现异常" + e);
			return BaseResponse.ServerException;
		}
	}
}
