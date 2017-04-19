package com.linle.priceSetting.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.priceSetting.model.PriceSetting;
import com.linle.priceSetting.service.PriceSettingService;

/**
 * 
 * @Description: 缴费单价管理
 * @author chenkai
 *
 */
@Controller
@RequestMapping("priceSetting")
public class PriceSettingController extends BaseController{

	@Autowired
	private PriceSettingService priceSettingService;
	// 列表
	@RequiresPermissions("price_setting")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model) {
		Page<PriceSetting> page = new Page<PriceSetting>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("communityId", getCommunity().getId());
			page.setParams(params);
			priceSettingService.getAllForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/priceSetting/priceSetting_list";
	}

	@RequiresPermissions("price_setting")
	@RequestMapping(value = "/del/{id}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(@PathVariable Long id) {
		try {
			PriceSetting record=new PriceSetting();
			record.setId(id);
			record.setIsDel(1);
			priceSettingService.updateByPrimaryKeySelective(record);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("price_setting")
	@RequestMapping(value = "/doOperate",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse doOperate(PriceSetting priceSetting) {
		try {
			Community currCommunity=getCommunity();
			PriceSetting record=new PriceSetting();
//			Integer floor=null;
//			if("propertyFee".equals(priceSetting.getType())){//物业费按照楼层来计算单价
//				floor=priceSetting.getFloor();
//			}
			record=priceSettingService.selectByType(currCommunity.getId(),priceSetting.getType(),null);
			if(priceSetting.getId()==null){
				//判断数据库是否已存在该类型
				if(record!=null){
					return new BaseResponse(2, "该类型单价已存在，不需要再新增该类型的单价");
				}
				priceSetting.setCreateDate(new Date());
				priceSetting.setIsDel(0);
				priceSetting.setCommunityId(currCommunity.getId());
				priceSettingService.insertSelective(priceSetting);
			}else{
				if(record!=null&&priceSetting.getId()!=record.getId()){
					return new BaseResponse(2, "该类型单价已存在");
				}
				priceSetting.setUpdateDate(new Date());
				priceSettingService.updateByPrimaryKeySelective(priceSetting);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	//toAdd
	@RequiresPermissions("price_setting")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView toAdd(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		PriceSetting obj= new PriceSetting();
		if (id != null) {
			 obj=priceSettingService.selectByPrimaryKey(id);
		}
		model.addAttribute("priceSetting", obj);
		mv.setViewName("priceSetting/priceSetting_add");
		return mv;
	}
	
}
