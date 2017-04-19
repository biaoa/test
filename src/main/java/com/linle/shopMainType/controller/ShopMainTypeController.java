package com.linle.shopMainType.controller;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.sys.ShopMainType;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.shopMainType.service.ShopMainTypeService;

@Controller
@RequestMapping("/shopMainType")
public class ShopMainTypeController extends BaseController {

	@Autowired
	private ShopMainTypeService mainTypeService;

	@RequiresPermissions("shopMainType_list")
	@RequestMapping(value = "/list")
	public String index(Integer pageNo, Model model) {
		Page<ShopMainType> page = new Page<ShopMainType>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		// 查询条件分装到这个map里面去
		try {
			mainTypeService.findAllMainType(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/shopType/mainType";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse add(ShopMainType mainType) {
		try {
			//判断分类名称是否存在
			if(mainTypeService.selectByTypeName(mainType.getTypeName())!=null){
				return new BaseResponse(1,"分类名称已存在");
			}
			if(mainType.getId()!=null){
				mainType.setUpdateDate(new Date());
				mainTypeService.updateByPrimaryKeySelective(mainType);
			}else{
				mainType.setCreateDate(new Date());
				mainTypeService.insertSelective(mainType);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
}
