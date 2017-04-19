package com.linle.shopPreferential.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.sys.PreferentialType;
import com.linle.entity.sys.ShopPreferential;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.preferentialType.service.PreferentialTypeService;
import com.linle.shopPreferential.service.ShopPreferentialService;

/**
 * 
 * @author pangd
 * @Description 商家优惠 信息维护
 */
@Controller
@RequestMapping("/shopPreferential")
public class ShopPreferentialController extends BaseController {

	@Autowired
	private ShopPreferentialService shopPreferentialService;

	@Autowired
	private PreferentialTypeService typeService;

	@RequiresPermissions("shopPreferential_list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNo, Model model) {
		Page<ShopPreferential> page = new Page<ShopPreferential>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		params.put("shopId", getShopByUserID().getId());
		page.setParams(params);
		try {
			shopPreferentialService.findAllPreferential(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/shopPreferential/shopPreferential_list";
	}
	
	@RequiresPermissions("shopPreferential_list")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd(Model model,Long id) {
		ShopPreferential preferential = new ShopPreferential();
		if(id!=null && id!=0){
			preferential = shopPreferentialService.selectByPrimaryKey(id);
		}
		model.addAttribute("preferential", preferential);
		
		List<PreferentialType> list = typeService.getAllType();
		model.addAttribute("typeList", list);
		
		return "/shopPreferential/shopPreferential_add";
	}
	
	@RequiresPermissions("shopPreferential_list")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(ShopPreferential preferential){
		try {
			
			if(preferential.getId()!=null){
				shopPreferentialService.updateByPrimaryKeySelective(preferential);
				return BaseResponse.OperateSuccess;
			}
			
			preferential.setCreateDate(new Date());
			preferential.setShopId(getShopByUserID().getId());
			boolean isok = shopPreferentialService.insertSelective(preferential)>0;
			_logger.info("添加优惠的结果是："+isok);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("添加优惠出错："+e);
			return BaseResponse.ServerException;
		}
	}
}
