package com.linle.commodity.controller;

import java.io.UnsupportedEncodingException;
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

import com.linle.commodity.service.CommodityService;
import com.linle.commodityType.service.CommodityTypeService;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.sys.Commodity;
import com.linle.entity.sys.CommodityType;
import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @author pangd
 * @Description 商品信息维护
 */
@RequestMapping("/sys/commodity")
@Controller
public class CommodityController extends BaseController {
	
	@Autowired
	private CommodityService commodityService;
	
	@Autowired
	private CommodityTypeService commodityTypeService;
	
	@RequiresPermissions("commodity_manage")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Integer pageNo,Model model,String commodityName,Long typeId){
		Page<Commodity> page = new Page<Commodity>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		try {
			if(commodityName!=null && !"".equals(commodityName)){
				commodityName=new String(commodityName.getBytes("iso8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		params.put("commodityName", commodityName);
		params.put("shopID", getShopByUserID().getId());
		params.put("typeId", typeId);
		page.setParams(params);
		try {
			commodityService.findAllCommodity(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		
		List<CommodityType> typeList = commodityTypeService.getAllTypeByShopID(getShopByUserID().getId());
		model.addAttribute("typeId", typeId);
		model.addAttribute("typeList", typeList);
		model.addAttribute("commodityName", commodityName);
		model.addAttribute("pagelist", page);
		return "/commodity/commodity_list";
	}
	
	@RequiresPermissions("commodity_add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String toAdd(Model model,Long id){
		Commodity commodity = new Commodity();
		if(id!=null&&id!=0){
			commodity = commodityService.selectByPrimaryKey(id);
		}
		model.addAttribute("commodity", commodity);
		
		List<CommodityType> typeList = commodityTypeService.getAllTypeByShopID(getShopByUserID().getId());
		model.addAttribute("typeList", typeList);
		return "/commodity/commodity_add";
	}
	@RequiresPermissions("commodity_add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(Commodity commodity){
		try {
			if(commodity.getId()!=null && commodity.getId()!=0){
				commodityService.updateByPrimaryKeySelective(commodity);
				return BaseResponse.OperateSuccess;
			}
			commodity.setShopId(getShopByUserID().getId());
			commodity.setCreateDate(new Date());
			boolean isok = commodityService.insertSelective(commodity)>0;
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("商品添加出错", e);
			return BaseResponse.ServerException;
		}
	}
}	
