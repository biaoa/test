package com.linle.houseResource.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.entity.sys.HouseResource;
import com.linle.houseResource.service.HouseResourceService;

/**
 * 
* @ClassName: HouseResourceController 
* @Description: 报修记录管理
* @author pangd
* @date 2016年3月28日 上午9:48:13 
*
 */
@Controller
@RequestMapping("houseResource")
public class HouseResourceController extends BaseController{
	
	@Autowired
	private HouseResourceService houseService;
	
	//列表
	@RequiresPermissions("house_manager")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model,String single,String type,String phone,String price,String createDate) {
		Page<HouseResource> page = new Page<HouseResource>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		params.put("communityId",getCommunity().getId());
		if (StringUtil.isNotNull(single)) {
			params.put("single", single);
		}
		if (StringUtil.isNotNull(type)) {
			params.put("type", type);
		}
		if (StringUtil.isNotNull(phone)) {
			params.put("phone", phone);
		}
		if (StringUtil.isNotNull(price)) {
			params.put("price", price);
		}
		if (StringUtil.isNotNull(createDate)) {
			params.put("createDate", DateUtil.ShortStringToDate(createDate));
		}
		page.setParams(params);
		try {
			houseService.findAllHouseResource(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		
		if (!StringUtil.isNotNull(type)) {
			type="-1";
		}
		model.addAttribute("price", price);
		model.addAttribute("createDate", createDate);
		model.addAttribute("single", single);
		model.addAttribute("type", type);
		model.addAttribute("phone", phone);
		model.addAttribute("pagelist", page);
		return "/houseResouce/houseResouce_list";
	}

}
