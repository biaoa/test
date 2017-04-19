package com.linle.shop.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.community.model.CommunityVo;
import com.linle.community.service.CommunityService;
import com.linle.communityPresident.service.CommunityPresidentService;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.CommunityPresident;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.ShopChildType;
import com.linle.entity.sys.ShopMainType;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.shop.service.ShopService;
import com.linle.shopChildType.service.ShopChildTypeService;
import com.linle.shopMainType.service.ShopMainTypeService;

@Controller
@RequestMapping("/sys/shop")
public class ShopController extends BaseController {

	@Autowired
	private ShopService shopService;

	@Autowired
	private CommunityService communityService;

	@Autowired
	private ShopMainTypeService mainService;

	@Autowired
	private ShopChildTypeService childService;

	@Autowired
	private CommunityPresidentService communityPresidentService;

	@RequiresPermissions("shop_manage")
	@RequestMapping("/list")
	public String list(Integer pageNo, String searchValue, Model model, Integer typeId, Integer shopStatus) {
		Page<Shop> page = new Page<Shop>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		// 如果不是sys 则只能查看属于自己的商铺 s
		params.put("typeId", typeId);
		params.put("shopStatus", shopStatus);
		Users user = getCurrentUser();
		if (user.getIdentity() != UserType.SYS) {
			params.put("createId", user.getId());
		}
		try {
			if (searchValue != null && !"".equals(searchValue)) {
				searchValue = new String(searchValue.getBytes("iso8859-1"), "utf-8");
				params.put("searchValue", searchValue);
			}
			page.setParams(params);
			shopService.findAllShops(page);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		List<ShopMainType> typeList = mainService.findAllMainType();
		model.addAttribute("typeId", typeId);
		model.addAttribute("typelist", typeList);
		model.addAttribute("shopStatus", shopStatus);
		model.addAttribute("pagelist", page);
		model.addAttribute("searchValue", searchValue);
		return "/shop/shop_list";
	}

	@RequiresPermissions(value = { "shop_edit", "_shop_edit" }, logical = Logical.OR)
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd(Model model, Long id) {
		Shop shop = new Shop();
		// 获得所有的店铺分类
		List<ShopMainType> mainList = mainService.findAllMainType();
		List<ShopChildType> childList = new ArrayList<>();
		if (id != null) {
			shop = shopService.selectByPrimaryKey(id);
			childList = childService.selectByMain(shop.getMainType().getId());
		} else {
			// 获得主分类下的第一个子分类
			childList = childService.selectByMain(mainList.get(0).getId());
		}
		// 获得社长所有的小区
		Users user = getCurrentUser();
		CommunityPresident president = communityPresidentService.selectByUserId(user.getId());
		// List<Community> community =
		// communityService.getCommunityByPresident(president.getId());

		List<CommunityVo> communityVo = communityService.getRegionAndCommunityListByPresident(president.getId());
		model.addAttribute("mainList", mainList);
		model.addAttribute("childList", childList);
		model.addAttribute("communityVo", communityVo);
		model.addAttribute("shop", shop);
		return "/shop/shop_add";
	}

	@RequiresPermissions(value = { "shop_edit", "_shop_edit" }, logical = Logical.OR)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(Shop shop) {
		try {

			boolean isok = false;
			if (shop.getSendPrice() == null) {
				shop.setSendPrice(new BigDecimal("0"));
			}
			if (shop.getDeliveryFee() == null) {
				shop.setDeliveryFee(new BigDecimal("0"));
			}

			if (shop.getId() != null && shop.getId() != 0) {
				shop.setUpdateDate(new Date());
				isok = shopService.updateByPrimaryKeySelective(shop) > 0;
			} else {
				shop.setCreateDate(new Date());
				shop.setCreateUser(getCurrentUser());
				isok = shopService.createShop(shop);
			}

			return isok ? BaseResponse.OperateSuccess : BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	@RequiresPermissions("_shop_edit")
	@RequestMapping("/editInfo")
	public String infoList(Model model) throws JsonProcessingException {
		// 获得社长所有的小区
		CommunityPresident president = communityPresidentService
				.selectByUserId(getShopByUserID().getCreateUser().getId());
		Shop shop = getShopByUserID();
		List<CommunityVo> communityVo = communityService.getRegionAndCommunityListByPresident(president.getId());
		// 获得所有的店铺分类
		List<ShopMainType> mainList = mainService.findAllMainType();
		// 获得主分类下的第一个子分类
		if (shop.getChildType() != null) {
			List<ShopChildType> childList = childService.selectByMain(shop.getMainType().getId());
			model.addAttribute("childList", childList);
		}
		model.addAttribute("mainList", mainList);
		model.addAttribute("communityVo", communityVo);
		model.addAttribute("shop", shop);
		System.out.println(m.writeValueAsString(getShopByUserID()));
		return "/shop/shop_add";
	}

	@RequiresPermissions(value = "shop_cut")
	@RequestMapping(value = "/settingcut", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse Settingcut(Long id, Float cut) {
		try {
			Shop shop = shopService.selectByPrimaryKey(id);
			if (shop != null) {
				shop.setCut(cut);
				shop.setUpdateDate(new Date());
				shopService.updateByPrimaryKey(shop);
				return BaseResponse.OperateSuccess;
			}
			return new BaseResponse(1, "店铺不存在");
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	@RequiresPermissions(value = "shop_preferential_cut")
	@RequestMapping(value = "/setpreferential", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse setPreferential(Long id, BigDecimal preferentialCutAmount) {
		try {
			Shop shop = shopService.selectByPrimaryKey(id);
			if (shop != null) {
				shop.setPreferentialCutAmount(preferentialCutAmount);
				shop.setUpdateDate(new Date());
				shopService.updateByPrimaryKey(shop);
				return BaseResponse.OperateSuccess;
			}
			return new BaseResponse(1, "店铺不存在");
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("抽成金额设置错误:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 设置商家提现手续费
	@RequiresPermissions("shop_withdrawalFee_setting")
	@RequestMapping(value = "/setwithdrawalFee", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse setShopWithdrawalFee(Long shopId, BigDecimal withdrawalFee) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				if (withdrawalFee != null && withdrawalFee.compareTo(BigDecimal.ZERO) >= 0
						&& withdrawalFee.compareTo(BigDecimal.ONE) <= 1) {
					Shop shop = shopService.selectByPrimaryKey(shopId);
					if (shop != null) {
						shop.setWithdrawalFee(withdrawalFee);
						shop.setUpdateDate(new Date());
						shopService.updateByPrimaryKeySelective(shop);
						return BaseResponse.OperateSuccess;
					}
					return new BaseResponse(1, "商家不存在,请刷新重试");
				}
				return new BaseResponse(1, "手续费输入错误");
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("设置商家提现手续费出错:" + e);
			return BaseResponse.ServerException;
		}
	}
}
