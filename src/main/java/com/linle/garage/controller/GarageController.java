package com.linle.garage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.entity.sys.Garage;
import com.linle.entity.vo.SpaceOrder;
import com.linle.garage.service.GarageService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.response.ApiResponse;
import com.linle.parkingspace.service.ParkingSpaceService;

/**
 * 
* @ClassName: GarageController 
* @Description: 车库
* @author pangd
* @date 2016年4月5日 上午10:41:01 
*
 */
@Controller
@RequestMapping("/garage")
public class GarageController extends BaseController{
	
	@Autowired
	private GarageService garageService;
	
	@Autowired
	private ParkingSpaceService parkingService;
	
	@RequiresPermissions("garage_list")
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
			garageService.findAllGarage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("garageName", garageName);
		model.addAttribute("communityName", community.getName());
		model.addAttribute("pagelist", page);
		return "/garage/garage_list";
	}
	
	@ResponseBody
	@RequiresPermissions("garage_list")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ApiResponse addGarage(@Valid Garage garage,BindingResult errors){
		boolean isok = false;
		try {
			garage.setCommunityId(getCommunity().getId());
			garage.setCreateDate(new Date());
			garage.setDelFlag(0);
			isok = garageService.insertSelective(garage)>0;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", garage.getId());
		return isok?new ApiResponse(0,"添加成功",map):new ApiResponse(1,"添加失败",null);
	}
	
	
	@RequiresPermissions("garage_list")
	@RequestMapping(value = "/ordeList")
	public String ordeList(Integer pageNo, Model model,String orderNo,String orderStatus,String userNmae,String orderType,String garageId) {
		Page<SpaceOrder> page = new Page<SpaceOrder>();
		Map<String, Object> params = new HashMap<String, Object>();
		Community community =getCommunity();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		params.put("communityId",community.getId());
		params.put("orderNo", orderNo);
		params.put("orderStatus", orderStatus);
		params.put("userNmae", userNmae);
		params.put("orderType", orderType);
		params.put("garageId", garageId);
		page.setParams(params);
		try {
			garageService.findAllGarageOrder(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		
		model.addAttribute("communityId", community.getId());
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("userNmae", userNmae);
		model.addAttribute("orderType", orderType);
		model.addAttribute("garageId", garageId);
		
		model.addAttribute("class1", (orderStatus==null ||"".equals(orderStatus))?"current":"");
		model.addAttribute("class3", "0".equals(orderStatus)?"current":"");
		model.addAttribute("class4", "5".equals(orderStatus)?"current":"");
		model.addAttribute("class5", "4".equals(orderStatus)?"current":"");
		
		model.addAttribute("garageList", garageService.getGarageList(community.getId()));
		model.addAttribute("pagelist", page);
		return "/garage/garage_order_list";
	}
	
	
	@ResponseBody
	@RequiresPermissions("garage_list")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public BaseResponse del(@Valid Garage garage,BindingResult errors){
		boolean isok = false;
		try {
			garage.setCommunityId(getCommunity().getId());
			isok = garageService.del(garage);
			//隐藏车位表
			boolean parkingisok = parkingService.del(garage.getId());
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		return isok?new ApiResponse(0,"删除成功",null):new ApiResponse(1,"删除失败",null);
	}
	
}
