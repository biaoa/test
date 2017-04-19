package com.linle.express.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.entity.sys.Garage;

@Controller
@RequestMapping("/express")
public class ExpressController extends BaseController{
	
	
	@RequiresPermissions("express_list")
	@RequestMapping(value = "/list")
	public String index(Integer pageNo, Model model,String garageName) {
		Page<Garage> page = new Page<Garage>();
		Map<String, Object> params = new HashMap<String, Object>();
		Community community =getCommunity();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		params.put("communityId",community.getId());
		params.put("garageName", garageName);
		page.setParams(params);
		try {
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("garageName", garageName);
		model.addAttribute("communityName", community.getName());
		model.addAttribute("pagelist", page);
		return "/garage/garage_list";
	}
}
