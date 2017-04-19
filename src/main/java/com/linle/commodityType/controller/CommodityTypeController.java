package com.linle.commodityType.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.commodityType.service.CommodityTypeService;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.sys.CommodityType;
import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @author pangd
 * @Description 商品类型管理
 */
@RequestMapping("/sys/commodityType")
@Controller
public class CommodityTypeController extends BaseController {

	@Autowired
	private CommodityTypeService commodityTypeService;

	@RequiresPermissions("commodity_type_manage")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNo, Model model) {
		Page<CommodityType> page = new Page<CommodityType>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		params.put("shopID", getShopByUserID().getId());
		page.setParams(params);
		try {
			commodityTypeService.findAllCommodityType(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/commodityType/commodityType_list";
	}

	@RequiresPermissions("commodity_type_manage")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String toAdd(Model model,Long id) {
		CommodityType commodityType = new CommodityType();
		if(id!=null){
			commodityType=commodityTypeService.selectByPrimaryKey(id);
		}
		model.addAttribute("commodityType",commodityType);
		return "/commodityType/commodityType_add";
	}

	@RequiresPermissions("commodity_type_manage")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(CommodityType commodityType) {
		try {
			//修改
			if(commodityType.getId()!=null && commodityType.getId()!=0){
				commodityTypeService.updateByPrimaryKeySelective(commodityType);
				return BaseResponse.OperateSuccess;
			}
			//新增
			commodityType.setCreateDate(new Date());
			commodityType.setShopId(getShopByUserID().getId());
			commodityType.setStatus(0);
			boolean isok = commodityTypeService.insertSelective(commodityType) > 0;
			_logger.info("新增商品分类的结果是：" + isok);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			_logger.info("新增商品出错：" + e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("commodity_type_manage")
	@RequestMapping(value = "del")
	@ResponseBody
	public BaseResponse del(long id) {
		try {
			BaseResponse res =new BaseResponse();
			res = commodityTypeService.del(id, getShopByUserID().getId());
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

}
